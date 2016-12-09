package battleship;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JTextArea;


public class ActiveGame implements ActionListener {
    public ArrayList<JButton> OpponentGameGrid;
    ArrayList<JButton> MyGameGrid;
    JTextArea MyInfoArea;
    JTextArea OpponentInfoArea;
    Map <String, ArrayList<Integer>> OpponentShips;
    MyShips ships;
    Computer comp;
    

    private int myStatistics;
    private int opponentStatistics;
    int deckLocation;
    
    public ActiveGame(JTextArea myinfoArea,
                      JTextArea opponentInfoArea,
                      ArrayList<JButton> myGameGrid,
                      ArrayList<JButton> opponentGameGrid,
                      MyShips myShips) {
        OpponentGameGrid = opponentGameGrid;
        MyGameGrid = myGameGrid;
        ships = myShips;
        MyInfoArea = myinfoArea;
        OpponentInfoArea = opponentInfoArea;
    }
    
    private JButton Btn;
    @Override
    public void actionPerformed(ActionEvent event) {
        //MY_MOVE
        Btn = (JButton) event.getSource();
        Btn.setEnabled(false);
        int deckLocation = OpponentGameGrid.indexOf(Btn);
        boolean me = true;
        if (checkForHit(deckLocation,OpponentGameGrid,comp.getShips(),OpponentInfoArea)) {
            //make another move

        }
        //comp move
        Btn = comp.makeMove(MyGameGrid);
        deckLocation = MyGameGrid.indexOf(Btn);
        if (checkForHit(deckLocation,MyGameGrid,ships.getShips(),MyInfoArea)) {
            //make another comp move

        }
               
    }
    
    public void withHuman(boolean human) {
        if (human == false) {
            comp = new Computer();
            comp.createArray();
        }
    }
    
    boolean checkForHit(int deckLocation,
                        ArrayList<JButton> gameGrid, 
                        Map<String, ArrayList<Integer>> ships,
                        JTextArea infoArea){
        boolean result=false;
        gameGrid.get(deckLocation).setBackground(Color.WHITE);
        gameGrid.get(deckLocation).setEnabled(false);
        for(Iterator<Map.Entry<String, ArrayList<Integer>>> it = ships.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, ArrayList<Integer>> entry = it.next();
            ArrayList<Integer> ship = (ArrayList<Integer>) entry.getValue();
            String key = (String) entry.getKey();

            if (ship.indexOf(deckLocation) >= 0) {
                gameGrid.get(deckLocation).setBackground(Color.RED);
                ships.get(key).remove(ships.get(key).indexOf(deckLocation));
                infoArea.append(key+" hit\n");
                result = true;
            }
            if (ships.get(key).isEmpty()) {
                infoArea.append(key+" sunk\n");
                it.remove();
            }
        }
        if (ships.values().isEmpty()) {
            infoArea.setText("Game is over");
        }
    return result;
    }
}
