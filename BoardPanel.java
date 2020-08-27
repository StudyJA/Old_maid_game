import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
	ImageIcon back = new ImageIcon("img/backs.jpg");
	static JLabel consoleLine = new JLabel("");
	static JLabel gameSetting = new JLabel("");
	static JLabel j; // Center
	
	BoardPanel() {
		setLayout(new GridLayout(1,3));
		setBackground(Color.white);
		
		Font f1 = new Font("Arial", Font.PLAIN, 12);
		gameSetting.setFont(f1);
		this.add(gameSetting);
		gameSetting.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel j = new JLabel(back);
        j.setSize(back.getIconWidth(), back.getIconHeight());
		this.add(j);
		
		Font f2 = new Font("Arial", Font.BOLD, 17);
		consoleLine.setFont(f2);
		this.add(consoleLine);
		consoleLine.setHorizontalAlignment(JLabel.CENTER);
	}

	// 왼쪽 보드에 콘솔 메시지 출력
	static public void showLeft(String msg) {
		gameSetting.setText(msg);
	}

	// 가운데 보드 이미지 출력
	static public void showCenter(ImageIcon icon) {
		j.setIcon(icon);
	}

	// 오른쪽 보드 게임 설정
	static public void showRight(String msg) {
		consoleLine.setText(msg);
	}

}
