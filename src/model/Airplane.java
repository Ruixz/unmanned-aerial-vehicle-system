/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import javax.swing.ImageIcon;
import tools.AStar;
import tools.FlightPath;
import tools.Node;

/**
 *
 * @author skipper
 */
public class Airplane {
    private Node start;     // Start point
    private Node destination;   // Destination point
    private int passBound;   // the biggest pass code
                            // 0 for small plane, 2 for big palne
    private Timer timer;    // How to implement timer in airplane
    private Image planeImage;
    private FlightPath futurePath;
//    private FlightPath futurePath;
    private List<Node> throughPath = new ArrayList<>();
    private List<Node> initialPath = new ArrayList<>();
    private AStar map;
    private Node currentNode;

    public Airplane(Node start, Node destination, int passBound, ImageIcon iplane, AStar map) {
        this.start = start;
        this.destination = destination;
        this.passBound = passBound;
        this.planeImage = iplane.getImage();
        this.map = map;
        this.futurePath = new FlightPath(map.search(start, destination, passBound));
        initialPath = map.search(start, destination, passBound);
        //test code
        System.out.println("Map start input:"+start+"destination input:"+destination);
        for(Node n: initialPath){
            System.out.print(n);
        }
        //test code
        System.out.println();
        System.out.println("Plane constructed before timer");
        Timer timer = new Timer();
        timer.schedule(new Fly(), 0, 1000);
    }
    
    private class Fly extends java.util.TimerTask{
        @Override
        public void run() {
            System.out.println("Timer run");
            if(futurePath.getCurrentList().isEmpty()) return;
            //check next step's status;
            int nextNodeInPathIndex = 1;
            int currentPlaneNodeIndex = 0;
            // How to stop timer?
            if(futurePath.isEmpty()) return;
            currentNode = futurePath.getCurrentList().get(currentPlaneNodeIndex);      //modify

            if(futurePath.size() == 1) return; 
            int x = futurePath.getCurrentList().get(nextNodeInPathIndex).getX();
            int y = futurePath.getCurrentList().get(nextNodeInPathIndex).getY();
            if(map.getMap()[x][y] > passBound){

                futurePath.setCurrentList(map.search(currentNode, destination, passBound));

                System.out.println("futurePath second search result:" + currentNode);

                for(Node n: futurePath.getCurrentList()){
                    System.out.println(n);
                }
                System.out.println("test end:");
            }
            
            Node passNode = futurePath.stepMove();
            throughPath.add(passNode);
        }        
    }
    // Set future futurePath every time have a new future futurePath from shortest futurePath algorithm
    
    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public int getPassBound() {
        return passBound;
    }

    public void setPassBound(int passBound) {
        this.passBound = passBound;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public Image getPlaneImage() {
        return planeImage;
    }

    public void setPlaneImage(Image planeImage) {
        this.planeImage = planeImage;
    }

    public FlightPath getFuturePath() {
        return futurePath;
    }

    public void setFuturePath(FlightPath futurePath) {
        this.futurePath = futurePath;
    }

    public List<Node> getThroughPath() {
        return throughPath;
    }

    public void setThroughPath(List<Node> throughPath) {
        this.throughPath = throughPath;
    }

    public List<Node> getInitialPath() {
        return initialPath;
    }

    public void setInitialPath(List<Node> initialPath) {
        this.initialPath = initialPath;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }
    public List<Node> getCurrentpathList(){
        if(futurePath == null) return null;
        else return futurePath.getCurrentList();
    }
}
