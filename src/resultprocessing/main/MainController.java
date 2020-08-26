/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.main;

import com.jfoenix.controls.JFXButton;
import databaseHandler.DataBaseHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import resultprocessing.deo.settings.DEOPreferences;
import resultprocessing.settings.AdminPreferences;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class MainController implements Initializable {

    
    @FXML
    private JFXButton adminLogin;
    @FXML
    private AnchorPane centerPane;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private  Label semester;
    @FXML
    private Label course;
    @FXML
    private Label credits;
    @FXML
    private VBox vbox;
    @FXML
    private VBox vbox2;
    @FXML
    private VBox vbox3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getRunningSemester();
    }    

    @FXML
    private void adminlogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resultprocessing/adminlogin/login.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataBaseHandler.getInstance();
            }
        });
        closeStage();
        AdminPreferences.initConfig();
    }

    @FXML
    private void OpenDEO(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resultprocessing/adminlogin/login.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataBaseHandler.getInstance();
            }
        });
        closeStage();
        DEOPreferences.initConfig();
    }

    private void closeStage() {
    Stage stage = (Stage)rootAnchorPane.getScene().getWindow();
    stage.close();
    }

    @FXML
    private void addMarks(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resultprocessing/adminlogin/login.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show(); 
        closeStage();
    }

    @FXML
    private void opendashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resultprocessing/dashboard/dashboard.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    public  void getRunningSemester()  {
        vbox.setStyle("-fx-padding:10;-fx-border-style:solid inside;-fx-border-width:2;-fx-border-insets:5;-fx-border-color:greenyellow;");
        
        
             String query = "SELECT MAX(SUBSTR(SUBJECT.semester,1,1))FROM SUBJECT";
             DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();
             ResultSet rs = dataBaseHandler.execQuery(query);
             
        try {
            while(rs.next()){
                int sem = rs.getInt(1);
                String st = String.valueOf(sem);
                termName(sem);
              
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void termName(int s){
        String str="abcd";
        switch (s) {
            case 1:
                semester.setText("First Semester");
                str = "1st";
                break;
            case 2:
                semester.setText("Second Semester");
                str = "2nd";
                break;       
            case 3:
                semester.setText("Third Semester");
                str = "3rd";
                break;
            default:
                break;
        }
        //credits.setText(str);
       getCourseCredtis(str);
       getTotalCourse(str);
    }
    
    public void getCourseCredtis(String sem){
        String query = "SELECT COUNT(SUBJECT.coursecredits)FROM SUBJECT WHERE SUBJECT.semester='"+sem+"'";
             DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();
             ResultSet rs = dataBaseHandler.execQuery(query);
        try {
            while(rs.next()){
                double semCredits = rs.getDouble(1);
                String st = String.valueOf(semCredits);
                course.setText(st);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void getTotalCourse(String sem){
          String query = "SELECT SUM(SUBJECT.coursecredits)FROM SUBJECT WHERE SUBJECT.semester='"+sem+"'";
             DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();
             ResultSet rs = dataBaseHandler.execQuery(query);
        try {
            while(rs.next()){
                double semCredits = rs.getDouble(1);
                String st = String.valueOf(semCredits);
                credits.setText(st);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
