package com.example.exam_cyclic_barrier;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainWindowM.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 440);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        */

        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("MainWindowM.fxml"));
        Parent mainWindow = mainLoader.load();

        MainWindowController mainWindowController = mainLoader.getController();

        stage.setTitle("Main Window");
        stage.setScene(new Scene(mainWindow, 700, 650));
        stage.show();


        FXMLLoader secondaryLoader = new FXMLLoader();
        secondaryLoader.setLocation(getClass().getResource("ProgramsWindow.fxml"));
        Parent secondaryWindow = secondaryLoader.load();
        ProgramsWindowController selectWindowController = secondaryLoader.getController();
        selectWindowController.setMainWindowController(mainWindowController);

        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Programs Window");
        secondaryStage.setScene(new Scene(secondaryWindow, 500, 400));
        secondaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}