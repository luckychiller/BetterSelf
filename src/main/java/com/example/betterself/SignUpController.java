package com.example.betterself;

import eu.hansolo.fx.countries.tools.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;

public class SignUpController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label welcomeText;
    @FXML
    private Button SignUpButton;
    @FXML
    private TextField Name;
    @FXML
    private DatePicker DateOfBirth;
    @FXML
    private TextField SignUpEmail;
    @FXML
    private PasswordField SignUpPassword;
    @FXML
    private PasswordField RptSignUpPassword;
    private Image profilePic;
    private File selectedFile;
    @FXML
    private Button AddProfilePic;
    @FXML
    private Hyperlink LogIn;
    @FXML
    private Hyperlink Help;
    @FXML
    private Label fileNamePic;
    public Connection con;
    private int rowCount;
    @FXML
    protected void OnAddProfilePicButtonClick(ActionEvent event) throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            profilePic = new Image(selectedFile.toURI().toString());
            fileNamePic.setText(selectedFile.toURI().toString());
        }
    }
    @FXML
    protected void onSignUpButtonClick(ActionEvent event) throws Exception {
        String UserName = Name.getText();
        LocalDate selectedDate = DateOfBirth.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String UserDOB = selectedDate.format(formatter);
        String recipientEmail=SignUpEmail.getText();
        String UserPassWord = SignUpPassword.getText();
        String ConfUserPass = RptSignUpPassword.getText();
        FileInputStream inputStream = new FileInputStream(selectedFile);
        if( ConfUserPass.equals(UserPassWord)) {
            java.sql.Connection con = null;
            try {
                Class.forName("oracle.jdbc.OracleDriver");
            } catch (ClassNotFoundException e) {
                System.out.println("Error loading the driver" + e);
            }
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "user1156");
                String sqlQuery = "SELECT COUNT(*) FROM BETTERUSER";
                PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    rowCount = resultSet.getInt(1);
                }
                String sql = "insert into BETTERUSER(Id,Name,Email,DateOfBirth,Profile_Pic,Password,Total_Streaks,Total_Daily,Total_DeadLifts,Points) values(?,?,?,to_date(?,'mm/dd/yyyy'),?,?,?,?,?,?)";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setInt(1, (rowCount+1));
                statement.setString(2, UserName);
                statement.setString(3, recipientEmail);
                statement.setString(4, UserDOB);
                statement.setBinaryStream(5, inputStream, (int) selectedFile.length());
                statement.setString(6, UserPassWord);
                statement.setInt(7, 0);
                statement.setInt(8, 0);
                statement.setInt(9, 0);
                statement.setInt(10, 0);
                statement.executeUpdate();
                System.out.println("Inserted");
                con.close();
                welcomeText.setText("Your account has been Created!, login from the login page.");
                Name.clear();
                DateOfBirth.setValue(null);
                SignUpEmail.clear();
                SignUpPassword.clear();
                RptSignUpPassword.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };
    @FXML
    protected void onLogInLinkClick(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root,752,641);
        stage.setTitle("BetterSelf -> SIgnUp");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onHelpLinkClick(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("Help-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root,752,641);
        stage.setTitle("BetterSelf -> Help");
        stage.setScene(scene);
        stage.show();
    }
}