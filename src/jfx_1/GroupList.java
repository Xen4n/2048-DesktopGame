/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfx_1;

import controllers.FrameController;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Xenan
 */
public class GroupList {

    private LinkedList<GroupPosition> list;

    public GroupList() {
        list = new LinkedList<>();
    }

    public GroupPosition get(int id) {
        ListIterator<GroupPosition> iter = list.listIterator();
        while (iter.hasNext()) {
            GroupPosition next = iter.next();
            if (next.equalsId(id)) {
                return next;
            }
        }
        return null;
    }

    public int findId(int x, int y) {
        ListIterator<GroupPosition> iter = list.listIterator();
        while (iter.hasNext()) {
            GroupPosition next = iter.next();
            if (next.isTrueCoordinate(x, y)) {
                return next.getGroupId();
            }
        }
        return 0;
    }

    public ArrayList<GroupPosition> getDublicateGroup(int x, int y) {
        ArrayList<GroupPosition> array = new ArrayList<>();
        ListIterator<GroupPosition> iter = list.listIterator();
        while (iter.hasNext() && (array.size() != 2)) {
            GroupPosition next = iter.next();
            if (next.isTrueCoordinate(x, y)) {
                array.add(next);
            }
        }
        if (array.size() == 2) {
            return array;
        } else {
            return null;
        }
    }

    public void delete(GroupPosition group){
        FrameController.boxPane.getChildren().remove(group);
        list.remove(group);
    }
    
    public void put(GroupPosition group) {
        list.addLast(group);
    }
    
    public void deleteAll(){
        list.clear();
    }
}
