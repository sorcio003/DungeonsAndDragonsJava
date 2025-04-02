package com.dnd.it;

import java.io.File;
import java.io.IOException;

import com.dnd.it.GameSystem.Classes.*;
import com.dnd.it.GameSystem.Model.Characters;
import com.dnd.it.GameSystem.Races.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    private Stage primaryStage;
    private Stage enemyViewer;
    private Stage mapViewer;
    private BorderPane rootLayout;

    private Characters player;
    private Characters enemy;

    private EnemyViewerController controller;
    private MapController mapcontroller;

    public App(){
        player = new Characters("Drake", 256, new Elf(), new Barbaro());
        player.getRaceClass().setStrength(14);
        player.getRaceClass().setBonus("Strength", 2);
        enemy = new Characters("Golem", 1098, new Golem(), new Barbaro());
        player.setDescription("Tutorials Point originated from the idea that there exists a class of readers who respond better to online content and prefer to learn new skills at their own pace from the comforts of their drawing rooms.");
        player.setMoneyValue(100);
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("D&D Java Game");

        initRootLayout();
        
        showEnemyViewer();

        showMap();

        showPersonOverview();

        primaryStage.setResizable(false);

        // Handler Close Action
        primaryStage.setOnCloseRequest(event ->{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Close");
            alert.initOwner(primaryStage);
            alert.setHeaderText("Are you sure to close App?");
            alert.setContentText("All not saved Data will lost.");

            // Wait User Answer
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.CANCEL) {
                event.consume();  // Cancel Closure Task
            } else {
                System.exit(0); // Close all Opened Windows
            }
        });
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            File file = new File("src/main/resources/Assets/Icon/D20.png");
            primaryStage.getIcons().add(new Image(file.toURI().toString()));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/Home.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            HomeController homeController = loader.getController();
            homeController.setApp(this);
            homeController.getEnemyController(this);
            homeController.getMapController(this);
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void showEnemyViewer(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/EnemyViewer.fxml"));
            AnchorPane enemyOverview = (AnchorPane) loader.load();

            EnemyViewerController enemyViewerController = loader.getController();
            enemyViewerController.setData(this);

            this.controller = enemyViewerController;
    
            Scene scene = new Scene(enemyOverview);
            enemyViewer = new Stage();
            enemyViewer.setTitle("D&D Java Game - Enemy");
            enemyViewer.setScene(scene);
            File file = new File("src/main/resources/Assets/Icon/D20.png");
            enemyViewer.getIcons().add(new Image(file.toURI().toString()));
            enemyViewer.show();

            enemyViewer.setResizable(false);
            enemyViewer.setOnCloseRequest(event ->{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Enemy Window");
                alert.initOwner(primaryStage);
                alert.setHeaderText("You Cannot Close this View");
                alert.setContentText("You must close all System from Main Page");
                alert.show();
                event.consume();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMap(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/Map.fxml"));
            AnchorPane mapOverview = (AnchorPane) loader.load();

            mapcontroller = loader.getController();
            mapcontroller.setMap(this);

            Scene scene = new Scene(mapOverview);
            mapViewer = new Stage();
            mapViewer.setTitle("D&D Java Game - Battle Map");
            mapViewer.setScene(scene);
            File file = new File("src/main/resources/Assets/Icon/Map.png");
            mapViewer.getIcons().add(new Image(file.toURI().toString()));
            mapViewer.show();

            mapViewer.setResizable(false);
            mapViewer.setOnCloseRequest(event ->{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Map Window");
                alert.initOwner(primaryStage);
                alert.setHeaderText("You Cannot Close this View");
                alert.setContentText("You must close all System from Main Page");
                alert.show();
                event.consume();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public Stage getEnemyStage(){
        return enemyViewer;
    }

    @SuppressWarnings("exports")
    public Characters getPlayer(){
        return player;
    }  

    @SuppressWarnings("exports")
    public Characters getEnemy(){
        return enemy;
    }

    public EnemyViewerController getEnemyController(){
        return controller;
    }

    public MapController getMapController(){
        return mapcontroller;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
