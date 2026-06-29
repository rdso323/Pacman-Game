@echo off
setlocal enabledelayedexpansion

REM Build Pac-Man Dragon Edition for Windows using jlink + jpackage.
REM Usage: scripts\build.bat [variant] [console]
REM   scripts\build.bat              -> baseline build
REM   scripts\build.bat improved     -> improved build
REM   scripts\build.bat baseline console -> keep a console window for debugging

set "VARIANT=%~1"
if "%VARIANT%"=="" set "VARIANT=baseline"
set "CONSOLE_FLAG=%~2"

call "%~dp0paths.bat" %VARIANT%

if "%JAVA_HOME%"=="" (
  echo ERROR: Set JAVA_HOME to your JDK folder, e.g.:
  echo   set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.8
  exit /b 1
)

if not exist "%JAVA_HOME%\bin\jpackage.exe" (
  echo ERROR: jpackage not found in %JAVA_HOME%\bin
  exit /b 1
)

if "%JAVAFX_HOME%"=="" (
  echo ERROR: Set JAVAFX_HOME to your JavaFX SDK folder, e.g.:
  echo   set JAVAFX_HOME=C:\javafx-sdk-21.0.8
  exit /b 1
)

REM jmods produce the most reliable JavaFX runtime for jpackage.
set "JFX_MODULE_PATH="
if defined JAVAFX_JMODS if exist "%JAVAFX_JMODS%\javafx.controls.jmod" set "JFX_MODULE_PATH=%JAVAFX_JMODS%"
if not defined JFX_MODULE_PATH if exist "%JAVAFX_HOME%\jmods\javafx.controls.jmod" set "JFX_MODULE_PATH=%JAVAFX_HOME%\jmods"
if not defined JFX_MODULE_PATH if exist "%JAVAFX_HOME%\lib\javafx.controls.jmod" set "JFX_MODULE_PATH=%JAVAFX_HOME%\lib"
if not defined JFX_MODULE_PATH (
  echo ERROR: JavaFX jmods are required for a working Windows executable.
  echo.
  echo Download "JavaFX Windows x64 SDK jmods" from:
  echo   https://gluonhq.com/products/javafx/
  echo.
  echo Extract and set:
  echo   set JAVAFX_JMODS=C:\javafx-jmods-21.0.8
  echo.
  echo Use the same major version as your JDK ^(21 with 21, 26 with 26^).
  exit /b 1
)

set "BUILD_DIR=%ROOT%\build"
set "RUNTIME_DIR=%BUILD_DIR%\runtime"
set "JPACKAGE_TEMP=%TEMP%\pacman-jpackage-%VARIANT%"
set "JPACKAGE_ARGS="
set "PATH=%JAVA_HOME%\bin;%PATH%"

if /I "%CONSOLE_FLAG%"=="console" set "JPACKAGE_ARGS=--win-console"

echo Building %APP_NAME%
echo   Project:  %ROOT%
echo   Install:  %APP_DIR%
echo   JDK:      %JAVA_HOME%
echo   JavaFX:   %JFX_MODULE_PATH%
echo.

if exist "%BUILD_DIR%\classes" rmdir /s /q "%BUILD_DIR%\classes"
if exist "%BUILD_DIR%\pacman.jar" del /q "%BUILD_DIR%\pacman.jar"
if exist "%BUILD_DIR%\sources.txt" del /q "%BUILD_DIR%\sources.txt"
if exist "%RUNTIME_DIR%" rmdir /s /q "%RUNTIME_DIR%"
if exist "%JPACKAGE_TEMP%" rmdir /s /q "%JPACKAGE_TEMP%"
if exist "%APP_DIR%" rmdir /s /q "%APP_DIR%"
mkdir "%BUILD_DIR%\classes" 2>nul
mkdir "%DIST_VARIANT%" 2>nul
mkdir "%PROJECT_DIST%" 2>nul

cd /d "%ROOT%"

echo [1/5] Compiling...
powershell -NoProfile -Command ^
  "Get-ChildItem -Path 'PACMAN','src' -Recurse -Filter '*.java' -File | ForEach-Object { $_.FullName.Substring((Get-Location).Path.Length + 1) } | Set-Content -LiteralPath '%BUILD_DIR%\sources.txt' -Encoding Ascii"
if errorlevel 1 exit /b 1

"%JAVA_HOME%\bin\javac.exe" --module-path "%JFX_MODULE_PATH%" ^
  --add-modules javafx.controls,javafx.media,javafx.fxml,javafx.graphics,javafx.base ^
  -d "%BUILD_DIR%\classes" ^
  @"%BUILD_DIR%\sources.txt"
if errorlevel 1 (
  echo ERROR: Compilation failed.
  exit /b 1
)

echo [2/5] Creating pacman.jar...
(
  echo Main-Class: PACMAN.Launcher
  echo.
) > "%BUILD_DIR%\manifest.mf"
"%JAVA_HOME%\bin\jar.exe" --create --file "%BUILD_DIR%\pacman.jar" --manifest "%BUILD_DIR%\manifest.mf" -C "%BUILD_DIR%\classes" .
if errorlevel 1 (
  echo ERROR: Failed to create pacman.jar.
  exit /b 1
)

echo [3/5] Creating custom runtime with jlink...
"%JAVA_HOME%\bin\jlink.exe" --module-path "%JAVA_HOME%\jmods;%JFX_MODULE_PATH%" ^
  --add-modules javafx.controls,javafx.fxml,javafx.media,javafx.graphics,javafx.base,java.desktop,jdk.unsupported ^
  --output "%RUNTIME_DIR%" ^
  --strip-native-commands false
if errorlevel 1 (
  echo ERROR: jlink failed.
  exit /b 1
)

echo [4/5] Running jpackage...
"%JAVA_HOME%\bin\jpackage.exe" --name "%APP_NAME%" ^
  --input "%BUILD_DIR%" ^
  --main-jar pacman.jar ^
  --main-class PACMAN.Launcher ^
  --runtime-image "%RUNTIME_DIR%" ^
  --type app-image ^
  --dest "%DIST_VARIANT%" ^
  --temp "%JPACKAGE_TEMP%" ^
  %JPACKAGE_ARGS%
if errorlevel 1 (
  echo ERROR: jpackage failed.
  exit /b 1
)

if not exist "%EXE%" (
  echo ERROR: Executable was not created:
  echo   %EXE%
  dir /s /b "%DIST_VARIANT%" 2>nul
  exit /b 1
)

echo [5/5] Copying game assets...
xcopy /E /I /Y "%ROOT%\Resources" "%APP_DIR%\Resources" >nul
copy /Y "%ROOT%\highScore.txt" "%APP_DIR%\highScore.txt" >nul
> "%PROJECT_DIST%\install-location.txt" echo %APP_DIR%

echo.
echo Build successful!
echo   %EXE%
echo.
echo Run:
echo   scripts\launch.bat %VARIANT%
echo.
echo If the .exe shows "Failed to launch JVM", run:
echo   scripts\diagnose.bat %VARIANT%
echo.

endlocal
