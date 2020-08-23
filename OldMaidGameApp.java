import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 *  게임을 세팅하고 턴을 진행하고 승자와 패자를 가리고 점수를 계산할 때 필요한 메소드를 모아놓은 클래스
 *  8/24 GUI 추가: 카드 이미지로 게임 상황을 보여줄 수 있다.
 */
public class OldMaidGameApp extends JFrame {

	Scanner scanner  = new Scanner(System.in);
	boolean running  = true;  // 전체 게임의 진행중 여부를 나타내는 변수.
	boolean winner   = false; // 승자가 나오면 상태가 true로 바뀌도록 하기
	ControlPlayer p  = new ControlPlayer();
	ControlCard   c  = new ControlCard();

	// 누군가 카드를 모두 버렸을 경우 승자와 패자 결정 및 점수 계산
	void isWin(Player previousPlayer, Player currentPlayer) {
		Player loser = p.findJoker();
		if (previousPlayer.cardList.size() == 0) {
			for(Player each: p.playerList) // 유저가 아니면 카드를 모두 보여줌
				if (each.getLocation() != 0)  flipCards(each);
			winner = true;
			show(previousPlayer.getName() + " is winner!!");
			previousPlayer.plusScore();
			show(loser.getName() + " has Joker!");
			loser.minusScore();
		}
		else if (currentPlayer.cardList.size() == 0) {
			for(Player each: p.playerList) // 유저가 아니면 카드를 모두 보여줌
				if (each.getLocation() != 0)  flipCards(each);
			winner = true;
			show(currentPlayer.getName() + " is winner!!");
			currentPlayer.plusScore();
			show(loser.getName() + " has Joker!");
			loser.minusScore();
		}
	}

	// 유저가 아닌 나머지 플레이어의 카드를 모두 공개
	public void flipCards(Player player) {
		player.panel.removeAll();
		for(Card card: player.cardList){
			String filename = "img/" + card + ".jpg";
			ImageIcon icon = new ImageIcon(filename);
			JLabel j = new JLabel(icon);
			j.setOpaque(true);
			j.setSize(icon.getIconWidth(),icon.getIconHeight());
			player.panel.add(j);
		}
		player.panel.revalidate();
		player.panel.repaint();
	}

	// 게임 종료
	void end() { running = false; }

	// 콘솔 출력
	void show(String msg) {
		System.out.println(msg);
	}

	// 플레이어 설정, 카드 범위 설정, 덱 생성
	void gameSetting() {
		show("Game setting...");

		System.out.print("How many players with you?: ");
		p.setTotalPlayer(scanner.nextInt());

		System.out.print("Range of Cards(1~?): ");
		c.setRangeOfCards(scanner.nextInt());

		show("Please type your name");
		System.out.print("Player's name: ");
		scanner.nextLine();
		p.addUser(scanner.nextLine(), 0);
		for (int i = 1; i < p.getTotalPlayer(); i++) {
			p.addPlayer("Computer" + i, i);
		} // User 한 명을 제외한 만큼의 수의 Player Computer'n'을 자동으로 playerList에 추가
		c.setDeck(c.getRangeOfCards() * 4 + 1);   // 덱 생성
	}

	public void run() { // 조커 찾기 게임을 텍스트로 진행 (테스트용)
		while (running) {
			show("* * * * * * * * * * * * * * * * * * * *");
			show("Let's play Old maid game!!");
			while(!winner){
				show("- - - - - - - - - - - - - -");
				show(p.currentPlayer.getName() + ": ");
				p.currentPlayer.draw(p.previousPlayer);
				p.currentPlayer.showCards();
				isWin(p.previousPlayer, p.currentPlayer);
				p.nextPlayer();
			}
			show("* * * * * * * * * * * * * * * * * * * *");
			// 4.사용자에게 게임 재진행 여부 묻는다.
			System.out.print("Would you like to exit game? (yes): ");
			if (scanner.next().equals("yes")) end();
			else { // 플레이어 정보는 놔두고 게임 초기화
				p.resetPlayerList();
				winner = false;
			}
		}
	}

	/**
	 * GUI
	 */
	OldMaidGameApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(4,1));
		int width = 1800, height = 900;

		gameSetting(); // 터미널에서 게임 세팅
		// 1. 덱을 섞는다.
		c.shuffle();
		// 2. 플레이어들에게 카드를 나눠준다.
		p.shareCards(c.getRangeOfCards(), c.getDeck());
		// 3. 중복 카드를 버리고 패널을 집어 넣는다.
		for (Player each : p.playerList) {
			each.dumpAll();
			if(each.getLocation() == 0) {
				User user = (User)each;
				user.panel = new UserPanel(user);
				this.add(user.panel);
			}
			else {
				each.panel = new PlayerPanel(each.cardList.size());
				this.add(each.panel);
			}
		}
		// 4. 시작 플레이어부터 게임을 시작한다.
		p.firstPlayer();

		setSize(width, height);
		setVisible(true);
	}

	public static void main(String[] args) {
		OldMaidGameApp game = new OldMaidGameApp();
		game.run();
	}
}