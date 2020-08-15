import java.util.*;

/**
 *  게임을 세팅하고 턴을 진행하고 승자와 패자를 가리고 점수를 계산할 때 필요한 메소드를 모아놓은 클래스
 */
public class OldMaidGameApp extends Thread {

	Scanner scanner  = new Scanner(System.in);
	boolean running  = true;  // 전체 게임의 진행중 여부를 나타내는 변수.
	boolean winner   = false; // 승자가 나오면 상태가 true로 바뀌도록 하기
	ControlPlayer p  = new ControlPlayer();
	ControlCard   c  = new ControlCard();

	// 누군가 카드를 모두 버렸을 경우 승자와 패자 결정 및 점수 계산
	void isWin(Player previousPlayer, Player currentPlayer) {
		Player loser = p.findJoker();
		if (previousPlayer.cardList.size() == 0) {
			winner = true;
			show(previousPlayer.getName() + " is winner!!");
			previousPlayer.plusScore();
			show(loser.getName() + " has Joker!");
			loser.minusScore();
		}
		else if (currentPlayer.cardList.size() == 0) {
			winner = true;
			show(currentPlayer.getName() + " is winner!!");
			currentPlayer.plusScore();
			show(loser.getName() + " has Joker!");
			loser.minusScore();
		}
	}

	// 게임 종료
	void end() { running = false; }

	// 콘솔 출력
	void show(String msg) {
		System.out.println(msg);
	}

	// 플레이어 설정, 카드 범위 설정, 덱 생성
	void gameSetting() {
		synchronized (this) {
			show("Game setting...");

			System.out.print("How many players with you?: ");
			p.setTotalPlayer(scanner.nextInt());

			System.out.print("Range of Cards(1~?): ");
			c.setRangeOfCards(scanner.nextInt());

			show("Please type your name");
			System.out.print("Player's name: ");
			p.addUser("test", 0);
			for (int i = 1; i < p.getTotalPlayer(); i++) {
				p.addPlayer("Computer" + i, i);
			} // User 한 명을 제외한 만큼의 수의 Player Computer'n'을 자동으로 playerList에 추가
			c.setDeck(c.getRangeOfCards() * 4 + 1);   // 덱 생성
			notify();
		}
	}

	public void run() {
		// 조커 찾기 게임을 진행
		synchronized (this) {
			while (running) {
				// 1. 덱을 섞는다.
				c.shuffle();
				// 2. 각 플레이어가 카드를 받는다.
				p.givenCards(c.getRangeOfCards(), c.getDeck());
				// 2. 자신이 가지고 있는 중복 카드들을 버린다.
				for (Player each : p.playerList) each.dumpAll();
				// 3. 시작 플레이어부터 게임을 시작한다.
				p.firstPlayer();
				// 3. 어떤 플레이어가 카드를 모두 버릴 때까지 게임을 진행한다.
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
			notify();
		}
	}

} //End Class OldMaidGameApreviousPlayer

/**
 *  플레이어 정보를 설정하고 플레이어 목록을 만들어 조작한다.
 *  주요 변수: totalPlayer, playerList, currentPlayer
 */
class ControlPlayer {
	private int totalPlayer  = 2;  // 전체 플레이어 수 나중에 조절가능
	Player previousPlayer; // 바로 전 턴을 진행한 플레이어
	Player currentPlayer;  // 현재 턴을 진행 중인 플레이어
	ArrayList<Player> playerList = new ArrayList<>(totalPlayer);

	void setTotalPlayer(int totalPlayer) { this.totalPlayer = totalPlayer; }
	int getTotalPlayer() { return totalPlayer; }

	// 다음 플레이어를 currentPlayer로 현재 플레이어를 이전 플레이어로 설정
	void nextPlayer() {
		synchronized (this) {
			previousPlayer = currentPlayer;
			if(currentPlayer == playerList.get(totalPlayer-1)) // 마지막 플레이어
				currentPlayer = playerList.get(0);
			else
				currentPlayer = playerList.get(currentPlayer.getLocation() + 1);
		}
	}

	// currentPlayer를 시작 플레이어로 변경
	// 시작 플레이어를 찾고 previousPlayer까지 변경
	void firstPlayer() {
		for(int i = 0; i<totalPlayer; i++) {
			currentPlayer = playerList.get(i);
			if(currentPlayer.isFirst()){
				if(i == 0)
					previousPlayer = playerList.get(totalPlayer-1);
				else
					previousPlayer = playerList.get(i-1);
				break;
			}
		}
	}

	// 이름과 위치를 받아 플레이어 생성
	void addPlayer(String name, int location) {
		playerList.add(new Player(name, location));
	}
	// addPlayer와 같은 역할로, User 플레이어 생성
	void addUser(String name, int location) {
		playerList.add(new User(name, location));
	}

	void resetPlayerList() {
		firstPlayer();
		currentPlayer.setFirst(false); // 시작 플레이어 없앰
		Iterator<Player> iter = playerList.iterator();
		while (iter.hasNext()) { // 플레이어가 가지고 있는 카드 모두 버림
			Player player = iter.next();
			player.cardList.clear();
		}
	}

	// Find player who has joker
	Player findJoker() {
		for(int i=0; i < totalPlayer; i++) {
			for (Card card : playerList.get(i).cardList)
				if (card.getNumber() == 0) return playerList.get(i);
		}
		return null;
	}

	// Distribute the deck to players and set a first player
	void givenCards(int rangeOfCards, Vector<Card> deck) {
		int randNum = (int)(Math.random()*totalPlayer);
		currentPlayer = playerList.get(randNum);
		currentPlayer.setFirst(true); // 카드를 첫번째로 받는 플레이어를 시작 플레이어로 설정
		int totalCards = rangeOfCards*4 + 1; // 전체 카드 수
		int shareCards = (int)(totalCards/totalPlayer);
		int startIndex = 0;
		int endIndex   = shareCards;

		// 모든 인원이 똑같이 받는 카드들
		for(int i=0; i<totalPlayer; i++) {
			List<Card> bundle = deck.subList(startIndex, endIndex);
			currentPlayer.setCardList(new Vector<Card>(bundle));
			startIndex += shareCards;
			endIndex   += shareCards;
			nextPlayer();
		}
		// 나머지 카드
		for(int i=0; i < totalCards%shareCards; i++) {
			currentPlayer.cardList.add(deck.get(startIndex+i));
			nextPlayer();
		}
	}
}

/**
 *  덱을 생성하고 플레이어에게 카드를 나눠주는 메소드를 모아놓은 클래스
 *  주요 변수: cardList
 */
class ControlCard {
	int rangeOfCards = 13;    // 카드 숫자 범위
	private Vector<Card> deck;

	void setRangeOfCards(int rangeOfCards) { this.rangeOfCards = rangeOfCards; }
	int getRangeOfCards() { return rangeOfCards; }

	void setDeck(int sizeOfDeck) {
		String[] shapes = {"◆", "♥", "♠", "♣"};
		this.deck = new Vector<Card>(sizeOfDeck);
		for(String shape : shapes)
			for(int i=1; i <= rangeOfCards; i++) {
				this.deck.add(new Card(shape, i));
			}
		this.deck.add(new Card("J", 0));
	}

	Vector<Card> getDeck() { return deck; }
	// deck 을 섞는다.
	void shuffle() {
		Collections.shuffle(this.deck);
	}
}




