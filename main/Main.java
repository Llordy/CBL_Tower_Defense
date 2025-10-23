package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
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

        
        try {
            String currentDir = new File(".").toURI().toString();
            currentDir = currentDir.substring(6, currentDir.length() - 2);

            BufferedReader bufferedReader = new BufferedReader(
                new FileReader("main\\Enemies.txt")
                );
            
            String str;

            ArrayList<String> enemyString = new ArrayList<String>();
            

            while ((str = bufferedReader.readLine()) != null) {
                enemyString.add(str);
            }

            bufferedReader.close();

            for (String s : enemyString) {
                System.out.println(s);
            }
    
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}