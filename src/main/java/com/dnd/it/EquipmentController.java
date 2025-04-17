package com.dnd.it;

import java.io.IOException;
import java.util.List;

import com.dnd.it.GameSystem.Model.Characters;
import com.dnd.it.GameSystem.Weapon.Armi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class EquipmentController {
    @FXML
    private TableView<Armi> WeaponsTable;
    @FXML
    private TableColumn<Armi, String> nameColumn;
    @FXML
    private TableColumn<Armi, String> diceColumn;
    @FXML
    private TableColumn<Armi, String> PropertyColumn;
    @FXML
    private TableColumn<Armi, String> holdingColumn;
    @FXML
    private TableColumn<Armi, String> LIFEColumn;
    @FXML
    private TableColumn<Armi, String> MAXlifeColumn;
    @FXML
    private Button SuitWeaponBTN;
    @FXML
    private Button UndressWeaponBTN;

    private Characters player;
    private Stage stage;
    private Label diceLabel;
    private Label historyLabel;

    public void initialize(){
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getWeaponDescriptionByIdStringProperty(0));
        diceColumn.setCellValueFactory(cellData -> cellData.getValue().getWeaponDescriptionByIdStringProperty(1));
        PropertyColumn.setCellValueFactory(cellData -> cellData.getValue().getWeaponDescriptionByIdStringProperty(2));
        holdingColumn.setCellValueFactory(cellData -> cellData.getValue().getHoldingProperty());
        LIFEColumn.setCellValueFactory(cellData -> cellData.getValue().getLife_Of_WeaponProperty());
        MAXlifeColumn.setCellValueFactory(cellData -> cellData.getValue().getMax_Life_Of_WeaponProperty());
    }

    public void SuittingWeapon(ActionEvent event) throws IOException{
        int index = WeaponsTable.getSelectionModel().getSelectedIndex();
        Armi arma = WeaponsTable.getItems().get(index);
        if(! player.Are_Already_Holding_Weapon() && ! arma.Is_Holding_a_Weapon() && arma.Check_Weapon_Still_enable_to_Figth()){
            WeaponsTable.getItems().get(index).set_Holding_Weapon(true);
            diceLabel.setText(arma.getWeaponDescriptionById(1));
            historyLabel.setText("Ti sei equipaggiato con '"+arma.getName()+"'\n");
            player.setCurrent_Holding_Weapon(arma);
            player.setDiceForDamage(arma.getDice());
            player.setNumber_Of_Dice_For_DMG(arma.getNumberofDice());
            player.setAlready_Holding_weapon(true);
            player.getCurrent_Holding_Weapon().setHolding_Active_Property();
            this.RefreshTableView(index);
        }
        else if(!arma.Check_Weapon_Still_enable_to_Figth()){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Weapon Broken");
            alert.initOwner(this.stage);
            alert.setHeaderText("You Cannot Suit this weapon anymore, it's Broken");
            alert.setContentText("Click Ok Bottun to close this view");
            alert.show();
            if(event != null)
                event.consume();
        }
        else if(arma.Check_Weapon_Was_Launched()){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Weapon Throwed");
            alert.initOwner(this.stage);
            alert.setHeaderText("You Cannot Suit or use it until it's dropped on ground");
            alert.setContentText("Click Ok Bottun to close this view");
            alert.show();
            if(event != null)
                event.consume();
        }
        else{
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Weapon Suitable");
            alert.initOwner(this.stage);
            alert.setHeaderText("You Cannot Suit more than 1 weapon at same time");
            alert.setContentText("Click Ok Bottun to close this view");
            alert.show();
            if(event != null)
                event.consume();
        }
    }

    public void UndressWeapon(ActionEvent event) throws IOException{
        int index = WeaponsTable.getSelectionModel().getSelectedIndex();
        Armi arma = WeaponsTable.getItems().get(index);
        if(player.Are_Already_Holding_Weapon() && arma.Is_Holding_a_Weapon()){
            WeaponsTable.getItems().get(index).set_Holding_Weapon(false);
            historyLabel.setText("Hai rimosso l'equipaggiamento '"+arma.getName()+"'\nOra combatteria a mani nude");
            player.setAlready_Holding_weapon(false);
            player.getCurrent_Holding_Weapon().setHolding_Not_Active_Property();
            /* se il cooldown non è terminato, il user avrà la sensazione di taccare a mani nude, ma la current weapon sarà != null */
            player.ResetCurrent_Holding_Weapon();
            player.ResetDiceForDamage();
            player.ResetNumber_Of_Dice_For_Dmg();
            diceLabel.setText("1d"+player.getDiceForDamage().getDiceMaxValue());
            this.RefreshTableView(index);
        }
        else{
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Weapon Suitable");
            alert.initOwner(this.stage);
            alert.setHeaderText("You are already not wearing weapon");
            alert.setContentText("Click Ok Bottun to close this view");
            alert.show();
            if(event != null)
                event.consume();
        }
    }

    public void RefreshTableView(int index) throws IOException{
        if(index >= 0)
            WeaponsTable.getItems().get(index).setHoldingProperty();
        WeaponsTable.refresh();
    }

        /* Getsione della tastiera */
    public void ActionPlayerWithKey(KeyEvent keyEvent) throws IOException{
        if(keyEvent.getCode().equals(KeyCode.E)){
            this.SuittingWeapon(null);
        }
        else if(keyEvent.getCode().equals(KeyCode.U)){
            this.UndressWeapon(null);
        }
    }

    public void setTableClass(App app, Stage stage, Label diceLabel, Label historyLabel){
        this.stage = stage;
        this.diceLabel = diceLabel;
        this.historyLabel = historyLabel;
        this.player = app.getPlayer();
        ObservableList<Armi> list = FXCollections.observableArrayList();
        List<Armi> temp = app.getPlayer().getClassPgClass().getWeaponList();
        for (Armi armi : temp) {
            list.add(armi);
        }
        WeaponsTable.setItems(list);
    }


}
