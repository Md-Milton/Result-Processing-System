/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.settings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
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


/**
 * FXML Controller class
 *
 * @author milton
 */
public class SettingsController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private AnchorPane rootAnchorPane;
    AdminPreferences preferences;
    
    boolean flag = false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            getadmindefaultValues();
            preferences = AdminPreferences.getPreferences();  
    }   
    
    
    public void getadmindefaultValues(){
        AdminPreferences preferences = AdminPreferences.getPreferences();
        username.setText(preferences.getUsename());
        password.setText(preferences.getPassword());
    }
    

   
    @FXML
    private void save(ActionEvent event) {
        String uname = username.getText();
        String pass = password.getText();
        
        AdminPreferences preferences = AdminPreferences.getPreferences();
        preferences.setUsename(uname);
        preferences.setPassword(pass);
        AdminPreferences.updateAdminConfig(preferences);
    }
    
public void load(){
         try {
                Parent root = FXMLLoader.load(getClass().getResource("/resultprocessing/login/adminmain.fxml"));
                Stage mainstage = new Stage();
                mainstage.setScene(new Scene(root));
                mainstage.setTitle("Library Assistant System");
                mainstage.show();
            } catch (IOException ex) {
                Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

@FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage)rootAnchorPane.getScene().getWindow();
        stage.close();
    }

    
}
