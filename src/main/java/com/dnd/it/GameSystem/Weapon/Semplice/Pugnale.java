package com.dnd.it.GameSystem.Weapon.Semplice;

import com.dnd.it.GameSystem.Dice.D4;
import com.dnd.it.GameSystem.Weapon.Armi_Mischia;

public class Pugnale extends Armi_Mischia{
    public Pugnale(){
        super(new D4());
        super.setName("Pugnale");
        super.setLife_Of_Weapon(8);
        super.setDistance(0);
        super.setNumberofDice(2);
        super.setSecondType("Semplice");
        super.setProperty("Accurata");
    }
}
