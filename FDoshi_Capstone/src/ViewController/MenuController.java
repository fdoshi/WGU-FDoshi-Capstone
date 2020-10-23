/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Main.RoundsHandoff;
import Models.User;
import Utils.DBConn;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author FDoshi
 */
public class MenuController{
    
    @FXML
    private Label drNameLbl;
    
    private RoundsHandoff myApp;
    private User currentUser;
    
    @FXML
    void onActionAddNewPt(ActionEvent event) throws IOException {
    Boolean isEditPt = myApp.showPtAddForm(currentUser);            
    }

    @FXML
    void onActionMakeReport(ActionEvent event) throws IOException, SQLException {
        myApp.showReport(currentUser);
    }

    @FXML
    void onActionViewPtList(ActionEvent event) throws IOException, SQLException {
    myApp.showPtsChart(currentUser); 
    }
    
    @FXML
    void onActionSignOut(ActionEvent event) throws IOException {
        DBConn.Disconnect();
        myApp.showLogin();
    }


    /**
     * Initializes the controller class.
     */
    
    public void setMenu(RoundsHandoff myApp, User phys ) {
        // TODO
        this.myApp = myApp;
        currentUser = phys;
        //drNameLbl.setText("Hello Dr. " + currentUser.getUsername());
        
    }    
    
}
