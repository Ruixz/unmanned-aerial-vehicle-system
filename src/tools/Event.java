/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.Random;

/**
 *
 * @author skipper
 */
public class Event {
    
    //one cell effect(wind: strong wind or breeze)
    public static void oneCellEvent(FlightPath futurePath, AStar map, int eventRate){
        int select = 2;
        if(futurePath.getCurrentList().size() <= 3){
            System.out.println("current path is too short");
            return;
        }
        
        int randomRow = futurePath.getCurrentList().get(select).getX();
        int randomColumn = futurePath.getCurrentList().get(select).getY();
        
        map.obstacleOccur(randomRow, randomColumn, eventRate);
    }
    
    //multiple cell effect(storm)
    public static void multiCellEvent(AStar map){
        
        Random rand = new Random();
        
        int randomX = rand.nextInt(map.getRow());
        int randomY = rand.nextInt(map.getColumn());
        int a[][] = map.getMap();
        while(a[randomX][randomY] ==AStar.MOUNTAIN){
            randomX = rand.nextInt(map.getRow());
            randomY = rand.nextInt(map.getColumn());
        }
        
        //storm row 1: (3,0)
        int relativeRow = 3;
        map.obstacleOccur(randomX+relativeRow, randomY, AStar.STORMEDGE);
        //storm row 2: (2,-1),(2,0),(2,1)
        relativeRow = 2;
        for(int i = -1;i < 2; i++){
            map.obstacleOccur(randomX+relativeRow, randomY+i, AStar.STORMEDGE);
        }
        map.obstacleOccur(randomX+relativeRow, randomY, AStar.STORM);
        //storm row 3: (1,-2),(1,-1),(1,0),(1,1),(1,2)
        relativeRow = 1;
        for(int i = -1;i < 2; i++){
            map.obstacleOccur(randomX+relativeRow, randomY+i, AStar.STORM);
        }
        map.obstacleOccur(randomX+relativeRow, randomY-2, AStar.STORMEDGE);
        map.obstacleOccur(randomX+relativeRow, randomY+2, AStar.STORMEDGE);
        //storm row 4: (0,-3),(0,-2),(0,-1),(0,0),(0,1),(0,2),(0,3)
        relativeRow = 0;
        for(int i = -2;i < 3; i++){
            map.obstacleOccur(randomX+relativeRow, randomY+i,AStar.STORM);
        }
        map.obstacleOccur(randomX, randomY, AStar.STORMCENTER);
        map.obstacleOccur(randomX+relativeRow, randomY+3,AStar.STORMEDGE);
        map.obstacleOccur(randomX+relativeRow, randomY-3,AStar.STORMEDGE);
        //storm row 5: (-1,-2),(-1,-1),(-1,0),(-1,1),(-1,2)
        relativeRow = -1;
        for(int i = -1;i < 2; i++){
            map.obstacleOccur(randomX+relativeRow, randomY+i,AStar.STORM);
        }
        map.obstacleOccur(randomX+relativeRow, randomY+2,AStar.STORMEDGE);
        map.obstacleOccur(randomX+relativeRow, randomY-2,AStar.STORMEDGE);
        //storm row 6: (-2,-1),(-2,0),(-2,1)
        relativeRow = -2;
        for(int i = -1;i < 2; i++){
            map.obstacleOccur(randomX+relativeRow, randomY+i,AStar.STORMEDGE);
        }
        map.obstacleOccur(randomX+relativeRow, randomY,AStar.STORM);
        //storm row 7: (-3,0)
        relativeRow = -3;
        map.obstacleOccur(randomX+relativeRow, randomY, AStar.STORMEDGE);      
    }
    
    //clear all effect of event
    public static void clearEvent(AStar map){
        int a[][] = map.getMap();
        for(int i=0;i<a.length;i++)
            for(int j=0;j<a[i].length;j++)
                if(a[i][j]!=6)
                    a[i][j] = 0;
        map.setMap(a);
    }
    
    
}
