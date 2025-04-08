package com.dnd.it.GameSystem;
import java.util.Random;

import javax.crypto.spec.RC2ParameterSpec;

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
    private String results_action;
    private int bonus;
    private int modifier;
    private int damage;
    private int launch;
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
        this.bonus = 0;
        this.modifier = 0;
        this.launch = 0;
        this.action = 0;
        this.untouchable = false;
        this.already_dodge = false;
        this.double_hit = false;
        this.player_tried_to_dodge = false;
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
    private void setAction(int action){
        this.action = action;
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
    private int getAction(){
        return this.action;
    }
    public EnemyAI getEnemyAI(){
        return this.enemyAI;
    }

    private void ClearResulstActionString(){
        this.results_action = "";
    }

    /* Questo metodo si occupa di pre settare il bonus, il modificatore, il danno e il risulatto del lancio del d20 */
    private void PreSetBattleAction(Characters character){
        this.ClearResulstActionString();
        /* set bonus value */
        setBonus(character.getRaceClass().getBonus("Strength"));
        /* setting modifier of characters */
        character.getRaceClass().setModificatore(character.getRaceClass().getStrength());
        setModifier(character.getRaceClass().getmodificatore());
        setDamage(0);
        /* Roll D20 to attack enemy */
        d20.RollDice();
        /* pre set del risultato del lancio d20 results + bonus + modificatore, anche se negativo */
        setLaunch(d20.getResult() + bonus + modifier);
    }

    private void PreSetBattleDodge(Characters character){
        d20.RollDice();
        character.getRaceClass().setModificatore(character.getRaceClass().getDexterity());
        setAction(d20.getResult() + character.getRaceClass().getmodificatore() + character.getRaceClass().getBonus("Dexterity"));
    }

    /* Algorithms for Battle */

    /*
     * Il giocatore ha tre opzioni, per iniziare con il codice gli do solo la possibilità di attaccare o schivare
     * 
     * Attacco:
     *  IL giocatore decide di attaccare, quindi tira un dado D20 + bonus_competenza + modificatore per vedere se il colpo fa a segno (se è >= alla guard del nemico)
     *  - Se il D20 + bonus_competenza + modificatore < guard, il colpo non ha effetto, e si passa al turno del nemico ( c'è da gestire il 20 critcio o 1 critico)
     *  - Se il D20 + bonus_competenza + modificatore >= guard, allora il giocatore (in base al suo personaggio, ma solo per il codice di prova) 
     *    tira un D10 + modificatore + bouns_competenza ( se esce fuori un 10 dal D10, hai diritto ad un secondo tiro di un D6 + modificatore + bonus_competenza)
     */

    /* Battle Turn Manager */
    public void BattleTurn(String input, int enemy_moves){
        this.ClearResulstActionString();
        /* Attacco Player */
        if (input.equals("Attacca")){
            this.Attack(enemy_moves);
            setResultsAction("Hai deciso di Attaccare...\n"+ getResultsAction() +"\n");
            untouchable = false;
        }
        /* Schivata Player */
        if (input.equals("Schiva")){

            setResultsAction("Hai deciso di schivare...\n");
            player_tried_to_dodge = true;

            if (Dodge() == -1){
                setResultsAction(this.getResultsAction() + "Il lancio ha avuto come esito un 1 critico!!\nIl nemico di Attacca può attaccare due volte per vantaggio.");
                untouchable = false;
                //results_action += "\nIl nemico ti infligge un danno pari a "+ EnemyAttack() + EnemyAttack();
            }
            else if (Dodge() == 1){
                setResultsAction(this.getResultsAction() + "Il lancio ha avuto esito positivo, il nemico non ti può attaccare");
                untouchable = true;
            }
            else{
                setResultsAction(this.getResultsAction() + "Il lancio ha avuto esito negativo. Il nemico ti può attaccare.\n");//Il nemico ti infligge un danno pari a "+ EnemyAttack();
                untouchable = false;
            }
        }
        /* Azione Nemico */
        if(input.equals("Azione Nemico")){
            if(enemy_moves == 2 && player_tried_to_dodge){
                enemy_moves = 1;
                player_tried_to_dodge = false;
            }
            if(! already_dodge ){
                if(enemy_moves == 3 ){
                    setResultsAction(this.getResultsAction() + "Il nemico si muove");
                }

                if(enemy_moves == 1){
                    if(!untouchable){
                        setResultsAction(this.getResultsAction() + "Il nemico ti attacca...\nDanno: "+this.EnemyAttack()+"\n");
                        if(double_hit){
                            setResultsAction(this.getResultsAction() + "Il nemico ti attacca una seconda volta...\nDanno: "+this.EnemyAttack()+"\n");
                            double_hit = false;
                        }
                    }
                    else{
                        setResultsAction(this.getResultsAction() + "Il player ha schivato il tuo colpo");
                        untouchable = false;
                    }
                }
                
            }
            else{
                already_dodge = false;
            }
            
        }

        
    }

    /* Player Movements */
    private void Attack(int enemy_status){
        this.PreSetBattleAction(player);

        setResultsAction(this.getResultsAction() + "Tiro su Forza\nD20: "+d20.getResult());
        
        d10.RollDice();
        setDamage(d10.getResult() + getBonus() + getModifier());

        if(enemy_status == 2){
            already_dodge = true;
            if(this.EnemyDodge() == 1){
                setResultsAction(this.getResultsAction() + "\nBonus Competenza (Forza): "+bonus+"\nModificatore (Forza): "+modifier+"\nEsito del tiro: "+ launch+"\nIl nemico ha schivato il colpo");
            }
            else{
                setResultsAction(this.getResultsAction() + "\nIl nemico ha provato a schivare ma con esito negativo");
            }  
        }
        /* Critical hit */
        if (d20.getResult() == 20){
            int add = 0;

            d6.RollDice();

            add = d6.getResult() + getBonus() + getModifier();

            enemy.getClassPgClass().DecreaseLife(getDamage()+add); 
            setResultsAction(this.getResultsAction() + "\nCritical Hit !!"+"\nBonus Competenza (Forza): "+getBonus()+"\nModificatore (Forza): "+getModifier()+"\nEsito del tiro: "+ getLaunch()+"\nDanno: "+getDamage()+" + "+add);
        }

        /* Critical Failure */
        else if (d20.getResult() == 1){
            double_hit = true;
            setResultsAction(this.getResultsAction() +"\nCritical Failure !!\nIl nemico può attaccare due volte per vantaggio!\nEsito del tiro: "+ getLaunch()); 
        }
        /*
         * If Sum of D20 result + Bonus player + Modifier is greater o equals then enemy guard, hit enemy rolling d10
         * Alltough, attack had no effect
         */
        else if ( getLaunch() >= enemy.getClassPgClass().getGuard() ){

            enemy.getClassPgClass().DecreaseLife(getDamage()); 
            setResultsAction(this.getResultsAction() + "\nDanno: "+getDamage()+"\nBonus Competenza (Forza): "+getBonus()+"\nModificatore (Forza): "+getModifier()+"\nEsito del tiro: "+ getLaunch());
        }
        else{ 
            setResultsAction(this.getResultsAction() + "\nColpo non andato a segno");
        }
    }

    private int EnemyAttack(){
        this.PreSetBattleAction(enemy);

        /*
         * If Sum of D20 result + Bonus player + Modifier is greater o equals then enemy guard, hit enemy rolling d10
         * Alltough, attack had no effect
         */
        if ( getLaunch() >= player.getClassPgClass().getGuard() ){
            d10.RollDice();

            setDamage(d10.getResult() + getBonus() + getModifier());
            player.getClassPgClass().DecreaseLife(getDamage());

            return getDamage();
        }

        return 0;
    }

    private int Dodge(){

        this.PreSetBattleDodge(player);

        if (getAction() <= 1){
            return -1;
        }

        if (getAction() >= enemy.getClassPgClass().getGuard()){
            return 1;
        }

        return 0;

    }

    /* Enemy AI (Move And Decision) */
    private int EnemyDodge(){

        this.PreSetBattleDodge(enemy);

        if (getAction() <= 1){
            return -1;
        }

        if (getAction() >= player.getClassPgClass().getGuard()){
            return 1;
        }

        return 0;

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
            setResultsAction("You Lost !");
        if(enemy.getClassPgClass().getLife() <= 0)
            setResultsAction("You Win!");
    }
}
