/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.Component;

public class OnePlayerGamePanel extends MainMenu{
    JFrame frame;
    public JPanel background;
    public JPanel firstPlayerPanel;
    public JPanel secondPlayerPanel;
    public JButton manageButton;
    JButton checkButton;    

    JButton cleanAreaBtn;
    public ArrayList<JButton> MyGameGrid;
    public ArrayList<JButton> OpponentGameGrid;
    public GuiButtons buttons = new GuiButtons();
    public MyShips myShips = new MyShips();
    ArrayList<JPanel> shipsButtons = new ArrayList<JPanel>(10);
    Computer comp;
    public JTextArea MyInfoArea;
    public JTextArea OpponentInfoArea;
    ActiveGame game;

    public void Build() {
        comp = new Computer();
        MyGameGrid = new ArrayList<JButton>(100); 
        OpponentGameGrid = new ArrayList<JButton>(100);
        
        //create elements on GUI like buttons, Text Fields, lables, etc
        JLabel gameProcessLb = new JLabel("Game");
        MyInfoArea = new JTextArea("Create ship\n", 15, 10);
        MyInfoArea.setLineWrap(true);
        MyInfoArea.setWrapStyleWord(true);
        JScrollPane MyInfoPane = new JScrollPane(MyInfoArea);
        
        OpponentInfoArea = new JTextArea(15, 10);
        OpponentInfoArea.setLineWrap(true);
        OpponentInfoArea.setWrapStyleWord(true);
        JScrollPane OpponentInfoPane = new JScrollPane(OpponentInfoArea);

        shipsCreator shipsCreator = new shipsCreator(MyGameGrid, myShips);
        //Buttons
        JButton createOrientationBtn = shipsCreator.getOrintationBtn();
        cleanAreaBtn = buttons.getCleanShipsAreaBtn(MyInfoArea,myShips,shipsCreator,MyGameGrid);
        
        manageButton = buttons.getstartGameBtn(MyInfoArea,OpponentInfoArea,MyGameGrid, 
                                               OpponentGameGrid,shipsCreator, myShips,cleanAreaBtn,createOrientationBtn);
        game = new ActiveGame(MyInfoArea,OpponentInfoArea,MyGameGrid,OpponentGameGrid,myShips);
        game.withHuman(false);
        buttons.setActiveGame(game);
        
        manageButton.setEnabled(false);
        
        shipsCreator.setManageBtnLink(manageButton);
        //Ships Buttons
        JPanel AircraftCarrier = shipsCreator.getSheepTypeButton(4,"AircraftCarrier(4)",MyInfoArea);
        shipsButtons.add(AircraftCarrier);
        JPanel FirstBattleShip = shipsCreator.getSheepTypeButton(3,"First BattleShip(3)",MyInfoArea);
        shipsButtons.add(FirstBattleShip);
        JPanel SecondBattleShip = shipsCreator.getSheepTypeButton(3,"Second BattleShip(3)",MyInfoArea);
        shipsButtons.add(SecondBattleShip);
        JPanel FirstCruise = shipsCreator.getSheepTypeButton(2,"First Cruiser(2)",MyInfoArea);
        shipsButtons.add(FirstCruise);
        JPanel SecondCruise = shipsCreator.getSheepTypeButton(2,"Second Cruiser(2)",MyInfoArea);
        shipsButtons.add(SecondCruise);
        JPanel ThirdCruise = shipsCreator.getSheepTypeButton(2,"Third Cruiser(2)",MyInfoArea);
        shipsButtons.add(ThirdCruise);
        JPanel FirstDestroyer = shipsCreator.getSheepTypeButton(1,"First Destroyer(1)",MyInfoArea);
        shipsButtons.add(FirstDestroyer);
        JPanel ThirdDestroyer = shipsCreator.getSheepTypeButton(1,"Third Destroyer(1)",MyInfoArea);
        shipsButtons.add(ThirdDestroyer);

        JLabel FirstPlayerName = new JLabel("Your Location");
	FirstPlayerName.setFont(new Font("Adobe Caslon Pro", Font.BOLD | Font.ITALIC, 34));
        
        JLabel SecondPlayerName = new JLabel("Opponent Location");
	SecondPlayerName.setFont(new Font("Adobe Caslon Pro", Font.BOLD | Font.ITALIC, 34));
        
        //create Panels
        //create secondPlayerPanel
        JPanel secondPlayerLabel = super.createPanel(20, 0, 0, 55, false);
        //set true value if need X_AXIS orientation
        secondPlayerLabel.add(SecondPlayerName);
        
        JPanel secondPlayerGrid = buttons.createGrids(OpponentGameGrid);
        
        secondPlayerPanel = super.createPanel(0, 0, 0, 0, false);
        
        JPanel tagline = super.createPanel(15, 0, 5, 0, false);
        JLabel taglineLbl = new JLabel("Try to hit me, Pussy!");
        tagline.add(taglineLbl);
        secondPlayerGrid.setAlignmentX( Component.RIGHT_ALIGNMENT);
        tagline.setAlignmentX( Component.RIGHT_ALIGNMENT);
        secondPlayerLabel.setAlignmentX( Component.RIGHT_ALIGNMENT);
        
        secondPlayerPanel.add(secondPlayerGrid);
        secondPlayerPanel.add(tagline);
        secondPlayerPanel.add(secondPlayerLabel);
        
        //create TextFields panel
        JPanel textFieldsPanel = super.createPanel(27, 10, 75, 10, false);
        textFieldsPanel.add(gameProcessLb);
        textFieldsPanel.add(MyInfoPane);
        JLabel OpponentLabel = new JLabel("Opponent");
        textFieldsPanel.add(OpponentLabel);
        textFieldsPanel.add(OpponentInfoPane);
        
        
        //create firstPlayerPanel
        firstPlayerPanel = super.createPanel(0, 0, 0, 0, false);
        JPanel firstPlayerLabel = super.createPanel(20, 180, 0, 0, false);
        firstPlayerLabel.add(FirstPlayerName);
        
        JPanel manageBtnPanel = super.createPanel(10, 35, 0, 0, true);
        manageBtnPanel.add(manageButton);
        manageBtnPanel.add(cleanAreaBtn);
        manageBtnPanel.add(createOrientationBtn);
        
        JPanel firstPlayerGrid = buttons.createGrids(MyGameGrid);
        MyGameGrid.stream().forEach((btn) -> {
            btn.addActionListener(shipsCreator);
        });
        
        firstPlayerGrid.setAlignmentX( Component.LEFT_ALIGNMENT);
        manageBtnPanel.setAlignmentX( Component.LEFT_ALIGNMENT);
        firstPlayerLabel.setAlignmentX( Component.LEFT_ALIGNMENT);
        
        firstPlayerPanel.add(firstPlayerGrid);
        firstPlayerPanel.add(manageBtnPanel);
        firstPlayerPanel.add(firstPlayerLabel);
        
        
        JPanel shipsBtnPanel = super.createPanel(38, 0, 75, 0, false);
        shipsButtons.stream().forEach((btn) -> {
            shipsBtnPanel.add(btn);
        });
        background = super.createPanel(15,15,15,15,true);
        
        background.add(secondPlayerPanel);
        background.add(textFieldsPanel);
        background.add(firstPlayerPanel);
        background.add(shipsBtnPanel);
        
        frame = new JFrame("BattleField");
        frame.getContentPane().add(background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200,100,1200,600);
        //frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);   
    }
    
    
    
    
}
    
