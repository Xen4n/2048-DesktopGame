/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Pavilion
 */
public class EachGroupBoxMove {
    private LinkedList<ArrayList<int[]>> linkedQueue;
    private ArrayList<int[]> array;
    
    public EachGroupBoxMove() {
        linkedQueue = new LinkedList<>();
        array = new ArrayList<>();
    }

    public void addToArray(int x, int y, int xDirect, int yDirect, int cellCount){
        int[] move = {x,y,xDirect,yDirect,cellCount};
        array.add(move);
    }
    
    public void arrayInQueue() {
        linkedQueue.addLast(array);
        array = new ArrayList<>();
    }

    public int[] outqueue(int id) {
        return linkedQueue.peekFirst().get(id);
    }

    public synchronized ArrayList<int[]> clearFirst() {
        return linkedQueue.removeFirst();
    }

    public int getCount(){
        
        return linkedQueue.isEmpty()?0:linkedQueue.peekFirst().size();
    }
    
    public boolean empty() {
        return linkedQueue.isEmpty();
    }
    
}
