package com.dnd.it.GameSystem;
import java.util.Random;
import com.dnd.it.GameSystem.Dice.*;
import com.dnd.it.GameSystem.Model.Characters;

public class Game {

    private Characters player;
    private Characters enemy;
    private Boolean untouchable;
    private Boolean double_hit;
    private Boolean already_dodge;
    private Boolean player_tried_to_dodge;
    private D20 d20;
    private D10 d10;
    private D6 d6;

    public Game(Characters player, Characters enemy){
        this.player = player;
        this.enemy = enemy;
        this.untouchable = false;
        this.already_dodge = false;
        this.double_hit = false;
        this.player_tried_to_dodge = false;
        this.d20 = new D20();
        this.d10 = new D10();
        this.d6 = new D6();
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

    public String BattleTurn(String input, int enemy_moves){
        String results = "";
        if (input.equals("Attacca")){
            results = "Hai deciso di Attaccare...";

            String attack_result = this.Attack(enemy_moves);
            results += "\n" + attack_result+"\n";
            untouchable = false;
        }

        if (input.equals("Schiva")){

            results = "Hai deciso di schivare...\n";
            player_tried_to_dodge = true;

            if (Dodge() == -1){
                results += "Il lancio ha avuto come esito un 1 critico!!\nIl nemico di Attacca può attaccare due volte per vantaggio.";
                untouchable = false;
                //results += "\nIl nemico ti infligge un danno pari a "+ EnemyAttack() + EnemyAttack();
            }
            else if (Dodge() == 1){
                results += "Il lancio ha avuto esito positivo, il nemico non ti può attaccare";
                untouchable = true;
            }
            else{
                results += "Il lancio ha avuto esito negativo. Il nemico ti può attaccare.\n";//Il nemico ti infligge un danno pari a "+ EnemyAttack();
                untouchable = false;
            }
        }
        if(input.equals("Azione Nemico")){
            if(enemy_moves == 2 && player_tried_to_dodge){
                enemy_moves = 1;
                player_tried_to_dodge = false;
            }
            if(! already_dodge ){
                if(enemy_moves == 3 ){
                    results += "Il nemico si muove";
                }

                if(enemy_moves == 1){
                    if(!untouchable){
                        results += "Il nemico ti attacca...\nDanno: "+this.EnemyAttack()+"\n";
                        if(double_hit){
                            results += "Il nemico ti attacca una seconda volta...\n"+this.EnemyAttack()+"\n";
                            double_hit = false;
                        }
                    }
                    else{
                        results += "Il player ha schivato il tuo colpo";
                        untouchable = false;
                    }
                }
                
            }
            else{
                already_dodge = false;
            }
            
        }

        return results;
    }

    private String Attack(int enemy_status){
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
        d10.RollDice();
        damage = d10.getResult() + bonus + modifier;

        if(enemy_status == 2){
            already_dodge = true;
            if(this.EnemyDodge() == 1){
                return results+"\nBonus Competenza (Forza): "+bonus+"\nModificatore (Forza): "+modifier+"\nEsito del tiro: "+ launch+"\nIl nemico ha schivato il colpo";
            }
            else{
                results += "\nIl nemico ha provato a schivare ma con esito negativo";
            }  
        }
        /* Critical hit */
        if (d20.getResult() == 20){
            int add = 0;

            d6.RollDice();

            add = d6.getResult() + bonus + modifier;

            enemy.getClassPgClass().DecreaseLife(damage+add); 

            return results+"\nCritical Hit !!"+"\nBonus Competenza (Forza): "+bonus+"\nModificatore (Forza): "+modifier+"\nEsito del tiro: "+ launch+"\nDanno: "+damage+" + "+add;
        }

        /* Critical Failure */
        else if (d20.getResult() == 1){
            double_hit = true;
            return results+"\nCritical Failure !!\nIl nemico può attaccare due volte per vantaggio!\nEsito del tiro: "+ launch;
        }
        /*
         * If Sum of D20 result + Bonus player + Modifier is greater o equals then enemy guard, hit enemy rolling d10
         * Alltough, attack had no effect
         */
        else if ( launch >= enemy.getClassPgClass().getGuard() ){

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

    private int EnemyDodge(){

        d20.RollDice();
        enemy.getRaceClass().setModificatore(enemy.getRaceClass().getDexterity());
        int action = d20.getResult() + enemy.getRaceClass().getmodificatore() + enemy.getRaceClass().getBonus("Dexterity");

        if (action <= 1){
            return -1;
        }

        if (action >= player.getClassPgClass().getGuard()){
            return 1;
        }

        return 0;

    }

    public Boolean EnemyAttackDecision(){
        Random rand_num = new Random();

        int decision = 1 + rand_num.nextInt(100);
        if(decision >= 50)
            return true;
        
        return false;
    }

    public Boolean EnemyDodgeDecision(){
        Random rand_num = new Random();

        int decision = 1 + rand_num.nextInt(100);
        if(decision >= 80)
            return true;
        
        return false;
    }

    public int EnemyAI(){
        if (this.EnemyAttackDecision()){
            return 1;
        }
        else if(this.EnemyDodgeDecision()){
            return 2;
        }
        else{
            return 3; /* 3 indica il fatto che il nemico si muove invece di difendersi o attaccare */
        }
    }

    public Boolean CheckEndBattle(){
        if(player.getClassPgClass().getLife() <= 0 || enemy.getClassPgClass().getLife() <= 0)
            return true;
        return false;
    }
    public String EndBattle(){
        String results = "";
        if(player.getClassPgClass().getLife() <= 0)
            results = "You Lost !";
        if(enemy.getClassPgClass().getLife() <= 0)
            results = "You Win!";
        
        return results;
    }
}
