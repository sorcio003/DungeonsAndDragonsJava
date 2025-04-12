package com.dnd.it;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import com.dnd.it.GameSystem.EnemyAI;
import com.dnd.it.GameSystem.Game;
import com.dnd.it.GameSystem.Model.Characters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeController {
    @FXML
    private AnchorPane FatherAnchorPane;
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
    private int enemy_moves;
    private int d20resulst;
    
    private App app;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Game game;
    private Characters player;
    private Characters enemy;
    private EnemyViewerController Enemycontroller;
    private MapController mapController;

    public void initialize(){

    }

    /* Scene Manager */
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

    public void SwitchEquipment(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Equipment.fxml"));
        root = loader.load();

        EquipmentController controller = loader.getController();
        controller.setTableClass(app);

        stage = new Stage();
        scene = new Scene(root);
        stage.setTitle("D&D Java Game - Equipment");
        InputStream inputStream = getClass().getResourceAsStream("/Assets/Icon/Map.png");
        stage.getIcons().add(new Image(inputStream));
        stage.setScene(scene);
        stage.show();

    }

    public void BattleResultsScreen() throws IOException{
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Battle Results");
        alert.initOwner(app.getPrimaryStage());
        this.game.EndBattle();
        alert.setHeaderText(this.game.getResultsAction()+" Do you want to restart Battle?");
        alert.setContentText("If you click Cancel You'll close App");
        // Wait User Answer
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.CANCEL) {
            System.exit(0);
        } else {
            app.restartGame();
        }
    }

    public void AttackBtnAction(ActionEvent event) throws IOException{
        if (mapController.check()){
            /* Pre decisione della mossa del nemico, viene creato qui la decisione di attaccare, schivare o muoversi */
            this.Battle("Player");
        }
        else{
            HistoryLabel.setText("Non puoi attaccare finchè non sei vicino al nemico");  
        }
        this.RechargeSpeed();
    }

    public void DodgeBtnAction(ActionEvent event) throws IOException{
        if (mapController.check()){
            /* Cosa succede qui, naturalmente sarà da rendere più elegante 
             * viene già deciso inizialmente quale sarà la mossa del nemico se attacare, schivare o muoversi
             * viene eseguita la mossa del player, poi viene eseguita la mossa del nemico, il tutto viene deciso e calcolato in base alla enemy_move
            */
            this.game.getEnemyAI().EnemyAIDecision(this.game.Roll_and_getD20Results());
            enemy_moves = this.game.getEnemyAI().getDecision();
            /* esecuzione BattleTurn Schiva */
            this.game.BattleTurn("Schiva", 0);
            /* print a schermo risultato esecuzione BattleTurn Schiva */
            HistoryLabel.setText(this.game.getResultsAction()); 
            /* esecuzione BattleTurn Azione Nemico */
            d20resulst = this.game.Roll_and_getD20Results();
            this.game.BattleTurn("Azione Nemico", enemy_moves); 
            this.game.getEnemyAI().setLastD20Results(d20resulst);

            HistoryLabel.setText(HistoryLabel.getText() +"\n"+ this.game.getResultsAction());  
            if( this.game.Are_Enemy_Moving() ){
                while(current_enemy_speed > 0) {
                    this.EnemyMovements();
                }
            }
            this.Enemycontroller.text("" + app.getEnemy().getClassPgClass().getLife());
            LifeLabelText.setText("" + app.getPlayer().getClassPgClass().getLife());
        }
        else{
            HistoryLabel.setText("Non puoi schivare finchè non sei vicino al nemico");  
        }
        RechargeSpeed();
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
            this.EndTurn();
            //HistoryLabel.setText("Player Moves - Movimento non concesso!\nMovimenti esauriti (Click EndTurn BTN)");
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
            this.EndTurn();
            //HistoryLabel.setText("Player Moves - Movimento non concesso!\nMovimenti esauriti (Click EndTurn BTN)");
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
            this.EndTurn();
            //HistoryLabel.setText("Player Moves - Movimento non concesso!\nMovimenti esauriti (Click EndTurn BTN)");
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
                this.EndTurn();
                //HistoryLabel.setText("Player Moves - Movimento non concesso!\nMovimenti esauriti (Click EndTurn BTN)");
            }
    }
    
    /* Getsione della tastiera */
    public void ActionPlayerWithKey(KeyEvent keyEvent) throws IOException{
        if(keyEvent.getCode().equals(KeyCode.W)){
            this.UpBtnAction(null);
        }
        else if(keyEvent.getCode().equals(KeyCode.S)){
            this.DownBtnAction(null);
        }
        else if(keyEvent.getCode().equals(KeyCode.A)){
            this.LeftBtnAction(null);
        }
        else if(keyEvent.getCode().equals(KeyCode.D)){
            this.RigthBtnAction(null);
        }
        else if(keyEvent.getCode().equals(KeyCode.F)){
            this.AttackBtnAction(null);
        }
        else if(keyEvent.getCode().equals(KeyCode.Q)){
            this.DodgeBtnAction(null);
        }
        else if(keyEvent.getCode().equals(KeyCode.Z)){
            this.EndTurn();
        }
    }

    /* Battle System */
    private void Battle(String Player) throws IOException{
        /* pre lancio del dado da parte del player */
        d20resulst = this.game.Roll_and_getD20Results();
        this.game.getEnemyAI().EnemyAIDecision(d20resulst);
        /* valore tra 1 , 2 e 3 che decide se il nemico attacca, schiva o si muove */
        enemy_moves = this.game.getEnemyAI().getDecision();
        if(Player.equals("Player")){
            this.game.BattleTurn("Attacca", enemy_moves);
            HistoryLabel.setText(this.game.getResultsAction());  
        }
        /* pre lancio del dado da parte del nemico */
        d20resulst = this.game.Roll_and_getD20Results();
        this.game.getEnemyAI().setLastD20Results(d20resulst);
        this.game.BattleTurn("Azione Nemico", enemy_moves);

        HistoryLabel.setText(HistoryLabel.getText() + this.game.getResultsAction());   
            if( this.game.Are_Enemy_Moving() ){
                while(current_enemy_speed > 0) {
                    this.EnemyMovements();
                }
            }  
        this.Enemycontroller.text("" + app.getEnemy().getClassPgClass().getLife());
        LifeLabelText.setText("" + app.getPlayer().getClassPgClass().getLife());
        /* Check Who is the Winner */
        if(this.game.CheckEndBattle())
            BattleResultsScreen();
    }

    public void EndTurn() throws IOException{
        HistoryLabel.setText("Turno del Nemico");
        if(mapController.check()){
            HistoryLabel.setText(HistoryLabel.getText() +  "\n");
            this.Battle("Enemy");
        }
        else{
            while(current_enemy_speed > 0) {
                this.EnemyMovements();
            }
        }
        RechargeSpeed();
    }

    /* per muoversi nella mappa */
    private void EnemyMovements(){
        Random rand_num = new Random();
        int moves = 1 + rand_num.nextInt(3);

        /* Movimento in Alto */
        if(moves == 1){
            moves = this.mapController.UpEnemy();
            if (moves == 1){
                current_enemy_speed -= mapController.getMeterForCell();
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Movimento in Alto"); 
            }
        }
        /* Movimento in Basso */
        else if(moves == 2){            
            moves = this.mapController.DownEnemy();
            if (moves == 1){
                current_enemy_speed -= mapController.getMeterForCell();
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Movimento in Basso"); 
            }
        }
        /* Movimento A Sinistra */
        else if(moves == 3){
            moves = this.mapController.LeftEnemy();
            if (moves == 1){
                current_enemy_speed -= mapController.getMeterForCell();
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Movimento a Sinistra"); 
            }
        }
        /* Movimento a Destra */
        else if(moves == 4){
            moves = this.mapController.RightEnemy();
            if (moves == 1){
                current_enemy_speed -= mapController.getMeterForCell();
                HistoryLabel.setText(HistoryLabel.getText()+"\nEnemy Moves - Movimento a Destra"); 
            }
        }
    }
    /* Recharge Speed */
    private void RechargeSpeed(){
        current_enemy_speed = app.getEnemy().getRaceClass().getSpeed();
        current_player_speed = app.getPlayer().getRaceClass().getSpeed();
    }

    /* Method to Call on App Class */
    public void setApp(App app) {

        this.app = app;

        this.player = app.getPlayer();
        this.enemy = app.getEnemy();

        this.game = new Game(player, enemy, new EnemyAI(this.player, this.enemy));

        // Add observable list data to the table
        InputStream inputStream = getClass().getResourceAsStream("/Assets/Chracters_Icon/" + player.getClassPgClass().getClass_Pg() + ".jpg");
        PhotoPG.setImage(new Image(inputStream));
        PlayerNameLabel.setText(player.getName());
        /* Base Attribute */
        LifeLabelText.setText("" + player.getClassPgClass().getLife());
        //BonusLabelText.setText("" + app.getPlayer().getRaceClass().getBonus("Strength"));
        //ModifierLabelText.setText("" + app.getPlayer().getRaceClass().getmodificatore());
        GuardLabelText.setText("" + player.getClassPgClass().getGuard());
        SpeedLabelText.setText("" + player.getRaceClass().getSpeed());

        current_player_speed = player.getRaceClass().getSpeed();
        current_enemy_speed = enemy.getRaceClass().getSpeed();
        /* Attribute  */
        StrengthLabelText.setText("" + player.getRaceClass().getStrength());
        DexterityLabelText.setText("" + player.getRaceClass().getDexterity());
        ConstitutionLabelText.setText("" + player.getRaceClass().getConstitution());
        IntelligenceLabelText.setText("" + player.getRaceClass().getIntelligence());
        WisdomLabelText.setText("" + player.getRaceClass().getWisdom());
        CharismLabelText.setText("" + player.getRaceClass().getCharism());
        MoneyLabelText.setText("" + player.getMoney());

        HistoryLabel.setText("");
    }

    public void getEnemyController(App app){
        this.Enemycontroller = app.getEnemyController();
    }

    public void getMapController(App app){
        this.mapController = app.getMapController();
    }
}
