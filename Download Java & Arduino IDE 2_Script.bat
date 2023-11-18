@echo off

set "jre_url=https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.7%%2B7/OpenJDK17U-jre_x64_windows_hotspot_17.0.7_7.msi"
set "jre_msifile=%~dp0OpenJDK17U-jre_x64_windows_hotspot_17.0.7_7.msi"

set "ide_url=https://downloads.arduino.cc/arduino-ide/arduino-ide_2.2.1_Windows_64bit.msi"
set "ide_msifile=%~dp0arduino-ide_2.2.1_Windows_64

set /p choice=Do you want to download Java JRE? (1 for Yes, 2 for No)

if "%choice%"=="1" (

      echo Downloading JRE...
      curl -L -o "%jre_msifile%" "%jre_url%"

	echo Installing JRE...
	msiexec /i "%jre_msifile%"

	echo Deleting JRE file...
	del "%jre_msifile%"

	echo Java JRE installation complete.

) else (
    echo Skipping Java JRE download and installation.
)

set /p choice=Do you want to download Arduino IDE? (1 for Yes, 2 for No)

if "%choice%"=="1" (

	echo Downloading Arduino IDE...
	curl -L -o "%ide_msifile%" "%ide_url%"

	echo Installing Arduino IDE...
	msiexec /i "%ide_msifile%"

	echo Deleting Arduino IDE file...
	del "%ide_msifile%"

) else (
    echo Skipping Arduino IDE download and installation.
)

pause
