package com.dnd.it.GameSystem.Races;

import java.util.Arrays;

public class Golem extends Race{
    
    public Golem(){
        super("Golem");
        this.setSpeed(3);
        this.setStrength(14);
        this.setDexterity(5);
        this.setConstitution(8);
        this.setIntelligence(1);
        this.setWisdom(1);
        this.setCharism(1);
        this.setBonus("Strength", 2);
        this.setBonus("Constitution", 2);
        this.AddAbility( Arrays.asList("Pelle Di Roccia") );
    }
}
