/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.generate.report;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import databaseHandler.DataBaseHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import resultprocessing.subjectList.SubjectListController;
import resultprocessing.utility.ResultProcessingUtil;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class ReportController implements Initializable {

    @FXML
    private AnchorPane rootanchorPane;
    @FXML
    private JFXTextField stuSemester;
    @FXML
    private JFXButton generateReport;
    @FXML
    private TableView<Report> tableview;
    @FXML
    private TableColumn<Report,String> stuIdcol;
    @FXML
    private TableColumn<Report, String> stunamecol;
    @FXML
    private TableColumn<Report, String> stucgpacol;

    ObservableList<Report>list= FXCollections.observableArrayList();
    @FXML
    private Label termname;
    @FXML
    private StackPane stackpane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
    }    
    
    public void initCol(){
        stuIdcol.setCellValueFactory(new PropertyValueFactory<>("stuId"));
        stunamecol.setCellValueFactory(new PropertyValueFactory<>("stuName"));
        stucgpacol.setCellValueFactory(new PropertyValueFactory<>("stuCgpa"));
    }

    @FXML
    private void generateReport(ActionEvent event) {
        list.clear();
        String sem = stuSemester.getText();
        if(sem.isEmpty()){
            alertmessage.AlertMaker.showAlert(stackpane, "Empty Input", "Error");
            
            return;
        }
        loadSemester(sem);
        String query = "SELECT REPORT.studentId,REPORT.name,REPORT.cgpa FROM REPORT WHERE REPORT.semester='"+sem+"'";
        DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();
        ResultSet rs = dataBaseHandler.execQuery(query);
        
        try {
            while(rs.next()){
                String id = rs.getString("studentId");
                String name = rs.getString("name");
                double cg = rs.getDouble("cgpa");
                String t = String.format("%.2f", cg);
                double c = Double.parseDouble(t);
 
                list.add(new Report(id, name, c));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableview.getItems().addAll(list);
    }
    
    private void loadSemester(String s) {
          if(s.equals("1st"))
                    termname.setText("Semester: First Year First Term Examination");      
          else if(s.equals("2nd"))
                    termname.setText("Semester: First year Second term");
          else if(s.equals("3rd"))
                    termname.setText("Semester: Second year First term");
          else if(s.equals("4th"))
                    termname.setText("Semester: Second year Second term");
          else if(s.equals("5th"))
                    termname.setText("Semester: Third year First term");
          else if(s.equals("6th"))
                    termname.setText("Semester: Third year Second term");
          else if(s.equals("7th"))
                    termname.setText("Semester: Fourth year First term");
          else if(s.equals("8th"))
                    termname.setText("Semester: Fourth year Second term");
                    
    }

    @FXML
    private void delete(ActionEvent event) {
        Report report = tableview.getSelectionModel().getSelectedItem();
        if(report == null){
            return;
        }
        boolean delete = DataBaseHandler.getInstance().deletereport(report);
    }

    
    @FXML
    private void generatePDF(ActionEvent event) {
        String sem = stuSemester.getText();
          List<List> printData = new ArrayList<>();
         String[] headers = {"Student ID", "Student Name", "Obtain CGPA"};
         printData.add(Arrays.asList(headers));
         for (Report info : list) {
            List<String> row = new ArrayList<>();
            row.add(info.getStuId());
            row.add(info.getStuName());
            row.add(String.valueOf(info.getStuCgpa()));
           
            printData.add(row);
        }
        ResultProcessingUtil.initPDFExprot(stackpane, rootanchorPane, getStage(), printData,sem);
    }
    
     private Stage getStage() {
        return (Stage) tableview.getScene().getWindow();
    }

    public static class Report{
        private SimpleStringProperty stuId;
        private SimpleStringProperty stuName;
        private SimpleDoubleProperty stuCgpa;
    
    
    public Report(String id,String name,double cg){
        this.stuId = new SimpleStringProperty(id);
        this.stuName = new SimpleStringProperty(name);
        this.stuCgpa = new SimpleDoubleProperty(cg);
    }

        public String getStuId() {
            return stuId.get();
        }

        public void setStuId(String stuId) {
            this.stuId = new SimpleStringProperty(stuId);
        }

        public String getStuName() {
            return stuName.get();
        }

        public void setStuName(String stuName) {
            this.stuName = new SimpleStringProperty(stuName);
        }

        public double getStuCgpa() {
            return stuCgpa.get();
        }

        public void setStuCgpa(double stuCgpa) {
            this.stuCgpa = new SimpleDoubleProperty(stuCgpa);
        }
    
    
    
    }
    
}
