import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class OldMaidGameApp {
	/**
	 *  게임 진행 관련 변수, 메소드
	 */
	Scanner scanner = new Scanner(System.in);
	boolean running = true;  // 전체 게임의 진행중 여부를 나타내는 변수.
	boolean winner  = false; // 승자가 나오면 상태가 true로 바뀌도록 하기
	/*메소드*/
	void end() { running = false; } // 게임 종료

	/**
	 *  플레이어 조작 관련 변수, 메소드
	 */
	ArrayList<Player> playerList = new ArrayList<>(2) ;
	Player currentPlayer; // 현재 턴을 진행 중인 플레이어
	int playerNumber = 0; // 플레이어 리스트에서 순서
	/*메소드*/
	void nextPlayer() {
		if(playerNumber == playerList.size()-1) // 마지막 유저
			playerNumber -= playerList.size();
		currentPlayer = playerList.get(++playerNumber);
	}  // 턴이 넘어갈 때마다 다음 플레이어로 변경
	void firstPlayer() {
		currentPlayer = playerList.get(playerNumber);
		for(;playerNumber < playerList.size(); playerNumber++) {
			if(currentPlayer.isFirst())
				break;
		}
	} // 시작 플레이어 정함
	void addPlayer(String name) { playerList.add(new Player(name));} // 이름 입력받아 playerList에 player 추가

	/**
	 *  카드 조작 관련 변수, 메소드
	 */
	private Vector<Card> deck; // CardList와 같은 Vector로 변경
	/*메소드*/
	void setDeck(int sizeOfDeck) {
		String[] shapes = {"◆", "♥", "♠", "♣"}; // 카드 문양 배정시 사용
		this.deck = new Vector<Card>(sizeOfDeck);
		for(String shape : shapes)
			for(int i=1; i <= 13; i++) {
				this.deck.add(new Card(shape, i));
			}
		this.deck.add(0, new Card("J", 0));
	}
	void shuffle() {
		Collections.shuffle(this.deck);
		//Player 하나 랜덤으로 선정
		//랜덤 선정된 Player.setFirst();
		//카드 배분(first Player가 한 장 더 많이 받도록)

	} //한 장 더 많은 플레이어 랜덤 선정, 그 player를 first로 설정, 카드 배분
	void dump() {} //player에게 뽑은 카드와 동일한 숫자 카드가 있는지 확인하고, 있으면 자동으로 그 두 장을 버림


	public void run() {

		/** Game setting
		 *  플레이어 수 설정, 카드 수 설정, Deck 생성
		 *  초기 버전은 2로 초기화
		 */
		// 시작할 때 자동으로 이름이 Computer인 Player을 playerList에 추가해야 함
		addPlayer("Computer");
		System.out.print("당신의 이름: ");
		addPlayer(scanner.next()); // 사용자 추가

		while(running) { // !isRunning 은 "진행중이 아니라면" 뜻을 가져서 !를 안 쓰도록 수정
			try {
				/** Game Setting
				 1. 카드 나눠주기
				 2.
				 3. 첫 번째 플레이어 지정
				 */
				firstPlayer();
				System.out.println("도둑잡기 게임을 시작합니다.");
				while(!winner) { // 승자가 없으면 게임을 진행한다는 의미에서 !winner 으로 변경
				}
				// 사용자에게 게임 재진행 여부 묻기
				System.out.print("그만 하시겠습니까? (yes): ");
				if(scanner.next().equals("yes"))
					end();
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