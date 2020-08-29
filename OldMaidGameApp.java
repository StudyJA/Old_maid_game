import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 *  게임을 세팅하고 턴을 진행하고 승자와 패자를 가리고 점수를 계산할 때 필요한 메소드를 모아놓은 클래스
 *  8/24 GUI 추가: 카드 이미지로 게임 상황을 보여줄 수 있다.
 */
public class OldMaidGameApp extends JFrame {

	static Scanner scanner = new Scanner(System.in);
	static boolean running = true; // 게임을 재시작 하는데 필요한 플래그
	ControlPlayer p = new ControlPlayer();
	ControlCard c = new ControlCard();
	
	BoardPanel boardPanel = new BoardPanel(); // GUI
	
	// 플레이어 설정, 카드 범위 설정, 덱 생성
	void gameSetting() {
		System.out.println("Game setting...");
		System.out.print("How many players with you?: ");
		p.totalPlayer = scanner.nextInt();
		System.out.print("Range of Cards(1~13): ");
		c.rangeOfCards = scanner.nextInt();
		BoardPanel.showRight("Number of players: " + p.totalPlayer + ", Range of cards: 1~" + c.rangeOfCards);

		for (int i = 0; i < p.totalPlayer - 1; i++) {
			p.addPlayer("Computer" + i, i);
		} // User 한 명을 제외한 만큼의 수의 Player Computer'n'을 자동으로 playerList에 추가

		System.out.print("Please type your name: ");
		scanner.nextLine();
		p.addUser(scanner.nextLine(), p.totalPlayer - 1); // 유저 패널은 맨 밑에 위치
		c.setDeck(c.rangeOfCards * 4 + 1);   // 덱 생성
	}

	void restart() {
		// 1. 덱, 플레이어 초기화
		p.resetPlayerList();
		c.resetDeck();
		// 2. 덱을 섞고 카드 분배
		c.shuffle();
		p.shareCards(c.rangeOfCards, c.getDeck());
		// 3. 중복카드 제거
		for (Player each : p.playerList) {
			each.dumpAll();
			if (!each.user)
				each.panel.cover();
			else
				each.panel.uncover(); // 유저 패널만 uncover
			each.panel.revalidate();
			each.panel.repaint();
		}
	}

	/**
	 * GUI
	 */
	OldMaidGameApp() {
		super("Old maid game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameSetting(); // 콘솔에서 게임 세팅
		setLayout(new GridLayout(p.totalPlayer + 1, 1)); // 전체 플레이어 수로 세로크기 결정
		setBackground(Color.white);
		BoardPanel.scoreBoard.setLayout(new GridLayout(p.totalPlayer, 1));
		this.add(boardPanel);
		// 1. 덱을 섞는다.
		c.shuffle();
		// 2. 플레이어들에게 카드를 나눠준다.
		p.shareCards(c.rangeOfCards, c.getDeck());
		// 3. 중복 카드를 버리고 패널을 집어 넣는다.
		int maxSize = 0; // 윈도창 가로 너비 설정을 위한 최대 카드 소지 개수
		for (Player each : p.playerList) {
			each.dumpAll();
			each.panel = new GamePanel(each);
			if (!each.user)
				each.panel.cover();
			else
				each.panel.uncover(); // 유저 패널만 카드 앞면 보여주기
			this.add(each.panel);
			if (maxSize < each.cardList.size())
				maxSize = each.cardList.size();

		}
		BoardPanel.addScoreList(p.playerList); // 스코어 보드에 플레이어 정보를 추가
		// 4. 시작 플레이어 설정
		p.firstPlayer();

		setSize(72 * (maxSize + 4), 101 * (p.totalPlayer + 2));
		setVisible(true);
	}

	static boolean isWin(Player prevPlayer, Player currentPlayer) {
		if (prevPlayer.cardList.size() == 0) {
			prevPlayer.score++;
			BoardPanel.scoreList.get(prevPlayer.location).setText(prevPlayer.name+": "+prevPlayer.score);
			prevPlayer.panel.add(new JLabel(prevPlayer.name + " is Winner!!", SwingConstants.CENTER));
			prevPlayer.panel.revalidate();
			prevPlayer.panel.repaint();
			return true;
		} else {
			currentPlayer.dump(); // 현재 플레이어가 중복 카드를 버린 후
			if (currentPlayer.cardList.size() == 0) {
				currentPlayer.score++;
				BoardPanel.scoreList.get(currentPlayer.location).setText(currentPlayer.name + ": " + currentPlayer.score);
				currentPlayer.panel.removeAll();
				currentPlayer.panel.add(new JLabel(currentPlayer.name + " is Winner!!", SwingConstants.CENTER));
				currentPlayer.panel.revalidate();
				currentPlayer.panel.repaint();
				return true;
			}
		}
		return false; // 승자 없음
	}

	public static void main(String[] args) throws InterruptedException {
		OldMaidGameApp game = new OldMaidGameApp();
		boolean winner = false; // 승자가 나올때 까지 게임 진행 시키는 플래그
		while (running) {
			while (!winner) {
				Player prevPlayer = game.p.previousPlayer;
				Player currentPlayer = game.p.currentPlayer;
				BoardPanel.scoreList.get(prevPlayer.location).setBackground(Color.white);
				BoardPanel.scoreList.get(currentPlayer.location).setBackground(Color.GRAY); // 턴 표시
				currentPlayer.draw(prevPlayer);
				
				Thread.sleep(3000); // 시간 3초 지연
				
				winner = isWin(prevPlayer, currentPlayer);
				game.p.nextPlayer();
			}
			game.p.findJoker();
			for (Player player : game.p.playerList) player.panel.uncover(); // 카드 전부 공개
			BoardPanel.updateScoreList(game.p.playerList); // 점수 업데이트
			BoardPanel.defaultCenter();
			System.out.print("Would you like to exit game? (yes):");
			if ("yes".equals(scanner.next())) running = false; // 게임 종료
			else { // 게임 다시 시작
				winner = false;
				game.restart();
			}
		}


	}
}