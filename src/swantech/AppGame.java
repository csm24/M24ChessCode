package swantech;

import javax.swing.*;

/**
 * Created by Ifetayo on 23/04/2015.
 */
public class AppGame {

    public void StartNewGame(){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppGame();
            }
        });
    }
}
