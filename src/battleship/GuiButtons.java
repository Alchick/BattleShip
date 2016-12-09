package battleship;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GuiButtons {
    ArrayList<JButton> shipsButtons = new ArrayList<JButton>(10);
    private ActiveGame Game;
    
    public void setActiveGame(ActiveGame game) {
        Game = game;
    }
    
    JButton getstartGameBtn(JTextArea myInfoArea, 
                            JTextArea opponentInfoArea, 
                            ArrayList<JButton> MyGameGrid,
                            ArrayList<JButton> OpponentGameGrid,
                            shipsCreator shipsCreator,
                            MyShips ships,
                            JButton cleanShipsAreaBtn,
                            JButton alignBtn) {
        JButton startGameBtn = new JButton("Start Game");
        startGameBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                myInfoArea.setText("Game Started\n");
                for (JButton btn:OpponentGameGrid) {
                    btn.addActionListener(Game);
                }
                for (JButton btn:MyGameGrid) {
                    btn.removeActionListener(shipsCreator);
                }              
                cleanShipsAreaBtn.setEnabled(false);
                alignBtn.setEnabled(false);
                startGameBtn.setEnabled(false);
            }
        });
        
        return startGameBtn;
    }
    
    JButton getCleanShipsAreaBtn(JTextArea InfoArea, MyShips ships, shipsCreator creator, ArrayList<JButton> gameGrid) {
        JButton cleanShipsAreaBtn = new JButton("Reset Ships");
        cleanShipsAreaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                ships.clear();
                for (JButton btn:gameGrid) {
                    btn.setText("");
                    btn.setEnabled(true);
                }
                ships.clear();
                creator.clear();
                InfoArea.setText("Add ships\n");
                for (JButton btn:creator.shipsButtons) {
                    btn.setEnabled(true);
                }
            } 
        });
        return cleanShipsAreaBtn;
    }
    
    JPanel createGrids(ArrayList<JButton> gameGrid) {
        String[] Alphabet = {"A", "B", "C", "D", "E", "F", "G", "K", "L", "M"};
        GridLayout sheepsTable = new GridLayout(11,10);
        sheepsTable.setVgap(5);
        sheepsTable.setHgap(5);
        JPanel SheepsArea = new JPanel(sheepsTable);
        for (int i = 0; i < 11; i++) {
            JButton num_btn = new JButton(String.valueOf(i));
            if (i==0) {num_btn.setText("");}
            num_btn.setMargin(new Insets(0, 0, 0, 0));
            num_btn.setPreferredSize(new Dimension(25, 25));
            num_btn.setContentAreaFilled(false);
            num_btn.setBorderPainted(false);
            SheepsArea.add(num_btn);
        } 
        for (int i = 0; i < 100; i++) {  
            JButton btn = new JButton();
            btn.setMargin(new Insets(0, 0, 0, 0));  
            btn.setBackground(Color.CYAN);
            if (i%10==0) {
                JLabel Alpha = new JLabel(Alphabet[i/10]);
                SheepsArea.add(Alpha);
            }
            gameGrid.add(btn);
            SheepsArea.add(btn);
        }
        return SheepsArea;
    }
    
}
    