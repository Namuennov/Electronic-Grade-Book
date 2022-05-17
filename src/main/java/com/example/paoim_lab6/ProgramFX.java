package com.example.paoim_lab6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProgramFX extends Application {

    private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoader;
    public static Student loggedStudent;
    public static ScreenController screenController;

    @Override
    public void start(Stage stage) throws IOException {
        fxmlLoader = new FXMLLoader(ProgramFX.class.getResource("programFX-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setTitle("School Simulator 2.0");
        stage.setScene(scene);
        stage.show();

        screenController = new ScreenController(scene);
        screenController.addScreen("programFX-view", FXMLLoader.load(getClass().getResource( "programFX-view.fxml" )));
    }

    public static void main(String[] args) {
        new DataGenerator();
        launch();
    }
}
