import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class Card extends JLabel {
	int number;      // 카드 숫자
	String shape;    // 카드 모양
	private ImageIcon forth; // 앞면 모양
	private ImageIcon back;	 // 뒷면 모양
	boolean clickable = false; // 클릭할 수 있는 상태

	Card (String shape, int number) {
		this.shape = shape;
		this.number = number;
		this.back = new ImageIcon("img/back.jpg");
		String filename = "img/" + shape + number + ".jpg";
		this.forth = new ImageIcon(filename);
		this.setOpaque(true);
		this.setSize(forth.getIconWidth(), forth.getIconHeight());
	}

	public void setForth() { setIcon(forth); }
	public void setBack()  { setIcon(back);	}
	public void enableClick(MouseAdapter ml) {
		if(!clickable)
			this.addMouseListener(ml);
	}
	@Override
	public String toString() {
		return shape + number;
	}
}
