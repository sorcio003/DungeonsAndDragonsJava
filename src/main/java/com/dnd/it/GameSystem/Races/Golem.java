package com.dnd.it.GameSystem.Races;

import java.util.Arrays;

public class Golem extends Race{
    
    public Golem(){
        super("Golem");
        this.setSpeed(3);
        this.AddAbility( Arrays.asList("Pelle Di Roccia") );
    }
}
