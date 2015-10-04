/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfx_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xenan
 */
public class UserScore implements Serializable {

    private final String name;
    private final int score;
    private final int maximum;

    public UserScore(String name, int score, int max) {
        this.name = name;
        this.score = score;
        this.maximum = max;
    }

    public void saveToFile() {
        File fileDir = new File(System.getProperty("user.dir") + "/Scores");
        fileDir.mkdirs();
        String tempname = name;
        File tempfile;
        int iterator = 1;
        do {
            tempfile = new File(fileDir.getAbsolutePath() + "/" + tempname + ".score");
            if (tempfile.exists()) {
                tempname = name + iterator++;
            } else {
                break;
            }
        } while (true);
        save(fileDir.getAbsolutePath() + "/" + tempname + ".score");
    }

    private void save(String filename) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(this);
                oos.flush();
            }
            System.out.println("Файл Score для " + name + " создан");
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
            }
        }
    }

    public StringProperty getName() {
        return new SimpleStringProperty(name);
    }
    
    public int getScore(){
        return score;
    }
    public int getMaximum(){
        return maximum;
    }
}
