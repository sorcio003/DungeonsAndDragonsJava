package com.dnd.it;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MapController implements Initializable{
    private int n_rows;
    private int n_columns;
    private int meters_for_cell;
    private int current_x_player;
    private int current_y_player;
    private int current_x_enemy;
    private int current_y_enemy;
    private ImageView playerPixel;
    private ImageView enemyPixel;
    @FXML
    private GridPane gridMap;
    
    private App app;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initialize(URL arg0, ResourceBundle arg1){
        meters_for_cell = 3;
    }
    /*
     * Per ogni movimento (Up, Down, Left, Rigth), prima di 'spostare' l'immagine o il personaggio, esso viene
     * eliminato come figlio dall'elemento GridPane poichè verrebbe lanciata una eccezione se ci sono dei duplicati
     * poi naturalmente viene fatto un check su se gli indici raggiungono max e min value
     */

    public int Up(){
        if((current_y_player-1) == current_y_enemy && current_x_player == current_x_enemy)
            return -1;
        if (current_y_player > 0 ){
            gridMap.getChildren().remove(playerPixel);

            current_y_player--;

            gridMap.add(playerPixel, current_x_player, current_y_player); 
            return 1;
        }
        return 0;
        
    }

    public int Down(){
        if((current_y_player+1) == current_y_enemy && current_x_player == current_x_enemy)
            return -1;
        if (current_y_player < n_rows - 1){
            gridMap.getChildren().remove(playerPixel);

            current_y_player++;

            gridMap.add(playerPixel, current_x_player, current_y_player);
            return 1;
        }
        return 0;
        
    }

    public int Left(){
        if((current_x_player-1) == current_x_enemy && current_y_player == current_y_enemy)
            return -1;
        if (current_x_player > 0){
            gridMap.getChildren().remove(playerPixel);

            current_x_player--;

            gridMap.add(playerPixel, current_x_player, current_y_player);    
            return 1;        
        }
        return 0;

    }

    public int Right(){
        if((current_x_player+1) == current_x_enemy && current_y_player == current_y_enemy)
            return -1;
        if (current_x_player < n_columns -1){
            gridMap.getChildren().remove(playerPixel);

            current_x_player++;

            gridMap.add(playerPixel, current_x_player, current_y_player);
            
            return 1;
        }
        return 0;

    }

    /* Enemy Movements */

    public int UpEnemy(){
        if((current_y_enemy-1) == current_y_player && current_x_player == current_x_enemy)
            return -1;
        if (current_y_enemy > 0 ){
            gridMap.getChildren().remove(enemyPixel);

            current_y_enemy--;

            gridMap.add(enemyPixel, current_x_enemy, current_y_enemy); 
            return 1;
        }
        return 0;
    }

    public int DownEnemy(){
        if((current_y_enemy+1) == current_y_player && current_x_player == current_x_enemy)
            return -1;
        if (current_y_enemy < n_rows - 1){
            gridMap.getChildren().remove(enemyPixel);

            current_y_enemy++;

            gridMap.add(enemyPixel, current_x_enemy, current_y_enemy);
            return 1;
        }
        return 0;
    }

    public int LeftEnemy(){
        if((current_x_enemy-1) == current_x_player && current_y_player == current_y_enemy)
            return -1;
        if (current_x_enemy > 0){
            gridMap.getChildren().remove(enemyPixel);

            current_x_enemy--;

            gridMap.add(enemyPixel, current_x_enemy, current_y_enemy);    
            return 1;        
        }
        return 0;
    }

    public int RightEnemy(){
        if((current_x_enemy+1) == current_x_player && current_y_player == current_y_enemy)
            return -1;
        if (current_x_enemy < n_columns -1){
            gridMap.getChildren().remove(enemyPixel);

            current_x_enemy++;

            gridMap.add(enemyPixel, current_x_enemy, current_y_enemy);
            
            return 1;
        }
        return 0;
    }

    /* si verifica se nell'intorno del player c'è il nemico 
     * - Restituisce false se non c'è nessuno 
     * - true se c'è almeno un nemico
    */
    public Boolean check(){
        /* Per effeturae questa verifica io verifico se la distanza tra le x e le y dei due player è 1 o -1 o 0 */
        //System.out.println("X: "+(current_x_player - current_x_enemy)+" Y: "+(current_y_player - current_y_enemy));
        if(((current_x_player - current_x_enemy) == 1) || ((current_x_player - current_x_enemy) == -1) || ((current_x_player - current_x_enemy) == 0)){
            if(((current_y_player - current_y_enemy) == 1) || ((current_y_player - current_y_enemy) == -1) || ((current_y_player - current_y_enemy) == 0)){
                return true;
            }
        }
        return false;
    }

    public int getMeterForCell(){
        return this.meters_for_cell;
    }

    public void setMap(App app) {

        this.app = app;

        n_rows = gridMap.getRowCount();
        n_columns = gridMap.getColumnCount();
        
        File playerPixelImage = new File("src/main/resources/Assets/Characters_Map_icon/"+app.getPlayer().getClassPgClass().getClass_Pg()+"_top_view.png");
        File enemyPixelImage = new File("src/main/resources/Assets/Characters_Map_icon/"+app.getEnemy().getRaceClass().getRace()+"_top_view.png");
        this.playerPixel = new ImageView(new Image(playerPixelImage.toURI().toString()));
        this.enemyPixel = new ImageView(new Image(enemyPixelImage.toURI().toString()));

        current_x_player = 0;
        current_y_player = n_rows/2;
        current_x_enemy = n_columns-1;
        current_y_enemy = n_rows/2;

        gridMap.add(playerPixel, current_x_player, current_y_player);
        gridMap.add(enemyPixel, current_x_enemy, current_y_enemy);
        
    }

}
