package com.dnd.it.GameSystem.Dice;

import java.util.Random;

public class D6 {
     private int result;

    public D6(){
        this.result = 0;
    }

    public void RollDice(){
        result = new Random().nextInt(6) + 1;
    }

    public int getResult(){
        return result;
    }
}
