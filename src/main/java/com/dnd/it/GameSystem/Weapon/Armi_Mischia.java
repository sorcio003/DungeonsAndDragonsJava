package com.dnd.it.GameSystem.Weapon;

import com.dnd.it.GameSystem.Dice.Dice;

public class Armi_Mischia extends Armi{
    public Armi_Mischia(Dice dice){
        super(dice);
        super.setFirstType("Mischia");
        /* la distanza minima che ci deve essere tra il player e il nemico è di zero, casella accanto */
        super.setDistance(0);
    }
}
