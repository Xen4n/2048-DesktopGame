/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfx_1;

import controllers.RatingController;
import controllers.LoginController;
import controllers.EndgameController;
import controllers.FrameController;
import animation.Motion;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Pavilion
 */
public class JFX_1 extends Application {

    public static BoxMap map = new BoxMap();
    private static boolean ENDGAME = false;
    public static UserInfo userInfo;
    public static AnimationTimer animation;
    public static Motion motion;
    private static double x = 0, y = 0;
    private static Stage stage;
    private static Scene scene;
    private static Effect sceneDefaultEffect;

    @Override
    public void start(Stage stage2) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/frame.fxml"));
        stage = stage2;
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles/boxGreen.css").toExternalForm());

        motion = new Motion(map);
        animation = new AnimationTimer() {
            private boolean launched = false;

            @Override
            public void handle(long now) {
                motion.processOfMoving();

            }

            @Override
            public void start() {
                if (!launched) {
                    super.start();
                }
                launched = true;
            }

            @Override
            public void stop() {
                super.stop();
                launched = false;
            }
        };

        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (ENDGAME) {
                endGameForUser();
                ENDGAME = false;
            } else {
                switch (event.getCode()) {
                    case UP:
                        motion.addDirection(0, -1);
                        break;
                    case DOWN:
                        motion.addDirection(0, 1);
                        break;
                    case LEFT:
                        motion.addDirection(-1, 0);
                        break;
                    case RIGHT:
                        motion.addDirection(1, 0);
                        break;
                    default:
                        break;
                }

                animation.start();
            }
        });

        scene.setOnMouseReleased((MouseEvent event) -> {
            x = 0;
            y = 0;
        });
        scene.setOnMouseDragged((MouseEvent event) -> {
            if (x != 0 && y != 0) {
                stage.setX(stage.getX() + event.getScreenX() - x);
                stage.setY(stage.getY() + event.getScreenY() - y);
            }
            x = event.getScreenX();
            y = event.getScreenY();
        });
        scene.setFill(null);
        stage.setScene(scene);
        stage.setTitle("2048 puzzle");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        userInfo = new UserInfo();
        sceneDefaultEffect = scene.getRoot().getEffect();
        startGameForUser();
    }

    public static Stage getStage() {
        return stage;
    }

    public static Scene getScene() {
        return scene;
    }

    public static void startGameForUser() {
        if (!userInfo.ifOpenLastTemp()) {
            changeBackBlur();
            userInfo.setName(showLoginDialog());
            changeBackBlur();
        }
        userInfo.initUser();
        FrameController.control.goAction();
    }

    public static void endGameForUser() {
        changeBackBlur();
        showEndDialog();
        changeBackBlur();
    }

    public static void showRatingForUser() {
        changeBackBlur();
        showRatingDialog();
        changeBackBlur();
    }

    private static void changeBackBlur() {
        if (scene.getRoot().getEffect() == sceneDefaultEffect) {
            scene.getRoot().setEffect(new GaussianBlur());
        } else {
            scene.getRoot().setEffect(sceneDefaultEffect);
        }
    }

    private static String showLoginDialog() {
        FXMLLoader loader = createDialogLoader("fxml/login.fxml");
        Scene dialogScene = createDialogScene(loader);
        Stage dialogStage = createDialogStage(dialogScene, 217, 120);
        LoginController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        dialogScene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            switch (event.getCode()) {
                case ENTER:
                    controller.okClick();
                    break;
            }
        });
        dialogStage.showAndWait();
        return controller.getName();
    }

    private static void showEndDialog() {
        FXMLLoader loader = createDialogLoader("fxml/endgame.fxml");
        Scene dialogScene = createDialogScene(loader);
        Stage dialogStage = createDialogStage(dialogScene, 217, 120);
        EndgameController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        dialogStage.showAndWait();
    }

    private static void showRatingDialog() {
        FXMLLoader loader = createDialogLoader("fxml/rating.fxml");
        Scene dialogScene = createDialogScene(loader);
        Stage dialogStage = createDialogStage(dialogScene, 297, 298);
        RatingController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        dialogStage.showAndWait();
    }

    private static FXMLLoader createDialogLoader(String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(JFX_1.class.getClassLoader().getResource(path));
        return loader;
    }

    private static Scene createDialogScene(FXMLLoader loader) {
        try {
            return new Scene((Parent) loader.load());
        } catch (IOException ex) {
            System.out.println("IO error: " + ex);
            return null;
        }
    }

    private static Stage createDialogStage(Scene dialogScene, double width, double height) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stage);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.setScene(dialogScene);
        dialogStage.setX(getModalPositionX(width));
        dialogStage.setY(getModalPositionY(height));
        return dialogStage;
    }

    public static void setEndGame() {
        ENDGAME = true;
    }

    private static double getModalPositionX(double width) {
        return stage.getX() + stage.getWidth() / 2 - width / 2;
    }

    private static double getModalPositionY(double height) {
        return stage.getY() + stage.getHeight() / 9 * 5 - height / 2;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
