package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.*;
import Windows.MainWindow;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("conf.fxml"));
        primaryStage.setTitle("Welcome to Toy Language!");
        Scene mainW = new MainWindow(root, 1100, 700, Creator.createCommands(), primaryStage);
        primaryStage.setScene(mainW);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
