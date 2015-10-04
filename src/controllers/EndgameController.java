/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.FrameController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jfx_1.JFX_1;
import jfx_1.UserScore;

/**
 * FXML Controller class
 *
 * @author Xenan
 */
public class EndgameController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Stage dialogStage;
    @FXML
    private Label name;
    @FXML
    private Label score;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(JFX_1.userInfo.getName());
        score.setText(String.valueOf(JFX_1.userInfo.getScore()));
    }

    public void setDialogStage(Stage stage) {
        dialogStage = stage;
    }

    @FXML
    private void okClick() {
        UserScore uScore = new UserScore(
                name.getText(),
                Integer.valueOf(score.getText()), 
                JFX_1.map.getMaximumNumberOfCell()
        );
        uScore.saveToFile();
        dialogStage.close();
        FrameController.control.onClickRestart();
    }
}
