package battleship.NetGame;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class SimpleServer implements Runnable {
    static int port;
    static int ipAddress;
    int LISTEN_PORT_BY_DEFAULT = 4444;
    ServerSocket server;
    Socket clientConnect;
    ArrayList<Integer> opponentShipIndex = new ArrayList<Integer>(1);
    ObjectInputStream InputStream;
    ObjectOutputStream OutputStream;
    Object netValue;
    JTextArea OpponentTextArea;
    JLabel Label;
    
    
    
    SimpleServer(JLabel label, JTextArea area) {
        Label = label;
        OpponentTextArea = area;
    }
    
    void setOponentTextArea(JTextArea textArea) {
        OpponentTextArea = textArea;
    }
    
    void setLabel(JLabel label) {
        Label = label;
    }
    
    void serverStart() throws IOException{
        try {
            if (port == 0) {
                server = new ServerSocket(LISTEN_PORT_BY_DEFAULT);
                Label.setText("waitin for client");
            } else {server = new ServerSocket(port);}
        } catch(Exception e) {Label.setText("Could'n listen this port");e.printStackTrace();}
    }
    
    public void run() {
        try {
            serverStart();
            clientConnect = server.accept();
            Label.setText("Success");
            OutputStream = new ObjectOutputStream(clientConnect.getOutputStream());
            InputStream = new ObjectInputStream(clientConnect.getInputStream());

            while(true) {
                try {
                    netValue = InputStream.readObject();
                    int index = (int) netValue;
                    opponentShipIndex.add(index);
                } catch(ClassCastException |ClassNotFoundException |IOException ex) {
                  if (ex instanceof ClassCastException) {
                    String message = (String) netValue;
                    OpponentTextArea.append(message);
                } if (ex instanceof ClassNotFoundException) {
                    Label.setText("Send unknown value");
                    ex.printStackTrace();
                } if (ex instanceof IOException) {
                    Label.setText("Net problems, restart game");
                    ex.printStackTrace();}}
            }
        } catch(Exception ex) {ex.printStackTrace();}
    }
    
    void checkMyShips(int deckLocation) {
        
    }
    
    boolean checkOnClient(int deckLocation) throws IOException {
        boolean result;
        OutputStream.writeUnshared(deckLocation);
        OutputStream.flush();
            while(true) {
                try {
                    netValue = InputStream.readObject();
                    result = (boolean) netValue;
                    return result;
                } catch(ClassCastException |ClassNotFoundException |IOException ex) {}
            }
    }
    
    void sendToClient(String message) throws IOException{
        OutputStream.writeUnshared(message);
        OutputStream.flush();
    }
    
    void sendToClient(int index) throws IOException {
        OutputStream.writeUnshared(index);
        OutputStream.flush();
    }
    
    int getShipIndex(){
        opponentShipIndex.clear();
        int index=0;
        int i = 0;
        while(true) {
            System.out.print("");
            i = i+1;
            if (opponentShipIndex.size()>0) {
                System.out.println(opponentShipIndex);
                index = opponentShipIndex.get(opponentShipIndex.size()-1);
                break;
            }
        }
       return index;
    }        
    
    
    
   
       
}