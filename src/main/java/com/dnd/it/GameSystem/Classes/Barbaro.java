package com.dnd.it.GameSystem.Classes;

import java.util.Arrays;

import com.dnd.it.GameSystem.Weapon.Semplice.Ascia;
import com.dnd.it.GameSystem.Weapon.Semplice.Pugnale;

public class Barbaro extends ClassPg{
    
    public Barbaro(){
        super("Barbaro");
        this.setLife(100);
        this.setGuard(12);
        this.addonWeaponList(new Ascia());
        this.addonWeaponList(new Pugnale());
        this.addPrivilegi(Arrays.asList("Ira", "Difesa Senza Armatura"));
    }
}
