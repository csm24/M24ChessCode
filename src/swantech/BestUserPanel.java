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
public class BestUserPanel extends JPanel {
	private JLabel nameLable;
	private JLabel nameValueLable;
	private JLabel winLable;
	private JLabel winValueLable;
	private JLabel lostLable;
	private JLabel lostValueLable;
	private JLabel drawLable;
	private JLabel drawValueLable;
	
	public BestUserPanel(){
		nameLable = new JLabel("Name");
		nameValueLable = new JLabel("Point");
		winLable = new JLabel("MoHo");
		winValueLable = new JLabel("30");
		drawLable = new JLabel("Simon");
		drawValueLable = new JLabel("19");
		lostLable = new JLabel("Ifetayo");
		lostValueLable = new JLabel("29");
		
		
		Dimension dim = getPreferredSize();
		dim.width = 150;
		
		setPreferredSize(new Dimension(150, 150));// hardCoded sizing
        setMaximumSize(new Dimension(150, 150));  // hardCoded sizing
        setMinimumSize(new Dimension(150, 150));  // hardCoded sizing
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.weightx = 1;
		gbc.weighty = 0.005;
		//gbc.weighty = GridBagConstraints.RELATIVE;
		gbc.gridx = gbc.gridy = 0;		
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;		
		gbc.insets = new Insets(0,10,0,0); 
		add(nameLable, gbc);
		
		gbc.gridx++;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;		
		gbc.insets = new Insets(0,10,0,0); 
		add(nameValueLable, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.LINE_START;		
		gbc.insets = new Insets(0,10,0,0); 
		add(winLable, gbc);
		
		gbc.gridx++;
		gbc.anchor = GridBagConstraints.LINE_END;		
		gbc.insets = new Insets(0,10,0,0); 
		add(winValueLable, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.LINE_START;		
		gbc.insets = new Insets(0,10,0,0); 
		add(lostLable, gbc);
		
		gbc.gridx++;
		gbc.anchor = GridBagConstraints.LINE_END;		
		gbc.insets = new Insets(0,10,0,0); 
		add(lostValueLable, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;		
		gbc.insets = new Insets(0,10,0,0); 
		add(drawLable, gbc);
		
		gbc.gridx++;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;		
		gbc.insets = new Insets(0,10,0,0); 
		add(drawValueLable, gbc);

		Border innerBorder = BorderFactory.createTitledBorder("User ranking");
		Border outterBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outterBorder, innerBorder)	);
	}
}
