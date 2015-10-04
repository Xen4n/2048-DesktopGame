/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import jfx_1.JFX_1;

/**
 *
 * @author Pavilion
 */
public class FrameController {

    public static FrameController control;
    public static Pane boxPane;
    private static Label name;
    private static Label score;

    @FXML
    Pane boxpane;
    @FXML
    Label textname;
    @FXML
    Label textscore;

    public static FrameController getControl() {
        return null;
    }

    public void initialize() {
        boxPane = boxpane;
        control = FrameController.this;
        name = textname;
        score = textscore;
        //JFX_1.userInfo = new UserInfo();

    }

    @FXML
    public void onClickLogin() {
        JFX_1.userInfo.saveTempInfo();
        JFX_1.map.clearAll();
        JFX_1.startGameForUser();
    }

    @FXML
    public void onClickRestart() {
        JFX_1.userInfo.deleteTempFile();
        JFX_1.map.clearAll();
        JFX_1.userInfo.setGrid(JFX_1.map.getGrid());
        JFX_1.userInfo.setScore(0);
        goAction();
    }

    @FXML
    public void onClickShowRating() {
        JFX_1.showRatingForUser();
    }

    @FXML
    public void onClickExit() {
        JFX_1.userInfo.saveTempInfo();
        JFX_1.getStage().close();
    }

    @FXML
    public void setSSheetBlue() {
        setSheetColor("Blue");
    }

    @FXML
    public void setSSheetGreen() {
        setSheetColor("Green");
    }

    private void setSheetColor(String colorString) {
        JFX_1.getScene().getStylesheets().clear();
        JFX_1.getScene().getStylesheets().add(getClass().getClassLoader().getResource("styles/box" + colorString + ".css").toExternalForm());
    }

    public static void setName(String text) {
        name.setText(text);
    }

    public static void setScore(int num) {
        score.setText(String.valueOf(num));
    }

    public void goAction() {
        int[][] grid = JFX_1.userInfo.getGrid();
        if (JFX_1.map.isNotBuzy(grid)) {
            JFX_1.map.addItem();
            JFX_1.map.addItem();
        } else {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (grid[j][i] != 0) {
                        JFX_1.map.addBox(i, j, grid[j][i]);
                        JFX_1.userInfo.setScore(JFX_1.userInfo.getScore() - grid[j][i]);
                    }
                }
            }
        }

//        JFX_1.map.addBox(0, 0, 2);
//        JFX_1.map.addBox(1, 0, 4);
//        JFX_1.map.addBox(2, 0, 8);
//        JFX_1.map.addBox(3, 0, 16);
//        JFX_1.map.addBox(0, 1, 32);
//        JFX_1.map.addBox(1, 1, 64);
//        JFX_1.map.addBox(2, 1, 128);
//        JFX_1.map.addBox(3, 1, 256);
//        JFX_1.map.addBox(0, 2, 512);
//        JFX_1.map.addBox(1, 2, 1024);
//        JFX_1.map.addBox(2, 2, 2048);
//        JFX_1.map.addBox(3, 2, 4096);
//        JFX_1.map.addBox(0, 3, 8192);
//        JFX_1.map.addBox(1, 3, 16384);
    }

}
