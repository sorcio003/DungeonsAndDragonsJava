package com.dnd.it.GameSystem.Dice;

public class D4 extends Dice{
    public D4(){
        super.setDiceMaxValue(4);
    }
    public void RollD4(){
        super.RollDice();
    }
}
