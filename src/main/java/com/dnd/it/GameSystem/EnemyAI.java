package com.dnd.it.GameSystem;

import java.util.Random;

import com.dnd.it.GameSystem.Model.Characters;

public class EnemyAI {

    /* la enemyAi deve prendere decisione di:
     * Attaccare, in base al fatto se è vicino al nemico, la come è gestita anche adesso va benissimo.
     * Muoversi, se il nemico decide di muoversi vorrei che si muovesse:
     * - o in direzione del player a inizio battaglia
     * - o allontanarsi quando decide di scappare dalla battaglia, quindi si allontanerà finchè non sarà lontano 3 blocchi dal player (una volta fatti questi 3 blocchi di movimento, le decisioni torneranno normali)
     *      - se il player si riavvicina, il nemico si presuppone decida di tentare di schivare (almeno una volta)
     * 
     * Se il player ha ottenuto un d20 == 1, fallimento critico, voglio che il nemico automaticamente attacchi !
     */

    private Characters player;
    private Characters enemy;
    private Random rand_num;
    private int lastd20results;
    private int probability;
    private int decision;
    private int max_prob_to_dodge;

    public EnemyAI(Characters player, Characters enemy){
        this.player = player;
        this.enemy = enemy;
        this.decision = 0;
        this.probability = 0;
        this.lastd20results = 0;
        this.max_prob_to_dodge = 70;
    }

    private void GenerateRandomProbability(){
        this.rand_num = new Random();
        setProbability(1 + rand_num.nextInt(100));
    }

    private Boolean EnemyAttackDecision(){
        GenerateRandomProbability();

        if(this.getProbability() >= 50)
            return true;
        
        return false;
    }

    public void ResetProbability_To_Dodge(){
        this.max_prob_to_dodge = 70;
    }

    private Boolean EnemyDodgeDecision(){
        GenerateRandomProbability();

        /* se la vita del nemico cala, aumenta la probabilità di schivare */
        if(enemy.getClassPgClass().getLife() < 30){
            this.IncreaseMax_prob_To_Dodge(-20);
        }

        if(this.getProbability() >= this.getMax_Prob_To_Dodge())
            return true;
        
        return false;
    }

    public void EnemyAIDecision(int d20PlayerResults){
            /* se il player ha ottenuto un fallimento critico automaticamente il nemico attacca */
            /* se l'ultimo d20 results del nemico è stato >= 15, per 'stupidità umana' deciderà di attaccare nuovamente  */
            /* obbligo di attacco se la differenza tra la vita del nemico e la vita del player è di 30 (naturalmente se il nemico ha almeno 30 punti vita in più) */
            if(d20PlayerResults == 0){
                /* se il player ha deciso di lanciare l'arma, il nemico avrà più probabilità di schivare (probabilità da superar 70-30 = 40 su 100, quindi il 60% di prob.) */
                this.IncreaseMax_prob_To_Dodge(-30);
                if(this.EnemyDodgeDecision()){
                    System.out.println("Il nemico schiva perchè lancia arma");
                    this.setDecision(2);
                }
                else{
                    this.setDecision(3); /* 3 indica il fatto che il nemico si muove invece di difendersi o attaccare */
                }
            }
            else{
                this.ResetProbability_To_Dodge();
                if (d20PlayerResults == 1 || this.getLastD20Results() >= enemy.getClassPgClass().getGuard() || (this.enemy.getClassPgClass().getLife() - this.player.getClassPgClass().getLife()) > 30 ){
                    this.setDecision(1);
                }
                /* altirmenti */
                else{
                    if (this.EnemyAttackDecision()){
                        this.setDecision(1);
                    }
                    else if(this.EnemyDodgeDecision()){
                        this.setDecision(2);
                    }
                    else{
                        this.setDecision(3); /* 3 indica il fatto che il nemico si muove invece di difendersi o attaccare */
                    }
                }
            }
    }

    /* Setter */
    private void setProbability(int probability){
        this.probability = probability;
    }
    private void setDecision(int decision){
        this.decision = decision;
    }
    public void setLastD20Results(int d20){
        this.lastd20results = d20;
    }
    /*
    private void setMax_prob_To_Dodge(int prob){
        this.max_prob_to_dodge = prob;
    }
    private void IncreaseMax_prob_To_Dodge(){
        this.max_prob_to_dodge++;
    }
    */
    private void IncreaseMax_prob_To_Dodge(int value){
        this.max_prob_to_dodge += value;
    }
    /* Getter */
    private int getProbability(){
        return this.probability;
    }
    public int getDecision(){
        return this.decision;
    }
    private int getLastD20Results(){
        return this.lastd20results;
    }
    private int getMax_Prob_To_Dodge(){
        return this.max_prob_to_dodge;
    }
}
