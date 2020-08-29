import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class BoardPanel extends JPanel {
	static ImageIcon back = new ImageIcon("img/backs.jpg");
	static JLabel consoleLine = new JLabel("");
	static GamePanel board = new GamePanel(); // Center
	static JPanel scoreBoard = new JPanel();
	static ArrayList<JLabel> scoreList = new ArrayList<JLabel>();
	
	BoardPanel() {
		setLayout(new GridLayout(1,3));
		setBackground(Color.white);
		
		this.add(scoreBoard);

		board.add(new JLabel(back));
        this.add(board);
		
		Font f2 = new Font("Arial", Font.BOLD, 17);
		consoleLine.setFont(f2);
		this.add(consoleLine);
		consoleLine.setHorizontalAlignment(JLabel.CENTER);
	}


	static public void addScoreList(ArrayList<Player> playerList) {
		for(Player p : playerList) {
			JLabel sc = new JLabel(p.name + ": " + p.score);
			Font f1 = new Font("Arial", Font.PLAIN, 13);
			sc.setFont(f1);
			sc.setOpaque(true);
			sc.setBackground(Color.white);
			scoreList.add(sc);
			scoreBoard.add(sc);
		}
	}
	
	static public void updateScoreList(ArrayList<Player> playerList) {
		Iterator<JLabel> iter = scoreList.iterator();
		for(Player p : playerList) {
			if(iter.hasNext()) {
				JLabel sc = iter.next();
				sc.setText(p.name + ": " + p.score);
			}
		}
	}

	// 가운데 보드 초기화
	static public void defaultCenter()
	{
		board.removeAll();
		board.add(new JLabel(back));
		board.revalidate();
		board.repaint();
	}

	// 가운데 보드 이미지 출력
	static public void showCenter(Card c1, Card c2)
	{
		board.removeAll();
		board.add(c1);
		board.add(c2);
		board.revalidate();
		board.repaint();
	}

	// 오른쪽 보드 게임 설정
	static public void showRight(String msg) {
		consoleLine.setText(msg);
	}

}
