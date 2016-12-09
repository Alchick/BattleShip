/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.NetGame;
import battleship.*;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class TwoPlayersGamePanel extends OnePlayerGamePanel {
    JTextArea myMessagesTArea;
    JTextArea opponentMessages;
    JLabel gameStatusLb;
    JLabel serverStatus;
    int Port;
    String ipAddress;
    
    void setIpAddress(String address) {
        ipAddress = address;
    }
    void setPort(String port) {
        try {
            Port = Integer.parseInt(port);
        } catch (Exception ex) {
            Port = 0;
        }
    }
    
    void setShips(MyShips ships) {
        
    }
    
    public void BuildGamePanel(boolean serverSide) {
        System.out.println(serverSide);
        super.Build();
        myMessagesTArea = new JTextArea(1, 1);
        myMessagesTArea.setTabSize(5);
        myMessagesTArea.setLineWrap(true);
        myMessagesTArea.setWrapStyleWord(true);
        JScrollPane MyInfoPane = new JScrollPane(myMessagesTArea);
        gameStatusLb = new JLabel("Not connect yet");
        JPanel myMessagesPanel = super.createPanel(0,0,0,0, false);
        myMessagesPanel.setAlignmentX( Component.LEFT_ALIGNMENT);
        myMessagesPanel.add(gameStatusLb);
        myMessagesPanel.add(MyInfoPane);
        super.firstPlayerPanel.add(myMessagesPanel);
        ActiveNetGame game = new ActiveNetGame(MyInfoArea,OpponentInfoArea,MyGameGrid,OpponentGameGrid,myShips);
        game.withHuman(true);
        super.buttons.setActiveGame(game);
        super.manageButton.setEnabled(true);
        
        try {
            if (serverSide) {
                SimpleServer server = new SimpleServer(gameStatusLb,myMessagesTArea) ;
                Thread serverThread = new Thread(server);
                serverThread.start();
                myMessagesTArea.append("Server");
                game.setServer(server);
                
                
            }else {
                SimpleClient client = new SimpleClient(gameStatusLb, myMessagesTArea);
                Thread clientThread = new Thread(client);
                clientThread.start();
                myMessagesTArea.append("Client");
                game.setClient(client);
            }
        }catch (Exception ex) {ex.printStackTrace();} 
    }
}
