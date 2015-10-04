/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import jfx_1.UserScore;

/**
 * FXML Controller class
 *
 * @author Xenan
 */
public class RatingController implements Initializable {

    private final String userDir = System.getProperty("user.dir");

    @FXML
    private TableView<UserScore> table;
    @FXML
    private TableColumn<UserScore, String> name;
    @FXML
    private TableColumn<UserScore, Integer> score;
    @FXML
    private TableColumn<UserScore, Integer> maximum;

    private Stage dialogStage;

    private ObservableList<UserScore> scoreData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<UserScore> userScores = getScoreFileList();
        if (userScores != null) {
            for (UserScore scr : userScores) {
                scoreData.add(scr);
            }
        }
        name.setCellValueFactory(cellData -> cellData.getValue().getName());
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        score.setCellFactory(new Callback<TableColumn<UserScore, Integer>, TableCell<UserScore, Integer>>() {
            @Override
            public TableCell<UserScore, Integer> call(TableColumn<UserScore, Integer> col) {
                return new TableCell<UserScore, Integer>() {
                    @Override
                    protected void updateItem(Integer score, boolean empty) {
                        super.updateItem(score, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(String.valueOf(score));
                        }

                    }
                };
            }
        });
        score.setSortType(TableColumn.SortType.DESCENDING);
        score.setSortable(true);
        maximum.setCellValueFactory(new PropertyValueFactory<>("maximum"));
        maximum.setCellFactory(new Callback<TableColumn<UserScore, Integer>, TableCell<UserScore, Integer>>() {
            @Override
            public TableCell<UserScore, Integer> call(TableColumn<UserScore, Integer> col) {
                return new TableCell<UserScore, Integer>() {
                    @Override
                    protected void updateItem(Integer maximum, boolean empty) {
                        super.updateItem(maximum, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(String.valueOf(maximum));
                        }

                    }
                };
            }
        });
        table.setItems(scoreData);
        table.getSortOrder().clear();
        table.getSortOrder().add(score);
    }

    private List<UserScore> getScoreFileList() {
        FileInputStream fis = null;
        List<UserScore> userScores = new ArrayList<>();
        File path = new File(userDir + "/Scores");
        if (path.exists() && path.listFiles().length > 0) {
            try {
                
                for (File file : path.listFiles()) {
                    fis = new FileInputStream(file);
                    ObjectInputStream oin = new ObjectInputStream(fis);
                    userScores.add((UserScore) oin.readObject());
                }
                return userScores;
            } catch (FileNotFoundException ex) {
                System.out.println("notfound");
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("no class");
            } finally {
                try {
                    fis.close();
                } catch (IOException ex) {
                }
            }
        }
        return null;

    }

    public void setDialogStage(Stage stage) {
        dialogStage = stage;
    }

    @FXML
    private void okClick(ActionEvent event) {
        dialogStage.close();
    }

}
