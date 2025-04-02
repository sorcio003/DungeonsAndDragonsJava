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
    private Label GuardLabelText;
    @FXML
    private Label SpeedLabelText;
    @FXML
    private Label EnemyNameLabel;
    /*Attribute */
    @FXML
    private Label StrengthTextLabel;
    @FXML
    private Label DexterityTextLabel;
    @FXML
    private Label ConstitutionTextLabel;
    @FXML
    private Label IntelligenceTextLabel;
    @FXML
    private Label WisdomTextLabel;
    @FXML
    private Label CharismTextLabel;

    private App app;

    public void initialize(){

    }

    public void setData(App app){
        this.app = app;

        File file = new File("src/main/resources/Assets/Chracters_Icon/"+app.getEnemy().getRaceClass().getRace()+".jpg");
        EnemyImage.setImage(new Image(file.toURI().toString()));
        EnemyNameLabel.setText(app.getEnemy().getName());
        LifeLabelText.setText("" + app.getEnemy().getClassPgClass().getLife());
        GuardLabelText.setText("" + app.getEnemy().getClassPgClass().getGuard());
        SpeedLabelText.setText("" + app.getEnemy().getRaceClass().getSpeed());
        StrengthTextLabel.setText("" + app.getEnemy().getRaceClass().getStrength());
        DexterityTextLabel.setText("" + app.getEnemy().getRaceClass().getDexterity());
        ConstitutionTextLabel.setText("" + app.getEnemy().getRaceClass().getConstitution());
        IntelligenceTextLabel.setText("" + app.getEnemy().getRaceClass().getIntelligence());
        WisdomTextLabel.setText("" + app.getEnemy().getRaceClass().getWisdom());
        CharismTextLabel.setText("" + app.getEnemy().getRaceClass().getCharism());
    }

    public void text(String texString){
        LifeLabelText.setText(texString);
    }


}
