# Dungeons And Dragons Java Game

# Current Features:

Features:
- Muovere il proprio personaggio all'interno della mappa 
- Attaccare (D20 esito + bonus competenza (Strength) + modificatore (Strentgh))
- Schivare (D20 esito + bonus competenza (Dexterity) + modificatore (Dexterity))
- Il nemico si muove all'interno della mapp in maniera pseudo-casuale
- Il Nemico Attacca o schiva in base a delle decisioni pseudo-casuali
- Alla fine del combattimento, si può riavviare la partita

Fix e Bug da sistemare (+ Nuove Features da fare):
- Migliorare la AI Nemico e i movimenti i scena (dargli una logica)
- Aggiungere la possibilità di colpire con armi da distanza 
- Aggiungere le ARMI
- Inventory System
- Provare ad aggiungere una nuova razza

# Algorithms for Battle

Il giocatore ha tre opzioni, per iniziare con il codice gli do solo la possibilità di attaccare o schivare o muoversi 
- Attacco:
     *  IL giocatore decide di attaccare, quindi tira un dado D20 + bonus_competenza +- modificatore per vedere se il colpo va a segno:
     *  Se il D20 + bonus_competenza +- modificatore < guard, il colpo non ha effetto, e si passa al turno del nemico
     *  Se il D20 + bonus_competenza +- modificatore >= guard, allora il giocatore attacca con il calcolo del Danno (d6 + bonus +- modificatore)
     *  # Casi Eccezionali:
     *  - Se il D20 = 1, si entra nell'eccezione di Critical Failure, quindi il player non attacca, e da un vantaggio al nemico, che può attaccare due volte
     *  - Se il D20 == 20, si entra nell'eccezione di Critical Hit, quindi il player può attaccare due volte il nemico
     * 
     *  La medesima logica è applicata all'azione del nemico che in base al risultato del D20:
     *  - Attacca Normalmente ( D20 >= player.guard)
     *  - Attacca 2 volte     ( D20 == 1)
     *  - Attacca 0 volte     ( D20 == 20)
     * 
     *  Inoltre il Nemico è dotato di una AI (ancora stupida) che gli permette di decidere se attaccare, schivare o muoversi, quindi compiere come 1 di queste tre azioni
     *  Si gestisce anche il cosa in cui, se decide di muoversi e si avvicina al player, può decidere se attaccare oppure no
     * 
- La EnemyAI è gestita come Classe a parte 
     *  Idee su come migliorarla:
     *  Aggiungere caratteristiche come:
     *  - intelligenza
     *  - Impulsività
     *  - Stratega
     *  Cercando di gestire le casistiche su "cosa devo fare se mi ritrovo in una determinata situazione", in sostanza creare una "finta AI programmata" con pattern
     * 
     *  Attualmente il Bonus e il Modificatore per ATTACCARE sono calcolati basandosi sull'attributo forza (Strenght) , invece per Schivare sono calcolati su destrezza(Dexterity)

# Report and Update

9 Aprile Update and Report:
- Ogni valore calcolato in battaglia è stato diversificato come attributo della classe Game
- Aggiunta di metodi per il PresettingBattleAction del player e del nemico 
  ossia pre calcolo dell'esito del lancio (d20+bonus+modificatore), danno potenziale
- Aggiunta metodo per il calcolo del danno aggiuntivo in caso di Critical Hit
- Gestione delle schivate da parte del player e del nemico e della decisione di movimento del nemico tramite Boolean
- Compattamento il risultato da stampare a schermo in un'attributo results_action di tipo String che viene passatta tramite getter (eliminazione della String enemy_decision)
- Riscritto il codice in maniera più elegante, Object Oriented, e aggiunti più commenti

8 Aprile Report:
- Sistemazione del codice, reso più elegante, ma in alcuni punti da migliorare

5 Aprile Report:
- Aggiunta del sistema di riavvio del gioco
- Gestione della AI del nemico migliorata, capacità di decidere se attaccare schivare o muoversi(non in maniera razionale)
- Fix problemi della GUI e risoluzione eccezioni in Battle come Critical Hit, Critical Failure e Dodge Miss

3 Aprile Update and Fix:
- Il player non si accavalla più con il nemico
- Bordi mappa gestiti
- Aggiornamento del Game system Battle
- Aggiunta della AI Enemy per gestire la probabilità di attacco, schivata o movimento in arena
- Fix dei movimenti in arena e decisione del nemico se attaccare o no
