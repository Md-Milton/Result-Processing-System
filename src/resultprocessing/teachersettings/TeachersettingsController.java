/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.teachersettings;

import alertmessage.AlertMaker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import databaseHandler.DataBaseHandler;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import resultprocessing.addTeacher.ViewTeacherController;
import resultprocessing.addTeacher.ViewTeacherController.Teacher;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class TeachersettingsController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    public static String name;
    public static String pass;
    public static void getName(String uname,String p) {
        name = uname;
        pass = p;
    }
    DataBaseHandler dataBaseHandler;
    @FXML
    private StackPane stackpane;
    @FXML
    private Label lblname;
    @FXML
    private AnchorPane rootpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //getDataForEdit();
        dataBaseHandler = dataBaseHandler.getInstance();
        getName();
    }    

    @FXML
    private void save(ActionEvent event) {
       getDataForEdit();
        
    }

   public void getName(){
       username.setText(name);
       password.setText(pass);
       
   }
     public void getDataForEdit() {
         username.setEditable(false);
         ViewTeacherController.Teacher teacher = new ViewTeacherController.Teacher(username.getText(),password.getText());
         if(dataBaseHandler.teachersettingsupdate(teacher,name)){
             AlertMaker.showAlert(stackpane, "Success", "updated");
         }else{
             AlertMaker.showAlert(stackpane, "Error", "not updated");
         }
    }
    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage)rootpane.getScene().getWindow();
        stage.close();
        
    }
    
}
