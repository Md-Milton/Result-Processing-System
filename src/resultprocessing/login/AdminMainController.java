/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.login;

import alertmessage.AlertMaker;
import com.jfoenix.controls.JFXTextField;
import databaseHandler.DataBaseHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.Observable;
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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AdminMainController implements Initializable {

    public static void getRunningSemester(String semestr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    DataBaseHandler dataBaseHandler;
    @FXML
    private StackPane stackPane;
    @FXML
    private MenuItem close;
    @FXML
    private MenuItem addteacher;
    @FXML
    private JFXTextField studentId;
    @FXML
    private JFXTextField semestr;
    @FXML
    private VBox vbox;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label semester;
    @FXML
    private VBox tableVbox;
    @FXML
    private TableView<Result> tableView;
    @FXML
    private TableColumn<Result, String> courseCodeColumn;
    @FXML
    private TableColumn<Result, String> courseTitleColumn;
    @FXML
    private TableColumn<Result, Double> courseCreditsColumn;
    @FXML
    private TableColumn<Result, Double> coursegradepointColumn;
    @FXML
    private TableColumn<Result, String> coursegradeColumn;
    
    @FXML
    private Label totalcredit;
    @FXML
    private Label tgpaa;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private MenuItem addteacher1;
    @FXML
    private TextField Ssemester;
    
    
   
  @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataBaseHandler = dataBaseHandler.getInstance();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
    }  

   @FXML
    private void loadStudentRecord(ActionEvent event) throws SQLException {
        String student_id = studentId.getText();
        String sem = semestr.getText();
        if(student_id.isEmpty()||sem.isEmpty()){
            tableView.setVisible(false);
            AlertMaker.showError("Student id/semester is empty ");
        }else{
            loadTable();
            ObservableList<Result>resultData = loadData(student_id,sem);
            populateResult(resultData);
            tableView.setVisible(true);
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            getCGPA_totalcrdits();
            
        }
    }

    
    
    public void insertstudentReport(String i,String n,String s) throws SQLException{
        String query2 = "SELECT SUM(RESULT.courseCredits*RESULT.gradePoint)/SUM(RESULT.courseCredits)FROM RESULT WHERE studentId='"+i+"' AND semester='"+s+"'";
        double tgpa1=0; 
        ResultSet rs2= dataBaseHandler.execQuery(query2);
        if(rs2.next()){
            tgpa1 = rs2.getDouble(1);
        }
        /*
                        +"studentId varchar(20)primary key,\n"
                        +"name varchar(100),\n"
                        +"cgpa DOUBLE PRECISION,\n"
                        +"semester varchar(10)primary key"
                        +")");
        */
        
         String query = "INSERT INTO REPORT(studentId,name,cgpa,semester) VALUES("
                +"'"+i+"',"
                +"'"+n+"',"
                +""+tgpa1+","
                +"'"+s+"'"
                +")";
         if(dataBaseHandler.execAction(query)){
             System.err.println(query);
         }else{
             System.err.println("Error in "+query);
         }
    }
    
    public void getCGPA_totalcrdits() throws SQLException{
        String student_id = studentId.getText();
        String sem = semestr.getText();
        try{
        String query1 = "SELECT SUM(RESULT.courseCredits)FROM RESULT WHERE studentId='"+student_id+"' AND semester='"+sem+"'";
        String query2 = "SELECT SUM(RESULT.courseCredits*RESULT.gradePoint)/SUM(RESULT.courseCredits)FROM RESULT WHERE studentId='"+student_id+"' AND semester='"+sem+"'";
        
        ResultSet rs1= dataBaseHandler.execQuery(query1);
        if(rs1.next()){
            double total_credit1 = rs1.getDouble(1);
            String t = Double.toString(total_credit1);
            totalcredit.setText("Total Credits: "+t);
        }
        
        
        ResultSet rs2= dataBaseHandler.execQuery(query2);
        if(rs2.next()){
            double tgpa1 = rs2.getDouble(1);
            String t = Double.toString(tgpa1);
            String total_tgpa = String.format("%.2f", tgpa1);
            tgpaa.setText("Obtain TGPA: "+total_tgpa);
        }
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private ObservableList<Result> loadData(String student_id, String sem) {
        String myquery = "SELECT RESULT.studentId,RESULT.courseCode,RESULT.courseTitle,RESULT.courseCredits,RESULT.semester,RESULT.gradePoint,RESULT.grade,\n"
                +"STUDENT.name\n"
                +"FROM RESULT\n"
                +"LEFT JOIN STUDENT\n"
                +"ON RESULT.studentId=STUDENT.id\n"
                +"WHERE RESULT.studentId='"+student_id+"'"
                + "AND RESULT.semester='"+sem+"'";
        
        String q = "SELECT STUDENT.name FROM STUDENT WHERE STUDENT.id='"+student_id+"'";
        ResultSet rs = dataBaseHandler.execQuery(q);
        try {
            if(rs.next()){
                String n = rs.getString(1);
                insertstudentReport(student_id,n,sem);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        try{
        ResultSet result = dataBaseHandler.execQuery(myquery);
        ObservableList<Result>resultList = getResultList(result);
        return resultList;
 
       }catch(Exception e){
           System.out.println("While searching result with " + student_id + " id, an error occurred: "+e);
           throw e;
       } 
    }

    private ObservableList<Result> getResultList(ResultSet rs) {
                ObservableList<Result>list = FXCollections.observableArrayList();
        try{
            while(rs.next()){
                id.setText("Student ID: "+rs.getString("studentId"));
                name.setText("Name: "+rs.getString("name"));
                String s = rs.getString("semester");
                loadSemester(s);
                
                Result result = new Result();
                result.setCourse_code(rs.getString("courseCode"));
                result.setCourse_title(rs.getString("courseTitle"));
                result.setCourse_credits(rs.getDouble("courseCredits"));
                result.setGrade_point(rs.getDouble("gradePoint"));
                result.setGrade(rs.getString("grade"));
                list.add(result);
            }
    }catch (SQLException ex) {
            Logger.getLogger(AdminMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public void populateResult(ObservableList<Result>resultData){
        if(resultData==null){
            AlertMaker.showAlert(stackPane, "Data Search Operation", "No Data Exists");
        }
        tableView.setItems(resultData);
    }
               

    public void loadTable(){
      
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("course_code"));
        courseTitleColumn.setCellValueFactory(new PropertyValueFactory<>("course_title"));
        courseCreditsColumn.setCellValueFactory(new PropertyValueFactory<>("course_credits"));
        coursegradepointColumn.setCellValueFactory(new PropertyValueFactory<>("grade_point"));
        coursegradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      
    }
    public void clearEntries(){
        
        id.setText("");
        name.setText("");
        semester.setText("");
        totalcredit.setText("");
        tgpaa.setText("");
        tableView.setPlaceholder(new Label("Now Data Entry"));
        tableView.setVisible(false);
      }
    private void loadSemester(String s) {
          if(s.equals("1st"))
                    semester.setText("Semester: First Year First Term Examination");      
          else if(s.equals("2nd"))
                    semester.setText("Semester: First year Second term");
          else if(s.equals("3rd"))
                    semester.setText("Semester: Second year First term");
          else if(s.equals("4th"))
                    semester.setText("Semester: Second year Second term");
          else if(s.equals("5th"))
                    semester.setText("Semester: Third year First term");
          else if(s.equals("6th"))
                    semester.setText("Semester: Third year Second term");
          else if(s.equals("7th"))
                    semester.setText("Semester: Fourth year First term");
          else if(s.equals("8th"))
                    semester.setText("Semester: Fourth year Second term");
                    
    }

    @FXML
    private void addTeacher(ActionEvent event) throws IOException {
        load("/resultprocessing/addTeacher/addTeacher.fxml", "Add Teacher");
    }

    @FXML
    private void close(ActionEvent event)throws IOException {
        Stage stage = (Stage)rootAnchorPane.getScene().getWindow();
        stage.close();
        load("/resultprocessing/main/main.fxml", "");
    }

    @FXML
    private void addDEO(ActionEvent event) throws IOException {
        load("/resultprocessing/addDEO/addDEO.fxml", "Add D.E.O.");
    }

    @FXML
    private void viewTeacher(ActionEvent event) throws IOException {
        load("/resultprocessing/addTeacher/viewTeacher.fxml", "Teacher Info");
    }

    @FXML
    private void viewDEO(ActionEvent event) throws IOException {
        load("/resultprocessing/addDEO/deoList.fxml","DEO");
    }
 
  public  void load(String location,String title) throws IOException{
        Parent parentRoot = FXMLLoader.load(getClass().getResource(location));
        Stage stage = new Stage();
        stage.setScene(new Scene(parentRoot));
        stage.show();
        
    }

    @FXML
    private void changePassword(ActionEvent event) throws IOException {
        load("/resultprocessing/settings/settings.fxml", "settings");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        load("/resultprocessing/main/main.fxml", "Main");
        close(event);
    }
    @FXML
    private void generateReport(ActionEvent event) throws IOException{
        load("/resultprocessing/generate/report/report.fxml", "");
        //close(event);
    }
    
    
    public static class Result{
        private SimpleStringProperty studentId;
        private SimpleStringProperty course_code;
        private SimpleStringProperty course_title;
        private SimpleDoubleProperty course_credits;
        private SimpleStringProperty semester;
        private SimpleDoubleProperty grade_point;
        private SimpleStringProperty  grade;

        public Result(String studentId, String course_code, String course_title, double course_credits, String semester, double grade_point, String grade) {
            this.studentId = new SimpleStringProperty(studentId);
            this.course_code = new SimpleStringProperty(course_code);
            this.course_title = new SimpleStringProperty(course_title);
            this.course_credits = new SimpleDoubleProperty(course_credits);
            this.semester = new SimpleStringProperty(semester);
            this.grade_point = new SimpleDoubleProperty(grade_point);
            this.grade = new SimpleStringProperty(grade);
        }
         public Result(String course_code, String course_title, double course_credits, double grade_point, String grade) {
            
            this.course_code = new SimpleStringProperty(course_code);
            this.course_title = new SimpleStringProperty(course_title);
            this.course_credits = new SimpleDoubleProperty(course_credits);
            this.grade_point = new SimpleDoubleProperty(grade_point);
            this.grade = new SimpleStringProperty(grade);
        }

        public Result() {
            
        }

        public String getStudentId() {
            return studentId.get();
        }

        public String getCourse_code() {
            return course_code.get();
        }

        public String getCourse_title() {
            return course_title.get();
        }

        public double getCourse_credits() {
            return course_credits.get();
        }

        public String getSemester() {
            return semester.get();
        }

        public double getGrade_point() {
            return grade_point.get();
        }

        public String getGrade() {
            return grade.get();
        }

        
        public void setStudentId(String studentId) {
            this.studentId = new SimpleStringProperty(studentId);
        }

        public void setCourse_code(String course_code) {
            this.course_code = new SimpleStringProperty(course_code);
        }

        public void setCourse_title(String course_title) {
            this.course_title = new SimpleStringProperty(course_title);
        }

        public void setCourse_credits(double course_credits) {
            this.course_credits = new SimpleDoubleProperty(course_credits);
        }

        public void setSemester(String semester) {
            this.semester = new SimpleStringProperty(semester);
        }

        public void setGrade_point(double grade_point) {
            this.grade_point = new SimpleDoubleProperty(grade_point);
        }

        public void setGrade(String grade) {
            this.grade = new SimpleStringProperty(grade);
        }
        
        
    }
    
}
