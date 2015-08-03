@echo off

:: echo %CD%
:: set orgpath=%CD%

:: Change to the directory that this batch file is in 
:: NB: it must be invoked with a full path! 
for /f %%i in ("%0") do set dirpath=%%~dpi

::echo %dirpath%

:: @echo on

pushd %dirpath%

set dirpath=%CD%\..

popd

pushd %dirpath%

set dirpath=%CD%

:: @echo off

setlocal ENABLEDELAYEDEXPANSION

if defined CLASSPATH (set _CLASSPATH=%CLASSPATH%;.) else (set _CLASSPATH=.)

FOR /R .\lib %%G IN (*.jar) DO (
set _CLASSPATH=!_CLASSPATH!;%%G
echo %%G
)

echo The Classpath definition is %_CLASSPATH%

java -classpath "%_CLASSPATH%" com.mbv.airtime.launcher.ConsoleLauncher

:: @echo on

popd

