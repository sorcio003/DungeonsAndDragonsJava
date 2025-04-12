package com.dnd.it.GameSystem.Dice;

public class D6 extends Dice{
    public D6(){
        super.setDiceMaxValue(6);
    }
    public void RollD6(){
        super.RollDice();
    }
}
