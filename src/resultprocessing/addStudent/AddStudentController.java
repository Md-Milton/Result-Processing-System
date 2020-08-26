/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.addStudent;

import alertmessage.AlertMaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import databaseHandler.DataBaseHandler;
import resultprocessing.studentlist.StudentListController;
import resultprocessing.studentlist.StudentListController.Student;


/**
 * FXML Controller class
 *
 * @author milton
 */
public class AddStudentController implements Initializable {
    
    
    

    @FXML
    private JFXTextField SId;
    @FXML
    private JFXTextField Sname;
    @FXML
    private JFXTextField Ssession;
    @FXML
    private JFXTextField Scontact;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton cancel;
    @FXML
    private AnchorPane rootancherPane;

    
    DataBaseHandler dataBaseHandler;
    private boolean isEditable = Boolean.FALSE;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataBaseHandler = dataBaseHandler.getInstance();
        
    }    

    

    
    //cancel operattion
    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage)rootancherPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addStudent(ActionEvent event) {
        
        String stuId = SId.getText();
        String stuName = Sname.getText();
        String session = Ssession.getText();
        String contact = Scontact.getText();
        
        if(stuId.isEmpty()|| stuName.isEmpty()|| session.isEmpty()||contact.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }
        if(isEditable){
            isEditable();
            return;
        }
        //else{
            /* + " studentid varchar(100) primary key,\n"
                        + " name varchar(200),\n"
                        + " session varchar(200),\n"
                        + " contact varchar(200)"
                        + ")");
            */
            String query = "INSERT INTO STUDENT VALUES ("
                    +"'"+stuId+"',"
                    +"'"+stuName+"',"
                    +"'"+session+"',"
                    +"'"+contact+"'"
                    +")";
            System.out.println(query);
            if(dataBaseHandler.execAction(query)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setHeaderText(null);
               alert.setContentText("Success!");
               alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Failed!");
                alert.showAndWait();
            }
        //}
        
    }

    public void getDataForEdit(StudentListController.Student student) {
    
        SId.setText(student.getStuId());
        Sname.setText(student.getStuName());
        Ssession.setText(student.getSession());
        Scontact.setText(student.getContact());
        SId.setEditable(false);
        isEditable = Boolean.TRUE;
    }
    
    public void isEditable(){
        Student student = new Student(SId.getText(),Sname.getText(),Ssession.getText(),Scontact.getText());
        
        if(dataBaseHandler.updateStudent(student)){
            AlertMaker.showConfirmation("Data updated successfully!!!");
        }else{
            AlertMaker.showError("Data not updated!!");
        }
    }
    
}
