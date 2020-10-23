/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Models.Patient;
import Models.User;
import static com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl.createDate;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author FDoshi
 */
public class DBConn {
    
    private static final String DBNAME = "sql9348412";
    private static final String URL = "jdbc:mysql://sql9.freemysqlhosting.net/" + DBNAME;
    private static final String USERNAME = "sql9348412";
    private static final String PASSWORD = "6JCSLpyfqE";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static Connection conn;
    
    public DBConn(){}
    
    //Connect to DB
    public static void Connect() {
        try {
            Class.forName(DRIVER);
            conn = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection sucessfull");
            } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    
    // Disconnect Database Connection
    public static void Disconnect() {
        try {
            conn.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    
    // Returns Database Connection
    public static java.sql.Connection getConnection() {
        return conn;
    }
    
    /*
    -----------------
    LOG IN FUNCTION
    -----------------
    */
    
    public static User physLogin(String username, String password) {
        User user = new User();
        try{
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM user WHERE userName=? AND userPW=?");
            pst.setString(1, username); 
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();                        
            if(rs.next()){
                user.setUsername(rs.getString("userName"));
                user.setPassword(rs.getString("userPW"));
                user.setUserID(rs.getString("userID"));
            } else {
                return null;    
            }            
                
        } catch(SQLException e){
            e.printStackTrace();
        }       
        return user;
    }
    
    /*
    ---------------------------------
     New Patient ADD/Edit METHODS
    ---------------------------------
    */
    
    public static boolean saveNewPt(String lastName, String firstName, String age, LocalDate date, String phys, String medsList, String ptNote) throws SQLException{
        
        String statement = "INSERT INTO patient (ptLastName, ptFirstName, ptAge, ptStatus, medsList, ptNote, noteDate, physName)"
                + "VALUE (?, ?, ?, 'active', ?, ?, ?, ?);";
        
        PreparedStatement pst = conn.prepareStatement(statement);
        pst.setString(1, lastName);
        pst.setString(2, firstName);
        pst.setString(3, age);        
        pst.setString(4, medsList); 
        pst.setString(5, ptNote); 
        pst.setDate(6, java.sql.Date.valueOf(date));
        pst.setString(7, phys);
        pst.execute(); 
        
        return true;
    }
    
    public static boolean updatePt(String lastName, String firstName, String age, LocalDate date, String phys, String medsList, String ptNote, int ptID) throws SQLException{
        
        String statement = "UPDATE patient SET ptLastName = ?, ptFirstName = ?, ptAge = ?, medsList = ?, ptNote = ?, noteDate = ?, physName= ? WHERE ptID = ?";
        
        
        PreparedStatement pst = conn.prepareStatement(statement);
        pst.setString(1, lastName);
        pst.setString(2, firstName);
        pst.setString(3, age);        
        pst.setString(4, medsList); 
        pst.setString(5, ptNote); 
        pst.setDate(6, java.sql.Date.valueOf(date));
        pst.setString(7, phys);
        pst.setInt(8, ptID);
        pst.execute();
                
        return true;
    }
    
    public static void deletePt(int ptID) throws SQLException {
        String deleteQuery = "DELETE FROM patient WHERE ptID = ?;";
        PreparedStatement pst = getConnection().prepareStatement(deleteQuery);
        pst.setInt(1, ptID);
        pst.executeUpdate();
    }
    
    /*
    ---------------------------------
    Patient List Form METHODS
    ---------------------------------
    */
    
    public static List<Patient> getAllPtData() throws SQLException{
       ObservableList<Patient> patientList = FXCollections.observableArrayList();  
        String statement = "SELECT * FROM patient";
        PreparedStatement pst =conn.prepareStatement(statement);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            int ptID = rs.getInt("ptID");
            String ptLastName = rs.getString("ptLastName");
            String ptFirstName = rs.getString("ptFirstName");
            String phys = rs.getString("physName");
            String age = String.valueOf(rs.getInt("ptAge"));
            String ptStatus = rs.getString("ptStatus");
            Date d = rs.getDate("noteDate");             
            LocalDate date = d.toLocalDate();
            String medsList = rs.getString("medsList");
            String ptNotes = rs.getString("ptNote");
            
            patientList.add(new Patient(ptID,phys,ptLastName,ptFirstName, age, ptStatus, date,
                medsList, ptNotes));
        }
        return patientList; 
    }
    
    public static List<Patient> getActivePtData() throws SQLException{
       ObservableList<Patient> activePatientList = FXCollections.observableArrayList();  
        String statement = "SELECT * FROM patient WHERE ptStatus = 'active'";
        PreparedStatement pst =conn.prepareStatement(statement);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            int ptID = rs.getInt("ptID");
            String ptLastName = rs.getString("ptLastName");
            String ptFirstName = rs.getString("ptFirstName");
            String phys = rs.getString("physName");
            String age = String.valueOf(rs.getInt("ptAge"));
            String ptStatus = rs.getString("ptStatus");
            Date d = rs.getDate("noteDate");             
            LocalDate date = d.toLocalDate();
            String medsList = rs.getString("medsList");
            String ptNotes = rs.getString("ptNote");
            
            activePatientList.add(new Patient(ptID,phys,ptLastName,ptFirstName, age, ptStatus, date,
                medsList, ptNotes));
        }
        return activePatientList; 
    }
    
    public static void changePtStatus(int ptID, String ptStatus) throws SQLException{
        String statement = "UPDATE patient SET ptStatus = ? WHERE ptID = ?";
        PreparedStatement pst = conn.prepareStatement(statement);
        if(ptStatus.equals("active")){
            pst.setString(1, "discharged");
        } else  if(ptStatus.equals("discharged")){
            pst.setString(1, "active");
        }
        pst.setInt(2, ptID);
        pst.executeUpdate();        
    }
    
    /*
    ---------------------------------
    GENERATE REPORT METHODS
    ---------------------------------
    */
    
    public void generateReport(){
        
    }
    
    
}