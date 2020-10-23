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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author FDoshi
 */
public class LoginController {
    @FXML
    private TextField userNameTxt;

    @FXML
    private PasswordField pwTxt;

    @FXML
    private Label formLbl;

    @FXML
    private Label userLbl;

    @FXML
    private Label pwLbl;
    
    @FXML
    private Label errorMsgLbl;

    private RoundsHandoff myApp;
    //User user = new User();
   
    
    @FXML
    void onActionLogIn(ActionEvent event) throws IOException {
        String userName = userNameTxt.getText();
        String password = pwTxt.getText();
        
        User currentUser;
        currentUser = DBConn.physLogin(userName, password);
        
        if (currentUser == null){
            errorMsgLbl.setText("Please enter correct credentials.");
        }else{
            myApp.showMenu(currentUser);
        }

        
        
    }
    
    @FXML
    void onActionCancel(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Initializes the controller class.
     */
    
    public void setLogin(RoundsHandoff main) {
        // TODO
        this.myApp = main;
        
    }    
    
}
