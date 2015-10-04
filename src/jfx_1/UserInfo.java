/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfx_1;

import controllers.FrameController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author тереховва
 */
public class UserInfo implements Serializable {

    private String name;
    private int score;
    private int[][] grid;
    private final String userDir = System.getProperty("user.dir");

    public UserInfo() {
        name = "";
        score = 0;
    }

    public void initUser() {
        UserInfo lastUser = openTempInfo();
        if (lastUser != null) {
            setGrid(lastUser.getGrid());
            setScore(lastUser.getScore());
        } else {
            setGrid(JFX_1.map.gridCreate());
            setScore(0);
        }
    }

    public void setName(String name) {
        this.name = name;
        FrameController.setName(this.name);
    }

    public void setScore(int num) {
        this.score = num;
        FrameController.setScore(this.score);
    }

    public void upScore(int iterate) {
        score += iterate;
        FrameController.setScore(this.score);
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
        JFX_1.map.setGrid(grid);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int[][] getGrid() {
        return grid;
    }

    public boolean ifOpenLastTemp() {
        File path = new File(userDir + "/Saves");
        File[] files;
        if (path.exists()) {
            files = path.listFiles();
            if (files.length != 0) {
                File lastF = files[0];
                for (File f : files) {
                    if (f.lastModified() > lastF.lastModified()) {
                        lastF = f;
                    }
                }
                String lastName = open(lastF.getAbsolutePath()).getName();
                if (lastName.equals(name)) {
                    return false;
                } else {
                    setName(lastName);
                    return true;
                }      
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public final UserInfo openTempInfo() {
        File file = new File(userDir + "/Saves/temp" + name + ".save");
        if (file.exists()) {
            return open(file.getAbsolutePath());
        } else {
            return null;
        }
    }

    public void saveTempInfo() {
        File fileDir = new File(userDir + "/Saves");
        fileDir.mkdirs();
        save(fileDir.getAbsolutePath() + "/temp" + name + ".save");
    }

    public void save(String filename) {
        grid = JFX_1.map.getGrid();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
            System.out.println("Файл для " + name + " создан");
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } finally {
            try {
                fos.close();
            } catch (IOException ex) {

            }
        }
    }

    private UserInfo open(String filename) {
        FileInputStream fis = null;
        UserInfo user = null;
        try {
            fis = new FileInputStream(filename);
            ObjectInputStream oin = new ObjectInputStream(fis);
            user = (UserInfo) oin.readObject();
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
        return user;
    }

    public void deleteTempFile() {
        File fileDir = new File(userDir + "/Saves");
        fileDir.mkdirs();
        File file = new File(fileDir.getAbsolutePath() + "/temp" + name + ".save");
        if (file.exists()) {
            file.delete();
        }
    }

//    public void showGrid() {
//        for (int i = 0; i < 4; i++) {
//            System.out.print("[");
//            for (int j = 0; j < 4; j++) {
//                System.out.print(grid[i][j] + ",");
//            }
//            System.out.println("]");
//        }
//        System.out.println("\n");
//    }
}
