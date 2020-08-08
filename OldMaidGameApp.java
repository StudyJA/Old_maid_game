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
		System.out.println("Proceed to old maid game setup");
		System.out.print("Total Player Counts?: ");
		totalPlayer = scanner.nextInt();
		System.out.print("Maximum value of Cards: ");
		rangeOfCards = scanner.nextInt();
		System.out.println("Please type your name");
		// 이름이 Computer인 Player을 playerList에 추가
		addPlayer("Computer", 0);
		scanner.nextLine();
		for(int i=1; i < totalPlayer; i++) {
			System.out.print(i + " Player's name: ");
			addPlayer(scanner.nextLine(), i);
		}
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
	 * 플레이어에게 카드를 나눠주는 shuffle 입니다.
	 * 카드를 더 많이 받은 한명의 플레이어의 first를 true로 설정합니다.
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

	// 조커를 가지고 있는 플레이어를 찾는다.
	void findJoker() {
		for(int i=0; i < totalPlayer; i++) {
			nextPlayer();
			for (Card card : currentPlayer.cardList)
				if (card.getNumber() == 0) {
					System.out.println(currentPlayer.getName() + " has the Joker!!!");
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
				// 1. 덱을 섞는다.
				shuffle();
				// 2. 시작 플레이어부터 게임을 시작한다.
				firstPlayer();
				// 3. 어떤 플레이어가 카드를 모두 버릴 때까지 게임을 진행한다.
				System.out.println("Let's play Old maid game!!");
				while(!winner) {
					System.out.print(currentPlayer.getName() + ": ");
					currentPlayer.draw(prevPlayer);
					if(!currentPlayer.getName().equals("Computer")) // 컴퓨터가 아니면 카드를 보여준다.
						currentPlayer.showCards();
					// 누군가 카드를 모두 버렸을 경우 승패를 결정한다.
					if (prevPlayer.cardList.size() == 0) {
						System.out.println(prevPlayer.getName() + " is winner!!");
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

				// 4.사용자에게 게임 재진행 여부 묻는다.
				System.out.print("Want game exit? (yes): ");
				if(scanner.next().equals("yes")) end();
				else { // 시작 플레이어 초기화
					firstPlayer();
					currentPlayer.setFirst(false);
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
