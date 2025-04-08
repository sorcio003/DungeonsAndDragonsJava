package com.dnd.it.GameSystem;

import java.util.Random;

public class EnemyAI {

    private Random rand_num;
    private int decision;

    public EnemyAI(){
        this.decision = 0;
    }

    private void GenerateRandomNumber(){
        this.rand_num = new Random();
        setDecision(1 + rand_num.nextInt(100));
    }

    private Boolean EnemyAttackDecision(){
        GenerateRandomNumber();

        if(getDecision() >= 50)
            return true;
        
        return false;
    }

    private Boolean EnemyDodgeDecision(){
        GenerateRandomNumber();

        if(getDecision() >= 80)
            return true;
        
        return false;
    }

    public int EnemyAIDecision(){
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

    /* Setter */
    private void setDecision(int decision){
        this.decision = decision;
    }
    /* Getter */
    private int getDecision(){
        return this.decision;
    }
}
