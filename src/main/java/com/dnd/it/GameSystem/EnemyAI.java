package com.dnd.it.GameSystem;

import java.util.Random;

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

    private Random rand_num;
    private int probability;
    private int decision;
    private int block_of_movements;

    public EnemyAI(){
        this.block_of_movements = 3;
        this.decision = 0;
        this.probability = 0;
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

    private Boolean EnemyDodgeDecision(){
        GenerateRandomProbability();

        if(this.getProbability() >= 70)
            return true;
        
        return false;
    }

    public void EnemyAIDecision(int d20PlayerResults){
        /* se l'ultima decisione presa, quindi la decisione precedente, è uguale a 3 e il numero di movimenti non è calato sotto a 0, continua a decidere di muoverti */
        if(this.getDecision() == 3 && this.getBlock_Of_Movements() > 0){
            this.decision = 3;
            this.DecreaseBlock_Of_Movements();
        }
        else{
            /* Reset a 3 */
            this.setBlock_Of_Movements(3);
            /* se il player ha ottenuto un fallimento critico automaticamente il nemico attacca */
            if (d20PlayerResults == 1){
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
    private void setBlock_Of_Movements(int block){
        this.block_of_movements = block;
    }
    private void DecreaseBlock_Of_Movements(){
        this.block_of_movements --;
    }
    /* Getter */
    private int getProbability(){
        return this.probability;
    }
    public int getDecision(){
        return this.decision;
    }
    private int getBlock_Of_Movements(){
        return this.block_of_movements;
    }
}
