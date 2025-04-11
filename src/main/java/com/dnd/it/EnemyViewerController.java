package com.dnd.it;
import java.io.InputStream;

import com.dnd.it.GameSystem.Model.Characters;

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
    
    private Characters enemy;

    public void initialize(){

    }

    public void setData(App app){
        this.enemy = app.getEnemy();

        InputStream inputStream = getClass().getResourceAsStream("/Assets/Chracters_Icon/" + enemy.getRaceClass().getRace() + ".jpg");
        EnemyImage.setImage(new Image(inputStream));
        EnemyNameLabel.setText(enemy.getName());
        LifeLabelText.setText("" + enemy.getClassPgClass().getLife());
        GuardLabelText.setText("" + enemy.getClassPgClass().getGuard());
        SpeedLabelText.setText("" + enemy.getRaceClass().getSpeed());
        StrengthTextLabel.setText("" + enemy.getRaceClass().getStrength());
        DexterityTextLabel.setText("" + enemy.getRaceClass().getDexterity());
        ConstitutionTextLabel.setText("" + enemy.getRaceClass().getConstitution());
        IntelligenceTextLabel.setText("" + enemy.getRaceClass().getIntelligence());
        WisdomTextLabel.setText("" + enemy.getRaceClass().getWisdom());
        CharismTextLabel.setText("" + enemy.getRaceClass().getCharism());
    }

    public void text(String texString){
        LifeLabelText.setText(texString);
    }


}
