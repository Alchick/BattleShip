package battleship;
import battleship.NetGame.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JComponent;
import java.awt.BorderLayout;

public class MainMenu implements ActionListener {
    JFrame MainMenu;
    JPanel menu;
    JButton exit;
    JPanel twoPlayersPanel;
    boolean isServer = true;
    
    public void createMenu() {
        MainMenu = new JFrame("MainMenu");
        menu = createPanel(5,5,5,5,false);
        
        twoPlayersPanel = createPanel(5,5,5,5,false);
        exit = new JButton("Exit");
        exit.addActionListener(this);
        exit.setPreferredSize(new Dimension(160,25));
        
        JButton onePlayerBtn = new JButton("One Player");
        onePlayerBtn.setPreferredSize(new Dimension(160,25));
        onePlayerBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                MainMenu.dispose();
                new OnePlayerGamePanel().Build();
            } 
        });
        
        JPanel onePlayerPanel = createPanel(5,5,5,5,false);
        onePlayerPanel.add(onePlayerBtn);
        JButton twoPlayersBtn = new JButton("Two Players");
        twoPlayersBtn.setPreferredSize(new Dimension(160,25));
        twoPlayersBtn.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent event) {
                JTextField PortField = new JTextField("Port: ",4);
                JTextField IPAddressField = new JTextField("IPAddress: ",4);
                TwoPlayersGamePanel gamePanel = new TwoPlayersGamePanel();
                JCheckBox Server = new JCheckBox("Server");
                JButton startGame = new JButton("Start Game");
                startGame.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent event) {
                       MainMenu.dispose();
                       //gamePanel.setIpAddress(IPAddressField.getText());
                       //gamePanel.setPort(PortField.getText());
                       gamePanel.BuildGamePanel(isServer);
                   } 
                });
                Server.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e){
                        isServer = true;
                        //twoPlayersPanel.add(IPAddressField);
                        //twoPlayersPanel.add(PortField);
                        twoPlayersPanel.add(startGame);
                        MainMenu.revalidate();
                        MainMenu.repaint();
                    }
                });
                JCheckBox Client = new JCheckBox("Client");
                Client.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e){
                        //twoPlayersPanel.add(IPAddressField);
                        //twoPlayersPanel.add(PortField);
                        isServer = false;
                        twoPlayersPanel.add(startGame);
                        MainMenu.revalidate();
                        MainMenu.repaint();
                    }
                });
                twoPlayersPanel.add(Server);
                twoPlayersPanel.add(Client);
                MainMenu.revalidate();
                MainMenu.repaint();
            }
        });
        twoPlayersPanel.add(twoPlayersBtn);
        menu.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        menu.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        menu.add(onePlayerPanel);
        //menu.add(twoPlayersPanel);
        menu.add(exit);
        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout());
        pan.add(menu, BorderLayout.CENTER);
        //MainMenu.setLayout(new GridBagLayout());
        //MainMenu.add(menu, new GridBagConstraints());
        //MainMenu.setSize(new Dimension(600, 400));
        
        MainMenu.getContentPane().add(pan);
        MainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainMenu.setBounds(400,200,600,400);
        MainMenu.setVisible(true);
        }
        //frame.setLayout(new GridBagLayout());
        //frame.add(panel, new GridBagConstraints());
        //frame.setSize(new Dimension(200, 200));
    public void actionPerformed(ActionEvent event) {
        MainMenu.dispose();         
    }
    
    public JPanel createPanel(int top, int left, int bottom, int right, boolean orientation) {
        JPanel panel = new JPanel();
        if (orientation) {
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        }
        else {panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));}
        //panel.setBorder(new CompoundBorder(new EmptyBorder(top,left,bottom,right),new BevelBorder(BevelBorder.LOWERED)));
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        return panel;
    }

    public static void main (String[] args) {
        MainMenu menu = new MainMenu();
        //menu.createMenu();
        //menu.bla();
        new OnePlayerGamePanel().Build();
        //new PlayerPlayerGamePanel().addElementsOnPanel();
    }
}
