/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import tools.AStar;

/**
 *
 * @author skipper
 */
public class Configuration {
    private static Path mapSrcPath = Paths.get("..\\Final6205_RuiSong\\data\\mapSrc.txt");
    private static Path citySrcPath = Paths.get("..\\Final6205_RuiSong\\data\\citySrc.txt");
    
     public Configuration() {
    }
    
    public static AStar initMap(){
        StringTokenizer st;
        Charset charset = Charset.forName("US-ASCII");
        AStar map = null;
        // Read and set Map initial data
        try(BufferedReader reader = Files.newBufferedReader(mapSrcPath, charset);){
            //read the first line for the vertice Number in the file
            String line = reader.readLine();
            st = new StringTokenizer(line);
            int ROW = Integer.parseInt(st.nextToken());
            int COLUMN = Integer.parseInt(st.nextToken()); 
//            System.out.println("R: " + ROW + "; C: "+ COLUMN); //for test purpose only
            int[][] mapArray = new int[ROW][COLUMN];
            
            //read the following and initialize map
            for(int i = 0; i < ROW; i++){
                line = reader.readLine();
                st = new StringTokenizer(line);
                for(int j = 0; j < COLUMN; j++){
                    String temp = st.nextToken();
                    mapArray[i][j] = Integer.parseInt(temp); 
                }
//                System.out.println(""); //test
            }
//            System.out.println("MapArray end"); //test
            map = new AStar(mapArray, ROW, COLUMN);
            
        } catch (Exception e) {
            //remain question: not understand about this exeption
            e.printStackTrace();    
        }        
        return map;
    }
    
    // initialize cities 
    public static List<City> initCities(){
        StringTokenizer st;
        Charset charset = Charset.forName("US-ASCII");
        List<City> cities = new ArrayList<>();
        String line;
        // Read and set Cities initial data
        try(BufferedReader reader = Files.newBufferedReader(citySrcPath, charset);){
            //read the data for each city and initiate them in application
            while((line = reader.readLine()) != null){
                st = new StringTokenizer(line);
                String cname = st.nextToken();
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                City tempCity = new City(cname, x, y);
                cities.add(tempCity);
            }
            //read the following and initialize map
            
            
        } catch (Exception e) {
            //remain question: not understand about this exeption
            e.printStackTrace();    
        }

        //test
//        for(City c: cities){
//            System.out.println(c);
//        }
        return cities;
    }
    
    //config test
    public static void main(String[] args){
        Configuration.initCities();
    }
}
