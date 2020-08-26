/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.addTeacher;

import alertmessage.AlertMaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import databaseHandler.DataBaseHandler;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import resultprocessing.addTeacher.ViewTeacherController.Teacher;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class AddTeacherController implements Initializable {

    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField contact;
    @FXML
    private JFXTextField rank;
    @FXML
    private AnchorPane rootPane;

    DataBaseHandler dataBaseHandler;
    @FXML
    private JFXTextField teacher_Id;
    @FXML
    private StackPane stackPane;
    
    boolean isEditable = Boolean.FALSE;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataBaseHandler  = dataBaseHandler.getInstance();
    }    

    @FXML
    private void save(ActionEvent event) {
        String id = teacher_Id.getText();
        String Tname = name.getText();
        String Tcontact = contact.getText();
        String Trank = rank.getText();
        String password = "";
        
        if(Tname.isEmpty()||Tcontact.isEmpty()||Trank.isEmpty()){
            AlertMaker.showAlert(stackPane, "Inpuet Error", "Please Enter All Fileds");
            return;
        }
        
        if(isEditable){
            isEditable();
            return;
        }
        
        /*
         statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        +"teacherId varchar(20)primary key,\n"
                        +"name varchar(100),\n"
                        +"contact varchar(100),\n"
                        +"rank varchar(100),\n"Assistant Professor
                        +"pass varchar(100)"
                        +")");
        */
        String query = "INSERT INTO TEACHER(teacherId,name,contact,rank,pass) VALUES("
                +"'"+id+"',"
                +"'"+Tname+"',"
                +"'"+Tcontact+"',"
                +"'"+Trank+"',"
                +"'"+password+"'"
                +")";


        System.err.println(query);
        if(dataBaseHandler.execAction(query)){
           AlertMaker.showConfirmation("success");
        }else{
            AlertMaker.showError("fail");
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage)rootPane.getScene().getWindow();
        stage.close();
    }
    
    public void getDataForEdit(ViewTeacherController.Teacher teacher) {
        teacher_Id.setText(teacher.getId());
        name.setText(teacher.getName());
        contact.setText(teacher.getContact());
        rank.setText(teacher.getRank());
        isEditable = Boolean.TRUE;
        teacher_Id.setEditable(false);
    }

    
    
    private void isEditable() {
        Teacher teacher = new Teacher(teacher_Id.getText(),name.getText(),contact.getText(),rank.getText());
        
        JFXButton yesButton = new JFXButton("YES");
        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev)->{
            boolean update = dataBaseHandler.editTeacher(teacher);
            if(update){
                JFXButton submit = new JFXButton("OKAY");
                AlertMaker.showMaterial(stackPane, Arrays.asList(submit),"Teacher Info Updation" , "Teacher information updated");
           }else{
                JFXButton submit = new JFXButton("Okay I'll check");
                AlertMaker.showMaterial(stackPane, Arrays.asList(submit),"Teacher Info Updation" , "Teacher information not updated");
            }
        });
        
        JFXButton noButton = new JFXButton("NO");
        noButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev)->{
            JFXButton submitButton = new JFXButton("Okay!");
              AlertMaker.showMaterial(stackPane, Arrays.asList(submitButton), "Teacher info updation cancelled!!!", null);
        });
        
        AlertMaker.showMaterial(stackPane, Arrays.asList(yesButton,noButton),"Cofirm updation" ,"Are you sure to update teacher info with name: "+teacher.getName());
  
    }

    
}
