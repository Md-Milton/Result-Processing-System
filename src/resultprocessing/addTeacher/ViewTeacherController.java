/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.addTeacher;

import alertmessage.AlertMaker;
import com.jfoenix.controls.JFXButton;
import databaseHandler.DataBaseHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import resultprocessing.studentlist.StudentListController;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class ViewTeacherController implements Initializable {

    @FXML
    private TableView<Teacher> tableView;
    @FXML
    private TableColumn<Teacher, String> idcol;
    @FXML
    private TableColumn<Teacher, String> nameCol;
    @FXML
    private TableColumn<Teacher, String> contactCol;
    @FXML
    private TableColumn<Teacher, String> rankCol;

    DataBaseHandler dataBaseHandler;
    ObservableList<Teacher>list = FXCollections.observableArrayList();
    @FXML
    private StackPane stackPane;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataBaseHandler = dataBaseHandler.getInstance();
        initCol();
        loadTEacherInfo();
                
    }    
        public void initCol(){
            idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
            rankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));
            
        }
    /*
                        +"teacherId varchar(20)primary key,\n"
                        +"name varchar(100),\n"
                        +"contact varchar(100),\n"
                        +"rank varchar(100),\n"
                        +"pass varchar(100)"
                        +")");
        */
        public void loadTEacherInfo(){
            list.clear();
            DataBaseHandler data = DataBaseHandler.getInstance();
            String query = "SELECT * FROM TEACHER";
            ResultSet rs = data.execQuery(query);
        try {
            while(rs.next()){
                String id = rs.getString("teacherId");
                String n = rs.getString("name");
                String c = rs.getString("contact");
                String r = rs.getString("rank");
                list.add(new Teacher(id,n,c,r));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewTeacherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.getItems().addAll(list);
        }
        
        
    @FXML
    private void editTeacher(ActionEvent event)throws IOException{
         Teacher teacher = tableView.getSelectionModel().getSelectedItem();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultprocessing/addTeacher/addTeacher.fxml"));
            Parent root = loader.load();
            
            AddTeacherController controller = loader.getController();
            controller.getDataForEdit(teacher);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest((e)->{
                refresh(); 
            });
            
        }catch(Exception e){
            Logger.getLogger(AddTeacherController.class.getName()).log(Level.SEVERE, null,e);
        }
    }

    @FXML
    private void deleteTeacher(ActionEvent event) {
        
        Teacher teacher = tableView.getSelectionModel().getSelectedItem();
        if(teacher == null){
            AlertMaker.showAlert(stackPane, "", "No teacher is selected to delete\n please select one");
        }
        JFXButton yesButton = new JFXButton("YES");
        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
            boolean delete = DataBaseHandler.getInstance().deleteTeacher(teacher);
            if(delete){
              JFXButton submitButton = new JFXButton("DONE");
              AlertMaker.showMaterial(stackPane, Arrays.asList(submitButton), "Teacher info has deleted!!!", null);
            }else{
                JFXButton submitButton = new JFXButton("Okay!I'll check");
              AlertMaker.showMaterial(stackPane, Arrays.asList(submitButton), "Teacher info has deleted!!!", null);
            }
        });
        
        
         JFXButton noButton = new JFXButton("NO");
         noButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev)->{
              JFXButton submitButton = new JFXButton("Okay!");
              AlertMaker.showMaterial(stackPane, Arrays.asList(submitButton), "Teacher info deletion cancelled!!!", null);
        });
        
        AlertMaker.showMaterial(stackPane, Arrays.asList(yesButton,noButton), "Confirm Deletion ", "Are you sure to delete teacher info??");
       
        
    }

    @FXML
    private void refresh() {
        
        loadTEacherInfo();
    }
   
    
    public static class Teacher{
        private SimpleStringProperty id;
        private SimpleStringProperty name;
        private SimpleStringProperty contact;
        private SimpleStringProperty rank;
        private SimpleStringProperty password;

        public Teacher(String name, String pass)
        {
             this.name = new SimpleStringProperty(name);
             this.password = new SimpleStringProperty(pass);
            
        }
        
        public Teacher(String id, String name, String contact, String rank) {
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.contact = new SimpleStringProperty(contact);
            this.rank = new SimpleStringProperty(rank);
        }
        
         public Teacher(String name, String contact, String rank) {
            this.name = new SimpleStringProperty(name);
            this.contact = new SimpleStringProperty(contact);
            this.rank = new SimpleStringProperty(rank);
        }

        public String getId() {
            return id.get();
        }

        public void setId(String id) {
            this.id = new SimpleStringProperty(id);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name = new SimpleStringProperty(name);
        }

        public String getContact() {
            return contact.get();
        }

        public void setContact(String contact) {
            this.contact = new SimpleStringProperty(contact);
        }

        public String getRank() {
            return rank.get();
        }

        public void setRank(String rank) {
            this.rank = new SimpleStringProperty(rank);
        }

        public String getPassword() {
            return password.get();
        }

        
        public void setPassword(String password) {
            this.password = new SimpleStringProperty(password);
        }
        
        
        
        
        
        
    }
}
