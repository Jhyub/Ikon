package dev.jhseo

import java.io.File

object Icon {
    private val iconThemes: MutableMap<String, MutableList<File>> = mutableMapOf()
    private val individualIcons: MutableMap<String, File> = mutableMapOf()

    init {
        if(System.getenv("HOME").isNullOrBlank())
            throw Exception("Environment variable HOME is null or blank.")

        val home = File(System.getenv("HOME"))
        val xdgDataHome = if (System.getenv("XDG_DATA_HOME").isNullOrBlank()) {
            home.resolve(".local/share")
        } else {
            File(System.getenv("XDG_DATA_HOME"))
        }
        val xdgDataDirs: List<File> = (System.getenv("XDG_DATA_DIRS") ?: "/usr/local/share/:/usr/share/")
            .split(":")
            .map { File(it) }

        // By default, apps should look in $HOME/.icons (for backwards compatibility),
        // in $XDG_DATA_DIRS/icons and in /usr/share/pixmaps (in that order).
        // - freedesktop.org icon theme specifications version 0.13
        listOf(
            home.resolve(".icons"),
            xdgDataHome.resolve("icons"),
            *xdgDataDirs.map { it.resolve("icons") }.toTypedArray(),
            File("/usr/share/pixmaps"),
        ).forEach {
            if (it.exists())
                it.listFiles()?.forEach { it ->
                    if (it.exists() && it.isDirectory)
                        if (iconThemes[it.name] != null)
                            iconThemes[it.name]?.add(it)
                        else
                            iconThemes[it.name] = mutableListOf(it)
                    // Icons are searched in order from override to overridden,
                    // so latter files shouldn't replace the former files.
                    if (it.exists() && it.isFile && !individualIcons.containsKey(it.nameWithoutExtension))
                        individualIcons[it.nameWithoutExtension] = it
                }
        }

        // If no icons were found during the process,
        // we would expect that the code is not running on a freedesktop environment.
        if(iconThemes.isEmpty() && individualIcons.isEmpty())
            throw Exception("KIcon is not running on a freedesktop environment.")
    }

    private fun currentIconTheme(): MutableList<File> {
        TODO()
    }

    private fun currentIconThemeWithDepends(): MutableList<File> {
        TODO()
    }

    fun fetch(name: String, themeName: String): File? {
        TODO()
    }

    fun fetch(name: String): File? {
        TODO()
    }
}