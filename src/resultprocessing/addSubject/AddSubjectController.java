/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.addSubject;

import alertmessage.AlertMaker;
import com.jfoenix.controls.JFXTextField;
import databaseHandler.DataBaseHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resultprocessing.login.AdminMainController;
import resultprocessing.main.MainController;
import resultprocessing.subjectList.SubjectListController;
import resultprocessing.subjectList.SubjectListController.Subject;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class AddSubjectController implements Initializable {

    @FXML
    private JFXTextField coursecode;
    @FXML
    private JFXTextField coursetitle;
    private JFXTextField coursecredits;

        
    DataBaseHandler dataBaseHandler;
    private boolean isEditable = Boolean.FALSE;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private JFXTextField semester;
    @FXML
    private JFXTextField course_credits;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataBaseHandler = dataBaseHandler.getInstance();
        
    }    

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage)rootAnchorPane.getScene().getWindow();
        stage.close();
    }

   

    @FXML
    private void saveCourse(ActionEvent event) {
         String course_code = coursecode.getText();
        String course_title = coursetitle.getText();
        String crt = course_credits.getText();
        double credit = Double.valueOf(crt);
        String semestr = semester.getText();
        
        
        //MainController.getRunningSemester(semestr);
        
        if(course_code.isEmpty()|| course_title.isEmpty()||crt.isEmpty()||semestr.isEmpty()){
            AlertMaker.showError("Please enter all fields");
            return;
        }
        
        if(isEditable){
            isEditable();
            return;
        }
        
/*                        +"coursecode varchar(100) primary key,\n"
                        +"coursetitle varchar(100),\n"
                        +"coursecredits DOUBLE PRECISION,\n"  
                        +"semester varchar(20),\n"
                        +"stu_id varchar(100),\n"
                        +"FOREIGN KEY (stu_id) REFERENCES STUDENT(id)"
                        +")");*/
          String query = "INSERT INTO SUBJECT(coursecode,coursetitle,coursecredits,semester) VALUES("
                    +"'"+course_code+"',"
                    +"'"+course_title+"',"
                    +""+credit+","
                    +"'"+semestr+"'"
                    +")";
        System.out.println(query);
        
        if(dataBaseHandler.execAction(query)){
            AlertMaker.showConfirmation("success");
        }else{
            AlertMaker.showError("fail");
        }
    }

    public void getDataForEdit(SubjectListController.Subject subject) {
        coursecode.setText(subject.getCode());
        coursetitle.setText(subject.getTitle());
        double st = subject.getCredit();
        String s = String.valueOf(st);
        course_credits.setText(s);
        semester.setText(subject.getSemtr());
        
        coursecode.setEditable(false);
        isEditable = Boolean.TRUE;
    }

    private void isEditable() {
        String crt = course_credits.getText();
        double credit = Double.valueOf(crt);
        Subject subject = new Subject(coursecode.getText(),coursetitle.getText(),credit,semester.getText());
         if(dataBaseHandler.updateSubject(subject)){
            AlertMaker.showConfirmation("Data updated successfully!!!");
        }else{
            AlertMaker.showError("Data not updated!!");
        }
    }
    
}
