package swantech;

import ictk.boardgame.chess.ChessBoard;
import ictk.boardgame.chess.Square;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ifetayo on 27/04/2015.
 */
public class FindKingTest {
    ChessEngine chessEngine;
    ChessBoard board;

    @Before
    public void setUp() throws Exception {
        chessEngine = new ChessEngine(PlayColour.BLACK);
        board = chessEngine.getChessBoard();
    }

    @Test
    public void TestFindKing() throws  Exception {

        Square testSquareWhite = new Square((byte)5, (byte)1); //file and rank
        Square testSquareBlack = new Square((byte)5, (byte)8);
       assertEquals("White King position is e1", testSquareWhite, chessEngine.FindKing("WHITE"));
       assertEquals("Black King position is e8", testSquareBlack, chessEngine.FindKing("BLACK"));
    }
}
