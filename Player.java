import java.util.*;

public class Player extends Card {
	private String name; //이름
	boolean isFirst=false; //순서
	int score=0; //점수
	
	Vector<Card> cardList = new Vector<Card>(); //가진 카드 목록
	
	String getName() {return name;} //player의 이름 반환
	void setName() {} //player 이름 설정
	void setFirst() {} //순서 설정
	
	void draw() {
		//옆 player cardList의 size() 중 랜덤으로 숫자 한 개 선정
		//옆 player cardList의 그 번호 카드를 삭제하고 player의 cardList에 추가
		
	} //옆 플레이어의 카드 랜덤으로 한 장 뽑아오기

	void showCards() {} //cardList의 카드들을 모두 출력
}
