package com.example.betterself;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
public class IndexController implements Initializable{
    public static UserData OurPerson;
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
    private VBox Profile;
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
    private Label StreakLabel;


    @FXML
    private ImageView ProfilePic;



    @FXML
    private PieChart ActivityChart;
    @FXML
    private LineChart<Number,Number> DeadlineChart;
    XYChart.Series<Number, Number> series1;
    private int DailyDone=0,DailyIncomplete=0,TimeBoundCount=0,StreakCount=0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //load all required items from the database for the user
        ProfilePic.setImage(new Image(new ByteArrayInputStream(OurPerson.Profile_Pic)));
        //Update program variables
        //setup the profile page
        //setup the leaderBoard
        //setup the timeline
        //setup my team page
        //setup the charts
        DeadlineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        DeadlineChart.setCreateSymbols(true);
        series1 = new XYChart.Series<>();
        series1.setName("Finish Within");
        DeadlineChart.getData().add(series1);
        TodoList.refresh();
        TodoList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        AddTaskType.getItems().addAll("Make It Habbit", "Finish Within", "Daily Duty");
        PieChart.Data slice1 = new PieChart.Data("Complete", DailyDone);
        PieChart.Data slice2 = new PieChart.Data("Incomplete", DailyIncomplete);
        ActivityChart.getData().addAll(slice1, slice2);
    }
    private void UpdateActivityChart(String TaskType,int Incrim,long days_left){
        if(TaskType.equals("Make It Habbit")) {
            StreakCount+=Incrim;
            StreakLabel.setText(String.valueOf(StreakCount));
        }
        else {
            if (TaskType.equals("Finish Within")) {
                TimeBoundCount++;
                XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(days_left, (Math.random())%5);
                series1.getData().add(dataPoint);
                DeadlineChart.layout();
                //Add item and deadline to graph.
            }
            else {
                PieChart.Data categoryAData = null;
                if(Incrim>0){
                    DailyIncomplete++;
                    for (PieChart.Data data : ActivityChart.getData()) {
                        if (data.getName().equals("Incomplete")) {
                            categoryAData = data;
                            break;
                        }
                    }
                    if (categoryAData != null) {
                        categoryAData.setPieValue(DailyIncomplete);
                        ActivityChart.setData(ActivityChart.getData());
                    }
                }
                else{
                    DailyDone++;
                    DailyIncomplete--;
                    for (PieChart.Data data : ActivityChart.getData()) {
                        if (data.getName().equals("Complete")) {
                            categoryAData = data;
                            break;
                        }
                    }
                    if (categoryAData != null) {
                        categoryAData.setPieValue(DailyDone);
                        ActivityChart.setData(ActivityChart.getData());
                    }
                    for (PieChart.Data data : ActivityChart.getData()) {
                        if (data.getName().equals("Incomplete")) {
                            categoryAData = data;
                            break;
                        }
                    }
                    if (categoryAData != null) {
                        categoryAData.setPieValue(DailyIncomplete);
                        ActivityChart.setData(ActivityChart.getData());
                    }
                }
            }
        }
    }
    @FXML
    private void OnAddItemButtonClicked(ActionEvent event) throws IOException {
        String aa = AddTaskName.getText();
        LocalDate selectedDate = AddTaskDate.getValue();
        LocalDate today = LocalDate.now();
        long daysBetween = today.until(selectedDate, ChronoUnit.DAYS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String bb = selectedDate.format(formatter);
        String _TaskType = AddTaskType.getValue();
        Button closeString = new Button("X");

        HBox hbox = new HBox();
        AddTaskName.clear();
        AddTaskDate.setValue(null);

        closeString.setOnAction(e -> {
            HBox hbox1 = (HBox) closeString.getParent();
            Label qwerty = (Label) hbox1.getChildren().get(2);
            TodoList.getItems().remove(hbox1);
            String ttext = qwerty.getText();
            UpdateActivityChart(ttext,-1,daysBetween);
        });

        Label taskLabel = new Label(aa);
        taskLabel.setPrefWidth(250);
        taskLabel.setTextFill(Color.RED);
        taskLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        Label taskType = new Label(_TaskType);
        taskLabel.setPrefWidth(200);
        taskLabel.setTextFill(Color.GREEN);
        taskLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        Label dateLabel = new Label(bb);
        dateLabel.setPrefWidth(150);
        dateLabel.setTextFill(Color.BLUE);
        dateLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        closeString.setStyle("-fx-background-color: #0000FF; -fx-text-fill: #FFFFFF");

        hbox.getChildren().add(taskLabel);
        hbox.getChildren().add(dateLabel);
        hbox.getChildren().add(taskType);
        hbox.getChildren().add(closeString);

        TodoList.getItems().add(hbox);
        UpdateActivityChart(_TaskType,1,daysBetween);
    }
    @FXML
    private void OnDashboardButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(true);
        My_Progress.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        Timeline.setVisible(false);
        Profile.setVisible(false);
        About_Us.setVisible(false);
    }
    @FXML
    private void My_ProgressButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Progress.setVisible(true);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        Timeline.setVisible(false);
        Profile.setVisible(false);
        About_Us.setVisible(false);
    }
    @FXML
    private void My_TeamButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Progress.setVisible(false);
        My_Team.setVisible(true);
        Leaderboard.setVisible(false);
        Timeline.setVisible(false);
        Profile.setVisible(false);
        About_Us.setVisible(false);
    }
    @FXML
    private void LeaderboardButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Progress.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(true);
        Timeline.setVisible(false);
        Profile.setVisible(false);
        About_Us.setVisible(false);
    }
    @FXML
    private void TimelineButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Progress.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        Timeline.setVisible(true);
        Profile.setVisible(false);
        About_Us.setVisible(false);
    }
    @FXML
    private void SettingButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Progress.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        Timeline.setVisible(false);
        Profile.setVisible(true);
        About_Us.setVisible(false);
    }
    @FXML
    private void About_UsButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Progress.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        Timeline.setVisible(false);
        Profile.setVisible(false);
        About_Us.setVisible(true);
    }
    @FXML
    protected void onHelpButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Help-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 752, 641);
        stage.setTitle("BetterSelf -> Help");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onLogOutClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 752, 641);
        stage.setTitle("BetterSelf -> Help");
        stage.setScene(scene);
        stage.show();
    }
}