package com.dnd.it.GameSystem.Races;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Race {
    private StringProperty race;
    private IntegerProperty speed; // speed (meters for units)
    private List<StringProperty> Ability;

    public Race(String race){
        this.race = new SimpleStringProperty(race);
        this.speed = new SimpleIntegerProperty(0);
        this.Ability = new ArrayList<StringProperty>();
    }

    /* Setter */

    public void setSpeed(int speedformeters){
        this.speed.set(speedformeters);
    }

    public void setRace(String race){
        this.race.set(race);
    }

    /* Getter */

    public int getSpeed(){
        return this.speed.get();
    }

    public String getRace(){
        return this.race.get();
    }

    /* Getter Property */

    public IntegerProperty getSpeedProperty(){
        return this.speed;
    }

    public StringProperty getSpeedStringProperty(){
        return new SimpleStringProperty(this.speed.toString());
    }

    public StringProperty getRaceProperty(){
        return this.race;
    }


    /* Ability */

    public void AddAbility(String ability){
        this.Ability.add(new SimpleStringProperty(ability));
    }

    public void AddAbility(List<String> abilities){
        for (String ability : abilities) {
            this.Ability.add(new SimpleStringProperty(ability));
        }
    }

    public void RemoveAbilityByName(String ability){
        this.Ability.remove(new SimpleStringProperty(ability));
    }

    public void RemoveAbilityByID(int id){
        if (id >= 0){
            this.Ability.remove(id);
        }
    }

    public List<String> getAbilityList(){
        return this.Ability.stream().map((ability) -> ability.get()).collect(Collectors.toList());
    }

    public List<StringProperty> getAbilityListProperty(){
        return this.Ability;
    }
    

    public StringProperty getAbilityPropertyByID(int id){
        return this.Ability.get(id);
    }

    public String getAbilityByID(int id){
        return this.Ability.get(id).get();
    }
}
