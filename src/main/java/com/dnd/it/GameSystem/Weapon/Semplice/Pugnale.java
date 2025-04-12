package com.dnd.it.GameSystem.Weapon.Semplice;

import com.dnd.it.GameSystem.Dice.D4;
import com.dnd.it.GameSystem.Weapon.Armi_Mischia;

public class Pugnale extends Armi_Mischia{
    public Pugnale(){
        super(new D4());
        super.setName("Pugnale");
        super.setNumberofDice(1);
        super.setSecondType("Semplice");
        super.setProperty("Accurata");
    }
}
