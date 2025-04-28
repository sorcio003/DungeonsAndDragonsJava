package com.dnd.it.GameSystem.Model;

import com.dnd.it.GameSystem.Races.Race;

public class Saver<T extends Race> {
    private T race;
    public void salva(T razza){
        this.race = razza; 
    }

    public T getRace(){
        return this.race;
    }
}
