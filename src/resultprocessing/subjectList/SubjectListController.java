/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.subjectList;

import alertmessage.AlertMaker;
import com.jfoenix.controls.JFXTextField;
import databaseHandler.DataBaseHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import resultprocessing.addStudent.AddStudentController;
import resultprocessing.addSubject.AddSubjectController;
import resultprocessing.deomain.DeoMainController;
import resultprocessing.login.AdminMainController;
import resultprocessing.login.AdminMainController.Result;
import resultprocessing.result.ResultController;
import resultprocessing.studentlist.StudentListController;
import resultprocessing.utility.ResultProcessingUtil;


/**
 * FXML Controller class
 *
 * @author milton
 */
public class SubjectListController implements Initializable {

    @FXML
    private TableColumn<Subject, String> Ccode;
    @FXML
    private TableColumn<Subject, String> cTitle;
    @FXML
    private TableColumn<Subject, Double> Ccredits;
    @FXML
    private TableView<Subject> tableview;

    ObservableList<Subject>list = FXCollections.observableArrayList();
    @FXML
    private JFXTextField semester;
    
    DataBaseHandler dataBaseHandler;
    @FXML
    private StackPane stackPane;
    
    AdminMainController admaAdminMainController;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableColumn<Subject, String> semesterCol;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataBaseHandler = dataBaseHandler.getInstance();
        initCol();
        SubjectData();
    }    

    
     private void initCol() {
        Ccode.setCellValueFactory(new PropertyValueFactory<>("code"));
        cTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        Ccredits.setCellValueFactory(new PropertyValueFactory<>("credit"));
        semesterCol.setCellValueFactory(new PropertyValueFactory<>("semtr"));
        
        
    }

    
    @FXML
    private void editSubject(ActionEvent event) {
        Subject subject = tableview.getSelectionModel().getSelectedItem();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultprocessing/addSubject/addSubject.fxml"));
            Parent parent = loader.load();
            
            AddSubjectController controller = loader.getController();
            controller.getDataForEdit(subject);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnCloseRequest((e)->{
                refreshData(); 
            });
            
        }catch(IOException ex){
            Logger.getLogger(DeoMainController.class.getName()).log(Level.SEVERE, null,ex);
        }
    }

    @FXML
    private void deleteSubject(ActionEvent event) {
Subject subject = tableview.getSelectionModel().getSelectedItem();
        
        if(subject ==null){
            AlertMaker.showInformation("No course is selected !please select a student to delete");
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Subject Deletion");
        alert.setContentText("Are you sure to delete the course with course code : "+subject.getCode());
        
        Optional<ButtonType>response = alert.showAndWait();
        if(response.get()==ButtonType.OK){
            boolean delete = DataBaseHandler.getInstance().deleteSubject(subject);
            if(delete){
                AlertMaker.showConfirmation("subject with course code: "+subject.getCode()+" is deleted");
                list.remove(subject);
                refreshData();
                
            }else{
                AlertMaker.showError("Subject with course code: "+subject.getCode()+" not deleted");
            }
        }else{
            AlertMaker.showError("Student deletion is cancelled!!!");
        }
        
    }

    private void SubjectData() {
          list.clear();
         DataBaseHandler data = DataBaseHandler.getInstance();
            String query = "SELECT * FROM SUBJECT";
            ResultSet rs = data.execQuery(query);
       try{
           while(rs.next()){
               String code = rs.getString("coursecode");
               String title = rs.getString("coursetitle");
               double  crt = rs.getDouble("coursecredits");
               String s = rs.getString("semester");
               list.add(new Subject(code, title,crt,s));
           }
           
       }catch(SQLException ex){
           Logger.getLogger(SubjectListController.class.getName()).log(Level.SEVERE,null,ex);
       }
       tableview.getItems().addAll(list);
       
    }

    ObservableList<Subject>resultData;
    @FXML
    private void searchCourse(ActionEvent event) {
        String sem = semester.getText();
        if(sem.isEmpty()){
            tableview.setVisible(false);
            AlertMaker.showError("semester is empty ");
        }else{
            initCol();
            resultData = loadSubjectData(sem);
            populateSubject(resultData);
            tableview.setVisible(true);
            tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
        }
     
         
   }
    
    /*
    +"course_code varchar(100) primary key,\n"
                        +"course_title varchar(100),\n"
                        +"course_credits DOUBLE PRECISION,\n"  
                        +"term varchar(20),\n"
                        +"stu_id varchar(100),\n"
                        +"FOREIGN KEY (stu_id) REFERENCES STUDENT(id)"
    */
ObservableList<Subject>list1 = FXCollections.observableArrayList();
public ObservableList<Subject> loadSubjectData(String sem) {
       String myquery = "SELECT SUBJECT.coursecode,SUBJECT.coursetitle,SUBJECT.coursecredits,SUBJECT.semester FROM SUBJECT WHERE SUBJECT.semester='"+sem+"'";
       try{
        ResultSet result = dataBaseHandler.execQuery(myquery);
        ObservableList<Subject>resultList = getSubjecttList(result);
        return resultList;
 
       }catch(Exception e){
           System.out.println("While searching subject");
           throw e;
       } 
    }
    
    private ObservableList<Subject> getSubjecttList(ResultSet rs) {
                
        try{
            while(rs.next()){
                Subject subject = new Subject();
                subject.setCode(rs.getString("coursecode"));
                subject.setTitle(rs.getString("coursetitle"));
                subject.setCredit(rs.getDouble("coursecredits"));
                subject.setSemtr(rs.getString("semester"));
                list1.add(subject);
            }
    }catch (SQLException ex) {
            Logger.getLogger(AdminMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list1;
    }
    

    private void populateSubject(ObservableList<Subject> resultData) {
        if(resultData==null){
            AlertMaker.showAlert(stackPane, "Data Search Operation", "No Data Exists");
        }
        tableview.setItems(resultData);
    }

   

    @FXML
    private void exportasPDF(ActionEvent event) {
        String sem = semester.getText();
         List<List> printData = new ArrayList<>();
         String[] headers = {"Course Code", "Course Title", "Credits"};
         printData.add(Arrays.asList(headers));
         for (Subject info : list1) {
            List<String> row = new ArrayList<>();
            row.add(info.getCode());
            row.add(info.getTitle());
            row.add(String.valueOf(info.getCredit()));
           
            printData.add(row);
        }
        ResultProcessingUtil.initPDFExprot(stackPane, rootPane, getStage(), printData,sem);
    }
   
     private Stage getStage() {
        return (Stage) tableview.getScene().getWindow();
    }

    @FXML
    private void closeStage(ActionEvent event) {
        Stage stage =(Stage)rootPane.getScene().getWindow();
        stage.close();
    }

    private void refreshData() {
            SubjectData();
    }
    
    public static class Subject{
            private SimpleStringProperty code;
            private SimpleStringProperty title;
            private SimpleDoubleProperty credit;
            private SimpleStringProperty semtr;
            
            public Subject(){}

        public Subject(String code, String title, double credit,String s) {
            this.code = new SimpleStringProperty(code);
            this.title = new SimpleStringProperty(title);
            this.credit = new SimpleDoubleProperty(credit);
            this.semtr = new SimpleStringProperty(s);
        }

        public String getSemtr() {
            return semtr.get();
        }

        public void setSemtr(String semtr) {
            this.semtr = new SimpleStringProperty(semtr);
        }

            
        
        public String getCode() {
            return code.get();
        }

        public void setCode(String code) {
            this.code = new SimpleStringProperty(code);
        }

        public String getTitle() {
            return title.get();
        }

        public void setTitle(String title) {
            this.title = new SimpleStringProperty(title);
        }

        public double getCredit() {
            return credit.get();
        }

        public void setCredit(double credit) {
            this.credit = new SimpleDoubleProperty(credit);
        }
            
        
            

    }
    
}
