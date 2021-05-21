# Ikon
[![logo](https://raw.githubusercontent.com/Jhyub/Ikon/master/ikon.png)](https://ikon.jhseo.dev/)
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
- [ ] Full X11 support on GNU/Linux  
- [ ] Full X11 support on BSD-like operating systems  
- [ ] Full wayland support  
