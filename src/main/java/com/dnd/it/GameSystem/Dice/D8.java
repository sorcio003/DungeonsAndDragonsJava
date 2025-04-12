package com.dnd.it.GameSystem.Dice;

public class D8 extends Dice{
    public D8(){
        super.setDiceMaxValue(8);
    }
    public void RollD8(){
        super.RollDice();
    }
}
