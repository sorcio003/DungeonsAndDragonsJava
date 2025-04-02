package com.dnd.it.GameSystem.Classes;

import java.util.Arrays;

public class Barbaro extends ClassPg{
    
    public Barbaro(){
        super("Barbaro");
        this.setLife(100);
        this.setGuard(12);
        this.addPrivilegi(Arrays.asList("Ira", "Difesa Senza Armatura"));
    }
}
