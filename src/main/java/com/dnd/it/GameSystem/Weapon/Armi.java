package com.dnd.it.GameSystem.Weapon;

import com.dnd.it.GameSystem.Dice.Dice;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Armi {
    /* Array di stringhe a due elemnti che sono 1 [mischia o distanza] 2 [semplice, guerra] */
    private String[] types;
    private String name;
    private int number_of_dices;
    private Dice dice;
    /* Attualmente non utili */
    //private int price;
    //private int weight; 
    /* è la proprietà dell'arma, come ad esempio se è accurata, aumenterà la pssobilità di colpire il bersaglio */
    private String property;
    private int damage;
    private int distance;
    /* Conterrà */
    private StringProperty[] weaponDescription;
    private Boolean is_Holding_a_Weapon;
    private StringProperty holding_Property;

    public Armi(Dice dice){
        this.types = new String[2];
        this.name = "";
        this.number_of_dices = 0;
        this.dice = dice;
        this.property = "";
        this.damage = 0;
        this.distance = 0;
        /*Array di 3 elmenti che conterrà <name, no_dice/dice (1d6), property> nella table ci sarà anche il btn is_hoding or suit per indossare l'arma */
        this.weaponDescription = new StringProperty[3];
        this.weaponDescription[0] = new SimpleStringProperty(this.name);
        this.weaponDescription[1] = new SimpleStringProperty(new String(this.number_of_dices+"d"+this.dice.getDiceMaxValue()));
        this.weaponDescription[2] = new SimpleStringProperty(this.property);
        this.is_Holding_a_Weapon = false;
        this.holding_Property = new SimpleStringProperty(is_Holding_a_Weapon.toString());
    }

    /* Methods */
    /* per calcolare il danno, in base al numero di ddo che posso lanciare, numeri di volte che posso lanciare il dado
     * Lancio il dado
     * incremento il danno del risultato del Dice roll + bonus + modifier
     */
    private void CalculateDamage(int bonus, int modifier){
        for (int i = 0; i < this.number_of_dices; i++) {
            this.dice.RollDice();
            this.IncreaseDamage(this.dice.getResult() + bonus + modifier);
        }
        
    }

    /* Setter */
    public void setFirstType(String type){
        this.types[0] = type;
    }
    public void setSecondType(String type){
        this.types[1] = type;
    }
    public void setName(String name){
        this.name = name;
        this.setNameinWeaponsDescription(this.name);
    }
    public void setNumberofDice(int number_of_dice){
        this.number_of_dices = number_of_dice;
        this.setDiceinWeaponsDescription(this.number_of_dices+"d"+this.dice.getDiceMaxValue());
    }
    public void setDice(Dice dice){
        this.dice = dice;
    }
    public void setProperty(String property){
        this.property = property;
        this.setPropertyinWeaponsDescription(this.property);
    }
    public void setDistance(int distance){
        this.distance = distance;
    }
    public void setDamage(int damage){
        this.damage = damage;
    }
    public void IncreaseDamage(int damage){
        this.damage += damage;
    }
    public void setWeaponsDescriptionByID(String string, int id){
        if(id >= 0 && id < 3){
            this.weaponDescription[id] = new SimpleStringProperty(string);
        }
    }

    private void setNameinWeaponsDescription(String string){
        this.weaponDescription[0] = new SimpleStringProperty(string);
    }

    private void setDiceinWeaponsDescription(String string){
        this.weaponDescription[1] = new SimpleStringProperty(string);
    }

    private void setPropertyinWeaponsDescription(String string){
        this.weaponDescription[2] = new SimpleStringProperty(string);
    }

    public void set_Holding_Weapon(Boolean bool){
        this.is_Holding_a_Weapon = bool;
    }

    public void setHoldingProperty(){
        this.holding_Property = new SimpleStringProperty(this.is_Holding_a_Weapon.toString());
    }

    public StringProperty getHoldingProperty(){
        return this.holding_Property;
    }

    /* Is Holding */
    public String getHoldingPropertyasString(){
        return this.holding_Property.get();
    }

    public Boolean Is_Holding_a_Weapon(){
        return this.is_Holding_a_Weapon;
    }

    /* Getter */
    public String getFirstType(){
        return this.types[0];
    }
    public String getSecondType(){
        return this.types[1];
    }
    public String getName(){
        return this.name;
    }
    public int getNumberofDice(){
        return this.number_of_dices;
    }
    public Dice getDice(){
        return this.dice;
    }
    public String getProperty(){
        return this.property;
    }
    public int getDistance(){
        return this.distance;
    }
    public int getDamage(){
        return this.damage;
    }

    public StringProperty[] getWeaponsDescriptionArrayStringProperty(){
        return this.weaponDescription;
    }
    public StringProperty getWeaponDescriptionByIdStringProperty(int id){
        if(id >= 0 && id < 3){
            return this.weaponDescription[id];
        }
        return null;
    }
    public String[] getWeaponsDescriptionArray(){
        String[] array = new String[3];
        for (int i = 0; i < 3; i++) {
            array[i] = this.weaponDescription[i].get();
        }
        return array;
    }
    public String getWeaponDescriptionById(int id){
        if(id >= 0 && id < 3){
            return this.weaponDescription[id].get();
        }
        return null;
    }
}
