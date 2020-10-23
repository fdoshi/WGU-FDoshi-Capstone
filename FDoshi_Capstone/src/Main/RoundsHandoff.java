/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Main;

import Models.Patient;
import Models.User;
import Utils.DBConn;
import ViewController.AddPatientController;
import ViewController.LoginController;
import ViewController.MenuController;
import ViewController.ReportsController;
import ViewController.ViewPatientsController;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author FDoshi
 */
public class RoundsHandoff extends Application {
    private Stage primaryStage;    
    //private User currentUser;
    User currentUser = new User();
    @Override
    public void start(Stage primaryStage) throws IOException{       
        this.primaryStage  = primaryStage; 
        
        showLogin();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        DBConn.Connect();
        launch(args);
        DBConn.Disconnect();
    }
              
    
    public void showLogin() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewController/Login.fxml"));
        AnchorPane pane = loader.load();
        
        LoginController controller = loader.getController();
        controller.setLogin(this);
        
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void showMenu(User currentUser) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewController/Menu.fxml"));
        AnchorPane pane = loader.load();
        
        //this.currentUser = currentUser;
        MenuController controller = loader.getController();
        controller.setMenu(this, currentUser);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();  
    }
    
    public boolean showPtAddForm(User currentUser) throws IOException{
       try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewController/AddPatient.fxml"));
        AnchorPane pane = loader.load();
        
        AddPatientController controller = loader.getController();
        controller.setPtForm(this, currentUser);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();  
        
        return controller.isEditPt();
        } catch (IOException e) {
        e.printStackTrace();
        return false;
        }
    }
    
    public boolean showPtUpdateForm(User currentUser, Patient selectedPt) throws IOException{
        try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewController/AddPatient.fxml"));
        AnchorPane pane = loader.load();        
        
        AddPatientController controller = loader.getController();
        controller.setPtUpdateForm(this, currentUser, selectedPt);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();  
        
        return controller.isEditPt();
        } catch (IOException e) {
        e.printStackTrace();
        return false;
        }
    }
    
    public void showPtsChart(User currentUser) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewController/ViewPatients.fxml"));
        AnchorPane pane = loader.load();
        
        //this.currentUser = currentUser;
        ViewPatientsController controller = loader.getController();
        controller.setPtListForm(this, currentUser);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();  
    }
    
    public void showReport(User currentUser) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewController/Reports.fxml"));
        AnchorPane pane = loader.load();
        
        //this.currentUser = currentUser;
        ReportsController controller = loader.getController();
        controller.setReport(this, currentUser);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();  
    }
    
}
