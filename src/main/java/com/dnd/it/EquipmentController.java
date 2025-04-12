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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private Button SuitWeaponBTN;
    @FXML
    private Button UndressWeaponBTN;

    private Characters player;
    private Stage stage;

    public void initialize(){
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getWeaponDescriptionByIdStringProperty(0));
        diceColumn.setCellValueFactory(cellData -> cellData.getValue().getWeaponDescriptionByIdStringProperty(1));
        PropertyColumn.setCellValueFactory(cellData -> cellData.getValue().getWeaponDescriptionByIdStringProperty(2));
        holdingColumn.setCellValueFactory(cellData -> cellData.getValue().getHoldingProperty());
    }

    public void SuittingWeapon(ActionEvent event) throws IOException{
        int index = WeaponsTable.getSelectionModel().getSelectedIndex();
        if(! player.Are_Already_Holding_Weapon() && ! WeaponsTable.getItems().get(index).Is_Holding_a_Weapon()){
            WeaponsTable.getItems().get(index).set_Holding_Weapon(true);
            player.setAlready_Holding_weapon(true);
            this.RefreshTableView(index);
        }
        else{
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Weapon Suitable");
            alert.initOwner(this.stage);
            alert.setHeaderText("You Cannot Suit more than 1 weapon at same time");
            alert.setContentText("Click Ok Bottun to close this view");
            alert.show();
            event.consume();
        }
    }

    public void UndressWeapon(ActionEvent event) throws IOException{
        int index = WeaponsTable.getSelectionModel().getSelectedIndex();
        if(player.Are_Already_Holding_Weapon() && WeaponsTable.getItems().get(index).Is_Holding_a_Weapon()){
            WeaponsTable.getItems().get(index).set_Holding_Weapon(false);
            player.setAlready_Holding_weapon(false);
            this.RefreshTableView(index);
        }
        else{
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Weapon Suitable");
            alert.initOwner(this.stage);
            alert.setHeaderText("You are already not wearing weapon");
            alert.setContentText("Click Ok Bottun to close this view");
            alert.show();
            event.consume();
        }
    }

    private void RefreshTableView(int index) throws IOException{
        WeaponsTable.getItems().get(index).setHoldingProperty();
        WeaponsTable.refresh();
    }

    public void setTableClass(App app, Stage stage){
        this.stage = stage;
        this.player = app.getPlayer();
        ObservableList<Armi> list = FXCollections.observableArrayList();
        List<Armi> temp = app.getPlayer().getClassPgClass().getWeaponList();
        for (Armi armi : temp) {
            list.add(armi);
        }
        WeaponsTable.setItems(list);
    }


}
