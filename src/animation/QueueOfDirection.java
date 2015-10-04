/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import java.util.LinkedList;

/**
 *
 * @author Pavilion
 */
public class QueueOfDirection {

    private LinkedList<int[]> linkedQueue;

    public QueueOfDirection() {
        linkedQueue = new LinkedList<>();
    }

    public void inqueue(int[] direct) {
        linkedQueue.addLast(direct);
    }

    public int[] outqueue() {
        return linkedQueue.peekFirst();
    }

    public synchronized void clearFirst() {
        linkedQueue.removeFirst();
    }

    public boolean empty() {
        return linkedQueue.isEmpty();
    }

}
