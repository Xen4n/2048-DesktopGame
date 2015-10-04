/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import jfx_1.BoxMap;
import static jfx_1.JFX_1.animation;

/**
 *
 * @author Pavilion
 */
public class Motion {

    private BoxMap map;
    private QueueOfDirection directQueue;
    public static EachGroupBoxMove groupQueue;
    private int cicleCount = 6;
    private int scale = 81 / cicleCount;
    private int difference = 81 - scale * cicleCount;
    private int counter = 0;
    private boolean isDifferent = false;

    public Motion(BoxMap map) {
        this.map = map;
        directQueue = new QueueOfDirection();
        groupQueue = new EachGroupBoxMove();
    }

    public void addDirection(int x, int y) {
        int[] direct = {x, y};
        directQueue.inqueue(direct);
    }

    public void processOfMoving() {
        if (counter == 0 && !directQueue.empty()) {
            int[] xy = directQueue.outqueue();
            isDifferent = map.moveCellsInGrid(xy[0], xy[1]);
            directQueue.clearFirst();
        }
        if (isDifferent) {
            int groupCount = groupQueue.getCount();

            for (int i = 0; i < groupCount; i++) {
                map.moveCell(groupQueue.outqueue(i), scale + (counter == cicleCount - 1 ? difference : 0));
            }
            counter++;

            if (counter >= cicleCount) {
                counter = 0;
                map.confirmMoving(groupQueue.clearFirst());
                map.addItem();
            }

            if (groupQueue.empty() && directQueue.empty()) {
                animation.stop();
            }
        }


    }

    public void reset() {
        directQueue = new QueueOfDirection();
        groupQueue = new EachGroupBoxMove();
    }

    public boolean isWorking() {
        return !directQueue.empty();
    }

}
