package com.dnd.it;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import com.dnd.it.GameSystem.Game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HomeController {
    @FXML
    private Button UpBtn;
    @FXML
    private Button DownBtn;
    @FXML
    private Button LeftBtn;
    @FXML
    private Button RightBtn;
    @FXML
    private Button InventoryBtn;
    @FXML
    private Button AttackBtn;
    @FXML
    private Button DodgeBtn;
    @FXML
    private Label LifeLabelText;
    @FXML
    private Label BonusLabelText;
    @FXML
    private Label ModifierLabelText;
    @FXML
    private Label GuardLabelText;
    @FXML
    private Label SpeedLabelText;
    @FXML
    private Label StrengthLabelText;
    @FXML
    private Label DexterityLabelText;
    @FXML
    private Label ConstitutionLabelText;
    @FXML
    private Label IntelligenceLabelText;
    @FXML
    private Label WisdomLabelText;
    @FXML
    private Label CharismLabelText;
    @FXML
    private Label MoneyLabelText;
    @FXML
    private TextField InputTextField;
    @FXML
    private Label HistoryLabel;
    @FXML 
    private ScrollPane ScrollHistory;
    @FXML
    private TextArea textArea;
    @FXML
    private Label PlayerNameLabel;
    @FXML
    private Label DescriptionLabel;
    @FXML
    private ImageView PhotoPG;

    private int current_player_speed;
    private int current_enemy_speed;
    
    private App app;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Game game;
    private EnemyViewerController Enemycontroller;
    private MapController mapController;

    public void initialize(){

    }

    public void SwitchInventory(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Inventory.fxml"));
        root = loader.load();

        //InventoryController inventoryController = loader.getController();

        stage = new Stage();
        scene = new Scene(root);
        stage.setTitle("D&D Java Game - Inventory");
        stage.setScene(scene);
        stage.show();

    }

    public void AttackBtnAction(ActionEvent event) throws IOException{
        if (mapController.check()){
            String enemy_movement = "";
            HistoryLabel.setText(game.BattleTurn("Attacca"));  
            enemy_movement = this.game.BattleTurn("Azione Nemico");
            HistoryLabel.setText(HistoryLabel.getText() + enemy_movement);  
            if(enemy_movement.equals("Il nemico si muove")){
                while(current_enemy_speed > 0) {
                    this.EnemyMovements();
                }
            }
            this.Enemycontroller.text("" + app.getEnemy().getClassPgClass().getLife());
            LifeLabelText.setText("" + app.getPlayer().getClassPgClass().getLife());
        }
        else{
            HistoryLabel.setText("Non puoi attaccare finchè non sei vicino al nemico");  
        }
        current_player_speed = app.getPlayer().getRaceClass().getSpeed();
        current_enemy_speed = app.getEnemy().getRaceClass().getSpeed();
    }

    public void DodgeBtnAction(ActionEvent event) throws IOException{
        if (mapController.check()){
            HistoryLabel.setText(game.BattleTurn("Schiva"));  
            this.Enemycontroller.text("" + app.getEnemy().getClassPgClass().getLife());
            LifeLabelText.setText("" + app.getPlayer().getClassPgClass().getLife());
        }
        else{
            HistoryLabel.setText("Non puoi schivare finchè non sei vicino al nemico");  
        }
        current_player_speed = app.getPlayer().getRaceClass().getSpeed();
    }

    public void UpBtnAction(ActionEvent event) throws IOException{
        if (current_player_speed > 0 ){
            int moves = this.mapController.Up();
            if(moves == 1){
               current_player_speed -= mapController.getMeterForCell();
                HistoryLabel.setText("Player Moves - Ancora max: "+current_player_speed/mapController.getMeterForCell()+" movimenti"); 
            }
            else if(moves == 0){
                HistoryLabel.setText("Player Moves - Movimento in alto non possibile, Bordo mappa");
            }
            else if(moves == -1){
                HistoryLabel.setText("Player Moves - Movimento in alto non possibile, Presenza Nemico");
            }
        }
        else{
            this.EnemyMoves();
        }
        
    }

    public void DownBtnAction(ActionEvent event) throws IOException{
        if (current_player_speed > 0 ){
            int moves = this.mapController.Down();
            if(moves == 1){
               current_player_speed -= mapController.getMeterForCell();
                HistoryLabel.setText("Player Moves - Ancora max: "+current_player_speed/mapController.getMeterForCell()+" movimenti"); 
            }
            else if(moves == 0){
                HistoryLabel.setText("Player Moves - Movimento in basso non possibile, Bordo mappa");
            }
            else if(moves == -1){
                HistoryLabel.setText("Player Moves - Movimento in basso non possibile, Presenza Nemico");
            }
        }
        else{
            this.EnemyMoves();
        }
        
    }

    public void LeftBtnAction(ActionEvent event) throws IOException{
        if (current_player_speed > 0 ){
            int moves = this.mapController.Left();
            if(moves == 1){
               current_player_speed -= mapController.getMeterForCell();
                HistoryLabel.setText("Player Moves - Ancora max: "+current_player_speed/mapController.getMeterForCell()+" movimenti"); 
            }
            else if(moves == 0){
                HistoryLabel.setText("Player Moves - Movimento a sinistra non possibile, Bordo mappa");
            }
            else if(moves == -1){
                HistoryLabel.setText("Player Moves - Movimento a sinistra non possibile, Presenza Nemico");
            }
        }
        else{
           this.EnemyMoves(); 
        }
        
    }

    public void RigthBtnAction(ActionEvent event) throws IOException{
        if (current_player_speed > 0 ){
            int moves = this.mapController.Right();
            if(moves == 1){
               current_player_speed -= mapController.getMeterForCell();
               HistoryLabel.setText("Player Moves - Ancora max: "+current_player_speed/mapController.getMeterForCell()+" movimenti"); 
            }
            else if(moves == 0){
                HistoryLabel.setText("Player Moves - Movimento a destra non possibile, Bordo mappa");
            }
            else if(moves == -1){
                HistoryLabel.setText("Player Moves - Movimento a destra non possibile, Presenza Nemico");
            }
        }
        else{
           this.EnemyMoves(); 
        }
    }

    private void EnemyMoves() throws IOException{
        if (current_player_speed == 0 && current_enemy_speed > 0){
            if(mapController.check() && this.game.EnemyAttackDecision()){
                this.EnemyTurn();
            }
            else{
                HistoryLabel.setText("Il Nemico esegue i suoi "+ current_enemy_speed/mapController.getMeterForCell()+" movimenti");
                while(current_enemy_speed > 0) {

                    this.EnemyMovements();

                    if(mapController.check() && this.game.EnemyAttackDecision()){
                        this.EnemyTurn();
                        break;
                    }
                }
            }
            
            /* Alla fine del movimento del nemico si ricaricano le velocità */
            current_player_speed = app.getPlayer().getRaceClass().getSpeed();
            current_enemy_speed = app.getEnemy().getRaceClass().getSpeed();
        }
    }

    private void EnemyTurn() throws IOException{
        HistoryLabel.setText(game.BattleTurn("Attacco Nemico"));  
        this.Enemycontroller.text("" + app.getEnemy().getClassPgClass().getLife());
        LifeLabelText.setText("" + app.getPlayer().getClassPgClass().getLife());
    }

    public void EndTurn() throws IOException{
        HistoryLabel.setText("Il Nemico esegue i suoi "+ current_enemy_speed/mapController.getMeterForCell()+" movimenti");
                while(current_enemy_speed > 0) {

                    this.EnemyMovements();

                    if(mapController.check() && this.game.EnemyAttackDecision()){
                        this.EnemyTurn();
                        break;
                    }
                }

        current_enemy_speed = app.getEnemy().getRaceClass().getSpeed();
        current_player_speed = app.getPlayer().getRaceClass().getSpeed();
    }

    private void EnemyMovements(){
        Random rand_num = new Random();
        int moves = 1 + rand_num.nextInt(3);

        /* Movimento in Alto */
        if(moves == 1){
            moves = this.mapController.UpEnemy();
            if(moves == -1){
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Movimento in alto non possibile, Presenza Player");
            }
            else if (moves == 1){
                current_enemy_speed -= mapController.getMeterForCell();
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Ancora max: "+current_enemy_speed/mapController.getMeterForCell()+" movimenti"); 
            }
            else{
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Movimento in alto non possibile, Bordo mappa");
            }

        }
        /* Movimento in Basso */
        else if(moves == 2){            
            moves = this.mapController.DownEnemy();
            if(moves == -1){
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Movimento in basso non possibile, Presenza Player");
            }
            else if (moves == 1){
                current_enemy_speed -= mapController.getMeterForCell();
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Ancora max: "+current_enemy_speed/mapController.getMeterForCell()+" movimenti"); 
            }
            else{
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Movimento in basso non possibile, Bordo mappa");
            }
        }
        /* Movimento A Sinistra */
        else if(moves == 3){
            moves = this.mapController.LeftEnemy();
            if(moves == -1){
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Movimento a sinistra non possibile, Presenza Player");
            }
            else if (moves == 1){
                current_enemy_speed -= mapController.getMeterForCell();
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Ancora max: "+current_enemy_speed/mapController.getMeterForCell()+" movimenti"); 
            }
            else{
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Movimento a sinistra non possibile, Bordo mappa");
            }
        }
        /* Movimento a Destra */
        else if(moves == 4){
            moves = this.mapController.RightEnemy();
            if(moves == -1){
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Movimento a destra non possibile, Presenza Player");
            }
            else if (moves == 1){
                current_enemy_speed -= mapController.getMeterForCell();
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Ancora max: "+current_enemy_speed/mapController.getMeterForCell()+" movimenti"); 
            }
            else{
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Movimento a destra non possibile, Bordo mappa");
            }
        }
    }

    public void setApp(App app) {

        this.app = app;

        this.game = new Game(app.getPlayer(), app.getEnemy());

        // Add observable list data to the table
        File file = new File("src/main/resources/Assets/Chracters_Icon/"+app.getPlayer().getClassPgClass().getClass_Pg()+".jpg");
        PhotoPG.setImage(new Image(file.toURI().toString()));
        PlayerNameLabel.setText(app.getPlayer().getName());
        /* Base Attribute */
        LifeLabelText.setText("" + app.getPlayer().getClassPgClass().getLife());
        //BonusLabelText.setText("" + app.getPlayer().getRaceClass().getBonus("Strength"));
        //ModifierLabelText.setText("" + app.getPlayer().getRaceClass().getmodificatore());
        GuardLabelText.setText("" + app.getPlayer().getClassPgClass().getGuard());
        SpeedLabelText.setText("" + app.getPlayer().getRaceClass().getSpeed());

        current_player_speed = app.getPlayer().getRaceClass().getSpeed();
        current_enemy_speed = app.getEnemy().getRaceClass().getSpeed();
        /* Attribute  */
        StrengthLabelText.setText("" + app.getPlayer().getRaceClass().getStrength());
        DexterityLabelText.setText("" + app.getPlayer().getRaceClass().getDexterity());
        ConstitutionLabelText.setText("" + app.getPlayer().getRaceClass().getConstitution());
        IntelligenceLabelText.setText("" + app.getPlayer().getRaceClass().getIntelligence());
        WisdomLabelText.setText("" + app.getPlayer().getRaceClass().getWisdom());
        CharismLabelText.setText("" + app.getPlayer().getRaceClass().getCharism());
        MoneyLabelText.setText("" + app.getPlayer().getMoney());
    }

    public void getEnemyController(App app){
        this.Enemycontroller = app.getEnemyController();
    }

    public void getMapController(App app){
        this.mapController = app.getMapController();
    }
}
