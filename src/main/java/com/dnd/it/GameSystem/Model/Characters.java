package com.dnd.it.GameSystem.Model;

import com.dnd.it.GameSystem.Classes.ClassPg;
import com.dnd.it.GameSystem.Races.Race;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Characters {
    private Race race;
    private ClassPg classPg;
    private StringProperty name;
    private IntegerProperty eta;
    private IntegerProperty level;
    private FloatProperty money; 
    private StringProperty Description;
    private StringProperty HystoryOfPg;

    public Characters(String name, int eta, Race race, ClassPg classPg){
        this.name = new SimpleStringProperty(name);
        this.race = race;
        this.classPg = classPg;
        this.eta = new SimpleIntegerProperty(eta);
        this.level = new SimpleIntegerProperty(1);
        this.money = new SimpleFloatProperty(0);
        this.Description = new SimpleStringProperty("Description");
        this.HystoryOfPg = new SimpleStringProperty("History");
    }

    /* Setter */
    public void setName(String name){
        this.name.set(name);
    }

    public void setLevel(int level){
        this.level.set(level);
    }

    public void setAge(int age){
        this.eta.set(age);
    }

    public void setDescription(String Description){
        this.Description.set(Description);
    }

    public void setHistoryOfPg(String History){
        this.HystoryOfPg.set(History);
    }

    public void setMoneyValue(float value){
        this.money.set(value);
    }

    /* Level */

    public void IncreaseLevel(){
        this.level.set(this.level.get() + 1);
    }

    public void IncreaseLevel(int quantity){
        this.level.set(this.level.get() + quantity);
    }

    /* Money */

    public void IncreaseMoney(){
        this.money.set(this.money.get() + 1);
    }

    public void IncreaseMoney(float value){
        this.money.set(this.money.get() + value);
    }

    public void DecreaseMoney(){
        if(this.money.get() > 0){
            this.money.set(this.money.get() - 1);
        }
    }

    public void DecreaseMoney(float value){
        if(this.money.get() > 0){
            this.money.set(this.money.get() - value);
        }
    }

    /* Getter */

    public Race getRaceClass(){
        return this.race;
    }

    public ClassPg getClassPgClass(){
        return this.classPg;
    }

    public String getName(){
        return this.name.get();
    }

    public int getLevel(){
        return this.level.get();
    }

    public int getAge(){
        return this.eta.get();
    }

    public String getDescription(){
        return this.Description.get();
    }

    public String getHistoryOfPg(){
        return this.HystoryOfPg.get();
    }

    public float getMoney(){
        return this.money.get();
    }

    /* Getter Property */

    public StringProperty getNameProperty(){
        return this.name;
    }

    public IntegerProperty getLevelProperty(){
        return this.level;
    }

    public IntegerProperty getAgeProperty(){
        return this.eta;
    }

    public StringProperty getDescriptionProperty(){
        return this.Description;
    }

    public StringProperty getHistoryOfPgProperty(){
        return this.HystoryOfPg;
    }

    public FloatProperty getMoneyProperty(){
        return this.money;
    }
}
