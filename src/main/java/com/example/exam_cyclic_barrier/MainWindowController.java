package com.example.exam_cyclic_barrier;

import Controller.Controller;
import Model.PrgState;
import Model.adt.IStack;
import Model.stmt.IStmt;
import Model.value.IValue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {

    @FXML
    private ListView<String> exeStackView;

    @FXML
    private Button execButton;

    @FXML
    private ListView<String> fileTableView;

    @FXML
    private TableView<Map.Entry<Integer, Pair<Integer,List<Integer>>>> barrierTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer,List<Integer>>>,String> indexColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer,List<Integer>>>,String> valueColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer,List<Integer>>>,String> listColumn;

    @FXML
    private TableColumn<Map.Entry<Integer,IValue>,Integer> heapTableAddresses;

    @FXML
    private TableColumn<Map.Entry<Integer,IValue>,String> heapTableValues;

    @FXML
    private TableView<Map.Entry<Integer, IValue>> heapTableView;

    @FXML
    private TextField nrPrgStates;

    @FXML
    private ListView<String> outputtableView;

    @FXML
    private ListView<Integer> progIdentifiersView;

    @FXML
    private TableColumn<Map.Entry<String,IValue>,String> symbolTableNames;

    @FXML
    private TableColumn<Map.Entry<String,IValue>,String> symbolTableValues;

    @FXML
    private TableView<Map.Entry<String,IValue>> symbolTableView;

    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
        populateProgStatesCount();
        populateIdentifiersView();
        execButton.setDisable(false);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.controller = null;

        heapTableAddresses.setCellValueFactory(p-> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapTableValues.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue() + " "));

        symbolTableNames.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey() + " "));
        symbolTableValues.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue() + " "));

        progIdentifiersView.setOnMouseClicked(mouseEvent -> changePrgState(getSelectedProgramState()));
        indexColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey() + " "));
        valueColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue().getKey() + " "));
        listColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue().getValue() + " "));

        execButton.setDisable(true);
    }

    private void changePrgState(PrgState currentProgState){
        if(currentProgState==null)
            return;
        try {
            populateProgStatesCount();
            populateIdentifiersView();
            populateHeapTableView(currentProgState);
            populateOutputView(currentProgState);
            populateFileTableView(currentProgState);
            populateExeStackView(currentProgState);
            populateSymTableView(currentProgState);
            populateBarrierTableView(currentProgState);
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR,e.getMessage());
            error.show();
        }

    }

    public void oneStepHandler(ActionEvent actionEvent) {
        if(controller==null){
            Alert error = new Alert(Alert.AlertType.ERROR,"No program selected!");
            error.show();
            execButton.setDisable(true);
            return;
        }
        PrgState programState = getSelectedProgramState();
        if(programState!=null && programState.isCompleted()){
            Alert error = new Alert(Alert.AlertType.ERROR,"Nothing left to execute!");
            error.show();
            return;
        }
        try {
            controller.executeOneStep();
            changePrgState(programState);
            if(controller.getRepository().getPrgList().size()==0)
                execButton.setDisable(true);

        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR,e.getMessage());
            error.show();
            execButton.setDisable(true);
        }

    }

    private PrgState getSelectedProgramState(){
        if(progIdentifiersView.getSelectionModel().getSelectedIndex()==-1)
            return null;
        int id = progIdentifiersView.getSelectionModel().getSelectedItem();
        return controller.getRepository().getProgramWithId(id);
    }

    private void populateProgStatesCount(){
        nrPrgStates.setText("Nr of Program States:" + controller.getRepository().getPrgList().size());
    }

    private void populateBarrierTableView(PrgState givenProgramState){
        barrierTableView.setItems(FXCollections.observableList(new ArrayList<>(givenProgramState.getBarrierTable().getContent().entrySet())));
        barrierTableView.refresh();
    }

    private void populateHeapTableView(PrgState givenProgramState){
        heapTableView.setItems(FXCollections.observableList(new ArrayList<>(givenProgramState.getHeapTable().getContent().entrySet())));
        heapTableView.refresh();
    }

    private void populateOutputView(PrgState givenProgramState) throws Exception {
        outputtableView.setItems(FXCollections.observableArrayList(givenProgramState.getOutput().getContent()));
    }

    private void populateFileTableView(PrgState givenProgramState){
        fileTableView.setItems(FXCollections.observableArrayList(givenProgramState.getFileTable().getContent().keySet()));
    }
    private void populateIdentifiersView(){
        progIdentifiersView.setItems(FXCollections.observableArrayList(controller.getRepository().getPrgList().stream().map(PrgState::getThreadID).collect(Collectors.toList())));
        progIdentifiersView.refresh();
    }

    private void populateExeStackView(PrgState givenProgramState){
        IStack<IStmt> stack = givenProgramState.getExecutionStack();
        List<String> stackOutput = new ArrayList<>();
        for (Object stm : stack.getValues()){
            stackOutput.add(stm.toString());
        }
        Collections.reverse(stackOutput);
        exeStackView.setItems(FXCollections.observableArrayList(stackOutput));
    }
    private void populateSymTableView(PrgState givenProgramState){
        symbolTableView.setItems(FXCollections.observableList(new ArrayList<>(givenProgramState.getSymTable().getContent().entrySet())));
        symbolTableView.refresh();
    }

}
