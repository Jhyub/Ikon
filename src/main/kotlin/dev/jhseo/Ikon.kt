package dev.jhseo

import java.io.File

object Ikon {
    private val iconThemes: MutableMap<String, MutableList<File>> = mutableMapOf()
    private val individualIcons: MutableMap<String, File> = mutableMapOf()

    private val home: File? = System.getenv("HOME")?.let { File(it) }
    private val xdgDataHome: File? = System.getenv("XDG_DATA_HOME")?.let { File(it) }
        ?: home?.resolve(".local/share")
    private val xdgDataDirs: List<File> = (System.getenv("XDG_DATA_DIRS") ?: "/usr/local/share/:/usr/share/")
        .split(":")
        .map { File(it) }
    private val xdgConfigHome: File? = System.getenv("XDG_CONFIG_HOME")?.let { File(it) }
        ?: home?.resolve(".config")
    private val xdgConfigDirs: List<File> = (System.getenv("XDG_CONFIG_DIRS") ?: "/etc/xdg")
        .split(":")
        .map { File(it) }

    init {
        // By default, apps should look in $HOME/.icons (for backwards compatibility),
        // in $XDG_DATA_DIRS/icons and in /usr/share/pixmaps (in that order).
        // - freedesktop.org icon theme specifications version 0.13
        listOfNotNull(
            home?.resolve(".icons"),
            xdgDataHome?.resolve("icons"),
            *xdgDataDirs.map { it.resolve("icons") }.toTypedArray(),
            File("/usr/share/pixmaps"),
        ).forEach {
            if (it.exists())
                it.listFiles()?.forEach { file ->
                    if (file.exists() && file.isDirectory)
                        if (iconThemes[file.name] != null)
                            iconThemes[file.name]?.add(file)
                        else
                            iconThemes[file.name] = mutableListOf(file)
                    // Icons are searched in order of from override to overridden,
                    // so latter files shouldn't replace the former files.
                    if (file.exists() && file.isFile && !individualIcons.containsKey(file.nameWithoutExtension))
                        individualIcons[file.nameWithoutExtension] = file
                }
        }
    }

    private fun parseIni(file: File, field: String, flagName: String?): String? {
        if (!file.exists()) return null

        var flag = flagName.isNullOrEmpty()
        var returnVal: String? = null
        file.forEachLine {
            if (it.contains("[$flagName]")) flag = true
            if (flag && it.contains(field)) {
                returnVal = it.split("=").last().replace("\\s".toRegex(), "")
                return@forEachLine
            }
        }
        return returnVal
    }

    private fun gtkThemeName(): String? {
        val gtk4 = xdgConfigHome?.resolve("gtk-4.0")?.resolve("settings.ini")
        val gtk3 = xdgConfigHome?.resolve("gtk-3.0")?.resolve("settings.ini")

        return when {
            gtk4?.exists() == true -> parseIni(gtk4, "gtk-icon-theme-name", "Settings")
            gtk3?.exists() == true -> parseIni(gtk3, "gtk-icon-theme-name", "Settings")
            else -> null
        }
    }

    private fun plasmaThemeName(): String? =
        xdgConfigHome?.let { parseIni(it.resolve("kdeglobals"), "Theme", "Icons") }

    fun themeName(): String? =
        gtkThemeName() ?: plasmaThemeName()

    private fun themeNameWithDepends(name: String): List<String>? {
        val themeFiles = iconThemes[name]
        if (themeFiles.isNullOrEmpty()) return null

        val themeNames: MutableList<String> = mutableListOf(name)
        themeFiles.forEach {
            val index = it.resolve("index.theme")
            parseIni(index, "Inherits", "Icon Theme")?.also {
                it.split(",").forEach {
                    if (!themeNames.contains(it)) themeNames.add(it)
                }
            }
        }

        return themeNames
    }

    private fun fetch(name: String, theme: File): File? {
        val candidates: MutableList<File> = mutableListOf()
        theme.listFiles()?.forEach {
            if (it.isFile && it.nameWithoutExtension == name) {
                candidates.add(it)
            } else if (it.isDirectory) {
                fetch(name, it)?.let { candidates.add(it) }
            }
        }
        candidates.sortBy { it.length() }
        return if (candidates.isEmpty()) null else candidates[0]
    }

    fun fetch(name: String, themeName: String): File? {
        iconThemes[themeName]?.forEach {
            fetch(name, it)?.also { return it }
        }
        return null
    }

    fun fetch(name: String): File? {
        themeName()?.also {
            themeNameWithDepends(it)?.forEach {
                fetch(name, it)?.also {
                    if (it.exists()) return it
                }
            }
        }
        return fetch(name, "hicolor") ?: individualIcons[name]
    }
}
