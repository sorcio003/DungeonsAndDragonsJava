package com.dnd.it;

import java.util.List;

import com.dnd.it.GameSystem.Weapon.Armi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

    public void initialize(){
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getWeaponDescriptionByIdStringProperty(0));
        diceColumn.setCellValueFactory(cellData -> cellData.getValue().getWeaponDescriptionByIdStringProperty(1));
        PropertyColumn.setCellValueFactory(cellData -> cellData.getValue().getWeaponDescriptionByIdStringProperty(2));
        holdingColumn.setCellValueFactory(cellData -> cellData.getValue().getHoldingProperty());
    }

    public void setTableClass(App app){
        ObservableList<Armi> list = FXCollections.observableArrayList();
        List<Armi> temp = app.getPlayer().getClassPgClass().getWeaponList();
        for (Armi armi : temp) {
            list.add(armi);
        }
        WeaponsTable.setItems(list);
    }


}
