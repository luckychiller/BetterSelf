package com.example.betterself;

import eu.hansolo.fx.countries.tools.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.*;
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
    @FXML
    private Hyperlink LogIn;
    @FXML
    private Hyperlink Help;
    public Connection con;
    @FXML
    protected void onSignUpButtonClick(ActionEvent event) throws Exception {
        Random random = new Random();
        int verificationCode = 100000 + random.nextInt(900000);
        final String senderEmail = "wasswalutufi@iut-dhaka.edu";
        final String senderPassword = "424Poseidon.";
        String UserName = Name.getText();
        String UserDOB = DateOfBirth.getPromptText();
        String recipientEmail=SignUpEmail.getText();
        String UserPassWord = SignUpPassword.getText();
        String ConfUserPass = RptSignUpPassword.getText();

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Verification Code");
        message.setText("Your verification code is: " + verificationCode);

        Transport.send(message);
        System.out.println("Verification code has been sent to your email.");

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Verification Code");
        dialog.setHeaderText("Verification code has been sent to your email. Please enter the verification code: ");
        dialog.setContentText("Verification Code:");

        dialog.showAndWait().ifPresent(InputCode -> {
            if (InputCode.equals(Integer.toString(verificationCode))) {
                java.sql.Connection con= null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    System.out.println("Error loading the driver" + e);
                }
                try {
                    con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "user1156");
                    String sql="insert into BETTERUSER(NAME,DateOfBirth,EMAIL,PASSWORD) values(?,to_date(?,'mm/dd/yyyy'),?,?)";
                    PreparedStatement statement=con.prepareStatement(sql);
                    statement.setString(1,UserName);
                    statement.setString(3,UserDOB);
                    statement.setString(5,recipientEmail);
                    statement.setString(6,UserPassWord);
                    statement.executeUpdate();
                    System.out.println("Inserted");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                showSuccessAlert("Verification Successful", "Your account has been verified!");
            } else {
                showErrorAlert("Verification Failed", "Invalid verification code. Please try again.");
            }
        });
    }
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
    private void showSuccessAlert(String title, String content) {
        showAlert(AlertType.INFORMATION, title, content);
    }
    private void showErrorAlert(String title, String content) {
        showAlert(AlertType.ERROR, title, content);
    }
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
