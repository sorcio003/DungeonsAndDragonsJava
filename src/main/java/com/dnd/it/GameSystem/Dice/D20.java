package com.dnd.it.GameSystem.Dice;

import java.util.Random;

public class D20 {
    private int result;

    public D20(){
        this.result = 0;
    }

    public void RollDice(){
        result = new Random().nextInt(20) + 1;
    }

    public int getResult(){
        return result;
    }
}
