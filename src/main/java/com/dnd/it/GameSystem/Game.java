package com.dnd.it.GameSystem;
import com.dnd.it.GameSystem.Dice.*;
import com.dnd.it.GameSystem.Model.Characters;

public class Game {

    private Characters player;
    private Characters enemy;
    private EnemyAI enemyAI;
    private Boolean untouchable;
    private Boolean double_hit;
    private Boolean already_dodge;
    private Boolean player_tried_to_dodge;
    private Boolean enemyDodge;
    private Boolean enemyMoving;
    private String results_action;
    private int bonus;
    private int modifier;
    private int damage;
    private int additional_damage;
    private int launch;
    private int dodge_action; // diversa da action e already_dodge pk serve al player per gestire il cirtical failure della schivata o il successo o insuccesso della schivata
    private int action;
    private D20 d20;
    private D10 d10;
    private D6 d6;

    public Game(Characters player, Characters enemy, EnemyAI enemyAI){
        this.player = player;
        this.enemy = enemy;
        this.enemyAI = enemyAI;
        this.results_action = "";
        this.damage = 0;
        this.additional_damage = 0;
        this.bonus = 0;
        this.modifier = 0;
        this.launch = 0;
        this.action = 0;
        this.dodge_action = 0;
        this.untouchable = false;
        this.already_dodge = false;
        this.double_hit = false;
        this.player_tried_to_dodge = false;
        this.enemyDodge = false;
        this.enemyMoving = false;
        this.d20 = new D20();
        this.d10 = new D10();
        this.d6 = new D6();
    }

    /* Setter */
    private void setResultsAction(String results){
        this.results_action = results;
    }
    private void setBonus(int bonus){
        this.bonus = bonus;
    }
    private void setModifier(int modifier){
        this.modifier = modifier;
    }
    private void setLaunch(int launch){
        this.launch = launch;
    }
    private void setDamage(int damage){
        this.damage = damage;
    }
    private void setAdditionDamage(int add){
        this.additional_damage = add;
    }
    private void setAction(int action){
        this.action = action;
    }  
    private void setDodgeAction(int value){
        this.dodge_action = value;
    } 
    private void setUntouchable(Boolean bool){
        this.untouchable = bool;
    }
    private void setAlreadyDodge(Boolean bool){
        this.already_dodge = bool;
    }
    private void setDoubleHit(Boolean bool){
        this.double_hit = bool;
    }
    private void setPlayerTryDodge(Boolean bool){
        this.player_tried_to_dodge = bool;
    }
    private void setEnemyDodge(Boolean bool){
        this.enemyDodge = bool;
    }
    private void setEnemyMoving(Boolean bool){
        this.enemyMoving = bool;
    }
    private void ClearResulstActionString(){
        this.results_action = "";
    }
    /* Getter */
    public String getResultsAction(){
        return this.results_action;
    }
    private int getBonus(){
        return this.bonus;
    }
    private int getModifier(){
        return this.modifier;
    }
    private int getLaunch(){
        return this.launch;
    }
    private int getDamage(){
        return this.damage;
    }
    private int getAdditionalDamage(){
        return this.additional_damage;
    }
    private int getAction(){
        return this.action;
    }
    private int getDodgeAction(){
        return this.dodge_action;
    }
    public EnemyAI getEnemyAI(){
        return this.enemyAI;
    }
    private Boolean Is_Untouchable(){
        return this.untouchable;
    }
    private Boolean Are_Already_Dodge(){
        return this.already_dodge;
    }
    private Boolean Are_Double_Hit(){
        return this.double_hit;
    }
    private Boolean Are_Player_Triyed_To_Dodge(){
        return this.player_tried_to_dodge;
    }
    public Boolean Are_Enemy_Moving(){
        return this.enemyMoving;
    }
    private Boolean Is_Enemy_Dodge(){
        return this.enemyDodge;
    }

    /* dal momento che mi serve sapere se il player ha avuto come esito un fallimento critico decido di crare un metodo per lanciare il dado d20 prima della decisione del nemico  */
    private void RollD20(){
        this.d20.RollDice();
    }
    /* questo serve al EnemyAi per capire se il risultato dell'attacco o della schivata è stato un Fallimento critico, così automaticamente attacca */
    public int getD20PlayerResults(){
        this.RollD20();
        return this.d20.getResult();
    }
    /* Pre setting Battle Action
     * I metodi sottostanti si occupano di Pre settarre i valori di:
     *      - Calcolo del bonus su forza o destrezza (così come modificatore)
     *      - lancio del D20
     *      - Potenziale Danno del player o nemico già calcolato in base a D20 + bonus + modificatore
     *      - Setting del esito del lancio
     * 
     * Invece il Metodo CalculateAdditionalDamage serve per calcolare il danno aggiuntivo nel caso in cui il player ottinee un Critical Hit
     */

    /* Metodi per la Battaglia */
    private void PreSetBattleAction(Characters character){
        //this.ClearResulstActionString();
        /* set bonus value */
        setBonus(character.getRaceClass().getBonus("Strength"));
        /* setting modifier of characters */
        character.getRaceClass().setModificatore(character.getRaceClass().getStrength());
        setModifier(character.getRaceClass().getmodificatore());
        setDamage(0);
        /* il lancio è gia avvenuto pre-pre calcolo per la decisione del nemico */
        /* pre set del risultato del lancio d20 results + bonus + modificatore, anche se negativo */
        setLaunch(d20.getResult() + bonus + modifier);
    }

    private void PreSetBattleDodge(Characters character){
        this.RollD20();
        character.getRaceClass().setModificatore(character.getRaceClass().getDexterity());
        setAction(d20.getResult() + character.getRaceClass().getmodificatore() + character.getRaceClass().getBonus("Dexterity"));
    }

    private void CalculateAdditionalDamage(){
        d6.RollDice();
        setAdditionDamage(d6.getResult() + getBonus() + getModifier());
    }
    
    /* Algorithms for Battle */

    /*
     * Il giocatore ha tre opzioni, per iniziare con il codice gli do solo la possibilità di attaccare o schivare
     * 
     * Attacco:
     *  IL giocatore decide di attaccare, quindi tira un dado D20 + bonus_competenza +- modificatore per vedere se il colpo va a segno:
     *  - Se il D20 + bonus_competenza +- modificatore < guard, il colpo non ha effetto, e si passa al turno del nemico
     *  - Se il D20 + bonus_competenza +- modificatore >= guard, allora il giocatore attacca con il calcolo del Danno (d6 + bonus +- modificatore)
     *  - Casi Eccezionali:
     *      - Se il D20 = 1, si entra nell'eccezione di Critical Failure, quindi il player non attacca, e da un vantaggio al nemico, che può attaccare due volte
     *      - Se il D20 == 20, si entra nell'eccezione di Critical Hit, quindi il player può attaccare due volte il nemico
     * 
     *  - La medesima logica è applicata all'azione del nemico che in base al risultato del D20:
     *      - Attacca Normalmente ( D20 >= player.guard)
     *      - Attacca 2 volte     ( D20 == 1)
     *      - Attacca 0 volte     ( D20 == 20)
     * 
     *  - Inoltre il Nemico è dotato di una AI (ancora stupida) che gli permette di decidere se attaccare, schivare o muoversi, quindi compiere come 1 di queste tre azioni
     *  - Si gestisce anche il cosa in cui, se decide di muoversi e si avvicina al player, può decidere se attaccare oppure no
     * 
     *  - La EnemyAI è gestita come Classe a parte 
     *  - Idee su come migliorarla:
     *       - Aggiungere caratteristiche come:
     *          - intelligenza
     *          - Impulsività
     *          - Stratega
     *    Cercando di gestire le casistiche su "cosa devo fare se mi ritrovo in una determinata situazione", in sostanza creare una "finta AI programmata" con pattern
     * 
     * - Attualmente il Bonus e il Modificatore per ATTACCARE sono calcolati basandosi sull'attributo forza (Strenght) , invece per Schivare sono calcolati su destrezza(Dexterity)
     */

    /* Battle Turn Manager */
    public void BattleTurn(String input, int enemy_moves){
        this.ClearResulstActionString();
        /* Attacco Player */
        if (input.equals("Attacca")){
            this.Attack(enemy_moves);
            this.setResultsAction("Hai deciso di Attaccare...\n"+ this.getResultsAction() +"\n");
            this.setUntouchable(false);
        }
        /* Schivata Player */
        if (input.equals("Schiva")){

            this.setResultsAction("Hai deciso di schivare...\n");
            this.setPlayerTryDodge(true);

            /* Avvio la procedura di calcolo della schivata */
            this.Dodge(player, enemy);

            if (this.getDodgeAction() == -1){
                this.setResultsAction(this.getResultsAction() + "Il lancio ha avuto come esito un 1 critico!!\nIl nemico di Attacca può attaccare due volte per vantaggio.");
                this.setUntouchable(false);
                //results_action += "\nIl nemico ti infligge un danno pari a "+ EnemyAttack() + EnemyAttack();
            }
            else if (this.getDodgeAction() == 1){
                this.setResultsAction(this.getResultsAction() + "Il lancio ha avuto esito positivo\nIl nemico non ti può attaccare");
                this.setUntouchable(true);
            }
            else{
                this.setResultsAction(this.getResultsAction() + "Il lancio ha avuto esito negativo\nIl nemico ti può attaccare.\n");//Il nemico ti infligge un danno pari a "+ EnemyAttack();
                this.setUntouchable(false);
            }
        }
        /* Azione Nemico */
        if(input.equals("Azione Nemico")){
            this.setEnemyMoving(false);
            if(enemy_moves == 2){
                enemy_moves = 1;
                this.setPlayerTryDodge(false);
            }
            /* Are_Already_Dodge return false or true */
            if( ! this.Are_Already_Dodge() ){
                if(enemy_moves == 3 ){
                    this.setResultsAction("Il nemico si muove");
                    this.setEnemyMoving(true);
                }

                if(enemy_moves == 1){
                    if( ! this.Is_Untouchable() ){
                        /* Il nemico attacco e setta il valore del danno che verrà concatenato come stringa nel results */
                        this.EnemyAttack();
                        this.setResultsAction("Il nemico ti attacca...\nDanno: "+this.getDamage()+"\n");
                        if(this.Are_Double_Hit()){
                            this.EnemyAttack();
                            this.setResultsAction(this.getResultsAction() + "Il nemico ti attacca una seconda volta...\nDanno: "+this.getDamage()+"\n");
                        }
                    }
                    else{
                        this.setResultsAction("\n" + this.getResultsAction() + "Il player ha schivato il tuo colpo");
                        this.setUntouchable(false);
                    }
                }
                
            }
            else{
                this.setAlreadyDodge(false);
            }
            /* Ho riscontrato un problema con il set false di double hit, rimane true quando il nemico non decide di attaccare, quindi set false alla fine di ogni scelta */
            this.setDoubleHit(false);
        }

        
    }

    /* Player Attack */
    private void Attack(int enemy_status){
        this.PreSetBattleAction(player);

        this.setResultsAction(this.getResultsAction() + "Tiro su Forza\nD20: "+d20.getResult());
        
        d10.RollDice();
        this.setDamage(d10.getResult() + getBonus() + getModifier());

        if(enemy_status == 2){
            this.setAlreadyDodge(true);
            this.Dodge(enemy, player);
            if(this.getDodgeAction() == 1){
                this.setResultsAction(this.getResultsAction() + "\nBonus Competenza (Forza): "+ this.getBonus() +"\nModificatore (Forza): "+ this.getModifier() +"\nEsito del tiro: "+ this.getLaunch() +"\nIl nemico ha schivato il colpo");
                /* Attributo boolean per dire se l'esito della sc hivata è andata a buon fine o no */
                this.setEnemyDodge(true);
            }
            else{
                this.setResultsAction(this.getResultsAction() + "\nIl nemico ha provato a schivare ma con esito negativo");
                this.setEnemyDodge(false);
            } 
            /* Already Dodge settato indipendentemente se ha esito positivo o negativo */
            this.setAlreadyDodge(true); 
        }
        /* Critical hit */
        if( ! this.Is_Enemy_Dodge() ){
            /* Critical Hit */
            if (d20.getResult() == 20){
                this.CalculateAdditionalDamage();
                enemy.getClassPgClass().DecreaseLife(getDamage()+getAdditionalDamage()); 
                this.setResultsAction(this.getResultsAction() + "\nCritical Hit !!"+"\nBonus Competenza (Forza): "+ this.getBonus() +"\nModificatore (Forza): "+ this.getModifier() +"\nEsito del tiro: "+ this.getLaunch() +"\nDanno: "+ this.getDamage() +" + "+ this.getAdditionalDamage());
            }

            /* Critical Failure */
            else if (d20.getResult() == 1){
                this.setDoubleHit(true);
                this.setResultsAction(this.getResultsAction() +"\nCritical Failure !!\nIl nemico può attaccare due volte per vantaggio!\nEsito del tiro: "+ getLaunch()); 
            }
            /*
            * If Sum of D20 result + Bonus player + Modifier is greater o equals then enemy guard, hit enemy rolling d10
            * Alltough, attack had no effect
            */
            /* Normale Damage */
            else if ( getLaunch() >= enemy.getClassPgClass().getGuard() ){
                enemy.getClassPgClass().DecreaseLife(getDamage()); 
                this.setResultsAction(this.getResultsAction() + "\nDanno: "+ this.getDamage() +"\nBonus Competenza (Forza): "+ this.getBonus() +"\nModificatore (Forza): "+ this.getModifier() +"\nEsito del tiro: "+ this.getLaunch());
            }
            /* Missed Hit */
            else{ 
                this.setResultsAction(this.getResultsAction() + "\nColpo non andato a segno");
            }
        }
        /* setto a false il check dell'avvenuta schivata del nemico per il nuovo turno */
        this.setEnemyDodge(false);

    }

    /* Enemy Attack */
    private void EnemyAttack(){
        this.PreSetBattleAction(enemy);

        /*
         * If Sum of D20 result + Bonus player + Modifier is greater o equals then enemy guard, hit enemy rolling d10
         * Alltough, attack had no effect
         */
        if ( this.getLaunch() >= player.getClassPgClass().getGuard() ){
            d10.RollDice();

            this.setDamage(d10.getResult() + this.getBonus() + this.getModifier());
            player.getClassPgClass().DecreaseLife(this.getDamage());
        }
        else{
            this.setDamage(0);
        }
    }

    /* Player or Enemy Dodge */
    /* Non confondersi col fatto che la classe java ha degli attributi con nomi uguali
     * il 'player' è inteso come la classe characters che invoca il metodo Dodge e quindi è il soggetto
     * che dovrà tentare di schivare contro il soggetto 'enemy'
     */
    private void Dodge(Characters player, Characters enemy){

        this.PreSetBattleDodge(player);

        if ( this.getAction() <= 1 ){
            this.setDodgeAction(-1);
        }

        else if ( this.getAction() >= enemy.getClassPgClass().getGuard() ){
            this.setDodgeAction(1);
        }

        else{
            this.setDodgeAction(0);
        }

    }

    /* End Battle Manager */
    public Boolean CheckEndBattle(){
        if(player.getClassPgClass().getLife() <= 0 || enemy.getClassPgClass().getLife() <= 0)
            return true;
        return false;
    }
    public void EndBattle(){
        this.ClearResulstActionString();
        if(player.getClassPgClass().getLife() <= 0)
            this.setResultsAction("You Lost!");
        if(enemy.getClassPgClass().getLife() <= 0)
            this.setResultsAction("You Win!");
    }
}
