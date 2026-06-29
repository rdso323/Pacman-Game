@echo off
setlocal

REM Launch the packaged game.
REM Usage: scripts\launch.bat [variant]

set "VARIANT=%~1"
if "%VARIANT%"=="" set "VARIANT=baseline"

call "%~dp0paths.bat" %VARIANT%

if not exist "%EXE%" (
  echo.
  echo ERROR: Game executable not found:
  echo   %EXE%
  echo.
  echo Build first:
  echo   scripts\build.bat %VARIANT%
  echo.
  exit /b 1
)

cd /d "%APP_DIR%"
start "" "%EXE%"

endlocal
