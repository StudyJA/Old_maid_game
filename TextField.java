import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextField extends JPanel {
	JTextField tf = new JTextField("이곳에 입력한 후 <Enter>키를 입력하세요", 50);
	String entered = "";
			
	TextField() {
		setBackground(Color.white);
		setVisible(true);
		setFocusable(true);
		requestFocus();
		this.add(tf);
		tf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField t = (JTextField)e.getSource();
				entered = t.getText();
				t.setText("");
			}
		});	
	}
}
