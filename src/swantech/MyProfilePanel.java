package swantech;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author Moho
 */
public class MyProfilePanel extends JPanel {
	
	private JLabel nameLable;
	private JLabel nameValueLable;
	private JLabel winLable;
	private JLabel winValueLable;
	private JLabel lostLable;
	private JLabel lostValueLable;
	private JLabel drawLable;
	private JLabel drawValueLable;
	
	
	public MyProfilePanel(){
		
		nameLable = new JLabel("Name:");
		nameValueLable = new JLabel("MoHo");
		winLable = new JLabel("Win:");
		winValueLable = new JLabel("30");
		lostLable = new JLabel("Lost:");
		lostValueLable = new JLabel("10");
		drawLable = new JLabel("Draw");
		drawValueLable = new JLabel("4");
		
		Dimension dim = getPreferredSize();
		dim.width = 200;
		setPreferredSize(dim);
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.weightx = 1;
		//gbc.weighty = GridBagConstraints.RELATIVE;
		gbc.weighty = 0.2;
		gbc.gridx = gbc.gridy = 0;		
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;		
		gbc.insets = new Insets(0,10,0,0); 
		add(nameLable, gbc);
		
		gbc.gridx++;
		gbc.anchor = GridBagConstraints.CENTER;		
		gbc.insets = new Insets(0,10,0,0); 
		add(nameValueLable, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;		
		gbc.insets = new Insets(0,10,0,0); 
		add(winLable, gbc);
		
		gbc.gridx++;
		gbc.anchor = GridBagConstraints.CENTER;		
		gbc.insets = new Insets(0,10,0,0); 
		add(winValueLable, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;		
		gbc.insets = new Insets(0,10,0,0); 
		add(lostLable, gbc);
		
		gbc.gridx++;
		gbc.anchor = GridBagConstraints.CENTER;		
		gbc.insets = new Insets(0,10,0,0); 
		add(lostValueLable, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;		
		gbc.insets = new Insets(0,10,0,0); 
		add(drawLable, gbc);
		
		gbc.gridx++;
		gbc.anchor = GridBagConstraints.CENTER;		
		gbc.insets = new Insets(0,10,0,0); 
		add(drawValueLable, gbc);
		
		
		Border innerBorder = BorderFactory.createTitledBorder("My profile");
		Border outterBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outterBorder, innerBorder)	);
	}
}
