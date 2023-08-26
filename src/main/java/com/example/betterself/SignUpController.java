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
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.*;

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
    private ComboBox<String> Country;
    @FXML
    private DatePicker DateOfBirth;
    @FXML
    private ComboBox<String> genderComboBox;
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
    @FXML
    protected void onSignUpButtonClick(ActionEvent event) throws IOException {
        Random random = new Random();
        int verificationCode = 100000 + random.nextInt(900000);
        final String senderEmail = "wasswalutufi@iut-dhaka.edu";
        final String senderPassword = "*******";
        String recipientEmail=SignUpEmail.getText();

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Verification Code");
            message.setText("Your verification code is: " + verificationCode);

            Transport.send(message);
            System.out.println("Verification code sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // ask user to confirm the email. and verify code.
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
}
