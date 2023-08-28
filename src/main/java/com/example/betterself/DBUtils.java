package com.example.betterself;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.sql.*;
import java.io.IOException;
import java.util.ResourceBundle;



public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String userName, String password, int highScore){
        Parent root = null;

        if(userName!= null && password!=null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                MainMenuController lgn = loader.getController();
                lgn.setUserInfo(LoginController.USERNAME, highScore);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        Scene scene=new Scene(root);
        Stage stage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        //stage.setMaximized(true);
        //stage.setTitle("Dune!");
        String chk="leaderboard.fxml";
        if(chk.equals(fxmlFile))
        {
            scene.getStylesheets().add(DBUtils.class.getResource("lead-board.css").toExternalForm());
            stage.setTitle("Leader Board");
            //  stage.setResizable(false);
        }
        stage.setScene(scene);
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String userName, String password, String name){
        ConnectionClass connectionClass = new ConnectionClass();
        Connection con = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet rs = null;


        try{
            con = connectionClass.getConnection();
            psCheckUserExist = con.prepareStatement("select * from USERINFO where userName = ?;");
            psCheckUserExist.setString(1, userName);
            rs = psCheckUserExist.executeQuery();

            if(rs.isBeforeFirst()){
                System.out.println("This Username is taken. Try some a different one.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username taken.");
                alert.show();
            }
            else{
                psInsert = con.prepareStatement("insert into USERINFO (userName, password, name, highscore) values (?, ?, ?, ?);");
                psInsert.setString(1, userName);
                psInsert.setString(2, password);
                psInsert.setString(3, name);
                psInsert.setInt(4, 0);
                psInsert.executeUpdate();

                changeScene(event, "mainMenu.fxml",  userName, null, 0);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try{
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(con != null){
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void logInUser(ActionEvent event, String userName, String password){
        ConnectionClass connectionClass = new ConnectionClass();
        Connection con = null;
        PreparedStatement preparedStatement= null;
        ResultSet rs = null;
        try{
            con = connectionClass.getConnection();
            preparedStatement = con.prepareStatement("select password, highscore from USERINFO where userName = ? ;");
            preparedStatement.setString(1, userName);
            rs = preparedStatement.executeQuery();

            if(!rs.isBeforeFirst()){
                System.out.println("Incorrect credentials!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User not found");
                alert.show();
            }
            else{
                while(rs.next()){
                    String retrievedPassword = rs.getString("password");
                    int retrievedScore = rs.getInt("highscore");
                    LoginController.HIGHSCORE=retrievedScore;
                    if(retrievedPassword.equals(password)){
                        changeScene(event, "mainMenu.fxml",userName, retrievedPassword, retrievedScore );
                    }
                    else{
                        System.out.println("Wrong password");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Incorrect password!");
                        alert.show();
                    }
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(con != null){
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }
}
