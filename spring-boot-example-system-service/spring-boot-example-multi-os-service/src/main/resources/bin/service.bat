@echo off

cd /d %~dp0
set CURRENT_DIR=%cd%

if [%1] == [] (
   %CURRENT_DIR%\winsw.exe help
) else (
   %CURRENT_DIR%\winsw.exe %1
)
