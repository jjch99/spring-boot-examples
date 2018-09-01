@echo off

cd /d %~dp0
set BINDIR=%cd%

set JARFILE=@bootstrap-jar@
echo JARFILE=%BINDIR%\%JARFILE%

cd ..
set BASEDIR=%cd%
echo BASEDIR=%BASEDIR%

set JAVA_HEAP_OPTS=-Xms256m -Xmx512m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%BASEDIR%\logs
set JAVA_GC_OPTS=-verbose:gc -Xloggc:%BASEDIR%\logs\gc.log -XX:+PrintGCDetails
set JAVA_OPTS=-server %JAVA_HEAP_OPTS% %JAVA_GC_OPTS%
set FILE_ENCODING=UTF-8

set ENV=@environment@

java %JAVA_OPTS% ^
    -cp %BASEDIR%\config;%BASEDIR% ^
    -Dfile.encoding=%FILE_ENCODING% ^
    -Dbasedir=%BASEDIR% ^
    -Dloader.path=%BASEDIR%\lib ^
    -jar %BINDIR%\%JARFILE% ^
        --spring.profiles.active=%ENV%
