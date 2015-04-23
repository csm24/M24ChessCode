package swantech;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Moho, modified by Ifetayo
 */
public class GameBoard extends JFrame {

    private static GameBoard gameBoard;
    JPanel leftPanel;
    JPanel centerPanel;
    JPanel rightPanel;

    private static final JButton[][] chessBoardSquares = new JButton[8][8];  // Row, Col (but all wrong)
    private JPanel chessBoard;
    private static final String[] COLS = new String[]{"a","b","c","d","e","f","g", "h"};
    private static final int SQUARESIZE = 64;
    private static final String imageDir= "../res/";
    private static ChessEngine chessEngine;
    private static PlayColour myColour;
    private static int candidateRow = 0, candidateCol = 0;
    private static JLabel statusLabel;
    private JTextArea blkMoveHistory;
    private JTextArea whtMoveHistory;
    private JScrollPane scrollBlkHistory;
    private JScrollPane scrollWhtHistory;

    private SquareEventListener squareEventListener;

    public GameBoard(){
        super("Swantech Chess Game");
        squareEventListener = new SquareEventListener();
        try {
            myColour = PlayColour.WHITE;
            chessEngine = new ChessEngine(chessEngine.otherColour(myColour));  // tell chess engine what colour it is playing
        } catch (Exception e){
            System.err.println("Failed to initialse the chess engine, exiting");
            System.err.println("Error is : " + e.getMessage());
            System.exit(99);
        }
    }

    /**
     * Game board instance TODO check for null
     * @return
     */
    public static GameBoard getGameBoardInstance(){
        return gameBoard;
    }

    public ChessEngine getChessEngineInstance(){
        return chessEngine;
    }

    public static JButton getButtonSquare(int row, int col){
        return chessBoardSquares[row][col];
    }

    public void SetBlackHistory(String text){
        blkMoveHistory.append(text + "\n");
    }
    public void SetWhiteHistory(String text){
        whtMoveHistory.append(text + "\n");
    }

    private void init(){
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        leftPanel = new JPanel();
        centerPanel = new JPanel(new BorderLayout());
        rightPanel = new JPanel(new GridLayout(2,1));

        BuildLeftPanelDisplay();
        BuildCenterPanelDisplay();
        BuildRightPanelDisplay();

        //TODO find out from Moho what should be displayed on the left panel

        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(rightPanel, BorderLayout.EAST);

        setSize(900, 700);
        setVisible(true);
    }

    /**
     * Set up the left panel display
     */
    private void BuildLeftPanelDisplay() {

    }

    /**
     * Set up the board display
     */
    private void BuildCenterPanelDisplay() {
        JPanel whtLblPnl = new JPanel(new GridLayout(0,8));
        JPanel blkLblPnl = new JPanel(new GridLayout(0,8));
        JPanel whtNumLblPnl = new JPanel(new GridLayout(8,1, 10, 10));
        JPanel blkNumLblPnl = new JPanel(new GridLayout(8,1, 10, 10));
        chessBoard = new JPanel(new GridLayout(0, 8));

        //A bit hacky, but it gets the job done
        for (int i = 0; i < 9; i++){
            if(i != 0 && i != 9){
                whtLblPnl.add(new JLabel(COLS[i-1], SwingConstants.CENTER));
                blkLblPnl.add(new JLabel(COLS[i-1], SwingConstants.CENTER));
            }
        }
       for (int i = 0; i < 8; i++){
            whtNumLblPnl.add(new JLabel(String.valueOf((8-i)), SwingConstants.CENTER));
           blkNumLblPnl.add(new JLabel(String.valueOf((8-i)), SwingConstants.CENTER));
       }

        whtLblPnl.setBorder(new EmptyBorder(10, 10, 10, 10));
        blkLblPnl.setBorder(new EmptyBorder(10, 10, 10, 10));
        whtNumLblPnl.setBorder(new EmptyBorder(10, 10, 10, 10));
        blkNumLblPnl.setBorder(new EmptyBorder(10, 10, 10, 10));

        // create the chess board squares
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int col = 0; col < chessBoardSquares.length; col++) {
            for (int row = 0; row < chessBoardSquares[col].length; row++) {
                JButton squareBtn = new JButton();
                squareBtn.putClientProperty("row", row);
                squareBtn.putClientProperty("col", col);
                squareBtn.putClientProperty("highlighted", false);
                squareBtn.setMargin(buttonMargin);
                squareBtn.addActionListener(squareEventListener);
                ImageIcon icon = new ImageIcon(new BufferedImage(SQUARESIZE,
                        SQUARESIZE, BufferedImage.TYPE_INT_ARGB));
                squareBtn.setIcon(icon);
                squareBtn.setBackground(setSquareBackgroundColor(row, col));

                chessBoardSquares[row][col] = squareBtn;
            }
        }
        resetPieces();  // Place the pieces in the start position

        // fill the chess board
        for (int rowcounter = 0; rowcounter < 8; rowcounter++) {
            for (int colcounter = 0; colcounter < 8; colcounter++) {
                chessBoard.add(chessBoardSquares[colcounter][rowcounter]);
            }
        }

        centerPanel.add(whtLblPnl, BorderLayout.NORTH);
        centerPanel.add(blkLblPnl, BorderLayout.SOUTH);
        centerPanel.add(whtNumLblPnl, BorderLayout.EAST);
        centerPanel.add(blkNumLblPnl, BorderLayout.WEST);
        centerPanel.add(chessBoard, BorderLayout.CENTER);
    }

    /**
     * Set up the right panel display for play history
     */
    private void BuildRightPanelDisplay() {
        blkMoveHistory = new JTextArea(30, 30);
        blkMoveHistory.setEditable(false);
        blkMoveHistory.setLineWrap(true);
        blkMoveHistory.setText("Black Player History \n");
        blkMoveHistory.setBackground(Color.WHITE);

        whtMoveHistory = new JTextArea(30, 30);
        whtMoveHistory.setEditable(false);
        whtMoveHistory.setLineWrap(true);
        whtMoveHistory.setText("White Player History \n");
        whtMoveHistory.setBackground(Color.WHITE);

        scrollBlkHistory = new JScrollPane(blkMoveHistory);
        scrollWhtHistory = new JScrollPane(whtMoveHistory);

        rightPanel.add(scrollBlkHistory);
        rightPanel.add(scrollWhtHistory);
    }

    /**
     * This method initializes each chess piece on the chess board.
     */
    public void resetPieces() {
        for (int i = 0; i < 8; i++) {
            setPiece(i, 6, "wp");
            setPiece(i, 1, "bp");
        }

        setPiece(7, 7, "wr");
        setPiece(6, 7, "wn");
        setPiece(5, 7, "wb");
        setPiece(4, 7, "wk");
        setPiece(3, 7, "wq");
        setPiece(2, 7, "wb");
        setPiece(1, 7, "wn");
        setPiece(0, 7, "wr");

        setPiece(0, 0, "br");
        setPiece(1, 0, "bn");
        setPiece(2, 0, "bb");
        setPiece(3, 0, "bq");
        setPiece(4, 0, "bk");
        setPiece(5, 0, "bb");
        setPiece(6, 0, "bn");
        setPiece(7, 0, "br");
    }

    /**
     * This method sets a chess piece icon to square
     * @param row int, row value of square piece to be set
     * @param col int, column value of square piece to be set
     * @param pieceName String, name of piece added e.g br (black rook)
     */
    public void setPiece(int row, int col, String pieceName) {
        assert row >= 0 && row < 8 && col >= 0 && col < 8;

        JButton b = chessBoardSquares[row][col];
        ImageIcon icon;
        BufferedImage piece = null;

        String fileName = imageDir + pieceName + ".png";
        try {
            piece = ImageIO.read(this.getClass().getResource(fileName));
        } catch (IOException ex) {
            //Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
        }

        icon = new ImageIcon(piece);
        b.setIcon(icon);
        b.setBackground(setSquareBackgroundColor(row, col));
        b.putClientProperty("piece", pieceName);
    }

    /**
     * Updates the board status text field
     * @param status - text to be shown
     */
    public void showBoardStatus(String status)
    {
        // todo: MOH update here
        statusLabel.setText(status);
    }

    /**
     * This method flashed the selected square when the selected piece on the selected square has not
     * valid move.
     * written by Ifetayo 23/4/2015
     * @param row int, row of the piece selected
     * @param col int, coloumn of the piece selected
     */
    public void flashSquare(int row, int col) {
        final JButton b = this.chessBoardSquares[row][col];
        Timer timer = new Timer(500, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(b.getBackground().equals(Color.ORANGE)){
                    b.setBackground(Color.GRAY);
                    ((Timer)e.getSource()).stop();
                }
                else{
                    b.setBackground(Color.ORANGE);
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
        b.putClientProperty("highlighted", false);
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
     * Highlights or unhighlights a given square
     * @param row, 0 - 7
     * @param col, 0 - 7
     * @param highlight
     */
    public void highlight(int row, int col, boolean highlight) {
        assert row >= 0 && row < 8 && col >= 0 && col < 8;

        JButton b = this.chessBoardSquares[row][col];
        if (highlight) {
            b.setBackground(Color.ORANGE);
            b.putClientProperty("highlighted", true);
        }
        else {
            b.setBackground(setSquareBackgroundColor(row, col));
            b.putClientProperty("highlighted", false);
        }
    }

    /**
     * Shorthand utility, remove all highlighting
     */
    public void unhighlightAll() {
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                highlight(r, c, false);
        candidateRow = candidateCol = 0;
    }

    public static void main(String[] args) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //ChessBoard ch = new ChessBoard();
        // Never returns (until exit)
        gameBoard = new GameBoard();
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                gameBoard.init();
            }
        });
    }
}
