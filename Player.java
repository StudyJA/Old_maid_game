import java.util.*;

public class Player {
	private String name; //이름
	private boolean first = false; // 시작 플레이어
	int score=0; //점수
	Vector<Card> cardList; 	// 가진 카드 목록
	// cardList는 OldMaidGameApp에서 생성 후 객체를 넘겨준다.

	Player (String name) {  // 플레이어 이름을 받아 객체를 생성 하도록 변경
		this.name = name;
	}
	public String getName() {return name;} //player의 이름 반환
	void setFirst(boolean isFirst) { this.first = first;}
	boolean isFirst() { return this.first;}
	void setCardList(Vector<Card> cardList) { this.cardList = cardList; }

	void draw() {
		//옆 player cardList의 size() 중 랜덤으로 숫자 한 개 선정

		//옆 player cardList의 그 번호 카드를 삭제하고 player의 cardList에 추가

	} //옆 플레이어의 카드 랜덤으로 한 장 뽑아오기

	public void showCards() {
		if ( cardList != null) {
			Iterator<Card> iter = cardList.iterator();
			while (iter.hasNext())
				System.out.print(iter.next() +" ");
			System.out.println();
		}
	} //cardList의 카드들을 모두 출력
}

