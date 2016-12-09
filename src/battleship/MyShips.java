/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.ArrayList;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class MyShips {
    private Map <String, ArrayList<Integer>> ships;
    MyShips () {
        ships = new HashMap<>();
    }

    String[] sheepTypes = {"S4","S3","S2","S1"};
    int deckCount = sheepTypes.length;
    int sheepCount = 1;
    int iter = 1;
    private String ineerDeckLocationMsg = "Succes";
    private String generalLocationsMsg = "Succes";

    public Map <String, ArrayList<Integer>> getShips() {
        return ships; 
    }
    
    public void addShip(String name, ArrayList<Integer> decks) {
        ships.put(name,new ArrayList<Integer>(decks));  
    }

    public ArrayList<String> getAllShipsDecks() {
        String[] decksTypes = {"S4","S3","S2","S1"};
        int iter = 0;
        int jiter = 0;
        int sheepCount = 1;
        int decksCount = 4;
        ArrayList<String> sheepsTypes = new ArrayList<String>(20);
        try {
            while (true) {
                if (jiter==decksCount*sheepCount) {
                    iter++;
                    sheepCount++;
                    decksCount--;
                    jiter=0;
                }
                sheepsTypes.add(decksTypes[iter]);
                jiter++;
            }
        }catch(Exception ex) {return sheepsTypes;}
    }
  
    public void clear() {
        ships.clear();
    }
}
