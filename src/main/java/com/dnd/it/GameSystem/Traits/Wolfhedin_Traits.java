package com.dnd.it.GameSystem.Traits;
import com.dnd.it.GameSystem.Races.Wolfhedin;

public class Wolfhedin_Traits {
    /* dal 3° livello incremento della velocità di + 3m
    *  Vantaggio su Percezione (Add in Ability)
    *  Trasformazione in Forma Lupina
    */
    public static void Sangue_Antico(Wolfhedin wolfhedin){
        wolfhedin.IncreaseSpeed(3);
        wolfhedin.AddAbility("Percezione");
        wolfhedin.setTransformationBool(true);
        wolfhedin.setTimeOfTransformation(7); // 1 minuto --> 7 turni poichè ogni turno dura circa 9 secondi;
        // se trasformato, non può afferrare armi
    }
}
