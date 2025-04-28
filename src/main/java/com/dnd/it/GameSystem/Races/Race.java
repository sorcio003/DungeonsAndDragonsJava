package com.dnd.it.GameSystem.Races;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Race {
    private StringProperty race;
    private IntegerProperty speed; // speed (meters for units)
    private IntegerProperty strength;
    private IntegerProperty dexterity;
    private IntegerProperty constitution;
    private IntegerProperty intelligence;
    private IntegerProperty wisdom;
    private IntegerProperty charism;
    private IntegerProperty modificatore;
    private Map<String, IntegerProperty> bonus_competenza;
    // le abilità sono quelle che scalano sui punti caratteristica
    private List<StringProperty> Ability;
    // i Traits invece sono le "abilità" che la razza puo sfrittare come sangue antico(trasformazione) per i wolfhedin
    private List<StringProperty> Traits;

    public Race(String race){
        this.race = new SimpleStringProperty(race);
        this.speed = new SimpleIntegerProperty(0);
        this.modificatore = new SimpleIntegerProperty(0);
        this.Ability = new ArrayList<StringProperty>();
        this.Traits = new ArrayList<StringProperty>();
        /* Base Attribute */
        this.strength = new SimpleIntegerProperty(0);
        this.dexterity = new SimpleIntegerProperty(0);
        this.constitution = new SimpleIntegerProperty(0);
        this.intelligence = new SimpleIntegerProperty(0);
        this.wisdom = new SimpleIntegerProperty(0);
        this.charism = new SimpleIntegerProperty(0);
        this.bonus_competenza = new HashMap<String,IntegerProperty>();

        this.bonus_competenza.put("Strength", new SimpleIntegerProperty(0));
        this.bonus_competenza.put("Dexterity", new SimpleIntegerProperty(0));
        this.bonus_competenza.put("Constitution", new SimpleIntegerProperty(0));
        this.bonus_competenza.put("Intelligence", new SimpleIntegerProperty(0));
        this.bonus_competenza.put("Wisdom", new SimpleIntegerProperty(0));
        this.bonus_competenza.put("Charism", new SimpleIntegerProperty(0));
    }

    /* Setter */

    public void setSpeed(int speedformeters){
        this.speed.set(speedformeters);
    }

    public void setRace(String race){
        this.race.set(race);
    }

    /* In base al tipo nome dato da fuori si setta il valore della caratteristica */
    public void setBonus(String attribute, int value){
        this.bonus_competenza.get(attribute).set(value);
    }

    /* il modificatore viene assegnato in battle time quando il personaggio
     decide di effetuare una azione scalando su una caratteristica */
    public void setModificatore(int attribute){
        if(attribute == 1) this.modificatore.set(-5); 
        if(attribute == 2 || attribute == 3) this.modificatore.set(-4); 
        if(attribute == 4 || attribute == 4) this.modificatore.set(-3); 
        if(attribute == 6 || attribute == 5) this.modificatore.set(-2); 
        if(attribute == 8 || attribute == 7) this.modificatore.set(-1); 
        if(attribute == 10 || attribute == 11) this.modificatore.set(0); 
        if(attribute == 12 || attribute == 13) this.modificatore.set(1); 
        if(attribute == 14 || attribute == 15) this.modificatore.set(2); 
        if(attribute == 16 || attribute == 17) this.modificatore.set(3); 
        if(attribute == 18 || attribute == 19) this.modificatore.set(4); 
        if(attribute == 20 || attribute == 21) this.modificatore.set(5); 
        if(attribute == 22 || attribute == 23) this.modificatore.set(6); 
        if(attribute == 24 || attribute == 25) this.modificatore.set(7); 
        if(attribute == 26 || attribute == 27) this.modificatore.set(8);
        if(attribute == 28 || attribute == 29) this.modificatore.set(9);  
        if(attribute == 30) this.modificatore.set(10); 
    }

    /* Attribute */

    public void setStrength(int strength){
        this.strength.set(strength);
    }

    public void setDexterity(int dexterity){
        this.dexterity.set(dexterity);
    }

    public void setConstitution(int constitution){
        this.constitution.set(constitution);
    }

    public void setIntelligence(int intelligence){
        this.intelligence.set(intelligence);
    }

    public void setWisdom(int wisdom){
        this.wisdom.set(wisdom);
    }

    public void setCharism(int charism){
        this.charism.set(charism);
    }

    /* Getter */

    public int getSpeed(){
        return this.speed.get();
    }

    public void IncreaseSpeed(){
        this.speed.set(this.getSpeed() + 1);
    }
    public void IncreaseSpeed(int value){
        this.speed.set(this.getSpeed() + value);
    }

    public String getRace(){
        return this.race.get();
    }

    public int getBonus(String attribute){
        return this.bonus_competenza.get(attribute).get();
    }

    public int getmodificatore(){
        return this.modificatore.get();
    }

    /* Attribute */

    public int getStrength(){
        return this.strength.get();
    }

    public int getDexterity(){
        return this.dexterity.get();
    }

    public int getConstitution(){
        return this.constitution.get();
    }

    public int getIntelligence(){
        return this.intelligence.get();
    }

    public int getWisdom(){
        return this.wisdom.get();
    }

    public int getCharism(){
        return this.charism.get();
    }


    /* Getter Property */

    public IntegerProperty getSpeedProperty(){
        return this.speed;
    }

    public IntegerProperty getmodificatoreProperty(){
        return this.modificatore;
    }

    public IntegerProperty getBonusProperty(String attribute){
        return this.bonus_competenza.get(attribute);
    }

    public StringProperty getBonusStringProperty(){
        return new SimpleStringProperty(this.bonus_competenza.toString());
    }

    public StringProperty getSpeedStringProperty(){
        return new SimpleStringProperty(this.speed.toString());
    }

    public StringProperty getmodificatoreStringProperty(){
        return new SimpleStringProperty(this.modificatore.toString());
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

    /* Traits */

    public void AddTraits(String trait){
        this.Traits.add(new SimpleStringProperty(trait));
    }

    public void AddTraits(List<String> Traits){
        for (String trait : Traits) {
            this.Traits.add(new SimpleStringProperty(trait));
        }
    }

    public void RemoveTraitsByName(String Traits){
        this.Traits.remove(new SimpleStringProperty(Traits));
    }

    public void RemoveTraitsByID(int id){
        if (id >= 0){
            this.Traits.remove(id);
        }
    }

    /* Getter Property */

    public List<String> getAbilityList(){
        return this.Ability.stream().map((ability) -> ability.get()).collect(Collectors.toList());
    }

    public List<StringProperty> getAbilityListProperty(){
        return this.Ability;
    }
    

    public StringProperty getAbilityPropertyByID(int id){
        return this.Ability.get(id);
    }

    public String getTraitsByID(int id){
        return this.Traits.get(id).get();
    }

    public List<String> getTraitsList(){
        return this.Traits.stream().map((Traits) -> Traits.get()).collect(Collectors.toList());
    }

    public List<StringProperty> getTraitsListProperty(){
        return this.Traits;
    }
    

    public StringProperty getTraitsPropertyByID(int id){
        return this.Traits.get(id);
    }

    public String getAbilityByID(int id){
        return this.Ability.get(id).get();
    }

    /* Attribute */

    public IntegerProperty getStrengthProperty(){
        return this.strength;
    }

    public IntegerProperty getDexterityProperty(){
        return this.dexterity;
    }

    public IntegerProperty getConstitutionProperty(){
        return this.constitution;
    }

    public IntegerProperty getIntelligenceProperty(){
        return this.intelligence;
    }

    public IntegerProperty getWisdomProperty(){
        return this.wisdom;
    }

    public IntegerProperty getCharismProperty(){
        return this.charism;
    }
}
