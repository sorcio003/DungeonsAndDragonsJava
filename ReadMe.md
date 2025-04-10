# 🐉 Dungeons & Dragons Java Game

Un gioco ispirato al celebre universo di **Dungeons & Dragons**, sviluppato in Java con meccaniche di combattimento a turni, movimento su mappa e un sistema di AI dinamico. Progettato per offrire un'esperienza avvincente, fedele alle regole base di D&D, ma con un tocco personale.

---

## ⚔️ Funzionalità Attuali

- ✅ Movimento del personaggio tramite tastiera (WASD)
- ✅ Combattimento a turni: Attacco (`F`), Schivata (`Q`), Termine turno (`Z`)
- ✅ Tiri salvezza e colpi basati su meccaniche D20 + bonus + modificatore
- ✅ AI del nemico con comportamento pseudo-casuale nei movimenti
- ✅ Gestione di colpi critici e fallimenti critici
- ✅ Sistema di riavvio partita al termine del combattimento
- ✅ Mappa con gestione dei bordi e posizionamento intelligente del nemico
---

## 🧠 Algoritmi di Combattimento

### 🗡️ Attacco

- **Tiro di attacco**: `D20 + Bonus Competenze + Modificatore (Forza)`
  - Se il risultato è **≥ guardia del nemico**, il colpo ha successo.
  - Se è **< guardia**, il colpo fallisce.
- **Danno**: `D6 + Bonus + Modificatore`
- **Eccezioni speciali**:
  - 🎯 **Critical Hit (D20 = 20)**: doppio attacco
  - 💥 **Critical Failure (D20 = 1)**: il nemico guadagna un attacco extra

### 🛡️ Schivata

- **Tiro di schivata**: `D20 + Bonus Competenze + Modificatore (Destrezza)`
- Usata per evitare l’attacco avversario.

### 🤖 Enemy AI

- Azioni: Attacco, Schivata o Movimento
- Comportamento attuale: pseudo-casuale
- Si adatta a fallimenti critici del giocatore
- Sistema booleano per decisioni dinamiche (es. avvicinamento → attacco)
- **Decisione di Attacco**
     - Se 💥 **D20 Player == 1** obbligo di Attacco  
     - Se ⭐ **D20 Enemy >= self.guard()** obbligo di Attacco
     - Se 🗡️ **enemy.life - player.life > 30** obbligo di Attacco
- **Decisione di Schivata**
     - Se 💥 **Enemy.life < 30** aumento probabilità di schivata
---

## 🚧 Prossimi Obiettivi

- 🔁 **Miglioramento AI**: 
  - Introduzione di attributi come *intelligenza*, *impulsività*, *strategia*
  - Muovere il nemico in base alla situazione (ESEMPIO: se ha vita bassa)
  - Decision-making basato sul contesto
- 🌽 **Attacchi a distanza**
- 💪 **Sistema Armi**
- 🍜 **Inventory System**
- 🧕 **Aggiunta di nuove razze**

---

## 📝 Report Aggiornamenti

### 📅 11 Aprile
- Aggiunte le azioni da tastiera (attacco, schivata, fine turno)
- Aggiornamento AI Nemico (Attacco, Schivata)

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