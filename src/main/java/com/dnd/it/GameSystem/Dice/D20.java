package com.dnd.it.GameSystem.Dice;

public class D20 extends Dice{
    public D20(){
        super.setDiceMaxValue(20);
    }
    public void RollD20(){
        super.RollDice();
    }
}
