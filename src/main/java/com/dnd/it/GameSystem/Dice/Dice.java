package com.dnd.it.GameSystem.Dice;

import java.util.Random;

public class Dice {
    private int result;
    private int range;

    public Dice(){
        this.result = 0;
        this.range = 0;
    }

    public void RollDice(){
        result = new Random().nextInt(this.range) + 1;
    }

    public int getResult(){
        return result;
    }

    public void setDiceMaxValue(int range){
        this.range = range;
    }
    public int getDiceMaxValue(){
        return this.range;
    }
}
