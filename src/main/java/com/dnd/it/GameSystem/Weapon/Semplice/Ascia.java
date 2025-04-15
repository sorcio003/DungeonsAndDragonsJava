package com.dnd.it.GameSystem.Weapon.Semplice;

import com.dnd.it.GameSystem.Dice.D6;
import com.dnd.it.GameSystem.Weapon.Armi_Mischia;

public class Ascia extends Armi_Mischia{
    public Ascia(){
        super(new D6());
        super.setName("Ascia");
        super.setLife_Of_Weapon(3);
        super.setNumberofDice(1);
        super.setSecondType("Semplice");
        super.setProperty("Lancio");
        super.setCoolDown_Usability(5);
        super.setTime_Of_Usability(3);
        super.ResetCounter_Cooldown_Usability();
        super.ResetCounter_time_of_Usability();
    }
}
