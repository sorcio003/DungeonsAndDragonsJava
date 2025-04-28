package com.dnd.it.GameSystem.Races;

import java.util.Arrays;

public class Wolfhedin extends Race{

    private Boolean is_Transformed;
    private int time_of_transformation;
    
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
        // la lista abilità è una lista che contiene le abilità basate sulle caratteritiche 
        this.AddAbility( Arrays.asList("Furtività", "Acrobazia", "Atletica", "Intimidazione"));
        // lista dei tratti della razza
        this.AddTraits( Arrays.asList("ScuroVisione", "Sangue Antico", "Furia Lupina"));
        this.is_Transformed = false;
        this.time_of_transformation = 0; 
    }

    public Boolean Is_Transformed(){
        return this.is_Transformed;
    }
    public void setTransformationBool(Boolean bool){
        this.is_Transformed = bool;
    }

    public int getTimeOfTransformation(){
        return this.time_of_transformation;
    }
    public void setTimeOfTransformation(int time){
        this.time_of_transformation = time;
    }
    public void IncreaseTimeOfTransformation(){
        this.time_of_transformation ++;
    }
    public void IncreaseTimeOfTransformation(int time){
        this.time_of_transformation += time;
    }
    public void DecreaseTimeOfTransformation(){
        this.time_of_transformation --;
    }
    public void DecreaseTimeOfTransformation(int time){
        this.time_of_transformation -= time;
    }
}
