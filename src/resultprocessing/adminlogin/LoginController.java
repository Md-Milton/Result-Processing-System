/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.adminlogin;

import alertmessage.AlertMaker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import resultprocessing.deo.settings.DEOPreferences;
import resultprocessing.result.ResultController;

import resultprocessing.settings.AdminPreferences;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane rootanchorPane;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    AdminPreferences adminpreferences;
    DEOPreferences dEOPreferences;

    DataBaseHandler dataBaseHandler;
    public static String na;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adminpreferences = AdminPreferences.getPreferences();
        dEOPreferences = DEOPreferences.getPreferences();
        dataBaseHandler = dataBaseHandler.getInstance();
    }    

    @FXML
    private void login(ActionEvent event) throws SQLException {
        String uname = username.getText();
        
        na=uname;
        String pass = DigestUtils.shaHex(password.getText());
        
        String p = password.getText();
        
        String query = "SELECT * FROM TEACHER WHERE name=? AND pass=?";
        boolean rs =  dataBaseHandler.loginteacher(query, uname, p);
        
        if(uname.equals(adminpreferences.getUsename()) && pass.equals(adminpreferences.getPassword())){
            can();
            load("/resultprocessing/login/adminmain.fxml");
        }
        else if(uname.equals(dEOPreferences.getUsername()) && pass.equals(dEOPreferences.getPasss())){
            can();
            load("/resultprocessing/deomain/deoMain.fxml");
        }else if(rs){
            ResultController.getName(na,p);
            load("/resultprocessing/result/result.fxml");
            can();
        }else{
            AlertMaker.showError("Bad Credentials");
        }
    }

    public void load(String location){
         try {
                Parent root = FXMLLoader.load(getClass().getResource(location));
                Stage mainstage = new Stage();
                mainstage.setScene(new Scene(root));
                mainstage.setTitle("");
                mainstage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public void can(){
    Stage stage = (Stage)rootanchorPane.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void cancel(ActionEvent event) throws IOException {
        Stage stage = (Stage)rootanchorPane.getScene().getWindow();
        stage.close();
        Parent parent = FXMLLoader.load(getClass().getResource("/resultprocessing/main/main.fxml"));
         Stage stage1 = new Stage();
        stage1.setScene(new Scene(parent));
        stage1.show(); 
    }
    
}
