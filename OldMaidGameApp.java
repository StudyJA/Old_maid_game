import java.util.*;

public class OldMaidGameApp {
	/**
	 *  게임 진행 관련 변수, 메소드
	 */
	Scanner scanner = new Scanner(System.in);
	boolean running = true;  // 전체 게임의 진행중 여부를 나타내는 변수.
	boolean winner  = false; // 승자가 나오면 상태가 true로 바뀌도록 하기
	int totalPlayer = 2;	 // 전체 플레이어 수 나중에 조절가능
	int rangeOfCards   = 13;    // 카드 숫자 범위
	/*메소드*/
	void end() { running = false; } // 게임 종료

	/**
	 *  플레이어 조작 관련 변수, 메소드
	 */
	ArrayList<Player> playerList = new ArrayList<>(totalPlayer);
	Player currentPlayer; // 현재 턴을 진행 중인 플레이어
	int currentNumber = 0; // 플레이어 리스트에서 순서
	/*메소드*/
	void nextPlayer() {
		if(++currentNumber >= totalPlayer) // 마지막 유저
			currentNumber = 0;
		currentPlayer = playerList.get(currentNumber);
	}  // 턴이 넘어갈 때마다 다음 플레이어로 변경
	void firstPlayer() {
		for(int i = 0; i < totalPlayer; i++) {
			currentPlayer = playerList.get(i);
			if(currentPlayer.isFirst()){
				currentNumber = i;
				break;
			}
		}
	} // 시작 플레이어 정함
	void addPlayer(String name) { playerList.add(new Player(name)); } // 이름 입력받아 playerList에 player 추가

	/**
	 *  카드 조작 관련 변수, 메소드
	 */
	private Vector<Card> deck; // CardList와 같은 Vector로 변경
	/*메소드*/
	void setDeck(int sizeOfDeck) {
		String[] shapes = {"◆", "♥", "♠", "♣"}; // 카드 문양 배정시 사용
		this.deck = new Vector<Card>(sizeOfDeck);
		for(String shape : shapes)
			for(int i=1; i <= rangeOfCards; i++) {
				this.deck.add(new Card(shape, i));
			}
		this.deck.add(new Card("J", 0));
	}
	void shuffle() {
		Collections.shuffle(this.deck);
		currentNumber = (int)(Math.random()*totalPlayer);
		currentPlayer = playerList.get(currentNumber);
		currentPlayer.setFirst(true);
		//전체 카드수 계산
		int totalCards = rangeOfCards*4 + 1; //전체 카드 숫자
		int shareCards = (int)(totalCards/totalPlayer);
		int startIndex = 0;
		int endIndex   = shareCards;

		// 인원 수에 맞춰 공평하게 배분되는 카드들
		for(int i=0; i<totalPlayer; i++) {
			currentPlayer.setCardList(new Vector<Card>(deck.subList(startIndex, endIndex)));
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
	void dump() {} //player에게 뽑은 카드와 동일한 숫자 카드가 있는지 확인하고, 있으면 자동으로 그 두 장을 버림


	public void run() {

		/** Game setting
		 *  플레이어 수 설정, 카드 수 설정, Deck 생성
		 *  초기 버전은 2로 초기화
		 */
		// 시작할 때 자동으로 이름이 Computer인 Player을 playerList에 추가해야 함
		addPlayer("Computer");
		System.out.print("당신의 이름: ");
		addPlayer(scanner.nextLine()); // 사용자 추가
		setDeck(rangeOfCards*4 +1);

		while(running) { // !isRunning 은 "진행중이 아니라면" 뜻을 가져서 !를 안 쓰도록 수정
			try {
				/** Game Setting
				 1. 카드 나눠주기
				 2.
				 3. 첫 번째 플레이어 지정
				 */
				shuffle();
				firstPlayer();
				System.out.println("도둑잡기 게임을 시작합니다.");
				/*
				while(!winner) {
				}
				*/
				for(int i=0; i<totalPlayer; i++) {
					currentPlayer.showCards();
					nextPlayer();
				}


				// 사용자에게 게임 재진행 여부 묻기
				System.out.print("그만 하시겠습니까? (yes): ");
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
	} //전체 게임 진행

	public static void main(String[] args) {
		var game = new OldMaidGameApp();
		game.run();
	}
}
/*
카드 배분
짝을 맞추어 자신의 카드 버리기
A부터 상대의 카드 랜덤 한 장 뽑기(A=홀수 장의 카드를 받은 플레이어)
A에게 뽑은 숫자와 같은 숫자 카드가 있으면 버리기, 없으면 그대로 턴 종료
둘 중 한 플레이어가 모든 카드를 버릴 때까지 반복
승자 선정
*/
