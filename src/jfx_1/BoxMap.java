/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfx_1;

import controllers.FrameController;
import animation.Motion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Pavilion
 */
public class BoxMap implements Serializable {

    private GroupList boxMap = new GroupList();
    private int boxCount = 0;
    private int[][] grid = gridCreate();
    public boolean ENDGAME = false;

    public BoxMap() {
    }

    public int[][] gridCreate() {
        int[][] gridRezult = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };
        return gridRezult;
    }

    public int getCount() {
        return boxCount;
    }

    private int getCellMoveCount(int x, int y, int direct) {
        int freeCell = x;
        switch (direct) {
            case 1:
                for (int i = 3; i > x; i--) {
                    if (grid[y][i] == 0) {
                        freeCell = i;
                        break;
                    }
                }
                break;
            case -1:
                for (int i = 0; i < x; i++) {
                    if (grid[y][i] == 0) {
                        freeCell = i;
                        break;
                    }
                }
                break;
        }
        return (freeCell - x) * direct;
    }

    public void moveEachCell(int id, int scale, int xDirection, int yDirection) {
        int X = boxMap.get(id).getX();
        int Y = boxMap.get(id).getY();
        if (xDirection != 0) {
            int cellCount = getCellMoveCount(X, Y, xDirection);
            boxMap.get(id).setTranslateX(boxMap.get(id).getTranslateX() + scale * xDirection * cellCount);
        } else {
            int cellCount = getCellMoveCount(Y, X, yDirection);
            boxMap.get(id).setTranslateY(boxMap.get(id).getTranslateY() + scale * yDirection * cellCount);
        }
    }

    public void moveCell(int[] move, int scale) {
        int id = boxMap.findId(move[0], move[1]);
        if (move[2] != 0) {
            boxMap.get(id).setTranslateX(boxMap.get(id).getTranslateX() + scale * move[2] * move[4]);
        } else {
            boxMap.get(id).setTranslateY(boxMap.get(id).getTranslateY() + scale * move[3] * move[4]);
        }
    }

    public void confirmMoving(ArrayList<int[]> array) {
        for (int[] move : array) {
            int id = boxMap.findId(move[0], move[1]);
            int tempX = move[0] + move[2] * move[4];
            int tempY = move[1] + move[3] * move[4];
            boxMap.get(id).setPosition(tempX, tempY);
        }
        for (int[] move : array) {
            int tempX = move[0] + move[2] * move[4];
            int tempY = move[1] + move[3] * move[4];
            ArrayList<GroupPosition> dublicateArray = boxMap.getDublicateGroup(tempX, tempY);
            if (dublicateArray != null) {
                boxMap.delete(dublicateArray.get(0));
                boxMap.delete(dublicateArray.get(1));
                addBox(tempX, tempY, grid[tempY][tempX]);
            }

        }
    }

    public boolean moveCellsInGrid(int xDirection, int yDirection) {
        int[][] saveGrid = getGrid();
        if (xDirection != 0) {
            for (int i = 0; i <= 3; i++) {
                int tempPosition = xDirection > 0 ? 3 : 0;
                int tempValue = grid[i][tempPosition];
                int iteratorPosition = tempPosition - 1 * xDirection;
                do {
                    if (grid[i][iteratorPosition] != 0) {
                        if (grid[i][iteratorPosition] == tempValue) {
                            grid[i][tempPosition] += grid[i][iteratorPosition];
                            grid[i][iteratorPosition] = 0;
                            Motion.groupQueue.addToArray(iteratorPosition, i, xDirection, yDirection, xDirection * (tempPosition - iteratorPosition));
                        } else {
                            if (grid[i][tempPosition] != 0) {
                                grid[i][tempPosition - 1 * xDirection] = grid[i][iteratorPosition];
                                Motion.groupQueue.addToArray(iteratorPosition, i, xDirection, yDirection, xDirection * (tempPosition - 1 * xDirection - iteratorPosition));
                            } else {
                                grid[i][tempPosition] = grid[i][iteratorPosition];
                                Motion.groupQueue.addToArray(iteratorPosition, i, xDirection, yDirection, xDirection * (tempPosition - iteratorPosition));
                                tempPosition = tempPosition + 1 * xDirection; //для последующего правильного установления темпПозиции
                            }
                            if (iteratorPosition != tempPosition - 1 * xDirection) {
                                grid[i][iteratorPosition] = 0;
                            }
                        }
                        tempPosition = tempPosition - 1 * xDirection;
                        tempValue = grid[i][tempPosition];
                        iteratorPosition = tempPosition - 1 * xDirection;
                    } else {
                        iteratorPosition = iteratorPosition - 1 * xDirection;
                    }
                } while (xDirection > 0 ? iteratorPosition > -1 : iteratorPosition < 4);
            }
            if (!gridEquals(saveGrid, grid)) {
                Motion.groupQueue.arrayInQueue();
            }
        } else {
            for (int j = 0; j <= 3; j++) {
                int tempPosition = yDirection > 0 ? 3 : 0;
                int tempValue = grid[tempPosition][j];
                int iteratorPosition = tempPosition - 1 * yDirection;
                do {
                    if (grid[iteratorPosition][j] != 0) {
                        if (grid[iteratorPosition][j] == tempValue) {
                            grid[tempPosition][j] += grid[iteratorPosition][j];
                            grid[iteratorPosition][j] = 0;
                            Motion.groupQueue.addToArray(j, iteratorPosition, xDirection, yDirection, yDirection * (tempPosition - iteratorPosition));
                        } else {
                            if (grid[tempPosition][j] != 0) {
                                if ((tempPosition - 1 * yDirection) != iteratorPosition) {
                                    grid[tempPosition - 1 * yDirection][j] = grid[iteratorPosition][j];
                                    Motion.groupQueue.addToArray(j, iteratorPosition, xDirection, yDirection, yDirection * (tempPosition - 1 * yDirection - iteratorPosition));
                                }
                            } else {
                                grid[tempPosition][j] = grid[iteratorPosition][j];
                                Motion.groupQueue.addToArray(j, iteratorPosition, xDirection, yDirection, yDirection * (tempPosition - iteratorPosition));
                                tempPosition = tempPosition + 1 * yDirection; //для последующего правильного установления темпПозиции
                            }
                            if (iteratorPosition != tempPosition - 1 * yDirection) {
                                grid[iteratorPosition][j] = 0;
                            }
                        }
                        tempPosition = tempPosition - 1 * yDirection;
                        tempValue = grid[tempPosition][j];
                        iteratorPosition = tempPosition - 1 * yDirection;
                    } else {
                        iteratorPosition = iteratorPosition - 1 * yDirection;
                    }
                } while (yDirection > 0 ? iteratorPosition > -1 : iteratorPosition < 4);
            }
            if (!gridEquals(saveGrid, grid)) {
                Motion.groupQueue.arrayInQueue();
            }
        }

        return !gridEquals(saveGrid, grid);
    }

    private boolean gridEquals(int[][] gridA, int[][] gridB) {
        boolean check = true;
        for (int i = 0; i < gridA.length; i++) {
            for (int j = 0; j < gridA[0].length; j++) {
                check &= gridA[i][j] == gridB[i][j];
            }
        }
        return check;
    }

    public void setGrid(int[][] grid) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.grid[i][j] = grid[i][j];
            }
        }
    }

    public int[][] getGrid() {
        int[][] tempGrid = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                tempGrid[i][j] = grid[i][j];
            }
        }
        return tempGrid;
    }

    public void addItem() {
        if (isFree()) {
            Random rand = new Random();
            boolean check = false;
            int X;
            int Y;
            do {
                X = rand.nextInt(4);
                Y = rand.nextInt(4);
                if (grid[Y][X] == 0) {
                    check = true;
                }
            } while (!check);
            int chislo;
            if (boxCount > 2) {
                if (rand.nextInt(100) <= 69) {
                    chislo = 2;
                } else {
                    chislo = 4;
                }
                addBox(X, Y, chislo);
            } else {
                chislo = 2;
                addBox(X, Y, 2);
            }
            grid[Y][X] = chislo;
            //showGrid();
            if (isNotWay()) {
                System.out.println("--------ENDGAME--------");
                JFX_1.setEndGame();
            }
        } else {
            System.out.println("Свободных ячеек нет");

        }
    }

    private boolean isFree() {
        boolean check = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] == 0) {
                    check &= false;
                }
            }
        }
        return !check;
    }

    public boolean isNotBuzy(int[][] grid) {
        boolean check = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] != 0) {
                    check &= false;
                }
            }
        }
        return check;
    }

    private boolean isNotWay() {
        boolean check = !isFree();
        int[][] newgrid = gridModificate();
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                int a = newgrid[i][j];
                if (a == newgrid[i - 1][j] || a == newgrid[i + 1][j] || a == newgrid[i][j - 1] || a == newgrid[i][j + 1]) {
                    check &= false;
                }
            }
        }
        return check;
    }

    public void showGrid() {
        for (int i = 0; i < 4; i++) {
            System.out.print("[");
            for (int j = 0; j < 4; j++) {
                System.out.print(grid[i][j] + ",");
            }
            System.out.println("]");
        }
        System.out.println("\n");
    }

    public Group get(int nomer) {
        return boxMap.get(nomer);
    }

    public void addBox(int x, int y, int nomer) {
        GroupPosition box = new GroupPosition(++boxCount);
        Rectangle rect = new Rectangle(75, 75);
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        rect.getStyleClass().add("box" + nomer);
        Label number = new Label(nomer + "");
        number.getStyleClass().add("number" + nomer);
        switch (nomer) {
            case 2:
                setNumberProperties(number, 40, 11, 24);
                break;
            case 4:
                setNumberProperties(number, 40, 11, 24);
                break;
            case 8:
                setNumberProperties(number, 40, 11, 24);
                break;
            case 16:
                setNumberProperties(number, 40, 11, 16);
                break;
            case 32:
                setNumberProperties(number, 40, 11, 16);
                break;
            case 64:
                setNumberProperties(number, 40, 11, 16);
                break;
            case 128:
                setNumberProperties(number, 40, 11, 6);
                break;
            case 256:
                setNumberProperties(number, 40, 11, 7);
                break;
            case 512:
                setNumberProperties(number, 38, 12, 8);
                break;
            case 1024:
                setNumberProperties(number, 32, 16, 4);
                break;
            case 2048:
                setNumberProperties(number, 32, 16, 4);
                break;
            case 4096:
                setNumberProperties(number, 32, 16, 4);
                break;
            case 8192:
                setNumberProperties(number, 32, 16, 4);
                break;
            case 16384:
                setNumberProperties(number, 28, 18, 1);
                break;
        }
        box.getChildren().add(rect);
        box.getChildren().add(number);
        box.setNewPosition(x, y);
        boxMap.put(box);
        FrameController.boxPane.getChildren().add(boxMap.get(boxCount));
        JFX_1.userInfo.upScore(nomer);
    }

    private void setNumberProperties(Label number, int size, int Y, int X) {
        number.setFont(Font.font("Vrinda", FontWeight.BOLD, FontPosture.REGULAR, size));
        number.setTranslateY(Y);
        number.setTranslateX(X);
    }

    private int[][] gridModificate() {
        int[][] gridMod = new int[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (i > 0 && i < 5 && j > 0 && j < 5) {
                    gridMod[i][j] = grid[i - 1][j - 1];
                } else {
                    gridMod[i][j] = 0;
                }
            }
        }
        return gridMod;
    }

    public int getMaximumNumberOfCell() {
        int maxcell = grid[0][0];
        for (int[] line : grid) {
            for (int cell : line) {
                if (cell > maxcell) {
                    maxcell = cell;
                }
            }
        }
        return maxcell;
    }

    public void clearAll() {
        boxMap.deleteAll();
        boxCount = 0;
        JFX_1.motion.reset();
        FrameController.boxPane.getChildren().clear();
        grid = gridCreate();
    }

}
