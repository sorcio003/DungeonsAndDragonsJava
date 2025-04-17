package com.dnd.it.GameSystem.Weapon.Semplice;

import com.dnd.it.GameSystem.Dice.D6;
import com.dnd.it.GameSystem.Weapon.Armi_Mischia;

public class Ascia extends Armi_Mischia{
    public Ascia(){
        super(new D6());
        super.setName("Ascia");
        super.setLife_Of_Weapon(12);
        super.setNumberofDice(1);
        super.setSecondType("Semplice");
        super.setProperty("Lancio");
    }
}
