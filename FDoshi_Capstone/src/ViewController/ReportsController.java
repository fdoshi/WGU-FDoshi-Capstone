/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Main.RoundsHandoff;
import Models.Patient;
import Models.User;
import Utils.DBConn;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author FDoshi
 */
public class ReportsController {
    
    @FXML
    private TextArea reportTxtArea;
    
    private User currentUser;
    private DBConn myConn;
    private RoundsHandoff myApp;
    File file = new File("Report.txt");

    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        myApp.showMenu(currentUser);
    }

    @FXML
    void onActionPrintReport(ActionEvent event) throws IOException {
        fileWriter(file, reportTxtArea);
        //JOptionPane.showMessageDialog(null, "Report is saved file.");
        openReport();
    }
    
    //save text area containt to file
    public void fileWriter(File savePath, TextArea textArea) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(savePath));
            bf.write(textArea.getText());
            bf.flush();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //open saved file
    public void openReport() throws IOException{
        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) desktop.open(file);        
        
        file = new File("C:\\Users\\Doshi\\Documents\\NetBeansProjects\\FDoshi_Capstone\\Report.txt");
        if(file.exists()) desktop.open(file);
        
    }           
            
    
    /**
     * Initializes the controller class.
     */
    
    public void setReport(RoundsHandoff myApp, User phys) {
        // TODO
        this.myApp = myApp;
        generateReport();
       
    } 
    
    public void generateReport() {
        try {
            
            String statement = "SELECT * FROM patient WHERE ptStatus = 'active'";
                    
            PreparedStatement pst = DBConn.getConnection().prepareStatement(statement);
            ResultSet rs = pst.executeQuery();    
           
            StringBuilder genReportText = new StringBuilder();
            genReportText.append(String.format("%1$-25s %2$-10s %3$-25s %4$-25s %5$-30s %6$-60s \n", 
                    "Name", "Age", "Date", "Physician", "Medicine", "Note"));
            genReportText.append(String.join("", Collections.nCopies(175, "-")));
            genReportText.append("\n");
            while(rs.next()) {
                genReportText.append(String.format("%1$-25s %2$-10s %3$-25s %4$-25s %5$-30s %6$-60s \n", 
                    rs.getString("ptLastName"), rs.getString("ptAge"), rs.getString("noteDate"),
                    rs.getString("physName"), rs.getString("medsList"), rs.getString("ptNote")));
                genReportText.append(String.join("", Collections.nCopies(175, "-")));
                genReportText.append("\n");
            }
            
            reportTxtArea.setText(genReportText.toString());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }   
    
}
