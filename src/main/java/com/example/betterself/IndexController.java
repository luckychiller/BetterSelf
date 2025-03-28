package com.example.betterself;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.*;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private Label StreakLabel;
    @FXML
    private PieChart ActivityChart;
    private int DailyDone=0,DailyIncomplete=0,TimeBoundCount=0,StreakCount=0,rowCount;


    @FXML
    private VBox Profile;
    @FXML
    private Button SettingButton;//profile button
    @FXML
    private ImageView ProfilePic;
    @FXML
    private Label PName;
    @FXML
    private Label PEmail;
    @FXML
    private Label PStreaks;
    @FXML
    private Label PDaily;
    @FXML
    private Label PCompl;
    @FXML
    private Label PPosition;


    @FXML
    private VBox Dashboard;
    @FXML
    private Button DashboardButton;
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
    private VBox My_Team;
    @FXML
    private Button My_TeamButton;

    @FXML
    private VBox Leaderboard;
    @FXML
    private Button LeaderboardButton;
    @FXML
    ListView<HBox> TopGuys = new ListView<>();
    ObservableList<HBox> items = FXCollections.observableArrayList();

    @FXML
    private VBox Timeline;
    @FXML
    private Button TimelineButton;
    @FXML
    private LineChart<Number,Number> timelineChart;
    XYChart.Series<Number, Number> series1;
    private int x,y,z;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        /////////////////////////////////////////////////////////////////////////////////
        //load all required items from the database for the user
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Intel.ser"))) {
            OurPerson = (UserData) ois.readObject();
            System.out.println("Object has been read from the file:");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        /////////////////////////////////////////////////////////////////////////

        //set up the dashboard
        TodoList.refresh();
        TodoList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        AddTaskType.getItems().addAll("Make It Habbit", "Finish Within", "Daily Duty");
        //Add items from the database

        ////////////////////////////////////////////////////////////////

        //set up my team page


        ///////////////////////////////////////////////////////////////////////

        //set up the leaderBoard
        TopGuys.setItems(items);
        java.sql.Connection con = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading the driver" + e);
        }
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "user1156");
            String sql="SELECT * FROM BETTERUSER ORDER BY POINTS DESC";
            PreparedStatement statement=con.prepareStatement(sql);
            ResultSet rs_ = statement.executeQuery();
            while (rs_.next()) {
                Label name = new Label(rs_.getString("NAME"));
                Label points = new Label(String.valueOf(rs_.getInt("Points")));
                name.setPrefWidth(300);
                if(name.getText().equals(OurPerson.Name))
                {
                    name.setTextFill(Color.BLUE);
                    points.setTextFill(Color.BLUE);
                }
                else
                {
                    name.setTextFill(Color.GREEN);
                    points.setTextFill(Color.GREEN);
                }
                name.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
                points.setPrefWidth(50);
                points.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
                HBox hBox = new HBox();
                hBox.getChildren().add(name);
                hBox.getChildren().add(points);
                TopGuys.getItems().add(hBox);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(1);
            throw new RuntimeException(e);
        }


        ////////////////////////////////////////////////////////////////


        //set up the timeline
        timelineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        timelineChart.setCreateSymbols(true);
        series1 = new XYChart.Series<>();
        series1.setName("Past Activity");
        timelineChart.getData().add(series1);
        //add items from the history table
        //update the time chart

        //////////////////////////////////////////////////////////////////////


        //set up the profile page
        ProfilePic.setImage(new Image(new ByteArrayInputStream(OurPerson.Profile_Pic)));
        PName.setText(OurPerson.Name);
        PEmail.setText(OurPerson.Email);
        PStreaks.setText(String.valueOf(OurPerson.Total_Streaks));
        PDaily.setText(String.valueOf(OurPerson.Total_Daily));
        PCompl.setText(String.valueOf(OurPerson.Total_DeadLifts));
        PPosition.setText(String.valueOf(OurPerson.Points));

        ///////////////////////////////////////////////////////////////

        //Update program variables
        DailyDone=0;
        DailyIncomplete=0;// count from task table;
        TimeBoundCount=0; //count from task table;
        StreakCount=0;// count from task table;

        //////////////////////////////////////////////////////////////////////
        //update the streaks label
        StreakLabel.setText(String.valueOf(StreakCount));

        ///////////////////////////////////////////////////////////////
        //set up the pie charts
        PieChart.Data slice1 = new PieChart.Data("Complete", DailyDone);
        PieChart.Data slice2 = new PieChart.Data("Incomplete", DailyIncomplete);
        ActivityChart.getData().addAll(slice1, slice2);
    }
    private void UpdateActivityChart(String TaskType,int Incrim,long days_left,String TaskName){
        //include it to the timeline

        if(TaskType.equals("Make It Habbit")) {
            StreakCount+=Incrim;
            StreakLabel.setText(String.valueOf(StreakCount));
        }
        else {
            if (TaskType.equals("Finish Within")) {
                TimeBoundCount++;
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

        Label T_Id= new Label(String.valueOf(rowCount+1));
        T_Id.setVisible(false);

        closeString.setStyle("-fx-background-color: #0000FF; -fx-text-fill: #FFFFFF");
        closeString.setOnAction(e -> {
            HBox hbox1 = (HBox) closeString.getParent();
            Label QNm = (Label) hbox1.getChildren().get(1);
            Label QDt = (Label) hbox1.getChildren().get(2);
            Label QTy = (Label) hbox1.getChildren().get(3);
            Label QId = (Label) hbox1.getChildren().get(4);

            java.sql.Connection con = null;
            try {
                Class.forName("oracle.jdbc.OracleDriver");
            } catch (ClassNotFoundException eX) {
                System.out.println("Error loading the driver" + eX);
            }
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "user1156");
                String sql = "insert into HISTORY (USER_Id,TASK_ID,TASK_NAME,TASK_DATE,TASK_TYPE) values(?,?,?,to_date(?,'mm/dd/yyyy'),?)";
                PreparedStatement statement = con.prepareStatement(sql);

                statement.setInt(1, OurPerson.UserId);
                statement.setInt(2, Integer.parseInt((QId.getText())));
                statement.setString(3, QNm.getText());
                statement.setString(4, QDt.getText());
                statement.setString(5, QTy.getText());
                statement.executeUpdate();
                System.out.println("Inserted into history");

                String sqlQuery2 = "DELETE FROM TASK WHERE USER_Id=? AND TASK_ID=? AND TASK_NAME=? AND TASK_DATE=? AND TASK_TYPE=?";
                PreparedStatement statement2 = con.prepareStatement(sqlQuery2);
                statement2.setInt(1, OurPerson.UserId);
                statement2.setInt(2, Integer.parseInt((QId.getText())));
                statement2.setString(3, QNm.getText());
                statement2.setString(4, "to_date("+QDt.getText()+",'mm/dd/yyyy')");
                statement2.setString(5, QTy.getText());
                statement2.executeUpdate();
                System.out.println("removed from task table");
                con.close();
            } catch (SQLException eXX) {
                throw new RuntimeException(eXX);
            }
            TodoList.getItems().remove(hbox1);
            System.out.println("item removed");
            UpdateActivityChart(QTy.getText(),-1,daysBetween,QNm.getText());
            //update points and refresh the leaderboard
        });

        hbox.getChildren().add(taskLabel);
        hbox.getChildren().add(dateLabel);
        hbox.getChildren().add(taskType);
        hbox.getChildren().add(T_Id);
        hbox.getChildren().add(closeString);
        TodoList.getItems().add(hbox);

        java.sql.Connection con = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading the driver" + e);
        }
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "user1156");
            String sqlQuery = "SELECT MAX(TASK_ID) FROM TASK";
            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }
            String sql = "insert into TASK(USER_Id,TASK_ID,TASK_NAME,TASK_DATE,TASK_TYPE) values(?,?,?,to_date(?,'mm/dd/yyyy'),?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, OurPerson.UserId);
            statement.setInt(2, (rowCount+1));
            statement.setString(3, aa);
            statement.setString(4, selectedDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            statement.setString(5, _TaskType);
            statement.executeUpdate();
            System.out.println("Inserted");
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        UpdateActivityChart(_TaskType,1,daysBetween,aa);
    }
    @FXML
    private void OnDashboardButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(true);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        Timeline.setVisible(false);
        Profile.setVisible(false);
    }
    @FXML
    private void My_TeamButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Team.setVisible(true);
        Leaderboard.setVisible(false);
        Timeline.setVisible(false);
        Profile.setVisible(false);
    }
    @FXML
    private void LeaderboardButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(true);
        Timeline.setVisible(false);
        Profile.setVisible(false);
    }
    @FXML
    private void TimelineButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        Timeline.setVisible(true);
        Profile.setVisible(false);
    }
    @FXML
    private void SettingButtonClick(ActionEvent event) throws IOException{
        Dashboard.setVisible(false);
        My_Team.setVisible(false);
        Leaderboard.setVisible(false);
        Timeline.setVisible(false);
        Profile.setVisible(true);
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
        //save data to the database
    }
}