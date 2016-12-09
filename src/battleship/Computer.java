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
import javax.swing.JButton;

public class Computer {
    private int[] generalLocation;
    private int counter = 0;
    static Random rand = new Random();
    String[] shipsNames = {"AircraftCarrier(4)","First BattleShip(3)","Second BattleShip(3)","First Cruiser(2)",
                           "Second Cruiser(2)","Third Cruiser(2)","First Destroyer(1)","Second Destroyer(1)",
                           "Third Destroyer(1)","Fourth Destroyer(1)"};
    Map <String, ArrayList<Integer>> Ships;
    public Computer(){createGeneralLocation();}
    
    public Map <String, ArrayList<Integer>> getShips() {
        return Ships;
    }
    
    private void createGeneralLocation() {
        generalLocation = new int[100];
        for (int i = 0; i < 100; i++) {
            this.generalLocation[i] = i;
        }
    }
    
    private void closeLocationElements(Integer elem) {
        try {
            this.generalLocation[elem] = -5;
            this.generalLocation[elem+1] = -5;
            this.generalLocation[elem-1] = -5;
            this.generalLocation[elem+10] = -5;
            this.generalLocation[elem-10] = -5;
        } catch (ArrayIndexOutOfBoundsException ex) {}//System.out.println("close location false");
                    //ex.printStackTrace();}//System.out.println("cloese elem is "+elem);}
        }
           
    
    private boolean checkDeck(int deck) {
        try {
            if (this.generalLocation[deck] < 0){
                //System.out.println("checkDeck "+deck+" false");
                return false;
            }        
        } catch (Exception ex) {return false;}
    return true;
    }
    
    private int getNextOperation() {
        int[] operations = {1,-1,10,-10};
    return operations[rand.nextInt(operations.length)];
    }
    
    private ArrayList<Integer> createDecks(int decksCount) {
        ArrayList<Integer> sheep = new ArrayList<Integer>(decksCount);
        int deck = rand.nextInt(this.generalLocation.length);;
        int operation = this.getNextOperation();
        
        while (sheep.size()<decksCount) {
            if (checkDeck(deck)==true) {
                sheep.add(this.generalLocation[deck]);
                deck=deck+operation;
            } else {
                deck = rand.nextInt(this.generalLocation.length);
                operation = this.getNextOperation();
                sheep.clear();
            }
        }
        for (int i=0; i<sheep.size();i++) {
            closeLocationElements(sheep.get(i));
        }
        counter++;
    return sheep;
    }
    
    public void createArray() {
        Ships = new HashMap<>(10);
        int deckCount = 4;
        int sheepCount = 1;
        //int currentSheep = 10;
        while (deckCount>0) {
            
            for (int i=1; i<sheepCount+1;i++) {
                ArrayList<Integer> a = this.createDecks(deckCount);
                Ships.put(shipsNames[counter-1], a);
            }
            sheepCount++;
            deckCount--;
        }   
    }
    
    JButton makeMove(ArrayList<JButton> gameGrid) {
        JButton btn = gameGrid.get(rand.nextInt(100));
            return btn;
    }
    
}
    

