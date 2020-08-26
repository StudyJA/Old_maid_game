import java.awt.*;
import javax.swing.*;

public class BoardPanel extends JPanel {
	ImageIcon back = new ImageIcon("img/backs.jpg");
	JLabel consoleLine = new JLabel("");
	JLabel gameSetting = new JLabel("");
	//JTextArea consoleLine = new JTextArea(3, 50);
	
	BoardPanel() {
		setSize(500, 300);
		setLayout(new GridLayout(3,1));
		setBackground(Color.white);
		
		Font f1=new Font("Arial", Font.PLAIN, 12);
		gameSetting.setFont(f1);
		this.add(gameSetting);
		gameSetting.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel j = new JLabel(back);
        j.setSize(back.getIconWidth(), back.getIconHeight());
		this.add(j);
		
		Font f2=new Font("Arial", Font.BOLD, 17);
		consoleLine.setFont(f2);
		this.add(consoleLine);
		consoleLine.setHorizontalAlignment(JLabel.CENTER);
	}
}
