package battleship.NetGame;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class SimpleClient implements Runnable {
    Socket serverConnect;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    ArrayList<Integer> opponentShipIndex = new ArrayList<Integer>(1);
    JLabel Label;
    JTextArea OponentTextArea;
    Object netValue;
    
    Map <String, ArrayList<Integer>> ships;

    SimpleClient(JLabel label, JTextArea textArea) {
        Label = label;
        OponentTextArea = textArea;
    }

    void connectToServer() {
        try {
            Label.setText("Connecting to localhost");
            serverConnect = new Socket("127.0.0.1",4444);
            Label.setText("Success");
            inputStream  = new ObjectInputStream(serverConnect.getInputStream());
            outputStream = new ObjectOutputStream(serverConnect.getOutputStream());
        } catch (IOException ex) {ex.printStackTrace();}   
    }
    
    public void run() {
        connectToServer();
        while(true) {
            try {
                netValue = inputStream.readObject();
                int index = (int) netValue;
                opponentShipIndex.add(index);
            } catch(ClassCastException |ClassNotFoundException |IOException ex) {
              if (ex instanceof ClassCastException) {
                String message = (String) netValue;
                OponentTextArea.append(message);
            } if (ex instanceof ClassNotFoundException) {
                Label.setText("Send unknown value");
            } if (ex instanceof IOException) {
                Label.setText("Net problems, restart game");
            }}
            
        }
    }

    
    boolean checkOnServer(int deckLocation) throws IOException {
        boolean result;
        outputStream.writeUnshared(deckLocation);
        outputStream.flush();
            while(true) {
                try {
                    netValue = inputStream.readObject();
                    result = (boolean) netValue;
                    return result;
                } catch(ClassCastException |ClassNotFoundException |IOException ex) {ex.printStackTrace();}
            }
    }
    
    
    
    void sendToServer(String message) throws IOException{
        outputStream.writeUnshared(message);
        outputStream.flush();
    }
    
    void sendToServer(int index) throws IOException {
        outputStream.writeUnshared(index);
        outputStream.flush();
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

