package swantech;

import ictk.boardgame.chess.ChessMove;
import ictk.boardgame.chess.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Event handler class for the chess board
 * @author Ifetayo
 * @author Simon
 * @author Moho
 */
public class SquareEventListener implements ActionListener {

    private static int candidateRow = 0, candidateCol = 0;
    private static final int SQUARESIZE = 64;
    /**
     * Invoked when an action occurs.     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JButton) {
            JButton b = (JButton)source;
            // Now have the button that was pressed
            // get the details of the square
            int row = (Integer)b.getClientProperty("row");
            int col = (Integer)b.getClientProperty("col");
            boolean isHighlighted = (Boolean)b.getClientProperty("highlighted");

            // OK lets play chess...
            // Its always users turn if we get here
            if (isHighlighted){
                // If it is highlighted, that's because I have already marked it as a legal move square,
                // So we can make a move!
                makeBoardMove(row, col);

            } else {
                // Else its an unhighlighted square, is it another candidate?
                showMoves(row, col);
            }
        } // else its not a button, I dom't care what else it may be
    }

    /**
     * User has clicked any square. If it their piece and can be moved,  \n
     * Record the place, highlight legal move squares
     * @param row int, integer row value of square on the board
     * @param col int, integer column value of square on the board
     */
    private void showMoves(int row, int col) {
        GameBoard.getGameBoardInstance().unhighlightAll();

        // What are the legal moves?
        ArrayList<Square> squares = AppGame.GameInstance().getChessEngineInstance().getLegalMoves(intSquare(row, col));
        if (squares == null || squares.size() == 0) {
            GameBoard.getGameBoardInstance().FlashSquare(row, col);
        } else {
            // OK, there are legal moves from here, record this as a candidate and highlight the legal moves.
            candidateCol = col;
            candidateRow = row;
            for (int i = 0; i < squares.size(); i++) {
                GameBoard.getGameBoardInstance().highlight(squareToRow(squares.get(i)),
                        squareToCol(squares.get(i)), true);
            }
        }
    }

    /**
     * Generates an ictk library square using our internal row and column numbering
     * @param f int, integer file of the square on the chess board
     * @param r int, integer rank of the square on the chess board
     * @return Square (just rank and file, no piece data)
     */
    private Square intSquare(int f, int r)
    {
        assert f >= 0 && f < 8 && r >= 0 && r < 8;

        r = 8-r;
        f++;
        //Square s = GameBoard.getGameBoardInstance().getChessEngineInstance().getChessSquare(r, f);
        Square s = AppGame.GameInstance().getChessEngineInstance().getChessSquare(r, f);

        return s;
    }

    private int squareToRow(Square s)
    {
        return s.getFile()-1;
    }
    private int squareToCol(Square s)
    {
        return 8 - s.getRank();
    }

    /**
     * User has already selected a possible move piece, and has now selected one of the highligted legal move squares
     * SO make the move, then play opposing move.
     * @param row int  row value of the square TOI square
     * @param col int column value of the square
     * (globals candidateRow, candidateCol have the FROM details)
     */
    private void makeBoardMove(int row, int col) {
        Square fromSquare = intSquare(candidateRow, candidateCol);
        Square toSquare = intSquare(row, col);
        //ChessEngineErrors ce = GameBoard.getGameBoardInstance().getChessEngineInstance().makeMyMove(fromSquare, toSquare);
        ChessEngineErrors ce = AppGame.GameInstance().getChessEngineInstance().makeMyMove(fromSquare, toSquare);

        if (ce != ChessEngineErrors.OK){
            System.err.println("ChessBoard:makeBoardMove: Chess engine ERROR : " + ce.name());
        } else {
            // Move OK, update board
            drawBoardMove(fromSquare, toSquare);

            // And immediately get engine/other player to make a move
            MakeOtherPlayerMove();
        }
    }

    /**
     * Player has just made their (legal) move -
     * this method does the 'Other Player' move
     * Chess engine or network player
     */
    private void MakeOtherPlayerMove() {
        //ChessMove to = GameBoard.getGameBoardInstance().getChessEngineInstance().engineMove();
        ChessMove to = AppGame.GameInstance().getChessEngineInstance().engineMove();
        Square kingSquare;
        JButton kingBtn = null;
        boolean isBlackMove = AppGame.GameInstance().getChessEngineInstance().board.isBlackMove();

        if(to.isCheckmate()){
            JOptionPane.showMessageDialog(GameBoard.getGameBoardInstance(), "Checkmate. Game Over!");
        }

        if(to.isCheck()){
            if(isBlackMove){
                kingSquare = AppGame.GameInstance().getChessEngineInstance().FindKing("BLACK");
            }
            else{
                kingSquare = AppGame.GameInstance().getChessEngineInstance().FindKing("WHITE");
            }
            kingBtn = buttonFromSquare(kingSquare);
            //GameBoard.getGameBoardInstance().FlashKing(kingSquare.getFile(), kingSquare.getRank());
            GameBoard.getGameBoardInstance().FlashKing(kingBtn, 1);
        }
        if(!to.isCheck()){
            GameBoard.getGameBoardInstance().FlashKing(kingBtn, 0);
        }

        //updateStatus(to);
        drawBoardMove(to.getOrigin(), to.getDestination());
        /*if(kingBtn != null)
            GameBoard.getGameBoardInstance().FlashKing(kingBtn);*/
    }


    private JButton buttonFromSquare(Square s)
    {
        Square t = s;
        return GameBoard.getGameBoardInstance().getButtonSquare(s.getFile()-1, 8-s.getRank());
        //return chessBoardSquares[s.getFile()-1][8-s.getRank()];
    }

    /**
     * This method removes the piece from the chess board
     * @param row int row value of the piece to be removed
     * @param col int column value of the piece to be removed
     */
    public void RemovePiece(int row, int col) {
        assert row >= 0 && row < 8 && col >= 0 && col < 8;

        JButton pieceBtn = GameBoard.getGameBoardInstance().getButtonSquare(row, col);
        ImageIcon icon = new ImageIcon(new BufferedImage(SQUARESIZE, SQUARESIZE, BufferedImage.TYPE_INT_ARGB));
        pieceBtn.setIcon(icon);
        pieceBtn.setBackground(setSquareBackgroundColor(row, col));
        pieceBtn.putClientProperty("piece", "");
    }

    /**
     * Simple utility to set the background colour based on row and col.
     * As a function because its needed to unhighlight a square
     * @param row 0..7
     * @param col 0..7
     * @return A colour (Gray or White)
     */
    private Color setSquareBackgroundColor (int row, int col) {
        assert row >= 0 && row < 8 && col >= 0 && col < 8;

        if ((row % 2 == 1 && col % 2 == 1)
                || (row % 2 == 0 && col % 2 == 0)) {
            return Color.WHITE;
        } else {
            return Color.GRAY;
        }
    }

    /**
     * Draws the move onto the board
     * Simple UNLESS its castling!
     * @param fromSquare Square
     * @param toSquare Square
     */
    private void drawBoardMove(Square fromSquare, Square toSquare) {
        JButton fromButon = buttonFromSquare(fromSquare);
        JButton toButon = buttonFromSquare(toSquare);
        String piece = (String)fromButon.getClientProperty("piece");
        RemovePiece(squareToRow(fromSquare), squareToCol(fromSquare));
        GameBoard.getGameBoardInstance().setPiece(squareToRow(toSquare), squareToCol(toSquare), piece);
        GameBoard.getGameBoardInstance().unhighlightAll();
        // did we just castle?
        didCastle(fromSquare, toSquare, piece);
    }

    private void didCastle(Square fromSquare, Square toSquare, String piece) {
        // Tricky one, this
        // IF the piece is a king AND it moved > 1 square THEN it must have casteled SO move the rook as well
        Square knightFrom, knightTo;
        byte rank = 0;

        if (Math.abs(fromSquare.getFile() - toSquare.getFile())  > 1) { // moving > 1 square along rank
            if (piece.equals("wk")) {rank = 1;}
            else if (piece.equals("bk")) {rank = 8;}
            if (rank > 0) { //ELSE is not a king (eg rook or queen moving along back row)
                //  king is castling
                if ((fromSquare.getFile() - toSquare.getFile()) > 0) {
                    // Castling 'left'
                     knightFrom = new Square((byte) 1, rank);
                     knightTo = new Square((byte) 4, rank);
                } else {
                    // Castling 'Right'
                     knightFrom = new Square((byte) 8, rank);
                     knightTo = new Square((byte) 6, rank);
                }
                drawBoardMove(knightFrom, knightTo); // Recurse, but only once, as its not a King
                }
            }
    }

    /**
     * ChessMove to has methods that describe the state of play after the move, this method
     * updates the display to show, eg check, check-mate
     * @param to
     */
    private void updateStatus(ChessMove to) {
        String playerColour = to.isBlackMove()?"White":"Black";  // backwards deliberately!
        if (to.isCheckmate()) {
            // GAME OVER
            //Square kingSq = GameBoard.getGameBoardInstance().getChessEngineInstance().findPiece("King", playerColour);
            Square kingSq = AppGame.GameInstance().getChessEngineInstance().findPiece("King", playerColour);
            GameBoard.getGameBoardInstance().highlight(squareToRow(kingSq), squareToCol(kingSq), true);
            GameBoard.getGameBoardInstance().showBoardStatus("Game Over");
            System.out.println("CheckMate");

        }
        else if (to.isCheck() || to.isDoubleCheck()) {
            GameBoard.getGameBoardInstance().showBoardStatus("CHECK!");
            System.out.println("Check");
            // find King?
            //Square kingSq = GameBoard.getGameBoardInstance().getChessEngineInstance().findPiece("King", playerColour);
            Square kingSq = AppGame.GameInstance().getChessEngineInstance().findPiece("King", playerColour);
            GameBoard.getGameBoardInstance().highlight(squareToRow(kingSq), squareToCol(kingSq), true);
        }
    }
}
