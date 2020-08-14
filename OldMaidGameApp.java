import java.util.*;

public class OldMaidGameApp {
	/**
	 *  게임 진행 관련 변수, 메소드
	 */
	Scanner scanner  = new Scanner(System.in);
	boolean running  = true;  // 전체 게임의 진행중 여부를 나타내는 변수.
	boolean winner   = false; // 승자가 나오면 상태가 true로 바뀌도록 하기
	int totalPlayer  = 2;	  // 전체 플레이어 수 나중에 조절가능
	int rangeOfCards = 13;    // 카드 숫자 범위
	/*메소드*/
	// 게임 종료
	void end() { running = false; }

	// 플레이어 설정, 카드 범위 설정, 덱 생성
	void gameSetting() {
		System.out.println("Game setting...");
		System.out.print("How many players with you?: ");
		totalPlayer = scanner.nextInt();
		System.out.print("Maximum value of Cards: ");
		rangeOfCards = scanner.nextInt();
		System.out.println("Please type your name");
		System.out.print("Player's name: ");
		scanner.nextLine();
		addUser(scanner.nextLine(), 0);
		for(int i=1; i < totalPlayer; i++) {
			addPlayer("Computer"+i, i);
		} // User 한 명을 제외한 만큼의 수의 Player Computer'n'을 자동으로 playerList에 추가
		setDeck(rangeOfCards*4 + 1);   // 덱 생성
	}

	/**
	 *  플레이어 조작 관련 변수, 메소드
	 */
	ArrayList<Player> playerList = new ArrayList<>(totalPlayer);
	Player prevPlayer;     // 바로 전 턴을 진행한 플레이어
	Player currentPlayer;  // 현재 턴을 진행 중인 플레이어
	/*메소드*/
	// currentPlayer를 다음 플레이어로 변경
	void nextPlayer() {
		prevPlayer = currentPlayer;
		if(currentPlayer == playerList.get(totalPlayer - 1)) // 마지막 유저
			currentPlayer = playerList.get(0);
		else
			currentPlayer = playerList.get(currentPlayer.getLocation() + 1);
	}
	
	// currentPlayer를 시작 플레이어로 변경
	void firstPlayer() {   
		for(int i = 0; i<totalPlayer; i++) {
			currentPlayer = playerList.get(i);
			if(currentPlayer.isFirst()){
				if(i == 0)
					prevPlayer = playerList.get(totalPlayer-1);
				else
					prevPlayer = playerList.get(i-1);
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

	/**
	 *  카드 조작 관련 변수, 메소드
	 */
	private Vector<Card> deck; // CardList와 같은 Vector로 변경
	/*메소드*/
	void setDeck(int sizeOfDeck) {
		String[] shapes = {"◆", "♥", "♠", "♣"}; 
		this.deck = new Vector<Card>(sizeOfDeck);
		for(String shape : shapes)
			for(int i=1; i <= rangeOfCards; i++) {
				this.deck.add(new Card(shape, i));
			}
		this.deck.add(new Card("J", 0));
	}

	/*
	 * 플레이어에게 카드를 나눠주는 shuffle
	 * 카드를 더 많이 받은 플레이어 한 명의 first를 true로 설정
	 */
	void shuffle() {
		Collections.shuffle(this.deck);
		int randNum = (int)(Math.random()*totalPlayer);
		currentPlayer = playerList.get(randNum);
		currentPlayer.setFirst(true);
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

	// Search a player who has joker
	void findJoker() {
		for(int i=0; i < totalPlayer; i++) {
			nextPlayer();
			for (Card card : currentPlayer.cardList)
				if (card.getNumber() == 0) {
					System.out.println(currentPlayer.getName() + " has the Joker!");
					currentPlayer.minusScore();
					return;
				}
		}
	}

	public void run() {
		// 플레이어 설정, 카드 범위 설정, 덱 생성
		gameSetting();
		// 조커 찾기 게임을 진행
		while(running) {
			try {
				// 1. 덱을 섞고, 중복 카드를 버린다.
				shuffle();
				
				for(int i=0; i<totalPlayer; i++) {
					playerList.get(i).dumpAll();
				}
				
				// 2. 시작 플레이어부터 게임을 시작한다.
				firstPlayer();
				// 3. 어떤 플레이어가 카드를 모두 버릴 때까지 게임을 진행한다.
				System.out.println("* * * * * * * * * * * * * * * * * * * *");
				System.out.println("Let's play Old maid game!!");
				while(!winner) {
					System.out.println("- - - - - - - - - - - - - -");
					System.out.print(currentPlayer.getName() + ": ");
					currentPlayer.draw(prevPlayer);
					currentPlayer.showCards();
					// 누군가 카드를 모두 버렸을 경우 승패를 결정한다.
					if (prevPlayer.cardList.size() == 0) {
						System.out.println(prevPlayer.getName() + " is winner!!");
						prevPlayer.plusScore();
						winner = true;
						findJoker();
					}
					else if (currentPlayer.cardList.size() == 0) {
						System.out.println(currentPlayer.getName() + " is winner!!");
						winner = true;
						findJoker();
					}
					nextPlayer();
				}
				System.out.println("* * * * * * * * * * * * * * * * * * * *");

				// 4.사용자에게 게임 재진행 여부 묻는다.
				System.out.print("Would you like to exit game? (yes): ");
				if(scanner.next().equals("yes")) end();
				else { // 플레이어 정보는 놔두고 게임 초기화
					firstPlayer();
					currentPlayer.setFirst(false); // 시작 플레이어 없앰
					winner = false; 			   // 승자 없앰
					Iterator<Player> iter = playerList.iterator();
					while (iter.hasNext()) { // 플레이어가 가지고 있는 카드 모두 버림
						Player player = iter.next();
						player.cardList.clear();
					}
				}
			}
			catch (Exception e) { // 예외 발생 시 end()로 정상 종료 하도록 예외처리
				System.out.println(e.getMessage());
				end();
			}
		}
	}

	public static void main(String[] args) {
		OldMaidGameApp game = new OldMaidGameApp();
		game.run();
	}
}