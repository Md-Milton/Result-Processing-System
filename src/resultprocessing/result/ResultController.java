/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.result;

import alertmessage.AlertMaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import databaseHandler.DataBaseHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import resultprocessing.addTeacher.AddTeacherController;
import resultprocessing.teachersettings.TeachersettingsController;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class ResultController implements Initializable {
    
    public static String uname="";
    public static String pass;
    public static void getName(String na,String p) {
        uname = na;
        pass = p;
    }

    @FXML
    private JFXTextField stu_id;
    @FXML
    private JFXTextField course_code;
    @FXML
    private JFXTextField course_title;
    @FXML
    private JFXTextField credits;
    @FXML
    private JFXTextField semester;
    @FXML
    private JFXTextField mark;
    @FXML
    private JFXTextField grade_point;
    @FXML
    private JFXTextField grade;

    
    DataBaseHandler databaseHandler ;
    @FXML
    private JFXButton calculate;
    @FXML
    private AnchorPane rootanchorPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private Label name;
    
    private static String n;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      databaseHandler = databaseHandler.getInstance();
      //name.setText(uname);
      
    }    

    @FXML
    private void calculate(ActionEvent event) {
         String m = mark.getText();
         double mrk=0;
         boolean numeric = true;
         if(m.isEmpty()){
             AlertMaker.showAlert(stackPane, "Error", "Please enter marks obtain");
         }
         
         try{
               mrk = Double.parseDouble(m);
         }catch(NumberFormatException e){
             numeric = false;
         }
         if(numeric)
           calculateCgpa(mrk);
         else
          AlertMaker.showAlert(stackPane, "Error", "Invalid mark!!\n Please enter a valid mark!!");

    }

    public void calculateCgpa(double mark){
       if(mark>=80){
            grade.setText("A+");
            calculateGrade("A+");
        }
        else if(mark>=75 && mark<=79){
            grade.setText("A");
            calculateGrade("A");
        }
        else if(mark>=70 && mark<=74){
            grade.setText("A-");
            calculateGrade("A-");
        }
        else if(mark>=65 && mark<=69){
            grade.setText("B+");
            calculateGrade("B+");
        }
        else if(mark>=60 && mark<=64){
            grade.setText("B");
            calculateGrade("B");
        }
        else if(mark>=55 && mark<=59){
            grade.setText("B-");
            calculateGrade("B-");
        }
        else if(mark>=50 && mark<=54){
            grade.setText("C+");
         calculateGrade("C+");   
        }
        else if(mark>=45 && mark<=49){
            grade.setText("C");
            calculateGrade("C");
        }
        else if(mark>=40 && mark<=44){
            grade.setText("D");
            calculateGrade("D");
        }
        else{
            grade.setText("F");
            calculateGrade("F");
        }

    }

    private void calculateGrade(String gradepoint) {
        switch(gradepoint){
            case "A+":
                grade_point.setText("4.0");
                break;
            case "A":
                grade_point.setText("3.75");
                break;
            case "A-":
                grade_point.setText("3.50");
                break;
            case "B+":
                grade_point.setText("3.25");
                break;
            case "B":
                grade_point.setText("3.00");
                break;
            case "B-":
                grade_point.setText("2.75");
                break;
            case "C+":
                grade_point.setText("2.50");
                break;
            case "C":
                grade_point.setText("2.25");
                break;
            case "D":
                grade_point.setText("2.00");
                break;
            default:
                grade_point.setText("0.00");
                break;
        }
    }
    @FXML
    private void save(ActionEvent event) {
        
        String stuid = stu_id.getText();
        String coursecode = course_code.getText();
        String coursetitle = course_title.getText();
        String credit = credits.getText();
        String semestr = semester.getText();
        String gradepoint = grade_point.getText();
        String gpa = grade.getText();
        
        if(stuid.isEmpty() || coursecode.isEmpty()|| coursetitle.isEmpty()||credit.isEmpty()||semestr.isEmpty()||gradepoint.isEmpty()
                ||gpa.isEmpty()){
            
            AlertMaker.showAlert(stackPane, "FORM INPUT ERROR", "PLEASE ENTER ALL FIELDS!!\n AND THEN TRY AGAIN");
            return;
        }
        grade_point.setEditable(false);
        grade.setEditable(false);
        
        double coursecredit = Double.parseDouble(credit);
        double gradepoints = Double.parseDouble(gradepoint);
        
        /*
                        +"studentId varchar(50) primary key,\n"
                        +"courseCode varchar(20),primary key\n"
                        +"courseTitle varchar(100),\n"
                        +"courseCredits DOUBLE PRECISION,\n"
                        +"semester varchar(50),\n"
                        +"gradePoint DOUBLE PRECISION,\n"
                        +"grade varchar(10),\n"
                        +"FOREIGN KEY (studentId) REFERENCES STUDENT(id),\n"
                        +"FOREIGN KEY (courseCode) REFERENCES SUBJECT(coursecode)"
                        +")");
        */
                String query = "INSERT INTO RESULT(studentId,courseCode,courseTitle,courseCredits,semester,gradePoint,grade) VALUES("
                        +"'"+stuid+"',"
                        +"'"+coursecode+"',"
                        +"'"+coursetitle+"',"
                        +""+coursecredit+","
                        +"'"+semestr+"',"
                        +""+gradepoints+","
                        +"'"+gpa+"'"
                        +")";
                
                if(databaseHandler.execAction(query)){
                    AlertMaker.showSuccessAlert(stackPane, "Data Insertion", "Data Inserted Successfully!!");
                }else{
                    AlertMaker.showAlert(stackPane, "Data Insertion Error", "Data not inserted!\nPlease try again");
                }
        
    }
    
    
    
    @FXML
    private void cancel(ActionEvent event) {
    Stage stage =(Stage)rootanchorPane.getScene().getWindow();
    stage.close();
    }

    @FXML
    private void close(ActionEvent event) {
    Stage stage =(Stage)rootanchorPane.getScene().getWindow();
    stage.close();
    }

  
    
    
    @FXML
    private void changepassword(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultprocessing/teachersettings/teachersettings.fxml"));
//        Parent parent = loader.load();
//        
        
        Parent parent = FXMLLoader.load(getClass().getResource("/resultprocessing/teachersettings/teachersettings.fxml"));
        TeachersettingsController.getName(uname,pass);
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show(); 
        
    }

    @FXML
    private void home(ActionEvent event) throws IOException {
          Parent parent = FXMLLoader.load(getClass().getResource("/resultprocessing/main/main.fxml"));
         Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show(); 
        close(event);
    }

    
    
}
