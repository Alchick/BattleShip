package battleship;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class shipsCreator implements ActionListener {
    ArrayList<JButton> shipsButtons = new ArrayList<JButton>(10);
    private ArrayList<JButton> selectedButtons;
    private JButton ManageButton;
    private JTextArea InfoArea;
    private ArrayList<JButton> GameGrid;
    private JButton gridBtn;
    private int decksCounter;   
    private JButton SelectedShip;
    private ArrayList<Integer> currentShip = new ArrayList<Integer>();
    private MyShips sheeps;
    boolean horizont = true;
    int deckLocation;
    
    shipsCreator(ArrayList<JButton> gameGrid, MyShips ships) {
        GameGrid = gameGrid; 
        sheeps = ships;
        selectedButtons = new ArrayList<JButton>(4);
    }
    
    int getDecksCounter() {
        return decksCounter;
    }    
    void setManageBtnLink(JButton manageButton) {
        ManageButton = manageButton;
    }
    
    void clear() {
        this.decksCounter = 0;
        this.currentShip.clear();
        this.selectedButtons.clear();
    }
    
    ArrayList<Integer> getCurrentShip() {
        return currentShip;
    }
    
    JPanel getSheepTypeButton(int size, String name, JTextArea infoArea) {
        JButton shipButton = new JButton(name);
        shipButton.setPreferredSize(new Dimension(160,25));
        shipButton.addActionListener(new ActionListener(){
            //function for choise sheep Button
            public void actionPerformed(ActionEvent event) {
                SelectedShip = shipButton;
                InfoArea = infoArea;
                decksCounter = size;
                infoArea.append("Create "+name+"\n");
                currentShip.clear();
           } 
        });
        shipsButtons.add(shipButton);
        JPanel shipBtnPanel = new JPanel();
        shipBtnPanel.setBorder(BorderFactory.createEmptyBorder(0,-20,-20,-20));
        shipBtnPanel.add(shipButton);
        return shipBtnPanel;
    }
    
    JButton getOrintationBtn(){
        JButton alignBtn = new JButton("horizontal");
        alignBtn.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent event) {
               horizont = !horizont;
               if (horizont) {
                   alignBtn.setText("horizontal");
               }
               if (!horizont) {
                   alignBtn.setText("vertical");
               }
           } 
        });
        return alignBtn;
    }

    @Override
    //function for cell in player grid
    public void actionPerformed(ActionEvent event) {
        selectedButtons.clear();
        currentShip.clear();
        gridBtn = (JButton) event.getSource();
        gridBtn.setText("S"+decksCounter);
        if (decksCounter==0) {
            gridBtn.setText("");
            return;
        }
        gridBtn.setEnabled(false);
        deckLocation = GameGrid.indexOf(gridBtn);
        currentShip.add(deckLocation);
        selectedButtons.add(GameGrid.get(deckLocation));
        for (int i = 1; i<decksCounter; i++) {
            if (horizont) {
                deckLocation = deckLocation+1;
                GameGrid.get(deckLocation).setEnabled(false);
                GameGrid.get(deckLocation).setText("S"+decksCounter);
                currentShip.add(deckLocation);
                selectedButtons.add(GameGrid.get(deckLocation));
                if ((deckLocation%10 == 0)&&(deckLocation - (deckLocation-1) > 0)) {
                    for(JButton btn:selectedButtons) {
                        btn.setEnabled(true);
                        btn.setText("");
                    }
                    clear();
                    InfoArea.append("Cant create behind location");
                    return;
                }
            }
            if (!horizont) {
                deckLocation = deckLocation+10;
                try {
                GameGrid.get(deckLocation).setEnabled(false);
                GameGrid.get(deckLocation).setText("S"+decksCounter);
                currentShip.add(deckLocation);
                selectedButtons.add(GameGrid.get(deckLocation));
                }catch (IndexOutOfBoundsException ex) {
                    for(JButton btn:selectedButtons) {
                        btn.setEnabled(true);
                        btn.setText("");
                    }
                    InfoArea.append("Cant create behind location");
                    clear();
                    return;
                }
            }
        }
        SelectedShip.setEnabled(false);
        InfoArea.append("Success");
        sheeps.addShip(SelectedShip.getText(), currentShip);
        //sheeps.checkGeneralLocations(sheeps.getShips());
        if (sheeps.getShips().size() == 8) {
            ManageButton.setEnabled(true);
        } 
        disableButtons();
    }
    
    void disableButtons() {
        for (JButton btn:selectedButtons) {
            int index = GameGrid.indexOf(btn);
            try {
            GameGrid.get(index+1).setEnabled(false);
            } catch(ArrayIndexOutOfBoundsException ex) {}
            try {
            GameGrid.get(index-1).setEnabled(false);
            } catch(ArrayIndexOutOfBoundsException ex) {}
            try {
            GameGrid.get(index+10).setEnabled(false);
            } catch(ArrayIndexOutOfBoundsException ex) {}
            try {
            GameGrid.get(index-10).setEnabled(false);
            } catch(ArrayIndexOutOfBoundsException ex) {}
        }
    }
}
