/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.deo.settings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class DeosettingsController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    DEOPreferences preferences;
    @FXML
    private AnchorPane rootanchorPane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getDefaultValues();
        preferences = DEOPreferences.getPreferences();
    }    
    public void getDefaultValues() {
        DEOPreferences preferences = DEOPreferences.getPreferences();
        username.setText(preferences.getUsername());
        password.setText(preferences.getPasss());
    }

    @FXML
    private void saveDEO(ActionEvent event) {
        String uname = username.getText();
        String pass = password.getText();
        
        DEOPreferences preferences = DEOPreferences.getPreferences();
        preferences.setUsername(uname);
        preferences.setPasss(pass);
        DEOPreferences.updateConfig(preferences);
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage)rootanchorPane.getScene().getWindow();
        stage.close();
    }

    
    
}
