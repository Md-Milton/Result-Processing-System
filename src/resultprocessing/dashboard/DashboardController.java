/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.dashboard;

import databaseHandler.DataBaseHandler;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import resultprocessing.main.MainController;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class DashboardController implements Initializable {

    //----first
    @FXML
    private Label fcourse;
    @FXML
    private Label fCredits;
    @FXML
    private Label maxcgpa;
    
   //----------second
    @FXML
    private Label Scourse;
    @FXML
    private Label Scredits;
    @FXML
    private Label Scgpa;
    //---------------third
    @FXML
    private Label tcourse;
    @FXML
    private Label tcredits;
      @FXML
    private Label tcgpa;
    
    //---fourth
    @FXML
    private Label frcourse;
    @FXML
    private Label frcredits;
//--------------fifth
    
    
    @FXML
    private Label ficourse;
    @FXML
    private Label ficredits;
    
    //----sixth
    @FXML
    private Label scourse;
    @FXML
    private Label scredits;
    
    //---seven
    @FXML
    private Label svcourse;
    @FXML
    private Label svcredits;
    @FXML
    //eight
    private Label ecourse;
    @FXML
    private Label ecredits;
  
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        firstterm();
        Secondterm();
        thirdterm();
        fourthterm();
        fifthterm();
        sixthterm();
        seventhterm();
        eighthterm();
        
    }   
    public void firstterm() {
            String first = "1st";
            String course = termCourse(first);
            String credits = termCredits(first);
            if(course.equals("")|| credits.equals("")){
                fcourse.setText("");
                fCredits.setText("No Entry" );
              
            }else{
                fcourse.setText("Total Course: "+course);
                fCredits.setText("Total Credits : "+credits);

            String query = "SELECT MAX(REPORT.cgpa)FROM REPORT WHERE semester='"+first+"'";
            DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();
            ResultSet rs = dataBaseHandler.execQuery(query);
        try {
            while(rs.next()){
            double tgpa1 = rs.getDouble(1);
            String t = Double.toString(tgpa1);
            String total_tgpa = String.format("%.2f", tgpa1);
            maxcgpa.setText("MAX CGPA: "+total_tgpa);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
            
    }
    
    
      public void Secondterm(){
        
        String first = "2nd";
            String course = termCourse(first);
            String credits = termCredits(first);
            if(course.equals("")|| credits.equals("")){
                Scourse.setText("");
                scredits.setText("No Entry" );
              
            }else{
                Scourse.setText("Total Course: "+course);
                Scredits.setText("Total Credits : "+credits);

            String query = "SELECT MAX(REPORT.cgpa)FROM REPORT WHERE semester='"+first+"'";
            DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();
            ResultSet rs = dataBaseHandler.execQuery(query);
        try {
            while(rs.next()){
            double tgpa1 = rs.getDouble(1);
            String t = Double.toString(tgpa1);
            String total_tgpa = String.format("%.2f", tgpa1);
            Scgpa.setText("MAX CGPA: "+total_tgpa);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
        
    }
    
    
    
    
     public void thirdterm(){
        String first = "3rd";
            String course = termCourse(first);
            String credits = termCredits(first);
            if(course.equals("")|| credits.equals("")){
                tcourse.setText("");
                tcredits.setText("No Entry" );
              
            }else{
                tcourse.setText("Total Course: "+course);
                tcredits.setText("Total Credits : "+credits);

            String query = "SELECT MAX(REPORT.cgpa)FROM REPORT WHERE semester='"+first+"'";
            DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();
            ResultSet rs = dataBaseHandler.execQuery(query);
        try {
            while(rs.next()){
            double tgpa1 = rs.getDouble(1);
            if(tgpa1==0.0){
                tcgpa.setText("MAX CGPA: "+"Exam not held yet");
            }else{
            String t = Double.toString(tgpa1);
            String total_tgpa = String.format("%.2f", tgpa1);
            tcgpa.setText("MAX CGPA: "+total_tgpa);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
            
    }
     public void fourthterm(){
        String first = "4th";
            String course = termCourse(first);
            String credits = termCredits(first);
            if(course.equals("")|| credits.equals("")){
                frcourse.setText("");
                frcredits.setText("No Entry" );
                
            }else{
                frcourse.setText("Total Course: "+course);
                frcredits.setText("Total Credits: "+credits);
            }
            
    }
     
        public void fifthterm(){
        String first = "5th";
            String course = termCourse(first);
            String credits = termCredits(first);
            if(course.equals("")|| credits.equals("")){
                ficourse.setText("");
                ficredits.setText("No Entry" );
                
            }else{
                ficourse.setText("Total Course: "+course);
                ficredits.setText("Total Credits: "+credits);
            }
            
    }
        public void sixthterm(){
        String first = "6th";
            String course = termCourse(first);
            String credits = termCredits(first);
            if(course.equals("")|| credits.equals("")){
                scourse.setText("");
                scredits.setText("No Entry" );
                
            }else{
                scourse.setText("Total Course: "+course);
                scredits.setText("Total Credits: "+credits);
            }
            
    }
        public void seventhterm(){
        String first = "7th";
            String course = termCourse(first);
            String credits = termCredits(first);
            if(course.equals("")|| credits.equals("")){
                svcourse.setText("");
                svcredits.setText("No Entry" );
                
            }else{
                svcourse.setText("Total Course: "+course);
                svcredits.setText("Total Credits: "+credits);
            }
            
    }
        
        
        public void eighthterm(){
        String first = "8th";
            String course = termCourse(first);
            String credits = termCredits(first);
            if(course.equals("")|| credits.equals("")){
                ecourse.setText("");
                ecredits.setText("No Entry" );
                
            }else{
                ecourse.setText("Total Course: "+course);
                ecredits.setText("Total Credits: "+credits);
            }
            
    }
     
     
  
    
    public String termCourse(String term){
         String query = "SELECT COUNT(SUBJECT.coursecredits)FROM SUBJECT WHERE SUBJECT.semester='"+term+"'";
             DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();
             ResultSet rs = dataBaseHandler.execQuery(query);
        try {
            if(rs.next()){
                double semCredits = rs.getDouble(1);
                if(semCredits != 0){
                String st = String.valueOf(semCredits);
                return st;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
            return "";
    }
    
    public String termCredits(String term){
             String query1 = "SELECT SUM(SUBJECT.coursecredits)FROM SUBJECT WHERE SUBJECT.semester='"+term+"'";
             DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();
             ResultSet rs1 = dataBaseHandler.execQuery(query1);
        try {
            if(rs1.next()){
                double semCredits = rs1.getDouble(1);
                if(semCredits!=0){
                String st = String.valueOf(semCredits);
                return st;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
  
}
