package Windows;

import Statements.IStatement;
import Statements.ProgramState;
import Tuple.Tuple;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Controller.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Callback;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;


public class ExecutionWindow extends Scene {

    private Stage stage;
    private Scene parentScene;

    private ProgramState currentProgram = null;

    private Button runOneStep;
    private Button goBack;

    private Map<Integer, Integer> currentHeap;
    private ObservableList<Map.Entry<Integer, Integer>> heapItems;

    private Map<String, Integer> currentLookupTable;
    private ObservableList<Map.Entry<String, Integer>> lookupTableItems;

    private Map<Integer, Tuple<String, BufferedReader>> currentFileTable;
    private ObservableList<Map.Entry<Integer, Tuple<String, BufferedReader>>> fileTableItems;

    private String exeStackString;
    private ObservableList<String> exeItems;


    private ObservableList<String> outputList;

    private ObservableList<String> programStatesIds;

    private List<String> currentOutput;

    private TextField nrPrg;
    private Controller c;

    private TableView<Map.Entry<Integer, Integer>> heapTable;
    private ListView<String> outTable;
    private TableView<Map.Entry<Integer, Tuple<String, BufferedReader>>> fileTableW;

    private TableView<Map.Entry<Integer, Integer>> lockTableW;
    private ObservableList<Map.Entry<Integer, Integer>> lockEntries;
    private Map<Integer, Integer> currentLockTable;

    private ListView<String> prgListIDs;

    private BorderPane bp;

    private TableView<Map.Entry<String, Integer>> variablesTable;
    private ListView<String> exeStackTable;




    ExecutionWindow(Scene root, int sizeX, int sizeY, Controller controller, Stage s){

        super (new GridPane(), sizeX, sizeY);
        stage = s;
        c = controller;
        parentScene = root;
        bp = new BorderPane();
        bp.getStylesheets().add("sample/application.css");
        nrPrg = new TextField();
        nrPrg.setText("Number of programmes is: " + c.getNrProgrammes());
        bp.setTop(nrPrg);
        setRoot(bp);
        currentProgram = c.getProgrammes().get(0);
        VBox leftLayout = new VBox();
        leftLayout.getChildren().addAll(new Label("Heap content:"));
        currentHeap = c.getProgrammes().get(0).getHeap().toMap();
        heapTable = new TableView<>();
        goHeap();
        leftLayout.getChildren().addAll(heapTable);

        currentFileTable = c.getProgrammes().get(0).getFileTable().toMap();
        fileTableW = new TableView<>();
        goFileTable();
        leftLayout.getChildren().addAll(new Label("File table:"), fileTableW);

        currentOutput = c.getProgrammes().get(0).getOutput().toList();
        outTable = new ListView<>();
        goOutput();
        leftLayout.getChildren().addAll(new Label("Output: "),outTable);

        lockTableW = new TableView<>();
        goLockTable();
        leftLayout.getChildren().addAll(new Label("Lock table: "), lockTableW);

        goBack = new Button("Back");
        goBackAction();


        VBox centerLayout = new VBox();
        programStatesIds = FXCollections.observableArrayList(c.getProgrammes().stream().map(prg -> {
            return prg.getID()+"";
        }).collect(Collectors.toList()));
        prgListIDs = new ListView<>();
        prgListIDs.setItems(programStatesIds);
        centerLayout.getChildren().addAll(new Label("Choose from program ID: "), prgListIDs);

        exeStackTable = new ListView<>();
        goExeStack();
        centerLayout.getChildren().addAll(new Label("Current exe stack:"), exeStackTable);

        runOneStep = new Button("Run one step");
        onAction();
        HBox btns = new HBox();
        btns.getChildren().addAll(runOneStep, goBack);
        centerLayout.getChildren().addAll(btns);

        VBox rightLayout = new VBox();
        variablesTable = new TableView<>();
        goLookupTable();
        rightLayout.getChildren().addAll(new Label("Variables:"), variablesTable);
        prgListIDs.setOnMouseClicked(this::changePrg);
        bp.setLeft(leftLayout);
        bp.setCenter(centerLayout);
        bp.setRight(rightLayout);

    }

    private void changePrg(MouseEvent mouseEvent) {
        int currentID = Integer.parseInt(prgListIDs.getSelectionModel().getSelectedItem());
        currentProgram = c.getProgramStateId(currentID);
        updateIDS();
        updateFileTable();
        updateHeap();
        goOutput();
        updateExeStack();
        updateCount();
        updateVariables();
    }

    private void goBackAction(){
        goBack.setOnAction(e-> {
            stage.setScene(parentScene);
            updateIDS();
            updateFileTable();
            updateHeap();
            goOutput();
            updateExeStack();
            updateCount();
            updateVariables();
            updateLock();
        });
    }

    private void onAction(){
        runOneStep.setOnAction(e -> {
            int currentID = Integer.parseInt(prgListIDs.getSelectionModel().getSelectedItem());
            currentProgram = c.getProgramStateId(currentID);
            c.oneStepForAll(c.getProgrammes());
            updateIDS();
            updateFileTable();
            updateHeap();
            goOutput();
            updateExeStack();
            updateCount();
            updateVariables();
            updateLock();
        });
    }


    private void goLookupTable(){
        TableColumn<Map.Entry<String, Integer>, String> column1 = new TableColumn<>("Variable name");
        column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });
        TableColumn<Map.Entry<String, Integer>, String> column2 = new TableColumn<>("Value");
        column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> p) {
                return new SimpleStringProperty(p.getValue().getValue().toString());
            }
        });
        currentLookupTable = currentProgram.getLookupTable().toMap();
        lookupTableItems = FXCollections.observableArrayList(currentLookupTable.entrySet());
        variablesTable.setItems(lookupTableItems);
        variablesTable.getColumns().addAll(column1, column2);
    }

    private void goLockTable(){
        TableColumn<Map.Entry<Integer, Integer>, String> column1 = new TableColumn<>("Number");
        column1.setCellValueFactory(
                (p) -> {
                    return new SimpleStringProperty(p.getValue().getKey().toString());
                }
        );
        TableColumn<Map.Entry<Integer, Integer>, String> column2 = new TableColumn<>("Value");
        column2.setCellValueFactory(
                (p) -> {
                    return new SimpleStringProperty(p.getValue().getValue().toString());
                }
        );
        currentLockTable = currentProgram.getLockTable().toMap();
        lockEntries = FXCollections.observableArrayList(currentLockTable.entrySet());
        lockTableW.setItems(lockEntries);
        lockTableW.getColumns().addAll(column1, column2);
    }

    private void updateLock(){
        currentLockTable = currentProgram.getLockTable().toMap();
        lockEntries = FXCollections.observableArrayList(currentLockTable.entrySet());
        lockTableW.setItems(lockEntries);
    }

    private void updateVariables(){
        currentLookupTable = currentProgram.getLookupTable().toMap();
        lookupTableItems = FXCollections.observableArrayList(currentLookupTable.entrySet());
        variablesTable.setItems(lookupTableItems);
    }

    private void updateIDS(){
        programStatesIds = FXCollections.observableArrayList(c.getProgrammes().stream().map(prg -> {
            return prg.getID()+"";
        }).collect(Collectors.toList()));
        prgListIDs.setItems(programStatesIds);
    }

    private void goHeap(){

    TableColumn<Map.Entry<Integer, Integer>, String> column1 = new TableColumn<>("Address");
    column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String> p) {
            return new SimpleStringProperty(p.getValue().getKey().toString());
        }
    });
    TableColumn<Map.Entry<Integer, Integer>, String> column2 = new TableColumn<>("Content");
        column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String> p) {
            return new SimpleStringProperty(p.getValue().getValue().toString());
        }
    });

    heapItems = FXCollections.observableArrayList(currentHeap.entrySet());
    heapTable.setItems(heapItems);
    heapTable.getColumns().setAll(column1, column2);

    }

    private void updateHeap(){
        currentHeap = currentProgram.getHeap().toMap();
        heapItems = FXCollections.observableArrayList(currentHeap.entrySet());
        heapTable.setItems(heapItems);
    }

    private void goFileTable() {

        TableColumn<Map.Entry<Integer, Tuple<String, BufferedReader>>, String> column1 = new TableColumn<>("File descriptor:");
        column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Tuple<String, BufferedReader>>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Tuple<String, BufferedReader>>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey().toString());
            }
        });
        TableColumn<Map.Entry<Integer, Tuple<String, BufferedReader>>, String> column2 = new TableColumn<>("Filename:");
        column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Tuple<String, BufferedReader>>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Tuple<String, BufferedReader>>, String> p) {
                return new SimpleStringProperty(p.getValue().getValue().x);
            }
        });

        currentFileTable = currentProgram.getFileTable().toMap();
        fileTableItems = FXCollections.observableArrayList(currentFileTable.entrySet());
        fileTableW.setItems(fileTableItems);
        fileTableW.getColumns().setAll(column1, column2);

    }

    private void updateCount(){
        nrPrg.setText("Number of programmes is: " + c.getNrProgrammes());
    }
    private void updateFileTable(){
        currentFileTable = currentProgram.getFileTable().toMap();
        fileTableItems  = FXCollections.observableArrayList(currentFileTable.entrySet());
        fileTableW.setItems(fileTableItems);
    }

    private void goOutput(){
        currentOutput = currentProgram.getOutput().toList();
        outputList = FXCollections.observableArrayList(currentOutput);
        outTable.setItems(outputList);

    }
    private void goExeStack(){

        exeStackString = "";
        exeItems = FXCollections.observableArrayList(exeStackString);
        exeStackTable.setItems(exeItems);

    }

    private void updateExeStack(){
        exeStackString = currentProgram.toString();
        exeItems = FXCollections.observableArrayList(exeStackString);
        exeStackTable.setItems(exeItems);
    }

    }




