/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author тереховва
 */
public class LoginController implements Initializable {

    private Stage dialogStage;
    private String name;
    @FXML
    private TextField textName;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void setDialogStage(Stage stage){
        dialogStage = stage;
    }
    public String getName(){
        return name;
    }
    @FXML
    public void okClick(){
        if(textName.getText().length()>3){
            name = textName.getText();
            dialogStage.close();
        } 
    }
    
}
