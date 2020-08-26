/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.studentlist;

import alertmessage.AlertMaker;
import databaseHandler.DataBaseHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.stage.Stage;
import resultprocessing.addStudent.AddStudentController;
import resultprocessing.deomain.DeoMainController;
import resultprocessing.main.MainController;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class StudentListController implements Initializable {
   
    ObservableList<Student>list = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Student> tableView;
    
    @FXML
    private TableColumn<Student, String> idCol;
    @FXML
    private TableColumn<Student, String> nameCol;
    @FXML
    private TableColumn<Student, String> sessionCol;
    @FXML
    private TableColumn<Student, String> contactCol;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadStudentData();
    }    

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("stuId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("stuName"));
        sessionCol.setCellValueFactory(new PropertyValueFactory<>("session"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        
    }

    // show data in table view
    private void loadStudentData() {
        list.clear();
        DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();
        String query = "SELECT * FROM STUDENT";
       ResultSet rs = dataBaseHandler.execQuery(query);
       try{
           while(rs.next()){
               String Id = rs.getString("id");
               String name = rs.getString("name");
               String session = rs.getString("session");
               String contact = rs.getString("contact");
               list.add(new Student(Id, name, session, contact));
           }
           
       }catch(SQLException ex){
           Logger.getLogger(StudentListController.class.getName()).log(Level.SEVERE,null,ex);
       }
       tableView.getItems().setAll(list);
       
    }

    
    //update student data 
    @FXML
    private void editStudent(ActionEvent event) {
        Student student = tableView.getSelectionModel().getSelectedItem();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultprocessing/addStudent/addStudent.fxml"));
            Parent parent = loader.load();
            
            AddStudentController controller = loader.getController();
            controller.getDataForEdit(student);
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
    private void refreshData() {
        loadStudentData();
    }

    @FXML
    private void deleteStudent(ActionEvent event) {
        Student student = tableView.getSelectionModel().getSelectedItem();
        
        if(student ==null){
            AlertMaker.showInformation("No student is selected !please select a student to delete");
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Student Deletion");
        alert.setContentText("Are you sure to delete the student with id : "+student.getStuId());
        
        Optional<ButtonType>response = alert.showAndWait();
        if(response.get()==ButtonType.OK){
            boolean delete = DataBaseHandler.getInstance().deleteStudent(student);
            if(delete){
                AlertMaker.showConfirmation("student with id: "+student.getStuId()+" is deleted");
                list.remove(student);
                refreshData();
                
            }else{
                AlertMaker.showError("Student with id: "+student.getStuId()+" not deleted");
            }
        }else{
            AlertMaker.showError("Student deletion is cancelled!!!");
        }
        
    }
    
    
    
    
    public static class Student{
        private SimpleStringProperty stuId;
        private SimpleStringProperty stuName;
        private SimpleStringProperty session;
        private SimpleStringProperty contact;

        
        
        public Student(String stuId, String stuName, String session, String contact) {
            this.stuId = new SimpleStringProperty(stuId);
            this.stuName = new SimpleStringProperty(stuName);
            this.session = new SimpleStringProperty(session);
            this.contact = new SimpleStringProperty(contact);
        }

        public String getStuId() {
            return stuId.get();
        }

        public String getStuName() {
            return stuName.get();
        }

        public String getSession() {
            return session.get();
        }

        public String getContact() {
            return contact.get();
        }
         
    }
}
