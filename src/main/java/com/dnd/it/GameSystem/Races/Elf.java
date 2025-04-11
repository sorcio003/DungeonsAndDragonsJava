package com.dnd.it.GameSystem.Races;

import java.util.Arrays;

public class Elf extends Race{
    
    public Elf(){
        super("Elf");
        this.setSpeed(9);
        this.setStrength(12);
        this.setDexterity(14);
        this.setConstitution(6);
        this.setIntelligence(12);
        this.setWisdom(12);
        this.setCharism(5);
        this.setBonus("Dexterity", 2);
        this.setBonus("Intelligence", 2);
        this.setBonus("Strength", 2);
        this.AddAbility( Arrays.asList("ScuroVisione", "Sensi Acuti", "Retaggio", "Trance") );
    }
    
}