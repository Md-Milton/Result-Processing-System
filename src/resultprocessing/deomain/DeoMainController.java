/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.deomain;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resultprocessing.addStudent.AddStudentController;





/**
 * FXML Controller class
 *
 * @author milton
 */
public class DeoMainController implements Initializable {

   
    @FXML
    private MenuItem addStudent;
    
    
    @FXML
    private AnchorPane rootAnchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    

    @FXML
    private void addStudent(ActionEvent event) throws IOException {
        load("/resultprocessing/addStudent/addStudent.fxml");
    }

    @FXML
    private void viewStudent(ActionEvent event) throws IOException {
        load("/resultprocessing/studentlist/studentList.fxml");
    }
    
    @FXML
    private void home(ActionEvent event) throws IOException {
        load("/resultprocessing/main/main.fxml");
        closeStage();
    }

    @FXML
    private void close(ActionEvent event) {
        closeStage();
    }
    
    @FXML
    private void addSubject(ActionEvent event) throws IOException {
        load("/resultprocessing/addSubject/addSubject.fxml");
    }

    @FXML
    private void viewSubjectInfo(ActionEvent event) throws IOException {
        load("/resultprocessing/subjectList/subjectList.fxml");
    }

    @FXML
    private void changepassword(ActionEvent event) throws IOException {
        load("/resultprocessing/deo/settings/deosettings.fxml");
    }
    
    public void load(String location) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(location));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    private void closeStage() {
        Stage stage = (Stage)rootAnchorPane.getScene().getWindow();
        stage.close();
    }
}
