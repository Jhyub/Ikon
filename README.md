# Ikon
[![Build Actions Status](https://github.com/Jhyub/Ikon/actions/workflows/build.yml/badge.svg)](https://github.com/Jhyub/Ikon/actions/workflows/build.yml?query=branch:master) [![Maven Central Version](https://img.shields.io/maven-central/v/dev.jhseo/ikon)](https://github.com/Jhyub/Ikon/releases)<br>
Ikon is a Kotlin/JVM library that fetches icons on a freedesktop environment.  
Ikon follows the freedesktop.org specifications for [Icon Themes](https://specifications.freedesktop.org/icon-theme-spec/icon-theme-spec-latest.html) and [XDG Base Directories](https://specifications.freedesktop.org/basedir-spec/basedir-spec-latest.html).  
[Sample Usage Video](https://youtu.be/7P4dwvTumbc)

# Usage
```kotlin
import java.io.File
import dev.jhseo.Ikon

val fileManagerIcon: File? = Ikon.fetch("system-file-manager")
val hicolorSpotifyIcon: File? = Ikon.fetch("spotify", "hicolor")
val themeName: String? = Ikon.themeName()
```
