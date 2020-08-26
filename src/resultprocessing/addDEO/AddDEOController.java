/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.addDEO;

import alertmessage.AlertMaker;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import databaseHandler.DataBaseHandler;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resultprocessing.addDEO.DeoListController.DEO;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class AddDEOController implements Initializable {

    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField contact;
    @FXML
    private JFXDatePicker jDate;

    DataBaseHandler databaseHandler;
    private boolean isEditable = Boolean.FALSE;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = databaseHandler.getInstance();
    }    

    @FXML
    private void save(ActionEvent event) throws ParseException {
        String Dname =name.getText();
        String Dcontact = contact.getText();
        String joining_date = ((JFXTextField)jDate.getEditor()).getText();
        jDate.setEditable(false);
        jDate.setStyle("-fx-text-fill:white");
        
        if(Dname.isEmpty()||Dcontact.isEmpty()||joining_date.isEmpty()){
            AlertMaker.showError("please enter all fields");
            return;
        }
        /*
                        +"name varchar(100)primary key,\n"
                        +"contact varchar(100),\n"
                        +"joiningDate Date,\n"
                        +"pass varchar(100)"
                        +")");
        */
        
        if(isEditable){
            isEditible();
            return;
        }
        
         String query = "INSERT INTO DEO(name,contact,joiningDate) VALUES("
                +"'"+Dname+"',"
                +"'"+Dcontact+"',"
                +"'"+joining_date+"'"
                +")";
         
         System.err.println(query);
        if(databaseHandler.execAction(query)){
           AlertMaker.showConfirmation("success");
        }else{
            AlertMaker.showError("fail");
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage)rootAnchorPane.getScene().getWindow();
        stage.close();
    }

    void getDataForEdit(DeoListController.DEO deo) {
        name.setText(deo.getName());
        contact.setText(deo.getContact());
        String joining_date = deo.getDate().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(joining_date, formatter);
        jDate.setValue(localDate);
    }

    private void isEditible() throws ParseException {
        String joining_date = ((JFXTextField)jDate.getEditor()).getText();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(joining_date);
        
        DEO deo = new DEO(name.getText(),contact.getText(),date);
        
        if(databaseHandler.updateDEO(deo)){
            AlertMaker.showConfirmation("Data updated successfully!!!");
        }else{
            AlertMaker.showError("Data not updated!!");
        }
    }
    
}
