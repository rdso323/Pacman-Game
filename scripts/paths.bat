@echo off
REM Shared install paths for Windows build/launch scripts.
REM The packaged app is stored under %%LOCALAPPDATA%% to avoid OneDrive/spaces issues.

set "VARIANT=%~1"
if "%VARIANT%"=="" set "VARIANT=baseline"

for %%I in ("%~dp0..") do set "ROOT=%%~fI"
set "APP_NAME=PacManDragon-%VARIANT%"
set "DIST_VARIANT=%LOCALAPPDATA%\PacManDragon\%VARIANT%"
set "APP_DIR=%DIST_VARIANT%\%APP_NAME%"
set "EXE=%APP_DIR%\%APP_NAME%.exe"
set "PROJECT_DIST=%ROOT%\dist\%VARIANT%"
