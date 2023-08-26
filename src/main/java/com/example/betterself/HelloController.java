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

        if (email== "wasswalutufi@iut-dhaka.edu") {
            if(password=="01234") {
            welcomeText.setText("Access Granted");
        }
            else
                if (email== "mahajabin@iut-dhaka.edu") {
                    if (password == "56789") {
                        welcomeText.setText("Access Granted");
                    }
                }
                else {
                    welcomeText.setText("Access Granted");
                }
        }
    }
    @FXML
    protected void onSignUpLinkClick(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("SignUp-view.fxml"));
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