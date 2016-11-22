@echo off
echo ************************************************************************
echo ** Bereinigung...                                                     **
echo ************************************************************************
@echo on
set mavenrepopath=C:\development\Tools\mavenRepo
@echo off
set /p removeaction="Sollen im aktuellen Workspace nach dem bereits vorhandenen Projekt 'keks' und dem Template-Archetype in C:\development\Tools\mavenRepo (ggf. Pfad anpassen!) gesucht und diese geloescht werden? (J/N)"

if /i %removeaction%==J (
	cd /d %~dp0
	cd ..
	RMDIR /S keks
	
	cd %mavenrepopath%\de\swm\pms
	RMDIR /S template
)

@echo off
echo ************************************************************************
echo ** Archetype wird erzeugt und dem lokalen Verzeichnis hinzugefuegt.   **
echo ** Bitte hierfuer beachten: Wenn das Archetype bereits besteht und    **
echo ** ein neu erstellter Archetype dieses ersetzen soll, kann es zu      **
echo ** Fehlern in dem Archetype kommen. Bei einem Fehlverhalten bei dem   **
echo ** erzeugen neuer Projekte von dem ersetzten Archetype, das Archetype **
echo ** vor dem Generieren aus dem lokalen Repository entfernen!           **
echo ************************************************************************

@echo on
cd /d %~dp0

call mvn clean archetype:create-from-project -Darchetype.properties=archetype.properties
cd target/generated-sources/archetype
call mvn install


@echo off
echo ************************************************************************
echo ** Erzeugt einen neuen, lokalen Archetype-Katalog                     **
echo ************************************************************************
@echo on
call mvn archetype:crawl


@echo off
echo ************************************************************************
echo ** Ein Beispielprojekt erzeugen und bauen?                            **
echo ************************************************************************
set /p version="Welche Template-Version (des Archetypes) verwenden? "
REM %version%
@echo on

cd ../../../..
call mvn archetype:generate -DgroupId=de.swm.pms.dose -DartifactId=keks -DArtifactUppercase=Keks -DBaseressourcesPath=de.swm.pms.rest.baseressources -DinteractiveMode=false -DarchetypeArtifactId=servicetemplate-archetype -DarchetypeVersion=%version% -DarchetypeGroupId=de.swm.pms.servicetemplate


@echo off
echo ************************************************************************
echo ** Baue das Projekt                                                   **
echo ************************************************************************
@echo on
cd keks
call mvn clean install


@echo off
echo ************************************************************************
echo ** Ende                                                               **
echo ************************************************************************
PAUSE