# PIC 16F84 Simulator

< **Hinweis zum Projekt:** > Dieses Projekt entstand im Sommer 2026 im Rahmen des Moduls *Rechnerarchitektur* am Anfang unseres Studiums. Aus heutiger Sicht würden wir die Software-Architektur definitiv anders aufbauen – insbesondere im Hinblick auf Clean-Code-Prinzipien, die Vermeidung von zu vielen statischen Methoden/Variablen und eine striktere Trennung von Backend und GUI (MVC-Pattern). Dennoch bleibt dieses Projekt für uns ein wichtiger Meilenstein: Es hat unser Verständnis für hardwarenahe Prozesse massiv geschärft und war ein großartiger Lernbeschleuniger für die agile Teamarbeit und Problemlösung.

---

## Projektbeschreibung
Ziel dieses Projektes war die Entwicklung eines realitätsnahen Software-Simulators für den Mikrocontroller PIC 16F84. Der Simulator emuliert die Kernkomponenten des Chips in einer reinen Softwareumgebung und ermöglicht es, die Funktionsweise und Datenverwaltung des Mikrocontrollers visuell nachzuvollziehen.

### Hauptfunktionen:
* **Prozessor-Emulation:** Nachbildung des Befehlssatzes (darunter Befehle wie `BTFSC`, `CALL`, `MOVF`, `RRF`, `SUBWF`, `DECFSZ`, `XORLW`).
* **Speicher- & Registerverwaltung:** Korrekte Abbildung von Bank0 und Bank1, inklusive der hardwareseitigen Logik des `TRIS`-Registers (Input/Output-Steuerung via Data-Latch).
* **Interrupt-Handling:** Vollständige Implementierung von Interrupt-Bedingungen (z. B. `TMR0Interrupt`, `RB0Interrupt` und `RBPortChangeInterrupt`) unter Berücksichtigung des GIE-Bits und des Stacks zur Rücksprungsicherung.
* **Interaktiver Debugger:** Ausführung von Testprogrammen im Einzelschrittmodus (`Step`) oder Endlosmodus (`Run`) mit der Möglichkeit, dynamisch Breakpoints im Code zu setzen.
* **Laufzeitzähler:** Integrierter Timer zur Messung der Programmlaufzeit in Mikrosekunden ($\mu s$).

---

## Tech-Stack
* **Programmiersprache:** Java 
* **GUI-Framework:** Java Swing 
* **Entwicklungsgrundlage:** Offizielle Datenblätter des "PIC 16F84 Data Sheet" 
---

## Architektur & Programmstruktur
Die Klassenstruktur wurde eng an die realen Hardwarekomponenten des Mikrocontrollers angelehnt:
* `Decoder`: Übernimmt das Parsen und Aufschlüsseln der binären Befehlscodes.
* `Datenspeicher`: Verwaltet die Register, Banken und Flags (Zero-, Digit-Carry- und Carry-Flag).
* `PC` & `Stack`: Steuern den Programmzähler und die Rücksprungadressen bei Unterprogrammen (`CALL`) oder Interrupts.
* `ReadFile`: Zuständig für das Einlesen und Laden der Assembler-Testprogramme.
* `GUI`: Die interaktive Benutzeroberfläche zur Visualisierung der Registerinhalte (binär/hexadezimal) und Pins.

---

## Projekterkenntnisse & Key Takeaways
Aus der anschließenden Projektreflektion haben wir wichtige Lehren für zukünftige Softwareprojekte gezogen:
1. **Architekturplanung:** Eine frühzeitige Trennung von Backend und UI spart im späteren Verlauf aufwändige Refactorings.
2. **OOP-Best-Practices:** Der übermäßige Einsatz statischer Methoden schränkt die Flexibilität ein – bei einer Überarbeitung würden wir auf ein objektorientierteres Design setzen.
3. **Team-Kommunikation:** Eine strukturierte Absprache und einheitliche Entwicklungsumgebungen von Tag 1 an sind essenziell für den Projekterfolg.

---

## Autoren
Dieses Projekt wurde im Juni 2024 gemeinsam erstellt von:
* Thuy Linh Trinh
* Nadine Bichler

*Entstanden an der Hochschule Offenburg im Studiengang AI (Fach: Rechnerarchitektur) unter der Leitung von Prof. Stefan Lehmann.*
