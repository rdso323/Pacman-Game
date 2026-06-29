# Building Pac-Man Dragon Edition Executables

## Quick start (Windows)

```cmd
git pull origin master

set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.8
set JAVAFX_HOME=C:\javafx-sdk-21.0.8
set JAVAFX_JMODS=C:\javafx-jmods-21.0.8

scripts\build.bat
scripts\launch.bat
```

**Important:** You need **both** the JavaFX SDK **and** the JavaFX **jmods** download (same version as your JDK). The jmods package is what makes the Windows `.exe` work reliably.

Download from [Gluon JavaFX](https://gluonhq.com/products/javafx/):
- JavaFX SDK (Windows x64)
- JavaFX jmods (Windows x64)

## If you see "Failed to launch JVM"

This usually means the app crashed immediately on startup (not that Java is missing). Run:

```cmd
scripts\diagnose.bat
```

That runs the game with a console window and shows the real error.

To rebuild with a console attached to the `.exe`:

```cmd
scripts\build.bat baseline console
scripts\launch.bat
```

## Install location

The packaged game is installed here (outside your OneDrive project folder):

```text
%LOCALAPPDATA%\PacManDragon\baseline\PacManDragon-baseline\
```

Your git project folder only contains source code and build scripts. The `.exe` lives in AppData.

## Variants

| Command | Output |
|---------|--------|
| `scripts\build.bat` | `PacManDragon-baseline` |
| `scripts\build.bat improved` | `PacManDragon-improved` |

Compare baseline vs improved by building both variants and running them side-by-side.

## Linux

```bash
sudo apt install openjfx openjdk-21-jdk
./scripts/build.sh
./scripts/build.sh baseline
dist/baseline/run.sh
```

## Notes

- `Resources/` and `highScore.txt` are copied next to the executable automatically.
- Match JavaFX version to JDK version (21 with 21, 26 with 26+).
- If OneDrive paths cause trouble, clone to `C:\Dev\Pacman-Game` and build there.
