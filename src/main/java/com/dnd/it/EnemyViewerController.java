package com.dnd.it;


import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EnemyViewerController {
    @FXML
    private ImageView EnemyImage;
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
    private Label EnemyNameLabel;

    private App app;

    public void initialize(){

    }

    public void setData(App app){
        this.app = app;

        File file = new File("src/main/resources/Assets/Chracters_Icon/"+app.getEnemy().getRaceClass().getRace()+".jpg");
        EnemyImage.setImage(new Image(file.toURI().toString()));
        EnemyNameLabel.setText(app.getEnemy().getName());
        LifeLabelText.setText("" + app.getEnemy().getClassPgClass().getLife());
        BonusLabelText.setText("" + app.getEnemy().getClassPgClass().getBonus());
        ModifierLabelText.setText("" + app.getEnemy().getClassPgClass().getmodificatore());
        GuardLabelText.setText("" + app.getEnemy().getClassPgClass().getGuard());
        SpeedLabelText.setText("" + app.getEnemy().getRaceClass().getSpeed());
    }

    public void text(String texString){
        LifeLabelText.setText(texString);
    }


}
