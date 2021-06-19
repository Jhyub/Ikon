# Ikon

<iframe width="560" height="315" src="https://www.youtube-nocookie.com/embed/7P4dwvTumbc" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

[![build Actions Status](https://github.com/Jhyub/Ikon/actions/workflows/build.yml/badge.svg)](https://github.com/Jhyub/Ikon/actions/workflows/build.yml?query=branch:master)<br>
Ikon is a Kotlin/JVM library that fetches icons on a freedesktop environment.  
Ikon follows the freedesktop.org specifications for [Icon Themes](https://specifications.freedesktop.org/icon-theme-spec/icon-theme-spec-latest.html) and [XDG Base Directories](https://specifications.freedesktop.org/basedir-spec/basedir-spec-latest.html).


# Usage
```kotlin
import java.io.File
import dev.jhseo.Ikon

val fileManagerIcon: File? = Ikon.fetch("system-file-manager")
val hicolorSpotifyIcon: File? = Ikon.fetch("spotify", "hicolor")
```

# TODO
- [ ] Full X11 support on Linux
- [ ] Full Wayland support
- [ ] Full support on *nix systems(*BSD, Solaris, etc)
