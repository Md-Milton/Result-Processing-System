/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseHandler;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import resultprocessing.addDEO.DeoListController;
import resultprocessing.addTeacher.ViewTeacherController;
import resultprocessing.addTeacher.ViewTeacherController.Teacher;
import static resultprocessing.adminlogin.LoginController.na;
import resultprocessing.generate.report.ReportController;
import resultprocessing.result.ResultController;
import resultprocessing.studentlist.StudentListController;
import resultprocessing.subjectList.SubjectListController;
import resultprocessing.subjectList.SubjectListController.Subject;

/**
 *
 * @author milton
 */
public class DataBaseHandler {
    private static DataBaseHandler handler = null;

    private static final String DB_URl = "jdbc:derby:resultprocessing;create=true";
    private static Connection connection = null;
    private static Statement statement = null;
    
   
     private DataBaseHandler() {
        createConnection();
        setStudentTable();
        setUpCourseTable();
        
        setUpResultTable();
        
        setUpTeacherTable();
        setUpDEO();
        setUpreport();
       //CourseTable();
        
        
    }
    
    public static DataBaseHandler getInstance() {
        if (handler == null) {
            handler = new DataBaseHandler();
        }
        return handler;
    }
    
    public void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            connection = DriverManager.getConnection(DB_URl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
        } catch (Exception e) {
            System.out.println("Exception  at exceQuery : data handler " + e.getLocalizedMessage());
            return null;
        }

        return result;
    }

     public boolean execAction(String query) {
        try {
            statement = connection.createStatement();
            statement.execute(query);
            return true;
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error " + e.getMessage(), "error occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at exceAction " + e.getLocalizedMessage());
            return false;
        }
    }

    public void setStudentTable() {
        String TABLE_NAME = "STUDENT";
        try {
            statement = connection.createStatement();
            DatabaseMetaData tables = connection.getMetaData();
            ResultSet result = tables.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (result.next()) {
                System.out.println("Table: " + TABLE_NAME + " already exists ready for go now!");
            } else {
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + " id varchar(100) primary key,\n"
                        + " name varchar(200),\n"
                        + " session varchar(200),\n"
                        + " contact varchar(200)"
                        + ")");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage() + ".......setup student table");
        }
    }

    public boolean updateStudent(StudentListController.Student student) {
           try{
               String query = "UPDATE STUDENT SET name=?,session=?,contact=? WHERE id=?";
               PreparedStatement preparedStatement = connection.prepareStatement(query);
               
               preparedStatement.setString(1, student.getStuName());
               preparedStatement.setString(2, student.getSession());
               preparedStatement.setString(3, student.getContact());
               preparedStatement.setString(4, student.getStuId());
               
               int result = preparedStatement.executeUpdate();
               if(result>0)
                   return true;
           }catch(SQLException ex){
               Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null,ex);
           }
        
        return false;
    }
    public boolean teachersettingsupdate(Teacher teacher,String name){
    
    
        try {
            String query = "UPDATE TEACHER SET pass=? WHERE name='"+name+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, teacher.getPassword());
            
            
            int result = preparedStatement.executeUpdate();
               if(result>0)
                   return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    return false;
}
public boolean deleteStudent(StudentListController.Student student) {
        try{
            String query = "DELETE FROM STUDENT WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, student.getStuId());
            int result = preparedStatement.executeUpdate();
            if(result == 1)
                return true;
            
        }catch(SQLException ex){
               Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null,ex);
         }
        return false;  
    }
    
public boolean deletereport(ReportController.Report report) {
       try{
            String query = "DELETE FROM REPORT WHERE studentId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, report.getStuId());
            int result = preparedStatement.executeUpdate();
            if(result == 1)
                return true;
            
        }catch(SQLException ex){
               Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null,ex);
         }
        return false;   
    }
    //course table 
public void setUpCourseTable() {
       String TABLE_NAME = "SUBJECT";
        try {
            statement = connection.createStatement();
            DatabaseMetaData tables = connection.getMetaData();
            ResultSet result = tables.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (result.next()) {
                System.out.println("Table: " + TABLE_NAME + " already exists ready for go now!");
            } else {
                 statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        +"coursecode varchar(100) primary key,\n"
                        +"coursetitle varchar(100),\n"
                        +"coursecredits DOUBLE PRECISION,\n"  
                        +"semester varchar(20),\n"
                        +"stu_id varchar(100),\n"
                        +"FOREIGN KEY (stu_id) REFERENCES STUDENT(id)"
                        +")");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage() + ".......setup subject table");
        }
    }
  
public void setUpResultTable(){
         String TABLE_NAME = "RESULT";
         try{
             statement = connection.createStatement();
             DatabaseMetaData table = connection.getMetaData();
             ResultSet result = table.getTables(null, null, TABLE_NAME.toUpperCase(), null);
             if(result.next()){
                 System.out.println("Table: " + TABLE_NAME + " already exists ready for go now!");
             }else{
                 statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        +"studentId varchar(100),\n"
                        +"courseCode varchar(100),\n"
                        +"courseTitle varchar(100),\n"
                        +"courseCredits DOUBLE PRECISION,\n"
                        +"semester varchar(50),\n"
                        +"gradePoint DOUBLE PRECISION,\n"
                        +"grade varchar(10),\n"
                        +"PRIMARY KEY (studentId,courseCode),\n"
                        +"FOREIGN KEY (studentId) REFERENCES STUDENT(id),\n"
                        +"FOREIGN KEY (courseCode) REFERENCES SUBJECT(coursecode)"
                        +")");
             }
         }catch(Exception e){
             System.err.println(e.getMessage() + ".......setup result table");
         }
     }
    
     
     //.................... teacher info operation..............................
     
public void setUpTeacherTable(){
         String TABLE_NAME = "TEACHER";
         try{
             statement = connection.createStatement();
             DatabaseMetaData data = connection.getMetaData();
             ResultSet table = data.getTables(null, null, TABLE_NAME.toUpperCase(), null);
             if(table.next()){
                 System.out.println("Table: " + TABLE_NAME + " already exists ready for go now!");
             }else{
                 statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        +"teacherId varchar(20)primary key,\n"
                        +"name varchar(100),\n"
                        +"contact varchar(100),\n"
                        +"rank varchar(100),\n"
                        +"pass varchar(100)"
                        +")");
             }
             
             
         }catch(Exception e){
             System.err.println(e.getMessage()+"error creating teacher table");
         }
     }
public void setUpreport(){
         String TABLE_NAME = "REPORT";
         try{
             statement = connection.createStatement();
             DatabaseMetaData data = connection.getMetaData();
             ResultSet table = data.getTables(null, null, TABLE_NAME.toUpperCase(), null);
             if(table.next()){
                 System.out.println("Table: " + TABLE_NAME + " already exists ready for go now!");
             }else{
                 statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        +"studentId varchar(20),\n"
                        +"name varchar(100),\n"
                        +"cgpa DOUBLE PRECISION,\n"
                        +"semester varchar(10),\n"
                        +"PRIMARY KEY (studentId,semester)"
                        +")");
             }
             
             
         }catch(Exception e){
             System.err.println(e.getMessage()+"error creating REPORT table");
         }
     }
public boolean editTeacher(ViewTeacherController.Teacher teacher) {
        try {
            String query = "UPDATE TEACHER SET name=?,contact=?,rank=? WHERE teacherId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setString(2, teacher.getContact());
            preparedStatement.setString(3, teacher.getRank());
            preparedStatement.setString(4, teacher.getId());
            
            int result = preparedStatement.executeUpdate();
            if(result>0){
                return true;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        return false;
            }
public boolean loginteacher( String query,String name,String pass){
            ResultSet rs = null; 
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, name);
            ps.setString(2, pass);
            
             rs = ps.executeQuery();
             if(rs.next()){
                 return true;
             }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
             
             return false;
         }
public void setUpDEO(){
         String TABLE_NAME = "DEO";
         try{
             statement = connection.createStatement();
             DatabaseMetaData data = connection.getMetaData();
             ResultSet table = data.getTables(null, null, TABLE_NAME.toUpperCase(), null);
             if(table.next()){
                 System.out.println("Table: " + TABLE_NAME + " already exists ready for go now!");
             }else{
                 statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        +"name varchar(100)primary key,\n"
                        +"contact varchar(100),\n"
                        +"joiningDate Date,\n"
                        +"pass varchar(100)"
                        +")");
             }
             
             
         }catch(Exception e){
             System.err.println(e.getMessage()+"error creating data entry operator table");
         }
     }
public boolean updateDEO(DeoListController.DEO deo) {
         try{
               String query = "UPDATE DEO SET name=?,contact=?,joiningDate=? WHERE name=?";
               PreparedStatement preparedStatement = connection.prepareStatement(query);
               
               preparedStatement.setString(1, deo.getName());
               preparedStatement.setString(2, deo.getContact());
               preparedStatement.setDate(3,(Date) deo.getDate());
               preparedStatement.setString(4,deo.getName());
               
               int result = preparedStatement.executeUpdate();
               if(result>0)
                   return true;
           }catch(SQLException ex){
               Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null,ex);
           }
        
        return false;
    }
public boolean deleteTeacher(ViewTeacherController.Teacher teacher) {
            
        try {
             String query = "DELETE FROM TEACHER WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, teacher.getId());
            int result = preparedStatement.executeUpdate();
            if(result == 1)
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
public boolean updateSubject(SubjectListController.Subject subject) {
        try{
               String query = "UPDATE SUBJECT SET coursetitle=?,coursecredits=?,semester=? WHERE coursecode=?";
               PreparedStatement preparedStatement = connection.prepareStatement(query);
               
               
               preparedStatement.setString(1, subject.getTitle());
               
               String st = String.valueOf(subject.getCredit());
               preparedStatement.setString(2, st);
               preparedStatement.setString(3, subject.getSemtr());
               preparedStatement.setString(4, subject.getCode());
               
               int result = preparedStatement.executeUpdate();
               if(result>0)
                   return true;
           }catch(SQLException ex){
               Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null,ex);
           }
        
        return false;
    }
public boolean deleteSubject(Subject subject) {
        try{
            String query = "DELETE FROM SUBJECT WHERE coursecode=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1,subject.getCode());
            int result = preparedStatement.executeUpdate();
            if(result == 1)
                return true;
            
        }catch(SQLException ex){
               Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null,ex);
         }
        return false;  
    }

    
}
