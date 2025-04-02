package com.dnd.it.GameSystem.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassPg {
    private StringProperty class_pg;
    private IntegerProperty modificatore;
    private IntegerProperty bonus_competenza;
    private IntegerProperty life;
    private IntegerProperty guard;
    private IntegerProperty strength;
    private IntegerProperty dexterity;
    private IntegerProperty constitution;
    private IntegerProperty intelligence;
    private IntegerProperty wisdom;
    private IntegerProperty charism;
    private IntegerProperty dodgePoints;
    private List<StringProperty> Privilegi;

    public ClassPg(String class_pg){
        this.class_pg = new SimpleStringProperty(class_pg);
        this.life = new SimpleIntegerProperty(0);
        this.modificatore = new SimpleIntegerProperty(0);
        this.guard = new SimpleIntegerProperty(0);
        /* Base Attribute */
        this.strength = new SimpleIntegerProperty(0);
        this.dexterity = new SimpleIntegerProperty(0);
        this.constitution = new SimpleIntegerProperty(0);
        this.intelligence = new SimpleIntegerProperty(0);
        this.wisdom = new SimpleIntegerProperty(0);
        this.charism = new SimpleIntegerProperty(0);
        this.dodgePoints = new SimpleIntegerProperty(0);
        this.bonus_competenza = new SimpleIntegerProperty(0);
        this.Privilegi = new ArrayList<StringProperty>();
    }

    /* Setter */

    public void setClassPg(String class_pg){
        this.class_pg.set(class_pg);
    }

    public void setBonus(int bonus_competenza){
        this.bonus_competenza.set(bonus_competenza);
    }

    public void setModificatore(int modificatore){
        this.modificatore.set(modificatore); 
    }

    public void setLife(int life){
        this.life.set(life);
    }

    public void setGuard(int guard){
        this.guard.set(guard);
    }

    public void setDodgePoints(int points){
        this.dodgePoints.set(points);
    }

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

    /* Dodge Points */
    public void IncreaseDodgePoints(){
        this.dodgePoints.set(this.dodgePoints.get() + 1);
    }

    public void IncreaseDodgePoints(int points){
        this.dodgePoints.set(this.dodgePoints.get() + points);
    }

    public void DecreaseDodgePoints(){
        if(this.dodgePoints.get() > 0)
            this.dodgePoints.set(this.dodgePoints.get() - 1);
        else
            this.dodgePoints.set(0);
    }

    public void DecreaseDodgePoints(int points){
        if(this.dodgePoints.get() > 0 && ((this.dodgePoints.get() - points) > 0))
            this.life.set(this.dodgePoints.get() - points);
        else
            this.dodgePoints.set(0);
    }


    /* Life */
    public void IncreaseLife(){
        this.life.set(this.life.get() + 1);
    }

    public void IncreaseLife(int life){
        this.life.set(this.life.get() + life);
    }

    public void DecreaseLife(){
        if(this.life.get() > 0)
            this.life.set(this.life.get() - 1);
        else
            this.life.set(0);
    }

    public void DecreaseLife(int life){
        if(this.life.get() > 0 && ((this.life.get() - life) > 0))
            this.life.set(this.life.get() - life);
        else
            this.life.set(0);
    }

    /* Privilegi */

    public void addPrivilegi(String privilegio){
        this.Privilegi.add(new SimpleStringProperty(privilegio));
    }

    public void addPrivilegi(List<String> privilegi){
        for (String privilegio : privilegi) {
            this.Privilegi.add(new SimpleStringProperty(privilegio));
        }
    }

    public void removePrivilegibyName(String name){
        this.Privilegi.remove(new SimpleStringProperty(name));
    }

    public void removePrivilegibyID(int id){
        this.Privilegi.remove(id);
    }

    /* modificatore */

    public void IncreaseModificatore(){
        this.modificatore.set(this.modificatore.get() + 1);
    }

    public void IncreaseModificatore(int modificatore){
        this.modificatore.set(this.modificatore.get() + modificatore);
    }

    public void DecreaseModificatore(){
        this.modificatore.set(this.modificatore.get() - 1);
    }

    public void DecreaseModificatore(int modificatore){
        this.modificatore.set(this.modificatore.get() - modificatore);
    }

    /* Getter */

    public int getBonus(){
        return this.bonus_competenza.get();
    }

    public int getDodgePoints(){
        return this.dodgePoints.get();
    }

    public int getLife(){
        return this.life.get();
    }

    public String getClass_Pg(){
        return this.class_pg.get();
    }

    public int getGuard(){
        return this.guard.get();
    }

    public int getmodificatore(){
        return this.modificatore.get();
    }

    public List<String> getPrivilegiList(){
        return this.Privilegi.stream().map((privilegio) -> privilegio.get()).collect(Collectors.toList());
    }

    public String getPrivilegiByID(int id){
        return this.Privilegi.get(id).get();
    }

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

    public IntegerProperty getBonusProperty(){
        return this.bonus_competenza;
    }

    public StringProperty getBonusStringProperty(){
        return new SimpleStringProperty(this.bonus_competenza.toString());
    }

    public IntegerProperty getLifeProperty(){
        return this.life;
    }

    public StringProperty getLifeStringProperty(){
        return new SimpleStringProperty(this.life.toString());
    }

    public StringProperty getClass_PgProperty(){
        return this.class_pg;
    }

    public IntegerProperty getGuardProperty(){
        return this.guard;
    }

    public StringProperty getGuardStringProperty(){
        return new SimpleStringProperty(this.guard.toString());
    }

    public IntegerProperty getmodificatoreProperty(){
        return this.modificatore;
    }

    public StringProperty getmodificatoreStringProperty(){
        return new SimpleStringProperty(this.modificatore.toString());
    }

    public List<StringProperty> getPrivilegiListProperty(){
        return this.Privilegi;
    }

    public StringProperty getPrivilegiPropertyByID(int id){
        return this.Privilegi.get(id);
    }

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
