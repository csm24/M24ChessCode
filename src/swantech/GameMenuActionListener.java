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
            // todo: add exit code
        } else {
            System.err.println("GamemenuActionListener:actionPerformed Unknown option");
        }

        // This code will not run under OS X InterrliJ - stuck with JVM 1.6!
//        switch (e.getActionCommand()){
//            case "New game":
//                GameBoard.getGameBoardInstance().GameOptionsDialog();//start game
//                //AppGame.GameInstance().GetGameBoardInstance().GameOptionsDialog(); //start game
//                break;
//            case "Exit":
//                //TODO create a handler for this
//                break;
//        }
    }
}
