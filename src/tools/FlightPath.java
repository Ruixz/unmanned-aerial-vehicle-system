/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author skipper
 */
public class FlightPath {
    List<Node> currentList;
    
    public FlightPath(){
        currentList = new ArrayList<>();
    }
    
    public FlightPath(List<Node> currentList){
        this.currentList = currentList;
    }

    public List<Node> getCurrentList() {
        return currentList;
    }

    public void setCurrentList(List<Node> currentList) {
        this.currentList = currentList;
    }
    
    public Node stepMove(){
        
        Node node = currentList.remove(0);
        return node;
    }
    
    public boolean isEmpty(){
        return currentList.isEmpty();
    }
    
    public int size(){
        return currentList.size();
    }
}
