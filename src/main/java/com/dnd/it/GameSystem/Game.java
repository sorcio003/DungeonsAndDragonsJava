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
            results = "Hai deciso di Attaccare...\n";

            String attack_result = this.Attack();

            if(attack_result.equals("critical failure")){
                results += "Hai ottenuto un fallimento critico!!\n"+
                           "Il nemico di attacca due volte per vantaggio!\n"+
                           "Il nemico ti ha attaccato con un danno pari a "+ this.EnemyAttack() +" + "+ this.EnemyAttack();
            }
            else{
                results += "Hai fatto un danno pari a "+attack_result+"\n"+
                           "Il nemico ti ha attaccato con un danno pari a "+ this.EnemyAttack();
            }
        }
        else if(input.equals("Attacco Nemico")){
            results += "Il nemico ti ha attaccato con un danno pari a "+ this.EnemyAttack();
        }

        else if (input.equals("Schiva")){
            if (player.getClassPgClass().getDodgePoints() > 0){

                player.getClassPgClass().DecreaseDodgePoints();

                results = "Hai deciso di schivare...\nLivello di Dodge Points: "+player.getClassPgClass().getDodgePoints()+"\n";

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
            else{
                results = "Non puoi più schivare!\nil livello dei tuoi Dodge Points è "+player.getClassPgClass().getDodgePoints();
            }
        }


        return results;
    }

    private String Attack(){

        int bonus = player.getClassPgClass().getBonus();
        int modifier = player.getClassPgClass().getmodificatore();
        int damage = 0;

        /* Roll D20 to attack enemy */
        d20.RollDice();

        /* Critical hit */
        if (d20.getResult() == 20){
            d10.RollDice();

            damage = d10.getResult() + bonus + modifier;

            enemy.getClassPgClass().DecreaseLife(damage);

            d6.RollDice();

            damage += d6.getResult() + bonus + modifier;

            enemy.getClassPgClass().DecreaseLife(damage);

            return "Danno critico (d20 = 20): "+damage;
        }

        /* Critical Failure */
        else if (d20.getResult() == 1){
            return "critical failure";
        }

        /*
         * If Sum of D20 result + Bonus player + Modifier is greater o equals then enemy guard, hit enemy rolling d10
         * Alltough, attack had no effect
         */
        else if ( (d20.getResult() + bonus + modifier) >= enemy.getClassPgClass().getGuard() ){
            d10.RollDice();

            damage = d10.getResult() + bonus + modifier;

            enemy.getClassPgClass().DecreaseLife(damage);

            return ""+damage+"\nD20 Results: "+d20.getResult();
        }

        return "0"+"\nD20 Results: "+d20.getResult();
    }

    private int EnemyAttack(){
        int bonus = enemy.getClassPgClass().getBonus();
        int modifier = enemy.getClassPgClass().getmodificatore();

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

        int bonus = player.getClassPgClass().getBonus();
        int modifier = player.getClassPgClass().getmodificatore();

        d20.RollDice();

        if (d20.getResult() == 1){
            return -1;
        }

        if ((d20.getResult() + bonus + modifier) >= player.getClassPgClass().getGuard()){
            return 1;
        }

        return 0;

    }

    private int EnemyDodge(){
        int bonus = enemy.getClassPgClass().getBonus();
        int modifier = enemy.getClassPgClass().getmodificatore();

        d20.RollDice();

        if (d20.getResult() == 1){
            return -1;
        }

        if ((d20.getResult() + bonus + modifier) >= enemy.getClassPgClass().getGuard()){
            return 1;
        }

        return 0;
    }
}
