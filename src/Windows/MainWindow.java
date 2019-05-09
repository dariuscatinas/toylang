package Windows;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import Controller.Controller;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;


public class MainWindow extends Scene {

    private VBox mainLayout;
    private ObservableList<String> programmes;
    private List<Controller> ctrls;
    private ListView<String> prgList;
    private Button selectBtn = new Button("Select");
    private Button closeBtn = new Button("Close");
    private Stage stage;


    public MainWindow(Parent root, int sizeX , int sizeY, List<Controller> controllers, Stage stage){
        super(root, sizeX, sizeY);
        this.stage = stage;
        ctrls = controllers;
        List<String> prs = new ArrayList<>();
        for(Controller c : controllers){
            prs.add(c.originalProgram());
        }
        programmes = FXCollections.observableArrayList(prs);
        mainLayout = new VBox(8);
        mainLayout.getChildren().addAll(new Label("Choose from programes:"));

        prgList = new ListView<String>();
        prgList.setItems(programmes);
        mainLayout.getChildren().add(prgList);

        HBox buttonGrid = new HBox(10);
        buttonGrid.getChildren().add(selectBtn);
        buttonGrid.getChildren().add(closeBtn);
        mainLayout.getChildren().add(buttonGrid);
        mainLayout.getStylesheets().add("sample/application.css");

        setRoot(mainLayout);
        setActions();

    }

    private void setActions(){
        selectBtn.setOnAction(e -> {
            String repr = prgList.getSelectionModel().getSelectedItem();
            if (repr == null){
                return;
            }
            Controller found = null;
            for (Controller c: ctrls) {
                if (c.savedOriginalProblem().equals(repr)) {
                    found = c;
                    break;
                }
            }
            if (found != null){
                ctrls.remove(found);
                Scene newScene = new ExecutionWindow(this, 600, 400, found, stage);
                stage.setScene(newScene);

            }
        });
    }
}
