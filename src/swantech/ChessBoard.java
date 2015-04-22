/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swantech;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
//import ictk.boardgame.chess.ChessBoard;
import ictk.boardgame.chess.ChessMove;
import ictk.boardgame.chess.ChessPiece;
import ictk.boardgame.chess.Square;
/**
 *
 * @author moho
 */
public class ChessBoard {
    
	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	private final JButton[][] chessBoardSquares = new JButton[8][8];
	private JPanel chessBoard;
	private static final String COLS = "ABCDEFGH";
	private static final int squareSize = 64;
        private static final String imageDir= "../res/";
	private static ChessEngine chessEngine;
	private static PlayColour myColour;

	ChessBoard()   {
		initializeGui();

		// And init the chess engine...
		try {
			myColour = PlayColour.WHITE;
			chessEngine = new ChessEngine(chessEngine.otherColour (myColour));  // tell chess engine what colur IT is playing
		} catch (Exception e){
			System.out.println("Failed to initialse the chess engine, exiting");
			System.out.println("Error is : " + e.getMessage());
			System.exit(99);
		}

	}

	
	private void initializeGui() {

		chessBoard = new JPanel(new GridLayout(0, 9));
		chessBoard.setBorder(new LineBorder(Color.BLACK));
		gui.add(chessBoard);

		// create the chess board squares
		Insets buttonMargin = new Insets(0, 0, 0, 0);
		for (int col = 0; col < chessBoardSquares.length; col++) {
			for (int row = 0; row < chessBoardSquares[col].length; row++) {
				JButton b = new JButton();
				b.putClientProperty("row", row);
				b.putClientProperty("col", col);
				b.putClientProperty("highlighted", false);
				b.setMargin(buttonMargin);
				ImageIcon icon = new ImageIcon(new BufferedImage(squareSize,
						squareSize, BufferedImage.TYPE_INT_ARGB));
				b.setIcon(icon);
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						actionSquare(e);
					}


				});
				b.setBackground(setSquareBackgroundColor(row, col));

				chessBoardSquares[row][col] = b;
			}
		}


		resetPieces();  // Place the pieces in the start position

		// fill the chess board
		chessBoard.add(new JLabel(""));
		// fill the top row
		for (int ii = 0; ii < 8; ii++) {
			chessBoard.add(new JLabel(COLS.substring(ii, ii + 1),
					SwingConstants.CENTER));
		}
		// fill the black non-pawn piece row
		for (int ii = 0; ii < 8; ii++) {
			for (int jj = 0; jj < 8; jj++) {
				switch (jj) {
				case 0:
					chessBoard.add(new JLabel("" + (ii + 1), SwingConstants.CENTER));
				default:
					chessBoard.add(chessBoardSquares[jj][ii]);
				}
			}
		}
               /*
                for (int ii = 7; ii >= 0; ii--) {
			chessBoard.add(new JLabel(COLS.substring(ii, ii + 1), SwingConstants.CENTER));
		}
                
                */
                
		JFrame f = new JFrame("Chess Board");
		f.add(this.getGui());
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLocationByPlatform(true);

		// ensures the frame is the minimum size it needs to be
		// in order display the components within it
		f.pack();
		// ensures the minimum size is enforced.
		f.setMinimumSize(f.getSize());
		f.setVisible(true);

	}


	// Just shorthand to avoid the typecast on every use.
	// NOTE chessEngine (and ictk library) work on rank & file = [1..8]
	// but this class works on [0..7],
	// AND rank in the wrong order! Should be 1 at Bottom (White Queen rank)
	// so these methods do the conversion
	// ONLY use these methods to convert to/from Square

	private Square intSquare(int f, int r)
	{
		assert f >= 0 && f < 8 && r >= 0 && r < 8;

		r = 8-r;
		f++;
		Square s = new Square((byte)f, (byte)r);
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
	 * This is the big one.
	 * Called when user clicks ANY square
	 * @param actionEvent e - get the jButton pressed from this
	 */
	private void actionSquare(ActionEvent e) {

		Object source = e.getSource();
		if (source instanceof JButton) {
			JButton b = (JButton)source;
			ArrayList<Square> squares;

			// Now have the button that was pressed
			// get the details of the square
			int row = (Integer)b.getClientProperty("row");
			int col = (Integer)b.getClientProperty("col");
			boolean isHighlighted = (Boolean)b.getClientProperty("highlighted");
			// If its not one of my pieces, do nothing


			// And unhighlight anything (its left over from last attempt)
			unhighlightAll();
			System.out.println("Button Clicked row : " + Integer.toString(row) +
					", col : " + Integer.toString(col));

			// OK lets play chess...
			// Its always users turn if we get here
			// What are the legal moves?
			squares = chessEngine.getLegalMoves(intSquare(row, col));
			if (squares == null) {
				System.out.println("Nothing on this square");
				highlight(row, col, true);
				chessBoard.repaint();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				highlight(row, col, false);

			} else {
				int moveCount = squares.size();
				for (int i = 0; i < moveCount; i++) {
					int r = squareToRow(squares.get(i));
					int c = squareToCol(squares.get(i));
					highlight(r, c, true);
				}
			}
		}
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
	private void unhighlightAll() {
		for (int r = 0; r < 8; r++)
			for (int c = 0; c < 8; c++)
				highlight(r, c, false);
	}

	public void RemovePiece(int row, int col) {

		assert row >= 0 && row < 8 && col >= 0 && col < 8;

		JButton b = chessBoardSquares[row][col];
		ImageIcon icon = new ImageIcon(new BufferedImage(squareSize,
				squareSize, BufferedImage.TYPE_INT_ARGB));
		b.setIcon(icon);
		b.setBackground(setSquareBackgroundColor(row, col));

	}

	public void resetPieces() {
		for (int i = 1; i <= 8; i++) {
			setPiece(i, 7, "wp");
			setPiece(i, 2, "bp");
		}

		setPiece(8, 8, "wr");
		setPiece(7, 8, "wn");
		setPiece(6, 8, "wb");
		setPiece(5, 8, "wk");
		setPiece(4, 8, "wq");
		setPiece(3, 8, "wb");
		setPiece(2, 8, "wk");
		setPiece(1, 8, "wr");

		setPiece(1, 1, "br");
		setPiece(2, 1, "bn");
		setPiece(3, 1, "bb");
		setPiece(4, 1, "bq");
		setPiece(5, 1, "bk");
		setPiece(6, 1, "bb");
		setPiece(7, 1, "bn");
		setPiece(8, 1, "br");
	}

	public void setPiece(int row, int col, String pieceName) {
		row--;
		col--;  // no idea why suddenly 1..8 not 0..7
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
	}
        
        private  JComponent getChessBoard() {
		return chessBoard;
	}

	private JComponent getGui() {
		return gui;
	}

}
