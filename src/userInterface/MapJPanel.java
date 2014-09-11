/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import javax.swing.ImageIcon;
import model.Airplane;
import model.City;
import tools.AStar;
import tools.Event;
import tools.FlightPath;
import tools.Node;

/**
 *
 * @author skipper
 */
public class MapJPanel extends javax.swing.JPanel {

    /**
     * Creates new form MapJPanel
     */
    int GWIDTH = 700,
        GHEIGHT = 700,
        XPADDING = 0, //mapArray x index direction
        YPADDING = 0, //mapArray y index direction
        CELL = 14,  // Every block's side lenghth
        HALFCELL = 7,
            YTARGET = -10,//ajust pixcl for start&target image y index in map
            XTARGET = -8, //ajust pixcl for start&target image x index in map
            YPLANE = -38,
            XPLANE = -28;
    
    Dimension screenSize = new Dimension(GWIDTH, GHEIGHT);
    Image dbImage;
    Graphics dbg;            
    
    private AStar map;
    private List<City> cities;
    
    private List<Airplane> flyingPlane = new ArrayList<>();
    
    private Image planeImage;
    private Image mapImage;
    private Image stormImage;
    private Image startImage;
    private Image targetImage;
    
    public MapJPanel(AStar map, List<City> cities) {
        initComponents();
        this.map = map;   
        this.cities = cities;
        ImageIcon i = new ImageIcon("..\\Final6205_RuiSong\\pic\\bigplane.png");
        ImageIcon imap = new ImageIcon("..\\Final6205_RuiSong\\pic\\map.jpg");
        ImageIcon istorm = new ImageIcon("..\\Final6205_RuiSong\\pic\\storm.png");
        ImageIcon istart = new ImageIcon("..\\Final6205_RuiSong\\pic\\start.png");
        ImageIcon itarget = new ImageIcon("..\\Final6205_RuiSong\\pic\\target.png");
        planeImage = i.getImage(); 
        mapImage = imap.getImage();
        stormImage = istorm.getImage();
        startImage = istart.getImage();
        targetImage = itarget.getImage();
    }
    
    //Luanch plane
    public void startTimer(Node start, Node end, int passBound){
        System.out.println("passbound2:"+passBound);
        if(passBound==2){
            Airplane plane = new Airplane(start, end,  passBound, 
                new ImageIcon("..\\Final6205_RuiSong\\pic\\bigplane.png"), map);
            System.out.print("Plane takes off at"+start +"destination"+end);
            flyingPlane.add(plane);
        }else if(passBound==0){
            Airplane plane = new Airplane(start, end,  passBound, 
                new ImageIcon("..\\Final6205_RuiSong\\pic\\smallplane.png"), map);
            System.out.print("Plane takes off at"+start +"destination"+end);
            flyingPlane.add(plane);
        }
    }
    
    public void breezeEvent(){
        for(Airplane planeE: flyingPlane){
            FlightPath fp = planeE.getFuturePath();
            Event.oneCellEvent(fp, map, AStar.BREEZE);
        }        
    }
    
    public void windEvent(){
        for(Airplane planeE: flyingPlane){
            FlightPath fp = planeE.getFuturePath();
            Event.oneCellEvent(fp, map, AStar.WIND);
        }        
    }
    
    @Override
    public void paint(Graphics g){
        dbImage = createImage(this.getWidth(), this.getHeight());
        dbg = dbImage.getGraphics();
        draw(dbg);
        g.drawImage(dbImage, 0, 0, this);
        
    }
    
    public void draw(Graphics g){
        //draw background
        g.drawImage(mapImage, 0, 0, this);
        //draw map obstacle        
        int[][] mapArray = map.getMap();
        for(int i=0;i<mapArray.length;i++){
            for(int j=0;j<mapArray[i].length;j++)
                if(mapArray[i][j] == AStar.STORMEDGE){
                    //light blue
                    g.setColor(new Color(0.2f,0.5f,1.0f,0.4f));
                    g.fillRect(j*CELL + YPADDING, i*CELL + XPADDING, CELL, CELL);  
                } else  if(mapArray[i][j] == AStar.STORM){
                    g.setColor(Color.BLUE);
                    //dark blue
                    g.setColor(new Color(0.1f,0.1f,1.0f,0.8f));
                    g.fillRect(j*CELL + YPADDING, i*CELL + XPADDING, CELL, CELL);  
                }
        }
        //draw storm picture
        for(int i=0;i<mapArray.length;i++){
            for(int j=0;j<mapArray[i].length;j++)
                // below 3 means storm radius
                if(mapArray[i][j] == AStar.STORMCENTER){
                  g.drawImage(stormImage, (j-3)*CELL, (i-3)*CELL, this);  
                }
        } 
        //draw cities
        g.setColor(Color.GREEN);
        for(City c: cities){
            g.fillRect(c.getY()*CELL + YPADDING, c.getX()*CELL + XPADDING, CELL, CELL);
        }
        
        //draw planeP.getFuturePath()
        for(Airplane planeP: flyingPlane){
            if(planeP.getFuturePath() != null){ 
                try{
                    // draw initial path
                    g.setColor(Color.GRAY);
                    for(Node p: planeP.getInitialPath()){
                        g.fillOval(p.getY()*CELL + YPADDING-7, p.getX()*CELL + XPADDING, HALFCELL, HALFCELL);
                    }
                    // draw actual pass path
                    g.setColor(Color.magenta);
                    for(Node p: planeP.getThroughPath()){
                        //System.out.print(p+" ");
                        g.fillOval(p.getY()*CELL + YPADDING, p.getX()*CELL + XPADDING, HALFCELL, HALFCELL);
                    }
                    
                    // draw current plan path
                    g.setColor(Color.CYAN);
                    for(Node p: planeP.getCurrentpathList()){
                        g.fillOval(p.getY()*CELL + YPADDING, p.getX()*CELL + XPADDING, HALFCELL, HALFCELL);
                    }
                }catch(ConcurrentModificationException e){
                    System.out.println("ConcurrentModificationException occurs, current node:" + planeP.getCurrentNode());
                }
            }
            // draw start point
            if(planeP.getStart() != null) {
                Node START = planeP.getStart();
                g.setColor(Color.ORANGE);
                g.fillRect(START.getY()*CELL + YPADDING, START.getX()*CELL + XPADDING, CELL, CELL);
                //g.drawImage(startImage,START.getY()*CELL + YPADDING + YTARGET, START.getX()*CELL + XPADDING + XTARGET, this);  
            }         

            // draw end point
            if(planeP.getDestination() != null) {
                Node END = planeP.getDestination();
                g.setColor(Color.RED);
                g.fillRect(END.getY()*CELL + YPADDING, END.getX()*CELL + XPADDING, CELL, CELL);
                //g.drawImage(targetImage,END.getY()*CELL + YPADDING + YTARGET, END.getX()*CELL + XPADDING + XTARGET, this);
            }

            // draw plane
            if(planeP.getFuturePath() != null){
    //            if(planeP.getFuturePath()==null){
    //                CURRENT = END;
    //            } 
                Node CURRENT = planeP.getCurrentNode();
                if(CURRENT == null) return;
                g.drawImage(planeP.getPlaneImage(), CURRENT.getY()*CELL + YPADDING + YPLANE, CURRENT.getX()*CELL + XPADDING+ XPLANE, this);
            } 
        }
         
        
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
