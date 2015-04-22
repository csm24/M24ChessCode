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

	ChessBoard() {
		initializeGui();                
		
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
				b.setMargin(buttonMargin);
				ImageIcon icon = new ImageIcon(new BufferedImage(squareSize,
						squareSize, BufferedImage.TYPE_INT_ARGB));
				b.setIcon(icon);
				if ((row % 2 == 1 && col % 2 == 1)
						|| (row % 2 == 0 && col % 2 == 0)) {
					b.setBackground(Color.WHITE);
				} else {
					b.setBackground(Color.GRAY);
				}
				chessBoardSquares[row][col] = b;
			}
		}

                
                resetPieces();
                RemovePiece(3, 2);
                setPiece(3, 4, "bp");
                highlight(3, 5);
                highlight(3, 4);
                
		

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

	/** 
	 * @param row, 0 - 7            
	 * @param col, 0 - 7            
	 */
	public void highlight(int row, int col) {
		Insets buttonMargin = new Insets(0, 0, 0, 0);
		JButton b = new JButton();
		b.setMargin(buttonMargin);
		ImageIcon icon = new ImageIcon(new BufferedImage(squareSize, squareSize, BufferedImage.TYPE_INT_ARGB));
		b.setIcon(icon);
		b.setBackground(Color.ORANGE);
		this.chessBoardSquares[row][col] = b;
	}

	public void RemovePiece(int row, int col) {
		Insets buttonMargin = new Insets(0, 0, 0, 0);
		JButton b = new JButton();
		b.setMargin(buttonMargin);
		ImageIcon icon = new ImageIcon(new BufferedImage(squareSize,
				squareSize, BufferedImage.TYPE_INT_ARGB));
		b.setIcon(icon);
		if ((row % 2 == 1 && col % 2 == 1) || (row % 2 == 0 && col % 2 == 0)) {
			b.setBackground(Color.WHITE);
		} else {
			b.setBackground(Color.GRAY);
		}
		this.chessBoardSquares[row - 1][col - 1] = b;
	}

	public void resetPieces() {
		setPiece(8, 8, "wr");
		setPiece(7, 8, "wn");
		setPiece(6, 8, "wb");
		setPiece(5, 8, "wk");
		setPiece(4, 8, "wq");
		setPiece(3, 8, "wb");
		setPiece(2, 8, "wk");
		setPiece(1, 8, "wr");
		setPiece(8, 7, "wp");
		setPiece(7, 7, "wp");
		setPiece(6, 7, "wp");
		setPiece(5, 7, "wp");
		setPiece(4, 7, "wp");
		setPiece(3, 7, "wp");
		setPiece(2, 7, "wp");
		setPiece(1, 7, "wp");

		setPiece(1, 1, "br");
		setPiece(2, 1, "bn");
		setPiece(3, 1, "bb");
		setPiece(4, 1, "bq");
		setPiece(5, 1, "bk");
		setPiece(6, 1, "bb");
		setPiece(7, 1, "bk");
		setPiece(8, 1, "br");
		setPiece(8, 2, "bp");
		setPiece(7, 2, "bp");
		setPiece(6, 2, "bp");
		setPiece(5, 2, "bp");
		setPiece(4, 2, "bp");
		setPiece(3, 2, "bp");
		setPiece(2, 2, "bp");
		setPiece(1, 2, "bp");

	}

	public void setPiece(int row, int col, String pieceName) {
		Insets buttonMargin = new Insets(0, 0, 0, 0);
		JButton b = new JButton();
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
		b.setMargin(buttonMargin);
		b.setIcon(icon);

		if ((row % 2 == 1 && col % 2 == 1) || (row % 2 == 0 && col % 2 == 0)) {
			b.setBackground(Color.WHITE);
		} else {
			b.setBackground(Color.GRAY);
		}
		this.chessBoardSquares[row - 1][col - 1] = b;
	}
        
        private  JComponent getChessBoard() {
		return chessBoard;
	}

	private JComponent getGui() {
		return gui;
	}

        /*
	public static void main_tmp(String[] args) {

		Runnable r = new Runnable() {

			@Override
			public void run() {
				ChessBoard cb = new ChessBoard();

				JFrame f = new JFrame("Chess Board");
				f.add(cb.getGui());
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				f.setLocationByPlatform(true);

				// ensures the frame is the minimum size it needs to be
				// in order display the components within it
				f.pack();
				// ensures the minimum size is enforced.
				f.setMinimumSize(f.getSize());
				f.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(r);
	}
        */
}
