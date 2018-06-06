# OOP2 Programmierprojekt 2Ibb/2iCbb FS18

## Bearbeitet von
 - Roger Kreienbühl
 - Florian Thiévent
 
## CUIE Komponente
- Daniel Yarza Navarro
- Arbnor Aliaj

## Abgabe
- Mittwoch, 6.6.2018, 18:00 Uhr
- Die Abgabe erfolgt durch ein "Push" auf den Master-Branch in Ihrem GitHub-Repository.

## Aufgabe: HydroPowerFX

Implementieren Sie eine Applikation auf Basis JavaFX gemäss der Aufgabenbeschreibung auf dem AD. 

Die dort vorhandene Beschreibung ist massgebend, hier die wichtigsten Punkte:
 - Benutzen Sie zur Umsetzung die vorgegebene Struktur des "Application-Template".
 - Die Verwendung von SceneBuilder und FXML ist nicht erlaubt.
 - Für eine 4.0 müssen folgende Basis-Features implementiert sein
   - Einlesen der Daten
   - Abspeichern der Änderungen
   - Darstellen aller Wasserkraftwerke in Tabelle/Liste 
   - Editor-Bereich
     - Editor-Bereich arbeitet stets auf dem in der Tabelle selektierten Wasserkraftwerk
     - Änderungen führen zu *unmittelbarem* Update der Tabelle und des Headers
     - Änderung von ‘Leistung (MW)’ sowie 'Kanton' führt zu einem Update des Footer-Bereichs
   - Header-Bereich 
     - Darstellung Name, Standort, Kanton, maximaler Leistung, Jahr der Inbetriebnahme
     - einfaches Styling via CSS
   - Footer-Bereich
     - Informationen zu den einzelnen Kantonen (Name des Kantons, Gesamtleistung aller Kraftwerke, Anzahl Kraftwerke)
     - diese Informationen werden immer aktuell gehalten
   - Layout mit SplitPane
   - sinnvolles Resizing-Verhalten
   - Anlegen eines neuen Wasserkraftwerks mit Update des Footer-Bereichs
   - Löschen bestehender Einträge mit Update des Footer-Bereichs
   - Programmstruktur
     - zwei Layer für Presentation-Model und View 


## Bitte beachten Sie:
 - Die Bewertungskriterien sind ebenfalls in dem pdf-File auf dem AD enthalten.
   - die dort definierten Bewertungskriterien sind massgebend
   - nicht compilierbarer Code wird mit einer 1.0 gewertet
   - snicht selbstständig bearbeitete Projekte führen zu einem deutlichen Notenabzug
   
 - Im Projekt enthalten sind zwei Daten-Files
   - `HYDRO_POWERSTATION.csv` enthält die anzuzeigenden Daten der Wasserkraftwerke
   - `cantons.csv` enthält Informationen über die Schweizer Kantone. Diese Daten sind für die Umsetzung der Basis-Features nicht unbedingt notwendig.
 
 - Das Programmierprojekt kann auch in einem 2er-Team bearbeitet werden. 
 
 - Falls Sie zu zweit arbeiten:
   - tragen Sie beide Namen unter "Bearbeitet von" ein
   - arbeiten Sie ausschliesslich in einem Repository
   - falls sie beide Zugang zu diesem Repository wollen: tragen Sie die zweite Person als "Collaborator" ein (auf GitHub unter "Settings - > Collaborators & teams")
   - löschen Sie das nicht benötigte Repository (auf GitHub unter "Settings")
   - arbeiten Sie gemeinsam und gleichzeitig an den Aufgaben (Stichwort: Pair-Programming)
   - das Aufteilen und separate Bearbeiten von Aufgaben ist nicht erwünscht
 
 - Ausdrücklich erlaubt und erwünscht ist, dass Sie sich gegebenenfalls Hilfe holen.
   - Das Programmierzentrum ist geöffnet und Nachfragen werden zum Beispiel über den im Repository integrierten 
 Issue Tracker oder per Mail gerne beantwortet. 
 
 - Nutzen Sie die Coding-Night am Montag, 28.5.2018, 18:00 Uhr. 


## Bei Problemen mit dem IntelliJ-Setup
Es kommt immer wieder mal vor, dass der Setup des IntelliJ-Projekts nicht auf Anhieb funktioniert oder "plötzlich"
nicht mehr funktioniert.

Sie brauchen in so einem Fall NICHT nochmal den Invitation-Link annehmen oder das Projekt via “Check out from Version Control” nochmal anlegen.

Statt dessen ist es am besten den IntelliJ-Setup neu generieren zu lassen. Dazu verwendet man den File "build.gradle", der eine 
komplette und IDE-unabhängige Projektbeschreibung enthält.

Die einzelnen Schritte:

- Schliessen Sie alle geöffneten Projekte (File -> Close Project)

- Wählen Sie “OPEN” 

- Es erscheint ein Finder-Fenster mit dem Sie zu ihrem Projekt navigieren.

- Dort wählen Sie den File “build.gradle” aus.

- Beim nächsten Dialog “Open as Project” wählen.

- Beim nächsten Dialog kontrollieren ob Java 9 ausgewählt ist.

- Dann “File already exists” mit YES bestätigen.

- ACHTUNG: Jetzt “Delete existing Project and Import” anklicken.

- Warten, warten, warten.

Wenn alles gut gegangen ist sollte im Project-View der Java-Ordner unter src/main blau sein und der Java-Ordner unter src/test grün.
