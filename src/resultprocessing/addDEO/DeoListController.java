/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.addDEO;

import databaseHandler.DataBaseHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.stage.Stage;
import resultprocessing.studentlist.StudentListController;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class DeoListController implements Initializable {

    @FXML
    private TableView<DEO> tableView;
    @FXML
    private TableColumn<DEO, String> nameCol;
    @FXML
    private TableColumn<DEO,String> contactCol;
    @FXML
    private TableColumn<DEO, Date> dateCol;

    DataBaseHandler dataBaseHandler;
    ObservableList<DEO>list = FXCollections.observableArrayList();
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataBaseHandler = dataBaseHandler.getInstance();
        inotCol();
        loadDeo();
    }    
    
    public void inotCol(){
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
    
    public void loadDeo(){
        list.clear();
        String query = "SELECT * FROM DEO";
        System.err.println(query);
        ResultSet rs = dataBaseHandler.execQuery(query);
        try{
            
            while(rs.next()){
                String n = rs.getString("name");
                String con = rs.getString("contact");
                Date d = rs.getDate("joiningDate");
                list.add(new DEO(n,con,d));
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        tableView.getItems().addAll(list);
    }

    @FXML
    private void editDEO(ActionEvent event) {
        DEO deo = tableView.getSelectionModel().getSelectedItem();
        try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultprocessing/addDEO/addDEO.fxml"));
         Parent parent = loader.load();
         
         AddDEOController controller = loader.getController();
         controller.getDataForEdit(deo);
          Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnCloseRequest((e)->{
                refresh(); 
            });
         
            
        } catch (IOException ex) {
            Logger.getLogger(DeoListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void refresh() {
        loadDeo();
    }
    
    public static class DEO{
        private SimpleStringProperty name;
        private SimpleStringProperty contact;
        private ObjectProperty<Date> date;
        
        public DEO(){
        }
        public DEO(String n,String c,Date d){
            this.name = new SimpleStringProperty(n);
            this.contact = new SimpleStringProperty(c);
            this.date = new SimpleObjectProperty<>(d);
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

        public Date getDate() {
            return date.get();
        }

        public void setDate(Date date) {
            this.date = new SimpleObjectProperty<>(date);
        }
        
        
        
        
        
    }
    
}
