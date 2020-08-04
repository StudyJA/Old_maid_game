
public class OldMaidGameApp extends Player {
	Player[] playerList=new Player[10];
	Card[] deck=new Card[53];
	
	
	void end() {} //게임 종료
	
	public void run() {
		System.out.println("도둑잡기 게임을 시작합니다.");
		while(true) {
			
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