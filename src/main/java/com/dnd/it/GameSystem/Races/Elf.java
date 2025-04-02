package com.dnd.it.GameSystem.Races;

import java.util.Arrays;

public class Elf extends Race{
    
    public Elf(){
        super("Elf");
        this.setSpeed(9);
        this.AddAbility( Arrays.asList("ScuroVisione", "Sensi Acuti", "Retaggio", "Trance") );
    }
    
}