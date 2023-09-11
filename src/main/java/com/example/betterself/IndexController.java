package com.example.betterself;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    @FXML
    ListView<HBox> TodoList;
    @FXML
    private void OnAddItemButtonClicked(ActionEvent event) throws IOException {
        String aa = AddTaskName.getText();
        LocalDate selectedDate = AddTaskDate.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String bb = selectedDate.format(formatter);
        Button closeString = new Button("X");

        HBox hbox = new HBox();
        AddTaskName.clear();
        AddTaskDate.setValue(null);

        closeString.setOnAction(e -> {
            HBox hbox1 = (HBox) closeString.getParent();
            TodoList.getItems().remove(hbox1);
        });

        Label taskLabel = new Label(aa);
        taskLabel.setPrefWidth(250);
        taskLabel.setTextFill(Color.RED);
        taskLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        Label dateLabel = new Label(bb);
        dateLabel.setPrefWidth(150);
        dateLabel.setTextFill(Color.BLUE);
        dateLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        closeString.setStyle("-fx-background-color: #0000FF; -fx-text-fill: #FFFFFF");

        hbox.getChildren().add(taskLabel);
        hbox.getChildren().add(dateLabel);
        hbox.getChildren().add(closeString);

        TodoList.getItems().add(hbox);
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
        TodoList.refresh();
        TodoList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        AddTaskType.getItems().addAll("Make It Habbit", "Time Bound", "Streak");
        //load all items from the database.
    }
}