/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author skipper
 */
public class City {
    private String name;
    private int x, y;
    
    public City(String name, int x, int y){
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString(){
        return name+" (" + x + ", " + y + ")";
    }
    
}
