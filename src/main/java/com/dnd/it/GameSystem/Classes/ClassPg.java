package com.dnd.it.GameSystem.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dnd.it.GameSystem.Weapon.Armi;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassPg {
    private StringProperty class_pg;
    private IntegerProperty life;
    private IntegerProperty guard;
    private List<StringProperty> Privilegi;
    private List<Armi> Weapons;

    public ClassPg(String class_pg){
        this.class_pg = new SimpleStringProperty(class_pg);
        this.life = new SimpleIntegerProperty(0);
        this.guard = new SimpleIntegerProperty(0);
        this.Privilegi = new ArrayList<StringProperty>();
        this.Weapons = new ArrayList<Armi>();
    }

    /* Setter */

    public void addonWeaponList(Armi weapon){
        this.Weapons.add(weapon);
    }

    public void removeFromWeaponList(Armi weapon){
        this.Weapons.remove(weapon);
    }

    public void setClassPg(String class_pg){
        this.class_pg.set(class_pg);
    }

    public void setLife(int life){
        this.life.set(life);
    }

    public void setGuard(int guard){
        this.guard.set(guard);
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

    /* Getter */

    public int getLife(){
        return this.life.get();
    }

    public String getClass_Pg(){
        return this.class_pg.get();
    }

    public int getGuard(){
        return this.guard.get();
    }

    public List<String> getPrivilegiList(){
        return this.Privilegi.stream().map((privilegio) -> privilegio.get()).collect(Collectors.toList());
    }

    public String getPrivilegiByID(int id){
        return this.Privilegi.get(id).get();
    }

    public List<Armi> getWeaponList(){
        return this.Weapons;
    }

    public Armi getWeaponByName(String name){
        /* mi aspetto che il nome dell'arma sia univoco e che sia il primo della lista dopop il fetch */
        List<Armi> armi = this.Weapons.stream().filter(weapon -> weapon.getName().equals(name)).collect(Collectors.toList());
        return armi.getFirst();
    }

    /* Getter Property */

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

    public List<StringProperty> getPrivilegiListProperty(){
        return this.Privilegi;
    }

    public StringProperty getPrivilegiPropertyByID(int id){
        return this.Privilegi.get(id);
    }

}
