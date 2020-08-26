/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.addcourse;

import alertmessage.AlertMaker;
import com.jfoenix.controls.JFXTextField;
import databaseHandler.DataBaseHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class AddcourseController implements Initializable {

    @FXML
    private JFXTextField coursecode;
    @FXML
    private JFXTextField coursetitle;
    @FXML
    private JFXTextField coursecredits;

    
    DataBaseHandler  databaseHandler;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = databaseHandler.getInstance();
    }    

    @FXML
    private void saveSubject(ActionEvent event) {
        String course_code = coursecode.getText();
        String course_title = coursetitle.getText();
        String course_credits = coursecredits.getText();
        double credits = Double.parseDouble(course_credits);
        if(course_code.isEmpty()|| course_title.isEmpty()||course_credits.isEmpty()){
            AlertMaker.showError("Error !Enter all fields");
        }
        /*
                        +"coursecode varchar(100) primary key,\n"
                        +"coursetitle varchar(100),\n"
                        +"coursecredits double(4,2),\n"
                        +"stu_id varchar(100),\n"
                        +"FOREIGN KEY (stu_id) REFERENCES STUDENT(id)"
                        +")");
        */
        
        String query = "INSERT INTO SUBJECT VALUES("
                +"'"+course_code+"',"
                +"'"+course_title+"',"
                +"'"+credits+"'"
                +")";
        System.out.println(query);
        if(databaseHandler.execAction(query)){
            AlertMaker.showConfirmation("Success");
        }else{
            AlertMaker.showError("Fail");
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
    }
    
}
