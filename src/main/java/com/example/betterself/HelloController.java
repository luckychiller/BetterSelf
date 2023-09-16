package com.example.betterself;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.sql.*;

public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label welcomeText;
    @FXML
    private Button LoginButton;
    @FXML
    private TextField LoginEmail;
    @FXML
    private PasswordField LoginPassword;
    @FXML
    private Hyperlink SignUp;
    @FXML
    private Hyperlink Help;


    @FXML
    protected void onLogInButtonClick(ActionEvent event) throws IOException {
        String email = LoginEmail.getText();
        String password = LoginPassword.getText();

        java.sql.Connection con= null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading the driver" + e);
        }
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "user1156");
            String sql="select * from BETTERUSER where email = ? and password = ?";
            PreparedStatement statement=con.prepareStatement(sql);
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                con.close();
                IndexController.OurPerson=new UserData(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getDate("DateOfBirth"),
                        rs.getBytes("Profile_Pic"),
                        rs.getString("Password"),
                        rs.getInt("Total_Streaks"),
                        rs.getInt("Total_Daily"),
                        rs.getInt("Total_Deadlifts"));
                root = FXMLLoader.load(getClass().getResource("index.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root, 752, 641);
                stage.setTitle("BetterSelf");
                stage.setScene(scene);
                stage.show();
                System.out.println("Access Granted");
            } else {
                welcomeText.setText("Access Denied");
                LoginEmail.clear();
                LoginPassword.clear();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onSignUpLinkClick (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("SignUp-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 752, 641);
        stage.setTitle("BetterSelf -> SIgnUp");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onHelpLinkClick (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Help-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 752, 641);
        stage.setTitle("BetterSelf -> Help");
        stage.setScene(scene);
        stage.show();
    }
}
