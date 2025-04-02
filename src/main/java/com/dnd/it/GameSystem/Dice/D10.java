package com.dnd.it.GameSystem.Dice;

import java.util.Random;

public class D10 {
     private int result;

    public D10(){
        this.result = 0;
    }

    public void RollDice(){
        result = new Random().nextInt(10) + 1;
    }

    public int getResult(){
        return result;
    }
}
