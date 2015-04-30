package swantech;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author by Ifetayo on 24/04/2015.
 */
public class GameMenuActionListener implements ActionListener {
    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = new String((e.getActionCommand()));
        if (cmd.equals("New game")){
            GameBoard.getGameBoardInstance().GameOptionsDialog();//start game
        } else if (cmd.equals("Exit")){
            System.out.println("EXIT");
            System.exit(0);
        } else {
            System.err.println("GamemenuActionListener:actionPerformed Unknown option");
        }
    }
}
