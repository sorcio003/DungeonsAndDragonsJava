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
    // accuracy è un'attributo che identifica quanto la'rma è accurata, quindi quanto è precisa nel colpo
    // se l'arma ha 'Accurata' come proprietà, viene settato quseto modficatore che inciderà sul risultato del lancio del d20 in attacco, di base è 0
    // se l'arma è 'Imprecisa' come proprietà, oppure come bilanciamento, infligge molti danni ma è molto pesate, il modificatore è negativo
    // -- Arma semplici mischia: +1
    // -- Armi semplici distanza: +2
    // -- Armi da guerra mischia: +2
    // -- Armi da guerra distanza: +3
    private int accuracy; 
    private int damage;
    private int distance;   // la distanza è intesa come distanza minima che il player deve tenere per poter attaccare con quell'arma
    private int[] range;      
    // il range invece è inteso come range o distanza minima e massima dalla quela il player più eventualmente lanciare l'arma (default 0)
    // range[0] == distanza massima, ossia la distanza più lontana che il player deve tenre per eventualmente lanciare l'arma, 
    //              esempio range[0] = 3, il player se distata al massimo 3 blocchi dal nemico, può effettuare il lancio
    // range[1] == distanza minima, ossia la distanza più piccola che il player deve tenere per eventualmente lanciare l'arma
    //              esempio range[1] = 1, il player se distata almeno 1 blocco dal nemico, può effettuare il lancio
    private int time_of_usability;
    private int count_time_of_usability;
    private int cooldown_usability;
    private int count_colldown;
    private int life_of_weapon;
    private int max_life;
    /* Conterrà */
    private StringProperty[] weaponDescription;
    private Boolean is_Holding_a_Weapon;
    private Boolean reset;
    private Boolean start_cooldown;
    private StringProperty holding_Property;

    public Armi(Dice dice){
        this.types = new String[2];
        this.name = "";
        this.number_of_dices = 0;
        this.dice = dice;
        this.property = "";
        this.damage = 0;
        this.accuracy = 0;
        this.distance = 0;
        this.life_of_weapon = 10;   // di default decido che ogni arma ha vita massima 10, quindi puoi eseguire 10 attacchi
        this.max_life = this.life_of_weapon;
        this.range = new int[2];
        this.range[0] = 0;
        this.range[0] = 0;
        // di default sono [0,0] perchè se l'arma non può essere lanciata, non si considerano i valori
        /* questi due parametri sono sostanzialmente */
        this.time_of_usability = 0; // per quanti turni puoi utilizzare l'arma, di seguito o non
        this.count_time_of_usability = 0;
        this.cooldown_usability = 0;    // turni neccessari per ricaricare il tempo di utilizzo
        this.count_colldown = 0;     // questo èparametro serve per calcolare il cooldwon rimante, è qeullo che viene aggiornato fino a quando non ritorna 0, poi viene risettato a colldown
        this.reset = false;
        this.start_cooldown = false;
        /*Array di 3 elmenti che conterrà <name, no_dice/dice (1d6), property> nella table ci sarà anche il btn is_hoding or suit per indossare l'arma */
        this.weaponDescription = new StringProperty[3];
        this.weaponDescription[0] = new SimpleStringProperty(this.name);
        this.weaponDescription[1] = new SimpleStringProperty(new String(this.number_of_dices+"d"+this.dice.getDiceMaxValue()));
        this.weaponDescription[2] = new SimpleStringProperty(this.property);
        this.is_Holding_a_Weapon = false;
        this.holding_Property = new SimpleStringProperty("Not Holding");
    }

    /* Methods */
    /* per calcolare il danno, in base al numero di ddo che posso lanciare, numeri di volte che posso lanciare il dado
     * Lancio il dado
     * incremento il danno del risultato del Dice roll + bonus + modifier
     */

    /* Setter */
    private void setAccuracy(int accuracy){
        this.accuracy = accuracy;
    }
    public void setLife_Of_Weapon(int life){
        this.life_of_weapon = life;
        this.setMax_Life(life);
    }
    public void setMaxRangeOFLaunchWeapon(int max){
        this.range[0] = max;
    }
    public void setMinRangeOFLaucnhWeapon(int min){
        this.range[1] = min;
    }
    private void setMax_Life(int life){
        this.max_life = life;
    }
    public void setTime_Of_Usability(int time){
        this.time_of_usability = time;
    }
    public void setCoolDown_Usability(int cooldown){
        this.cooldown_usability = cooldown;
    }
    public void setReset(Boolean bool){
        this.reset = bool;
    }
    public void setStart_Cooldown(Boolean bool){
        this.start_cooldown = bool;
    }
    /* i due metodi sottostanti resettano i contatori, il primo lo risetta a 0 l'altro al valore massimo del cooldown */
    public void ResetCounter_time_of_Usability(){
        this.count_time_of_usability = 0;
    }
    public void ResetCounter_Cooldown_Usability(){
        this.count_colldown = this.cooldown_usability;
    }
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
    /* ogni volta che viene inserita la proprietà 'Lancio' dell'arma
     * Automaticamente viene settato di default il maxrange = 3 e il minrange = 1
     */
    public void setProperty(String property){
        this.property = property;
        this.setPropertyinWeaponsDescription(this.property);
        if(this.property.equals("Lancio")){
            this.setMaxRangeOFLaunchWeapon(4);
            this.setMinRangeOFLaucnhWeapon(3);
        }
        if(this.property.equals("Accurata")){
            if(this.getFirstType().equals("Mischia")){
                if(this.getSecondType().equals("Semplice")){
                    this.setAccuracy(1);
                }
                else if(this.getSecondType().equals("Da Guerra")){
                    this.setAccuracy(2);
                }
            }
            else if(this.getFirstType().equals("Distanza")){
                if(this.getSecondType().equals("Semplice")){
                    this.setAccuracy(2);
                }
                else if(this.getSecondType().equals("Da Guerra")){
                    this.setAccuracy(3);
                }
            }
        }
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
        this.holding_Property = new SimpleStringProperty(this.holding_Property.get());
    }

    public void setHoldingBrokenProperty(){
        this.holding_Property = new SimpleStringProperty("Broken");
    }

    public void setHoldingThrowedProperty(){
        this.holding_Property = new SimpleStringProperty("Throwed");
    }

    public void setHolding_Active_Property(){
        this.holding_Property = new SimpleStringProperty("Holding");
    }

    public void setHolding_Not_Active_Property(){
        this.holding_Property = new SimpleStringProperty("Not Holding");
    }

    public StringProperty getHoldingProperty(){
        return this.holding_Property;
    }

    /* Life of Weapon */
    public void ResetLife_Of_Weapon_AtDefault(){
        this.life_of_weapon = 10;
    }
    public void ResetLife_Of_Weapon(){
        this.life_of_weapon = this.getMax_life();
    }
    public void IncreaseLife_Of_Weapon(){
        this.life_of_weapon++;
    }
    public void IncreaseLife_Of_Weapon(int amount){
        this.life_of_weapon += amount;
    }
    public void DecreaseLife_Of_Weapon(){
        this.life_of_weapon--;
    }
    public void DecreaseLife_Of_Weapon(int amount){
        this.life_of_weapon -= amount;
    }
    /* se la vita dell'arma è minore o uguale a 0, return false */
    public Boolean Check_Weapon_Still_enable_to_Figth(){
        if (this.life_of_weapon <= 0){
            this.life_of_weapon = 0;
            return false;
        }
        return true;
    }
    public Boolean Check_Weapon_Was_Launched(){
        return this.getHoldingPropertyasString().equals("Throwed");
    }

    /* Range Check and getter */
    /* se entrambi i valori di massimo o minimo sono [0,0] l'arma non può essere lanciata */
    public Boolean Is_Weapon_Throwable(){
        if (this.range[0] != 0 && this.range[1] != 0)
            return true;
        else   
            return false;
    }
    public int getMaxRangeOFLaunchWeapon(){
        return this.range[0];
    }
    public int getMinRangeOFLaucnhWeapon(){
        return this.range[1];
    }

    /* Is Holding */
    public String getHoldingPropertyasString(){
        return this.holding_Property.get();
    }

    public Boolean Is_Holding_a_Weapon(){
        return this.is_Holding_a_Weapon;
    }

    /* Time of Usability Attributes */
    // questo metodo incrementa un'attributo che infdica il tempo o turni di utilizzo effettuati dell'arma
    public void IncreaseTime_Of_Usability(){
        this.count_time_of_usability ++;
    }
    /* questo metodo ti dice se hai esaurito il tempo o turni per l'usabilità */
    /* se il contatore temporaneo è == al tempo do utilizzo massimo, ritrono True */
    public Boolean Check_Time_Of_Usbility_Terminated(){
        return this.count_time_of_usability == (this.time_of_usability );
    }
    public void DecreaseCooldown_Usability(){
        this.count_colldown --;
    }
    public Boolean Check_CoolDown_Terminated(){
        return this.count_colldown == 0;
    }

    /* Getter */
    private int getMax_life(){
        return this.max_life;
    }
    public int getAccuracy(){
        return this.accuracy;
    }
    public int getLife_Of_Weapon(){
        return this.life_of_weapon;
    }
    public int getTime_Of_Usability(){
        return this.time_of_usability;
    }
    public int getCooldown_Usability(){
        return this.cooldown_usability;
    }
    public int getRemainTime_Of_Usability(){
        return this.time_of_usability - this.count_time_of_usability;
    }
    public Boolean CheckReset(){
        return this.reset;
    }
    public Boolean CheckStartCooldown(){
        return this.start_cooldown;
    }
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

    public StringProperty getLife_Of_WeaponProperty(){
        StringProperty Stringlife = new SimpleStringProperty(""+this.getLife_Of_Weapon());
        return Stringlife;
    }
    public StringProperty getMax_Life_Of_WeaponProperty(){
        StringProperty stringMaxLife  = new SimpleStringProperty(""+this.getMax_life());
        return stringMaxLife;
    }
}
