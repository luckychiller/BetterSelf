package com.example.betterself;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class IndexController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private VBox Dashboard;
    @FXML
    private VBox My_Timeline;
    @FXML
    private VBox My_Team;
    @FXML
    private VBox Leaderboard;
    @FXML
    private VBox History;
    @FXML
    private VBox Setting;
    @FXML
    private VBox About_Us;
    @FXML
    private Button DashboardButton;
    @FXML
    private Button My_TimelineButton;
    @FXML
    private Button My_TeamButton;
    @FXML
    private Button LeaderboardButton;
    @FXML
    private Button HistoryButton;
    @FXML
    private Button SettingButton;
    @FXML
    private Button About_UsButton;
    @FXML
    private Slider ThemeChanger;
    @FXML
    private void onThemeChangerClicked(){

    }
    @FXML
    private void OnDashboardButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(true);
        My_Timeline.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        History.setVisible(false);
        Setting.setVisible(false);
        About_Us.setVisible(false);
    }
    @FXML
    private void My_TimelineButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Timeline.setVisible(true);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        History.setVisible(false);
        Setting.setVisible(false);
        About_Us.setVisible(false);
    }
    @FXML
    private void My_TeamButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Timeline.setVisible(false);
        My_Team.setVisible(true);
        Leaderboard.setVisible(false);
        History.setVisible(false);
        Setting.setVisible(false);
        About_Us.setVisible(false);
    }
    @FXML
    private void LeaderboardButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Timeline.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(true);
        History.setVisible(false);
        Setting.setVisible(false);
        About_Us.setVisible(false);
    }
    @FXML
    private void HistoryButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Timeline.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        History.setVisible(true);
        Setting.setVisible(false);
        About_Us.setVisible(false);
    }
    @FXML
    private void SettingButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Timeline.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        History.setVisible(false);
        Setting.setVisible(true);
        About_Us.setVisible(false);
    }
    @FXML
    private void About_UsButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Timeline.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        History.setVisible(false);
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
}