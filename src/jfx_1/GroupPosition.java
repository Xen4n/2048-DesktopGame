/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfx_1;

import javafx.scene.Group;

/**
 *
 * @author Pavilion
 */
public class GroupPosition extends Group {
    
    private int x = 0;
    private int y = 0;
    private int id;
    
    public GroupPosition(int id){
        super();
        this.id = id;
    }
    
    public int getGroupId(){
        return id;
    }
    
    public void setNewPosition(int i, int j){
        this.setTranslateX(3);
        this.setTranslateY(-3);
        this.setTranslateX(this.getTranslateX()+(i-x)*81);
        this.setTranslateY(this.getTranslateY()+(j-y)*81);
        x = i;
        y = j;
    }
    
    public void setPosition(int i, int j){
        x = i;
        y = j;
    }
    
    public boolean isTrueCoordinate (int X, int Y){
        return x == X && y == Y;
    }
    
    public boolean equals(GroupPosition group){
        return x == group.getX() && y == group.getY();
    }
    
    public boolean equalsId(int id){
        return this.id == id;
    }
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }
    
}
