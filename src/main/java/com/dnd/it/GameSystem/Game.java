package com.dnd.it.GameSystem;
import com.dnd.it.GameSystem.Dice.*;
import com.dnd.it.GameSystem.Model.Characters;

public class Game {

    private Characters player;
    private Characters enemy;
    private D20 d20;
    private D10 d10;
    private D6 d6;

    public Game(Characters player, Characters enemy){
        this.player = player;
        this.enemy = enemy;
        this.d20 = new D20();
        this.d10 = new D10();
        this.d6 = new D6();
    }

    /* Algorithms for Battle */

    /* Player turn */

    /*
     * Il giocatore ha tre opzioni, per iniziare con il codice gli do solo la possibilità di attaccare o schivare
     * 
     * Attacco:
     *  IL giocatore decide di attaccare, quindi tira un dado D20 + bonus_competenza + modificatore per vedere se il colpo fa a segno (se è >= alla guard del nemico)
     *  - Se il D20 + bonus_competenza + modificatore < guard, il colpo non ha effetto, e si passa al turno del nemico ( c'è da gestire il 20 critcio o 1 critico)
     *  - Se il D20 + bonus_competenza + modificatore >= guard, allora il giocatore (in base al suo personaggio, ma solo per il codice di prova) 
     *    tira un D10 + modificatore + bouns_competenza ( se esce fuori un 10 dal D10, hai diritto ad un secondo tiro di un D6 + modificatore + bonus_competenza)
     */

    public String BattleTurn(String input){
        String results = "";
        if (input.equals("Attacca")){
            results = "Hai deciso di Attaccare...";

            String attack_result = this.Attack();

            if(attack_result.equals("critical failure")){
                results += "Hai ottenuto un fallimento critico!!\n"+
                           "Il nemico di attacca due volte per vantaggio!\n"+
                           "Il nemico ti ha attaccato con un danno pari a "+ this.EnemyAttack() +" + "+ this.EnemyAttack();
            }
            else{
                results += "\n" + attack_result+"\n"+
                           "Il nemico ti ha attaccato con un danno pari a "+ this.EnemyAttack();
            }
        }
        else if(input.equals("Attacco Nemico")){
            results += "Il nemico ti ha attaccato con un danno pari a "+ this.EnemyAttack();
        }

        else if (input.equals("Schiva")){

            results = "Hai deciso di schivare...\n";

            if (Dodge() == -1){
                results += "Il lancio ha avuto come esito un 1 critico!!\nIl nemico di Attacca due volte per vantaggio.";
                results += "\nIl nemico ti infligge un danno pari a "+ EnemyAttack() + EnemyAttack();
            }
            else if (Dodge() == 1){
                results += "Il lancio ha avuto esito positivo, il nemico non ti può attaccare";
            }
            else{
                results += "Il lancio ha avuto esito negativo. Il nemico ti attacca.\nIl nemico ti infligge un danno pari a "+ EnemyAttack();
            }
        }


        return results;
    }

    private String Attack(){

        String results = "";

        int bonus = player.getRaceClass().getBonus("Strength");
        player.getRaceClass().setModificatore(player.getRaceClass().getStrength());
        int modifier = player.getRaceClass().getmodificatore();
        int damage = 0;

        /* Roll D20 to attack enemy */
        d20.RollDice();

        int launch = d20.getResult() + bonus;
        if(modifier > 0) launch += modifier;

        results += "Tiro su Forza\nD20: "+d20.getResult();

        /* Critical hit */
        if (d20.getResult() == 20){
            d10.RollDice();

            damage = d10.getResult() + bonus + modifier;

            enemy.getClassPgClass().DecreaseLife(damage);

            d6.RollDice();

            damage += d6.getResult() + bonus + modifier;

            enemy.getClassPgClass().DecreaseLife(damage);

            return results+"\nCritical Hit !!"+"\nBonus Competenza (Forza): "+bonus+"\nModificatore (Forza): "+modifier+"\nEsito del tiro: "+ launch+"\nDanno: "+damage;
        }

        /* Critical Failure */
        else if (d20.getResult() == 1){
            return results+"\nCritical Failure !!\nEsito del tiro: "+ launch;
        }

        /*
         * If Sum of D20 result + Bonus player + Modifier is greater o equals then enemy guard, hit enemy rolling d10
         * Alltough, attack had no effect
         */
        else if ( launch >= enemy.getClassPgClass().getGuard() ){
            d10.RollDice();

            damage = d10.getResult() + bonus + modifier;

            enemy.getClassPgClass().DecreaseLife(damage);

            return results+"\nDanno: "+damage+"\nBonus Competenza (Forza): "+bonus+"\nModificatore (Forza): "+modifier+"\nEsito del tiro: "+ launch;
        }

        return results + "\nColpo non andato a segno";
    }

    private int EnemyAttack(){
        int bonus = enemy.getRaceClass().getBonus("Strength");
        enemy.getRaceClass().setModificatore(player.getRaceClass().getStrength());
        int modifier = enemy.getRaceClass().getmodificatore();

        /* Roll D20 to attack enemy */
        d20.RollDice();

        /*
         * If Sum of D20 result + Bonus player + Modifier is greater o equals then enemy guard, hit enemy rolling d10
         * Alltough, attack had no effect
         */
        if ( (d20.getResult() + bonus + modifier) >= player.getClassPgClass().getGuard() ){
            d10.RollDice();

            player.getClassPgClass().DecreaseLife(d10.getResult() + bonus + modifier);

            return d10.getResult() + bonus + modifier;
        }

        return 0;
    }

    private int Dodge(){

        d20.RollDice();
        player.getRaceClass().setModificatore(player.getRaceClass().getDexterity());
        int action = d20.getResult() + player.getRaceClass().getmodificatore() + player.getRaceClass().getBonus("Dexterity");

        if (action <= 1){
            return -1;
        }

        if (action >= enemy.getClassPgClass().getGuard()){
            return 1;
        }

        return 0;

    }
}
