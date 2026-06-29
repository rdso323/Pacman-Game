@echo off
setlocal

REM Run the packaged game with the bundled JVM and a visible console.
REM This bypasses the native .exe launcher and shows the real error message.
REM Usage: scripts\diagnose.bat [variant]

set "VARIANT=%~1"
if "%VARIANT%"=="" set "VARIANT=baseline"

call "%~dp0paths.bat" %VARIANT%

set "JAVA_EXE=%APP_DIR%\runtime\bin\java.exe"
set "JAR=%APP_DIR%\app\pacman.jar"

if not exist "%JAVA_EXE%" set "JAVA_EXE=%APP_DIR%\runtime\bin\java"
if not exist "%JAR%" set "JAR=%APP_DIR%\pacman.jar"

if not exist "%JAVA_EXE%" (
  echo ERROR: Bundled Java runtime not found under:
  echo   %APP_DIR%\runtime\bin
  echo.
  echo Rebuild first:
  echo   scripts\build.bat %VARIANT%
  exit /b 1
)

if not exist "%JAR%" (
  echo ERROR: Game jar not found under:
  echo   %APP_DIR%
  dir /s /b "%APP_DIR%\*.jar" 2>nul
  exit /b 1
)

echo Running with bundled Java...
echo   Java: %JAVA_EXE%
echo   Jar:  %JAR%
echo   Dir:  %APP_DIR%
echo.

cd /d "%APP_DIR%"
"%JAVA_EXE%" -Duser.dir="%APP_DIR%" -cp "%JAR%" PACMAN.Launcher

echo.
echo Exit code: %ERRORLEVEL%
pause

endlocal
