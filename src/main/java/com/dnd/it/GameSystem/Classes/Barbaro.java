package com.dnd.it.GameSystem.Classes;

import java.util.Arrays;

public class Barbaro extends ClassPg{
    
    public Barbaro(){
        super("Barbaro");
        this.setLife(100);
        this.setGuard(12);
        this.setBonus(2);
        this.setModificatore(2);
        this.setStrength(10);
        this.setDexterity(5);
        this.setIntelligence(2);
        this.setConstitution(3);
        this.setCharism(2);
        this.setWisdom(2);
        this.setDodgePoints(getBonus()+getmodificatore());
        this.addPrivilegi(Arrays.asList("Ira", "Difesa Senza Armatura"));
    }
}
