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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author FDoshi
 */
public class ViewPatientsController {
    @FXML
    private TableView<Patient> allPtsTbl;

    @FXML
    private TableColumn<Patient, Integer> allPtsIDCol;

    @FXML
    private TableColumn<Patient, String> allPtsLNameCol;

    @FXML
    private TableColumn<Patient, String> allPtsFNameCol;


    @FXML
    private TableView<Patient> activePtsTbl;

    @FXML
    private TableColumn<Patient, Integer> activePtsIDCol;

    @FXML
    private TableColumn<Patient, String> activePtsLNameCol;

    @FXML
    private TableColumn<Patient, String> activePtsFNameCol;

    @FXML
    private Button activeToallBtn;

    @FXML
    private Button allToactiveBtn;
    
    
    private RoundsHandoff myApp;
    private User currentUser;
    private DBConn myConn;
    ObservableList<Patient> patientList;
    
    

    @FXML
    void onActionActiveToAllBtn(ActionEvent event) throws SQLException {
        Patient selectedPt = activePtsTbl.getSelectionModel().getSelectedItem();
        allToactiveBtn.setDisable(true);
        if(selectedPt != null){            
            int ptID = selectedPt.getPtID();
            String ptStatus = selectedPt.getPtStatus();
            DBConn.changePtStatus(ptID, ptStatus);
            JOptionPane.showMessageDialog(null, "Patient has been dischaged.");
            setActivePtList();
            allToactiveBtn.setDisable(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Discharge the patient");
            alert.setHeaderText("Select a patient");
            alert.setContentText("Please select a patient you  want to discharge.");
            alert.showAndWait();
        }
        setActivePtList();
        allToactiveBtn.setDisable(false);
    }

    @FXML
    void onActionAllToActiveBtn(ActionEvent event) throws SQLException {
        Patient selectedPt = allPtsTbl.getSelectionModel().getSelectedItem();
        activeToallBtn.setDisable(true);
        if(selectedPt != null){            
            int ptID = selectedPt.getPtID();
            String ptStatus = selectedPt.getPtStatus();
            DBConn.changePtStatus(ptID, ptStatus);
            JOptionPane.showMessageDialog(null, "Patient is readmitted.");
            
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Re-Admmite the patient");
            alert.setHeaderText("Select a patient");
            alert.setContentText("Please select a patient you  want to readmit.");
            alert.showAndWait();
        }
        setActivePtList();
        activeToallBtn.setDisable(false);
    }

    @FXML
    void onActionEditPt(ActionEvent event) throws IOException {
        Patient selectedPt = activePtsTbl.getSelectionModel().getSelectedItem();
        
        if(selectedPt != null){
            Boolean isEditPt;
            isEditPt = myApp.showPtUpdateForm(currentUser, selectedPt);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update patient data");
            alert.setHeaderText("Select a patient");
            alert.setContentText("Please select a patient from Active Patients list.");
            alert.showAndWait();
        } 
    }

    @FXML
    void onActionOpenAddPt(ActionEvent event) throws IOException {
        Boolean isEditPt;
        isEditPt = myApp.showPtAddForm(currentUser); 
    }   

    @FXML
    void onAtionDeletPt(ActionEvent event) {
        
        Patient selectedPt = activePtsTbl.getSelectionModel().getSelectedItem();
        
        if (selectedPt != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete this patient details?");
            alert.showAndWait()
            .filter(response -> response == ButtonType.OK)
            .ifPresent(response -> {
                try {
                    myConn.deletePt(selectedPt.getPtID());
                } catch (SQLException ex) {
                    Logger.getLogger(ViewPatientsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    myApp.showPtsChart(currentUser);
                } catch (IOException ex) {
                    Logger.getLogger(ViewPatientsController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ViewPatientsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            );
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No patient selected for Deletion");
            alert.setContentText("Please select patient in the Table to delete");
            alert.showAndWait();
        }    
    }
    
    public void setActivePtList() throws SQLException{
        activePtsIDCol.setCellValueFactory(new PropertyValueFactory<>("ptID"));
       activePtsLNameCol.setCellValueFactory(new PropertyValueFactory<>("ptLastName"));
       activePtsFNameCol.setCellValueFactory(new PropertyValueFactory<>("ptFirstName"));
       activePtsTbl.setItems((ObservableList<Patient>) DBConn.getActivePtData());        
    }
    
    
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        myApp.showMenu(currentUser);
    }


    /**
     * Initializes the controller class.
     * @param myApp
     * @param phys
     * @throws java.sql.SQLException
     */
    
    public void setPtListForm(RoundsHandoff myApp, User phys) throws SQLException {
        // TODO
        this.myApp = myApp;
        currentUser = phys;
        
       allPtsIDCol.setCellValueFactory(new PropertyValueFactory<>("ptID"));
       allPtsLNameCol.setCellValueFactory(new PropertyValueFactory<>("ptLastName"));
       allPtsFNameCol.setCellValueFactory(new PropertyValueFactory<>("ptFirstName"));
       allPtsTbl.setItems((ObservableList<Patient>) DBConn.getAllPtData());
       
       activePtsIDCol.setCellValueFactory(new PropertyValueFactory<>("ptID"));
       activePtsLNameCol.setCellValueFactory(new PropertyValueFactory<>("ptLastName"));
       activePtsFNameCol.setCellValueFactory(new PropertyValueFactory<>("ptFirstName"));
       activePtsTbl.setItems((ObservableList<Patient>) DBConn.getActivePtData());
    }    
    
}


