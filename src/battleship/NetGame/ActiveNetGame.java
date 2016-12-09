/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.NetGame;
import battleship.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.io.IOException;
import java.util.Map;

public class ActiveNetGame extends ActiveGame {
    SimpleServer Server;
    SimpleClient Client;
    boolean isServer;
    public ActiveNetGame(JTextArea myinfoArea,
                      JTextArea opponentInfoArea,
                      ArrayList<JButton> myGameGrid,
                      ArrayList<JButton> opponentGameGrid,
                      MyShips myShips) {
        super(myinfoArea,opponentInfoArea,myGameGrid,opponentGameGrid,myShips);
        //System.out.println("This is child class, bithces");
    }
    
    public void setServer(SimpleServer server) {
        Server = server;
        isServer = true;
    }
    
    public void setClient(SimpleClient client) {
        Client = client;
        isServer = false;
    }
    
    private JButton Btn;
    public void actionPerformed(ActionEvent event) {
        int deckLocation;
        boolean result;
        System.out.println(isServer);
        if (isServer) {
            Btn = (JButton) event.getSource();
            System.out.println("Server");
            Btn.setEnabled(false);
            deckLocation = super.OpponentGameGrid.indexOf(Btn);
            serverSend(deckLocation);
            deckLocation = Server.getShipIndex(); //get client move
            result = super.checkForHit(deckLocation, super.MyGameGrid, super.ships.getShips(), super.MyInfoArea);
            //watch if len my ships is none - end game
        }
        if (!isServer) {
            System.out.println("Client");
            deckLocation = Client.getShipIndex(); //get client move
            super.MyGameGrid.get(deckLocation).setEnabled(false); //turn off button
            result = super.checkForHit(deckLocation, super.MyGameGrid, super.ships.getShips(), super.MyInfoArea);
            //check if my shis is empty, end game
            Btn = (JButton) event.getSource();
            Btn.setEnabled(false);
            deckLocation = super.OpponentGameGrid.indexOf(Btn);
            clientSend(deckLocation);
            
        }
    }
    void serverSend(int deckLocation) {
        boolean result = false;
        try {
            result = Server.checkOnClient(deckLocation);
        } catch (IOException ex) {ex.printStackTrace();} //need to set correct string in GUI
        if (result) {
            Btn.setBackground(Color.RED);
            super.OpponentInfoArea.append("You hit me...aaaa");
        } 
        if(!result){Btn.setBackground(Color.WHITE);}
        }
    
    void clientSend(int deckLocation) {
        boolean result = false;
        try{
            result = Client.checkOnServer(deckLocation);
        } catch(IOException ex) {ex.printStackTrace();}
        if (result) {
            Btn.setBackground(Color.RED);
            super.OpponentInfoArea.append("You hit me...aaaa");
        }
        if(!result)
        {Btn.setBackground(Color.WHITE);}
    }
}

