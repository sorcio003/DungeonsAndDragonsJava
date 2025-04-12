package com.dnd.it.GameSystem.Dice;

public class D10 extends Dice{
    public D10(){
        super.setDiceMaxValue(10);
    }
    public void RollD10(){
        super.RollDice();
    }
}
