package main;

import javax.swing.JFrame;

/**MAIN. */
public class Main {
    
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Tower Defense");

        GamePanel gamePanel = new GamePanel();
        gamePanel.startupCode(); //just so certain code can be ran before main loop
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}