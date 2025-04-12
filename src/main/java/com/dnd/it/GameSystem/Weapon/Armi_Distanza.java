package com.dnd.it.GameSystem.Weapon;

import com.dnd.it.GameSystem.Dice.Dice;

public class Armi_Distanza extends Armi{
    public Armi_Distanza(Dice dice){
        super(dice);
        super.setFirstType("Distanza");
        /* la distanza minima che ci deve essere tra il player e il nemico è di zero, casella accanto */
        super.setDistance(3);
    }
}
