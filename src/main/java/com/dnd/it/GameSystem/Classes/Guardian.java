package com.dnd.it.GameSystem.Classes;

import java.util.Arrays;

public class Guardian extends ClassPg{
    public Guardian(){
        super("Guardiano");
        this.setLife(100);
        this.setGuard(13);
        this.addPrivilegi(Arrays.asList("Difesa Impenetrabile"));
    }
}
