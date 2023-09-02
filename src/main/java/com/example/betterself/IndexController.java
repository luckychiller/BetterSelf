package com.example.betterself;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class IndexController {
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
}