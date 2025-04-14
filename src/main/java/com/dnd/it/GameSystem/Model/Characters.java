package com.dnd.it.GameSystem.Model;

import com.dnd.it.GameSystem.Classes.ClassPg;
import com.dnd.it.GameSystem.Dice.D4;
import com.dnd.it.GameSystem.Dice.Dice;
import com.dnd.it.GameSystem.Races.Race;
import com.dnd.it.GameSystem.Weapon.Armi;

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
    private Boolean already_holding_weapon;
    private Dice diceforDamage;
    private int number_of_dice_for_dmg;
    private Armi current_holding_weapon;

    /* di base, ogni personaggio quando attacca a mani nude usa 1d4 per il danno */
    public Characters(String name, int eta, Race race, ClassPg classPg){
        this.name = new SimpleStringProperty(name);
        this.race = race;
        this.classPg = classPg;
        this.eta = new SimpleIntegerProperty(eta);
        this.level = new SimpleIntegerProperty(1);
        this.money = new SimpleFloatProperty(0);
        this.Description = new SimpleStringProperty("Description");
        this.HystoryOfPg = new SimpleStringProperty("History");
        this.already_holding_weapon = false;
        this.diceforDamage = new D4();
        this.number_of_dice_for_dmg = 1;
        this.current_holding_weapon = null;
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

    public void setAlready_Holding_weapon(Boolean bool){
        this.already_holding_weapon = bool;
    }

    public void setDiceForDamage(Dice dice){
        this.diceforDamage = dice;
    }

    public void setNumber_Of_Dice_For_DMG(int number){
        this.number_of_dice_for_dmg = number;
    }

    public void ResetDiceForDamage(){
        this.diceforDamage = new D4();
    }
    public void ResetNumber_Of_Dice_For_Dmg(){
        this.number_of_dice_for_dmg = 1;
    }

    public void setCurrent_Holding_Weapon(Armi arma){
        this.current_holding_weapon = arma;
    }

    public void ResetCurrent_Holding_Weapon(){
        this.current_holding_weapon = null;
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

    public Boolean Are_Already_Holding_Weapon(){
        return this.already_holding_weapon;
    }

    public Dice getDiceForDamage(){
        return this.diceforDamage;
    }

    public int getNumber_Of_Dice_For_Dmg(){
        return this.number_of_dice_for_dmg;
    }

    public Armi getCurrent_Holding_Weapon(){
        return this.current_holding_weapon;
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
