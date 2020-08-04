
public class OldMaidGameApp extends Player {
	boolean isRunning=false; //전체 게임의 진행중 여부를 나타내는 변수. end()를 통해 false로 바뀜
	boolean finished=false; //while문의 반복 조건으로 사용할 변수. 승자가 나오면 상태가 true로 바뀌도록 하기
	
	Player[] playerList=new Player[10];
	Card[] deck=new Card[53]; //playerList, deck 배열은 vector 등 제네릭 종류로 변경해도 상관 없을 것 같아요
	
	Player isPlaying=new Player();
	void setPlayer(Player a) {isPlaying=a;} //턴이 넘어갈 때마다 isPlaying player를 변경
	
	void addPlayer() {} //player 이름 입력받아 playerList에 player 추가
	void shuffle() {
		//Player 하나 랜덤으로 선정
		//랜덤선정된Player.setFirst();
		//카드 배분(first Player가 한 장 더 많이 받도록)
		
	} //한 장 더 많은 플레이어 랜덤 선정, 그 player를 first로 설정, 카드 배분
	void dump() {} //player에게 뽑은 카드와 동일한 숫자 카드가 있는지 확인하고, 있으면 자동으로 그 두 장을 버림
	void end() {isRunning=false;} //게임 종료
	
	public void run() {
		isRunning=true;
		
		//시작할 때 자동으로 이름이 computer인 Player을 playerList에 추가해야 함
		addPlayer(); //사용자 추가
		
		while(!isRunning) {
			
			System.out.println("도둑잡기 게임을 시작합니다.");
			while(!finished) {
				/*
				 
				 */
			}
			
			//사용자에게 게임 재진행 여부 묻기
			if(재진행)
				continue;
			else
				end();
		}
	} //전체 게임 진행
}

/*
카드 배분
짝을 맞추어 자신의 카드 버리기
A부터 상대의 카드 랜덤 한 장 뽑기(A=홀수 장의 카드를 받은 플레이어)
A에게 뽑은 숫자와 같은 숫자 카드가 있으면 버리기, 없으면 그대로 턴 종료
둘 중 한 플레이어가 모든 카드를 버릴 때까지 반복
승자 선정
*/
