@echo off

cd /d %~dp0
set BINDIR=%cd%
cd ..
set BASEDIR=%cd%

java -cp %BASEDIR%/config;%BASEDIR%/lib/* @shutdown-class@
