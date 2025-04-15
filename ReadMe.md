# 🐉 Dungeons & Dragons Java Game (Alpha Test 1.0.0)

Un gioco ispirato al celebre universo di **Dungeons & Dragons**, sviluppato in Java con meccaniche di combattimento a turni, movimento su mappa e un sistema di AI dinamico. Progettato per offrire un'esperienza avvincente, fedele alle regole base di D&D, ma con un tocco personale.

---

## 📦 Requisiti di Sistema

> Per garantire l’avvio corretto del gioco, è necessario disporre di una **Java Virtual Machine compatibile con Java 21**.

### ✅ Windows (Consigliato)
- Il gioco può essere avviato direttamente tramite file `.exe` senza necessità di installazione aggiuntiva.
- Il file `.exe` è stato testato e generato per Windows 10/11 a **64 bit**.

### 💻 Altri Sistemi Operativi (Linux / UNIX / macOS)
- Il gioco è completamente compatibile anche su ambienti *non-Windows*, ma richiede l’avvio manuale del file `.jar`.
- Modalità di avvio alternative:
  - 🐍 **Via Python**: eseguire `launcher.py` presente nella cartella `Launcher/`
  - 💡 **Via VSCode o Terminale**: con `JavaFX` configurato correttamente e (opzionalmente) `Maven`
- Il file `.jar` si trova nella cartella `target/dnd-1.0-SNAPSHOT.jar`

### ☕ Requisiti Java
È richiesta l’installazione di una **JVM compatibile con Java 21**, ad esempio:

```
openjdk 21.0.4 2024-07-16
OpenJDK Runtime Environment OpenLogic-OpenJDK (build 21.0.4+7-adhoc.Administrator.jdk21u)
OpenJDK 64-Bit Server VM OpenLogic-OpenJDK (build 21.0.4+7-adhoc.Administrator.jdk21u, mixed mode, sharing)
```

> 🛠️ **Maven** non è obbligatorio, ma è consigliato per compilazioni manuali o esecuzione tramite IDE come VSCode/IntelliJ.

---

## ⚔️ Caratteristiche dei Personaggi

| Classe     | CA  | Vita | Descrizione                         |
|------------|-----|------|--------------------------------------|
| Barbaro    | 12  | 100  | Guerriero potente, forza bruta       |
| Guardiano  | 13  | 100  | Difensore robusto, maggiore difesa   |

| Razza  | Velocità | Forza | Des | Cos | Int | Sag | Car | Bonus               |
|--------|-----------|--------|------|------|------|------|------|-----------------------|
| Elfo   | 9 blocchi | 12     | 8    | 8    | 8    | 8    | 8    | +2 Des, Int, Forza    |
| Golem  | 3 blocchi | 14     | 5    | 8    | 1    | 1    | 1    | +2 Forza, Costituzione |

---

## 🗡️ Armi Disponibili

| Arma      | Tipo                  | Danno  | Robustezza | Utilizzo (turni) | Ricarica (turni) | Note aggiuntive                                 |
|-----------|-----------------------|--------|-------------|------------------|------------------|-------------------------------------------------|
| Ascia     | Mischia Semplice      | 1d6    | 12          | 3                | 5                | Buon danno base, arma del Barbaro               |
| Pugnale   | Mischia Semplice      | 2d4    | 8           | 2                | 3                | Più veloce ma con usura rapida                  |
| Mani Nude| Nessuna arma           | 1d4    | ∞           | ∞                | ∞                | Default quando nessuna arma è equipaggiata      |

- Ogni arma ha una **vita utile (robustezza)**: quando raggiunge 0, l’arma è **rotta** (`broken`) e non potrà più essere utilizzata.
- Ogni turno (attacco, movimento, schivata o mani nude) influisce sul tempo di utilizzo/ricarica dell’arma.
- Quando un'arma termina il tempo di utilizzo, il sistema effettua automaticamente lo **switch su mani nude**.
- Una volta terminato il tempo di ricarica, l’arma può essere nuovamente impugnata se non è rotta.

---

## ⚔️ Funzionalità Attuali

- ✅ Razza e Classe **Player** (`Elfo`), (`Barbaro`)
- ✅ Razza e Classe **Enemy** (`Golem`),(`Guardiano`)
- ✅ Movimento del personaggio tramite tastiera (WASD)
- ✅ Combattimento a turni: Attacco (`F`), Schivata (`Q`), Termine turno (`Z`)
- ✅ Tiri salvezza e colpi basati su meccaniche D20 + bonus + modificatore
- ✅ AI del nemico con comportamento pseudo-casuale nei movimenti
- ✅ Gestione di colpi critici e fallimenti critici
- ✅ Sistema di riavvio partita al termine del combattimento
- ✅ Mappa con gestione dei bordi e posizionamento intelligente del nemico
- ✅ Sistema Armi con danni variabili, robustezza, tempi di utilizzo e ricarica
- ✅ Calcolo corretto di danni multipli (es. 2d4)

---

## 🧠 Algoritmi di Combattimento

Il sistema di combattimento si ispira alle regole classiche di D&D, ma semplificato per l'esperienza in tempo reale e adattato alla logica turn-based del gioco.

### 🗡️ Attacco

- **Tiro per colpire**:
  - Formula: `1d20 + Bonus Competenze + Modificatore (Forza)`
  - Il tiro viene confrontato con la **Classe Armatura (CA)** dell’avversario:
    - Se il risultato è **maggiore o uguale alla CA**, l’attacco colpisce.
    - Altrimenti, l’attacco fallisce.
- **Calcolo del Danno**:
  - Se l’attacco va a segno, il danno inflitto dipende dall’arma equipaggiata:
    - **Mani nude** → `1d4 + Modificatore`
    - **Arma equipaggiata** → `Danno dell'Arma + Modificatore`
- **Eventi Speciali**:
  - 🎯 **Colpo Critico (1d20 = 20)**: esegue un attacco aggiuntivo immediato.
  - 💥 **Fallimento Critico (1d20 = 1)**: il nemico ottiene un turno extra gratuito.

### 🛡️ Schivata

- **Tiro di Schivata**:
  - Formula: `1d20 + Bonus Competenze + Modificatore (Destrezza)`
  - Se il risultato supera un valore soglia (stabilito in base al nemico o al contesto), l’attacco in arrivo viene **annullato**.
- Utile per difendersi da attacchi potenzialmente letali o in caso di bassa vita.

### 🤖 Enemy AI (Intelligenza Artificiale del Nemico)

- Il nemico agisce in maniera pseudo-casuale, ma guidata da **condizioni dinamiche**:
  - Può **attaccare**, **schivare** o **muoversi**.
- **Fattori che influenzano le decisioni**:
  - 💥 Il player ha ottenuto un fallimento critico → AI attacca con priorità.
  - ⭐ L’AI ottiene un tiro d’attacco superiore alla CA del player → attacco obbligato.
  - 🩸 Vita del player ≪ Vita del nemico → AI aggressiva.
  - 🛡️ Vita del nemico bassa → maggiore probabilità di schivare.
- Decisione governata da **condizioni booleane** (es. `shouldAttack`, `shouldDodge`, `shouldMove`).

> Il sistema può evolvere in futuro includendo parametri come **coraggio, intelligenza, istinto di sopravvivenza**, rendendo l’AI più “umana”.

---

## 🚧 Prossimi Obiettivi

- 🔁 **Miglioramento AI**: 
  - Introduzione di attributi come *intelligenza*, *impulsività*, *strategia*
  - Muovere il nemico in base alla situazione (ESEMPIO: se ha vita bassa)
  - Decision-making basato sul contesto
- 🌽 **Attacchi a distanza**
- 🍜 **Inventory System**
- 🧕 **Aggiunta di nuove razze**
- ⚡ **Possibilità di Utilizzare le Abilità**
- 🌿 **Item per curarsi (Esclusiva Player x2)**
- 💪 Migliorare il **Sistema Armi**
  - Gestire Armi anche per il nemico

---

## 📝 Report Aggiornamenti

### 📅 15 Aprile
- 🔧 **Aggiornamento Sistema Armi**:
  - Implementazione del sistema di lancio dell'arma se l'arma ha proprietà (`Lancio`) e gestione dell'accuratezza se l'arma ha (`Accuratezza`)
  - Sistema ancora **BUGGATO**, sto lavorando per sistemare questi bug
  - Se non si prova a lanciare l'arma, il gioco va senza intoppi

### 📅 14 Aprile
- 🔧 **Aggiornamento Sistema Armi**:
  - Ogni arma ha ora:
    - **Robustezza (vita)** → n° massimo di attacchi eseguibili prima della rottura
    - **Tempo di utilizzo** → turni consecutivi in cui può essere usata
    - **Tempo di ricarica** → turni necessari prima che possa essere riutilizzata
  - Azioni come attacco, movimento, schivata, mani nude → contano come **turni**
  - Introduzione flag per switch automatico su mani nude a fine utilizzo
  - Flag `broken` per armi che non possono più essere usate
- 🎲 Supporto a danni con **più dadi** (es. `2d4`, `3d6`)
- 🛡️ Aggiornamento equipaggiamento del Barbaro:
  - **Ascia**: 1d6, vita 12, 3 turni utilizzo, 5 turni ricarica
  - **Pugnale**: 2d4, vita 8, 2 turni utilizzo, 3 turni ricarica


### 📅 12 Aprile
- Aggiunta sistema di Armi per il solo **Player**
- Aggiunta dell'ascia e del pugnale
- Gestione is_holding_weapon per il player

### 📅 11 Aprile
- Aggiunte le azioni da tastiera (attacco, schivata, fine turno)
- Aggiornamento AI Nemico (Attacco, Schivata)
- Test del gioco (valutazione tasso vittoria e sconfitta) **Gioco Moderato**
- Creazione del file .exe e .jar
- Fix bug dell'immagini non visibili nel file .jar con InpustStream
- per la creazione del file .exe, creato senza visualizzazione console e con comando
  pyinstaller launcher.py --onefile --noconsole (nella cartella Launcher)
- Mini-Bilanciamento del Gioco (guarda statistiche player, enemy)

### 📅 10 Aprile
- Supporto input tramite frecce direzionali
- Introdotta superclasse `Dice` per gestione dadi (D20, D10, D6)
- Codice ristrutturato in stile più elegante e OOP
- Miglioramenti AI: riconoscimento fallimento critico del player

### 📅 9 Aprile
- Valori di battaglia incapsulati nella classe `Game`
- Aggiunta logica `PresettingBattleAction` per pre-calcolo esiti
- Supporto per danni critici e gestione schivata nemico/player
- Output centralizzato in `results_action`

### 📅 8 Aprile
- Refactoring codice per maggiore leggibilità e mantenibilità

### 📅 5 Aprile
- Aggiunto restart del gioco
- Miglioramenti AI nemico in combattimento
- Fix bug GUI e gestione eccezioni

### 📅 3 Aprile
- Blocco sovrapposizione player-nemico
- Gestione bordi mappa e movimenti intelligenti
- Prima versione della EnemyAI con pattern decisionali base

---

## 👨‍💻 Tech Stack

- **Java 17+**
- OOP Principles
- Console I/O
- Custom AI Logic

---

## 📌 Note Finali

Questo progetto è in continua evoluzione, con l’obiettivo di simulare un combattimento tattico e coinvolgente in stile D&D, implementato completamente in Java. Feedback, suggerimenti e contributi sono benvenuti!

## 📄 Recensione da parte dello sviluppatore

Ho testato più di 3/4 partite consecutive e il tasso di vincita si aggira intorno ai 2 su 4. Molto dipende dalla strategia adottata dal giocatore: agire impulsivamente o ignorare le potenzialità della schivata spesso porta alla sconfitta!

