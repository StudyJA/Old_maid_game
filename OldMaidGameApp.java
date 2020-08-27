import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 *  게임을 세팅하고 턴을 진행하고 승자와 패자를 가리고 점수를 계산할 때 필요한 메소드를 모아놓은 클래스
 *  8/24 GUI 추가: 카드 이미지로 게임 상황을 보여줄 수 있다.
 */
public class OldMaidGameApp extends JFrame {

	Scanner scanner  = new Scanner(System.in);
	static boolean winner   = false; // 승자가 나오면 상태가 true로 바뀌도록 하기
	ControlPlayer p  = new ControlPlayer();
	ControlCard   c  = new ControlCard();



	// 플레이어 설정, 카드 범위 설정, 덱 생성
	void gameSetting() {
		System.out.println("Game setting...");
		System.out.print("How many players with you?: ");
		p.totalPlayer = scanner.nextInt();
		System.out.print("Range of Cards(1~13): ");
		c.rangeOfCards = scanner.nextInt();
		BoardPanel.showRight( "Number of players: " + p.totalPlayer + ", Range of cards: 1~" + c.rangeOfCards);

		for (int i = 0; i < p.totalPlayer -1; i++) {
			p.addPlayer("Computer" + i, i);
		} // User 한 명을 제외한 만큼의 수의 Player Computer'n'을 자동으로 playerList에 추가

		System.out.print("Please type your name: ");
		scanner.nextLine();
		p.addUser(scanner.nextLine(), p.totalPlayer-1); // 유저 패널은 맨 밑에 위치
		c.setDeck(c.rangeOfCards * 4 + 1);   // 덱 생성
	}

	/**
	 * GUI
	 */
	OldMaidGameApp() {
		super("Old maid game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameSetting(); // 콘솔에서 게임 세팅
		setLayout(new GridLayout(p.totalPlayer+1,1)); // 전체 플레이어 수로 세로크기 결정
		setBackground(Color.white);
		BoardPanel boardPanel = new BoardPanel(); // GUI
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
			if(!each.user)
				each.panel.cover();
			else
				each.panel.uncover(); // 유저 패널만 카드 앞면 보여주기
			this.add(each.panel);
			if(maxSize < each.cardList.size())
				maxSize = each.cardList.size();
		}
		// 4. 시작 플레이어 설정
		p.firstPlayer();

		setSize(72*(maxSize+4), 101*(p.totalPlayer+2));
		setVisible(true);
	}

}

class Main implements Runnable {
	static OldMaidGameApp game;
	static Scanner scanner = new Scanner(System.in);
	public void run() { // 게임의 승자가 나오면 게임을 중지 하고 점수 계산 후 다시 시작할 지 정함
		System.out.println("* * * * * * * * * * * * * * * * * * * *");
		System.out.println("Let's play Old maid game!!");
		try{
			while (!game.winner) {
				System.out.println("- - - - - - - - - - - - - -");
				BoardPanel.showLeft(game.p.currentPlayer.name + "'s turn ");
				game.p.currentPlayer.draw(game.p.previousPlayer);
				game.p.currentPlayer.showCards();
				game.p.nextPlayer();
				Thread.sleep(3000);
			}
			for(Player player: game.p.playerList) player.panel.uncover(); // 카드 전부 공개
			game.p.findJoker(); // 조커 찾기
			System.out.println("* * * * * * * * * * * * * * * * * * * *");
			System.out.print("Would you like to exit game? (yes):");
			if("yes".equals(scanner.next())) {
				// 1. 덱, 플레이어 초기화
				game.p.resetPlayerList();
				game.c.resetDeck();
				// 2. 덱을 섞고 카드 분배
				game.c.shuffle();
				game.p.shareCards(game.c.rangeOfCards, game.c.getDeck());
				// 3. 중복카드 제거
				for (Player each : game.p.playerList) {
					each.dumpAll();
					if(!each.user)
						each.panel.cover();
					else
						each.panel.uncover(); // 유저 패널만 uncover
					each.panel.revalidate();
					each.panel.repaint();
				}
				Thread tmpThread = new Thread(new Main());
				tmpThread.start();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		game = new OldMaidGameApp();
		Thread tmpThread = new Thread(new Main());
		tmpThread.start();
	}

}