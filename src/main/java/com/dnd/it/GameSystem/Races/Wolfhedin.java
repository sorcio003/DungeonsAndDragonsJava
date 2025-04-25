package com.dnd.it.GameSystem.Races;

import java.util.Arrays;

public class Wolfhedin extends Race{
    
    public Wolfhedin(){
        super("Wolfhedin");
        this.setSpeed(7);//in teoria sarebbe 7,5
        this.setBonus("Dexterity", 2);
        this.setBonus("Strength", 1);
        // facciamo la prova con un wolfhedin che ha scelto il cammino della zanna rossa (Forza)
        this.setStrength(12);
        this.setDexterity(14);
        this.setConstitution(8);
        this.setIntelligence(4);
        this.setWisdom(4);
        this.setCharism(6);
        this.AddAbility( Arrays.asList("ScuroVisione", "Sensi Acuti", "Mutaforma", "Furia Lupina", "Atletica", "Intimidazione", "Furtività", "Acrobazia"));
    }
}
