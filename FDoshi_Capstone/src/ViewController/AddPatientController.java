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
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author FDoshi
 */
public class AddPatientController {
    
    @FXML
    private Label physNoteLbl;
    
    @FXML
    private Label headerLbl;


    @FXML
    private TextField fNameTxt;

    @FXML
    private TextField lNameTxt;

    @FXML
    private TextField ageTxt;

    @FXML
    private TextArea medsListTxtArea;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea notesTxtArea;
    
    @FXML
    private Button backBtn;
    
    private RoundsHandoff myApp;
    private User currentUser;
    private Patient updatePt;
    private DBConn myConn;
    private boolean isEditPt = false;

    @FXML
    void onActionBack(ActionEvent event) throws IOException, SQLException {
        myApp.showPtsChart(currentUser);
       
    }   
   
    @FXML
    void onActionSavePtData(ActionEvent event) throws SQLException {
        
        if(validatePtData()){
            if (isEditPt()){
                String lastName = lNameTxt.getText();
                String firstName = fNameTxt.getText();
                String age = ageTxt.getText();
                LocalDate date = datePicker.getValue();
                String phys = updatePt.getPhysName();
                String medsList = medsListTxtArea.getText();
                String ptNote = notesTxtArea.getText();
                int ptId = updatePt.getPtID();
                
                DBConn.updatePt(lastName,firstName, age, date, phys, medsList, ptNote, ptId);
                JOptionPane.showMessageDialog(null, "Patient's data is updated successfully.");
            }else {
                String lastName = lNameTxt.getText();
                String firstName = fNameTxt.getText();
                String age = ageTxt.getText();
                LocalDate date = datePicker.getValue();
                String phys = currentUser.getUsername();
                String medsList = medsListTxtArea.getText();
                String ptNote = notesTxtArea.getText();
        
                DBConn.saveNewPt(lastName,firstName, age, date, phys, medsList, ptNote);
                JOptionPane.showMessageDialog(null, "Patient's data is saved successfully.");
            }
            
        }  
    }
       
    private boolean validatePtData(){
        String lastName = lNameTxt.getText();
        String firstName = fNameTxt.getText();
        String age = ageTxt.getText();              
        String medsList = medsListTxtArea.getText();
        String ptNote = notesTxtArea.getText();
        
        String errorMsg = "";
        
        if(lastName == null){
            errorMsg += "Please enter patient's last name. \n";
        }
        if(firstName == null){
            errorMsg += "Please enter patient's first name. \n";
        }
        if(age == null){
            age = "0";
            errorMsg += "Please enter patient's age. \n";
        }        
        if(medsList == null){
            errorMsg += "Please enter patient's medications list. \n";
        }
        if(ptNote == null){
            errorMsg += "Please enter physician's note. \n";
        }
        
        if (errorMsg.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);           
            alert.setTitle("Required Fields");
            alert.setHeaderText("Please enter all required details.");
            alert.setContentText(errorMsg);
            alert.showAndWait();
            return false; 
        }
    }
   

    /**
     * Initializes the controller class.
     */
    
    public void setPtForm(RoundsHandoff myApp, User phys) {
        // TODO
        this.myApp = myApp;
        currentUser = phys;
        //physNoteLbl.setText("Dr." + currentUser.getUsername() + "'s Note");
        datePicker.setValue(LocalDate.now());
    } 
    
    public void setPtUpdateForm(RoundsHandoff myApp, User phys, Patient selectedPt){
        isEditPt = true;
        this.myApp = myApp;
        this.currentUser = phys;
        updatePt = selectedPt;
        headerLbl.setText("View/Update Patient Details ");
        physNoteLbl.setText("Update Note");
        datePicker.setValue(selectedPt.getCreateDate()); 
        fNameTxt.setText(selectedPt.getPtFirstName());
        lNameTxt.setText(selectedPt.getPtLastName());
        ageTxt.setText(String.valueOf(selectedPt.getAge()));
        medsListTxtArea.setText(selectedPt.getMedsList());
        notesTxtArea.setText(selectedPt.getPtNotes());
        
    }

    public boolean isEditPt() {
       return isEditPt;
    }

}


