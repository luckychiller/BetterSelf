package com.example.betterself;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class IndexController implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private VBox Dashboard;
    @FXML
    private VBox My_Progress;
    @FXML
    private VBox My_Team;
    @FXML
    private VBox Leaderboard;
    @FXML
    private VBox Timeline;
    @FXML
    private VBox Setting;
    @FXML
    private VBox About_Us;
    @FXML
    private Button DashboardButton;
    @FXML
    private Button My_ProgressButton;
    @FXML
    private Button My_TeamButton;
    @FXML
    private Button LeaderboardButton;
    @FXML
    private Button TimelineButton;
    @FXML
    private Button SettingButton;
    @FXML
    private Button About_UsButton;
    @FXML
    private Slider ThemeChanger;
    @FXML
    private Button AddItem;
    @FXML
    private TextField AddTaskName;
    @FXML
    private DatePicker AddTaskDate;
    @FXML
    private ComboBox<String> AddTaskType;
    //@FXML
    //TableView<ItemDTO> tableView;
    @FXML
    ListView<ItemDTO> TodoList;
//    @FXML
//    private TableColumn<ItemDTO,String> status;
//    @FXML
//    private TableColumn<ItemDTO,String> task;
//    @FXML
//    private TableColumn<ItemDTO,String> due;
//    @FXML
//    private TableColumn<ItemDTO,Button> edit;
    ObservableList<ItemDTO> observableList = FXCollections.observableArrayList();
    @FXML
    private void OnAddItemButtonClicked(ActionEvent event) throws IOException {
        String aa = AddTaskName.getText();
        LocalDate selectedDate = AddTaskDate.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String bb = selectedDate.format(formatter);
        Button closeString = new Button("X");

        ItemDTO newItem = new ItemDTO("New", aa, bb, closeString);
        observableList.add(newItem);
        AddTaskName.clear();
        AddTaskDate.setValue(null);

        closeString.setOnAction(e -> {
            System.out.println(closeString.getParent());
            int rowIndex = observableList.indexOf(closeString.getParent());
            System.out.println("Close button in row " + rowIndex + " clicked.");
            if (rowIndex >= 0) {
                System.out.println("Close button in row " + rowIndex + " clicked.");
                observableList.remove(rowIndex);
                //other acitivities
            }
        });

    }
    @FXML
    private void OnDashboardButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(true);
        My_Progress.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        Timeline.setVisible(false);
        Setting.setVisible(false);
        About_Us.setVisible(false);
    }
    @FXML
    private void My_ProgressButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Progress.setVisible(true);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        Timeline.setVisible(false);
        Setting.setVisible(false);
        About_Us.setVisible(false);
    }
    @FXML
    private void My_TeamButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Progress.setVisible(false);
        My_Team.setVisible(true);
        Leaderboard.setVisible(false);
        Timeline.setVisible(false);
        Setting.setVisible(false);
        About_Us.setVisible(false);
    }
    @FXML
    private void LeaderboardButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Progress.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(true);
        Timeline.setVisible(false);
        Setting.setVisible(false);
        About_Us.setVisible(false);
    }
    @FXML
    private void TimelineButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Progress.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        Timeline.setVisible(true);
        Setting.setVisible(false);
        About_Us.setVisible(false);
    }
    @FXML
    private void SettingButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Progress.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        Timeline.setVisible(false);
        Setting.setVisible(true);
        About_Us.setVisible(false);
    }
    @FXML
    private void About_UsButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Progress.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        Timeline.setVisible(false);
        Setting.setVisible(false);
        About_Us.setVisible(true);
    }
    @FXML
    protected void onHelpButtonClick (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Help-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 752, 641);
        stage.setTitle("BetterSelf -> Help");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onLogOutClick (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 752, 641);
        stage.setTitle("BetterSelf -> Help");
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TodoList.setItems(observableList);
        TodoList.refresh();
        TodoList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //        tableView.setItems(observableList);
//        tableView.refresh();
//        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        status.setCellValueFactory(new PropertyValueFactory<>("status"));
//        task.setCellValueFactory(new PropertyValueFactory<>("task"));
//        due.setCellValueFactory(new PropertyValueFactory<>("due"));
//        edit.setCellValueFactory(new PropertyValueFactory<>("edit"));
        AddTaskType.getItems().addAll("Make It Habbit", "Time Bound", "Streak");
        //load all items from the database.
    }
}