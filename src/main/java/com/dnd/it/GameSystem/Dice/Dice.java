package com.dnd.it.GameSystem.Dice;

import java.util.Random;

public class Dice {
    private int result;

    public Dice(){
        this.result = 0;
    }

    public void RollDice(int range){
        result = new Random().nextInt(range) + 1;
    }

    public int getResult(){
        return result;
    }
}
