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
        switch (e.getActionCommand()){
            case "New game":
                GameBoard.getGameBoardInstance().GameOptionsDialog();//start game
                //AppGame.GameInstance().GetGameBoardInstance().GameOptionsDialog(); //start game
                break;
            case "Exit":
                //TODO create a handler for this
                break;
        }
    }
}
