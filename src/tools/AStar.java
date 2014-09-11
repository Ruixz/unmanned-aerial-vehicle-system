/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author skipper
 */
public class AStar {
    
    final int COST_STRAIGHT = 10;       //vertical and parallel path estimate cost
    final int COST_DIAGONAL = 14;       //diagonal path estimate cost
    //some map attribute value
    
    //pass passBound table
    public static final int PASS = 0;
    public static final int BREEZE = 1;
    public static final int STORMEDGE = 2;
    public static final int WIND = 3;
    public static final int STORM = 4;
    public static final int STORMCENTER = 5;
    public static final int MOUNTAIN = 6;
    
    private int[][] map;        //map(refer to pass table)
    private List<Node> openList;        //open list
    private List<Node> closeList;       //close list
    
    private List<Node> currentList;
    
    private int row;
    private int column;
    
    public AStar(int[][] map,int row,int column){
        this.map=map;
        
        this.row=row;
        this.column=column;
        openList=new ArrayList<>();
        closeList=new ArrayList<>();
        currentList = new ArrayList<>();
    }
    
    //search for coordinate (-1:error, 0:miss, 1:find)
    public int search(int x1,int y1,int x2,int y2, int passBound){
        openList.removeAll(openList);
        closeList.removeAll(closeList);
        
        if(x1<0||x1>=row||x2<0||x2>=row||y1<0||y1>=column||y2<0||y2>=column){
            return -1;
        }
        if(map[x1][y1]==0||map[x2][y2]==0){
            return -1;
        }
        Node sNode=new Node(x1,y1,null);
        Node eNode=new Node(x2,y2,null);
        openList.add(sNode);
        List<Node> resultList=search(sNode, eNode, passBound);
        if(resultList.isEmpty()){
            return 0;
        }
        for(Node node:resultList){
            map[node.getX()][node.getY()]=-1;
        }
        return 1;
    }
    
    //check if the path can connect
    private boolean checkPath(int x,int y,Node parentNode,Node eNode,int cost, int passBound){
        Node node=new Node(x, y, parentNode);
        //check if it can pass in the map
        if(map[x][y]>passBound){
            closeList.add(node);
            return false;
        }
        //check if the closeList contains the node
        if(isListContains(closeList, x, y)!=-1){
            return false;
        }
        //check if the openList contains the node
        int index=isListContains(openList, x, y);
        if(index!=-1){
            //check if there are better G, update G, F
            if((parentNode.getG()+cost)<openList.get(index).getG()){
                node.setParentNode(parentNode);
                countG(node, cost);
                countH(node, eNode);
                countF(node);
                openList.set(index, node);
            }
        }else{
            //add node into openList
            node.setParentNode(parentNode);
            count(node, eNode, cost);
            openList.add(node);
        }
        return true;
    }
    
    //check if the list contains the specified element(return index of list;otherwise return -1)
    private int isListContains(List<Node> list,int x,int y){
        for(int i=0;i<list.size();i++){
            Node node=list.get(i);
            if(node.getX()==x&&node.getY()==y){
                return i;
            }
        }
        return -1;
    }
    
    //get the path
    private void getPath(List<Node> resultList,Node node){
        if(node.getParentNode()!=null){
            getPath(resultList, node.getParentNode());
        }
        resultList.add(node);
    }
    
    //calculate F,G,H
    private void count(Node node,Node eNode,int cost){
        countG(node, cost);
        countH(node, eNode);
        countF(eNode);
    }
    
    //calculate G
    private void countG(Node node,int cost){
        if(node.getParentNode()==null){
            node.setG(cost);
        }else{
            node.setG(node.getParentNode().getG()+cost);
        }
    }
    
    //calculate H(Manhattan distance)
    private void countH(Node node,Node eNode){
        node.setH(Math.abs(node.getX()-eNode.getX())+Math.abs(node.getY()-eNode.getY()));
    }
    
    //calculate F
    private void countF(Node node){
        node.setF(node.getG()+node.getH());
    }
    
    //A* search path
    public List<Node> search(Node sNode,Node eNode, int passBound){
        openList.removeAll(openList);
        closeList.removeAll(closeList);
        System.out.println("search begin");
        
        sNode.setParentNode(null);
        
        List<Node> resultList=new ArrayList<>();
        boolean isFind=false;
        openList.add(sNode);
        Node node=null;
        while(openList.size()>0){
            //get the smallest F in openList
            node=openList.get(0);
            //check if find the end
            if(node.getX()==eNode.getX()&&node.getY()==eNode.getY()){
                isFind=true;
                break;
            }
            if((node.getY()-1)>=0){
                checkPath(node.getX(),node.getY()-1,node, eNode, COST_STRAIGHT, passBound);
            }
            if((node.getY()+1)<column){
                checkPath(node.getX(),node.getY()+1,node, eNode, COST_STRAIGHT, passBound);
            }
            if((node.getX()-1)>=0){
                checkPath(node.getX()-1,node.getY(),node, eNode, COST_STRAIGHT, passBound);
            }
            if((node.getX()+1)<row){
                checkPath(node.getX()+1,node.getY(),node, eNode, COST_STRAIGHT, passBound);
            }
            if((node.getX()-1)>=0&&(node.getY()-1)>=0){
                checkPath(node.getX()-1,node.getY()-1,node, eNode, COST_DIAGONAL, passBound);
            }
            if((node.getX()-1)>=0&&(node.getY()+1)<column){
                checkPath(node.getX()-1,node.getY()+1,node, eNode, COST_DIAGONAL, passBound);
            }
            if((node.getX()+1)<row&&(node.getY()-1)>=0){
                checkPath(node.getX()+1,node.getY()-1,node, eNode, COST_DIAGONAL, passBound);
            }
            if((node.getX()+1)<row&&(node.getY()+1)<column){
                checkPath(node.getX()+1,node.getY()+1,node, eNode, COST_DIAGONAL, passBound);
            }
            //remove from openList, add to closeList
            closeList.add(openList.remove(0));
            //sort the collection
            Collections.sort(openList, new NodeFComparator());
        }
        if(isFind){
            getPath(resultList, node);
        }

        currentList = resultList;
        
        return resultList;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int[][] getMap() {
        return map;
    }
    
    public void obstacleOccur(int x, int y, int eventRate){
        if((x<0 || x>=row)||(y<0 || y>=column)||map[x][y]==MOUNTAIN)
            return;
        map[x][y] = eventRate;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }
    

}
