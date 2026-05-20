# PIC 16F84 Simulator

> [cite_start]📌 **Hinweis zum Projekt:** > Dieses Projekt entstand im Sommer 2026 im Rahmen des Moduls *Rechnerarchitektur* am Anfang unseres Studiums[cite: 3]. [cite_start]Aus heutiger Sicht würden wir die Software-Architektur definitiv anders aufbauen – insbesondere im Hinblick auf Clean-Code-Prinzipien, die Vermeidung von zu vielen statischen Methoden/Variablen und eine striktere Trennung von Backend und GUI (MVC-Pattern)[cite: 173, 378, 379, 381, 382]. [cite_start]Dennoch bleibt dieses Projekt für uns ein wichtiger Meilenstein: Es hat unser Verständnis für hardwarenahe Prozesse massiv geschärft und war ein großartiger Lernbeschleuniger für die agile Teamarbeit und Problemlösung[cite: 14, 383].

---

## Projektbeschreibung
[cite_start]Ziel dieses Projektes war die Entwicklung eines realitätsnahen Software-Simulators für den Mikrocontroller **PIC 16F84**[cite: 14, 16, 20]. [cite_start]Der Simulator emuliert die Kernkomponenten des Chips in einer reinen Softwareumgebung und ermöglicht es, die Funktionsweise und Datenverwaltung des Mikrocontrollers visuell nachzuvollziehen[cite: 15, 20, 21].

### Hauptfunktionen:
* [cite_start]**Prozessor-Emulation:** Nachbildung des Befehlssatzes (darunter Befehle wie `BTFSC`, `CALL`, `MOVF`, `RRF`, `SUBWF`, `DECFSZ`, `XORLW`)[cite: 15, 21, 181, 202, 211, 228, 254, 294, 317].
* [cite_start]**Speicher- & Registerverwaltung:** Korrekte Abbildung von Bank0 und Bank1, inklusive der hardwareseitigen Logik des `TRIS`-Registers (Input/Output-Steuerung via Data-Latch)[cite: 15, 21, 167, 370, 371, 372].
* [cite_start]**Interrupt-Handling:** Vollständige Implementierung von Interrupt-Bedingungen (z. B. `TMR0Interrupt`, `RB0Interrupt` und `RBPortChangeInterrupt`) unter Berücksichtigung des GIE-Bits und des Stacks zur Rücksprungsicherung[cite: 350, 351, 352, 355, 361, 369].
* [cite_start]**Interaktiver Debugger:** Ausführung von Testprogrammen im Einzelschrittmodus (`Step`) oder Endlosmodus (`Run`) mit der Möglichkeit, dynamisch Breakpoints im Code zu setzen[cite: 23, 161, 162, 165].
* [cite_start]**Laufzeitzähler:** Integrierter Timer zur Messung der Programmlaufzeit in Mikrosekunden ($\mu s$)[cite: 34, 156].

---

## Tech-Stack
* [cite_start]**Programmiersprache:** Java [cite: 17]
* [cite_start]**GUI-Framework:** Java Swing [cite: 17]
* [cite_start]**Entwicklungsgrundlage:** Offizielle Datenblätter des "PIC 16F84 Data Sheet" [cite: 17]

---

## Architektur & Programmstruktur
[cite_start]Die Klassenstruktur wurde eng an die realen Hardwarekomponenten des Mikrocontrollers angelehnt[cite: 178]:
* [cite_start]`Decoder`: Übernimmt das Parsen und Aufschlüsseln der binären Befehlscodes[cite: 178].
* [cite_start]`Datenspeicher`: Verwaltet die Register, Banken und Flags (Zero-, Digit-Carry- und Carry-Flag)[cite: 178, 261, 326, 327].
* [cite_start]`PC` & `Stack`: Steuern den Programmzähler und die Rücksprungadressen bei Unterprogrammen (`CALL`) oder Interrupts[cite: 178, 204, 205, 352].
* [cite_start]`ReadFile`: Zuständig für das Einlesen und Laden der Assembler-Testprogramme[cite: 166, 178, 179].
* [cite_start]`GUI`: Die interaktive Benutzeroberfläche zur Visualisierung der Registerinhalte (binär/hexadezimal) und Pins[cite: 167, 169, 173, 178].

---

## Projekterkenntnisse & Key Takeaways
[cite_start]Aus der anschließenden Projektreflektion haben wir wichtige Lehren für zukünftige Softwareprojekte gezogen:
1. [cite_start]**Architekturplanung:** Eine frühzeitige Trennung von Backend und UI spart im späteren Verlauf aufwändige Refactorings[cite: 378, 381, 382].
2. [cite_start]**OOP-Best-Practices:** Der übermäßige Einsatz statischer Methoden schränkt die Flexibilität ein – bei einer Überarbeitung würden wir auf ein objektorientierteres Design setzen[cite: 378, 379].
3. [cite_start]**Team-Kommunikation:** Eine strukturierte Absprache und einheitliche Entwicklungsumgebungen von Tag 1 an sind essenziell für den Projekterfolg[cite: 380].

---

## Autoren
[cite_start]Dieses Projekt wurde im Juni 2024 gemeinsam erstellt von[cite: 3]:
* [cite_start]Thuy Linh Trinh [cite: 2]
* [cite_start]Nadine Bichler [cite: 2]

[cite_start]*Entstanden an der Hochschule Offenburg im Studiengang KI/AI (Fach: Rechnerarchitektur) unter der Leitung von Prof. Stefan Lehmann.* [cite: 3, 4]
