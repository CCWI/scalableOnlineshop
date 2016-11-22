![Logo](./img/header_light.png)

# Prototyp eines skalierbaren Shopsystems - Am Beispiel von Microservices  
<a href="https://scan.coverity.com/projects/codemax-scalable-shopsystem">
  <img alt="Coverity Scan Build Status"
       src="https://scan.coverity.com/projects/9743/badge.svg"/>
</a>
[![Build Status](https://travis-ci.org/CodeMax/Scalable-Shopsystem.svg?branch=master)](https://travis-ci.org/CodeMax/Scalable-Shopsystem)
[![devDependencies Status](https://david-dm.org/bower/bower/dev-status.svg)](https://david-dm.org/bower/bower?type=dev)

## Problemstellung
Im Zuge der Forderung nach Skalierbarkeit für Webanwendungen wie dem Onlineshop, gilt meist die individuelle Analyse, welche Architekturentscheidungen und verfügbaren Mittel den Anforderungen am geeignetsten entgegnen. Außerdem gilt hierbei, dass Skalierbarkeit die Komplexität des Systems in den meisten Fällen erhöht und Probleme entstehen können, die mit einer anderen Architektur ggf. nicht einher gehen. Die Kenntnis über entsprechende Probleme und mögliche Fallstricke fehlen häuﬁg noch in den Unternehmen, um eine entsprechende Architektur zu konzipieren bzw. diese auch umzusetzen. Es gilt daher mögliche Fallstricke aufzudecken und entsprechende Maßnahmen zu ergreifen.

## Zielsetzung
Im Fokus der Arbeit steht die technische Konzeption und die hierauf aufbauende Implementierung eines prototypischen, hoch skalierbaren Shopsystems. Es wird daher schrittweise ein technisches Konzept erarbeitet, in dem Architektur und entsprechende Entwurfsentscheidungen erläutert sind. Im Zuge der Implementierungen ist zunächst ein generischer Microservice zu konzeptionieren und zu entwickeln, der die Grundlage für die in den Prototypen verwendeten Services darstellt und darüber hinaus auch in Zukunft für die Erweiterung des Systems durch zusätzliche Services als Template verwendet werden kann. Im Anschluss an das Prototyping gilt es, die verwendete Architektur mit der eines typischen, nicht skalierenden Shopsystems zu vergleichen.

## Getting started
1. Check out Services
2. Docker installieren (Unter Windows getestet)
3. Backendservices in Container laufen lassen (siehe Dokumentation für Details) <br>
3.1. mvn clean package <br>
3.2. Verzeichnis nach <service>-build/target wechseln, um das gebaute Fat-Jar in einen Container zu setzen. <br>
3.3. Service in einen Container legen. <br>
    ```
    docker build -t <specific_servicename_of_Dockerfile>
    ```
    <br>
3.4. Container starten. <br>
    ```
    docker run -p <port>:<port> -t <specific_servicename_of_Dockerfile>
    ```
    <br>
4.5. Container des Artikel-Services starten. <br>
    ```
    docker run -p <port>:<port> --link <userservice-port>:<userservice-port> --dns=8.8.8.8 --dns=8.8.4.4 -t article
    ```
4. Installiere <a href="https://nodejs.org/en/download/">NodeJS</a>
5. Installiere [npm](https://www.npmjs.com/) module 
    ```
    (Die Abhängigkeiten werden unter dem Verzeichnis: '/storefront/node_modules/..' erwartet)
    ```
6. Mittels gulp den Storefront bauen, eine Live-Show öffnet sich mittels Browser-Sync.

## Entwicklung weiterer Services
In dem Repo unter /template liegt ein Maven-Archetyp bereit, das für das Generieren neuer Services genutzt werden kann.

## Mehr Infos / Dokumentation
Zusätzliche Informationen zu Entwurfsentscheidungen, der Architektur und der Umsetzung sind in dem Verzeichnis /dokumentation abgelegt.

## Lizenzierung
Code und Dokumentation sind frei unter der Apache-Lizenz Version 2.0 verfügbar.