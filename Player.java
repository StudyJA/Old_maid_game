import java.util.*;

public class Player {
	private String name;  // 이름
	private int location; // 게임 보드에서 위치
	private boolean first = false; // 시작 플레이어인지 확인
	private int score = 0;  // 점수
	List<Card> cardList; 	// 플레이어가 가지고 있는 카드들

	Player (String name, int location) {
		this.name = name;
		this.location = location;
	}
	public String getName()  { return name; }
	public int getLocation() { return location; }
	public void setFirst(boolean first) { this.first = first; }
	public boolean isFirst() { return this.first; }
	public void setCardList(List<Card> cardList) { this.cardList = cardList; }

	// cardList에서 카드를 삭제하고 반환한다.
	Card giveRandomCard() {
		Card card = null;
		int index = 0;
		int i = (int)(Math.random()*cardList.size());
		for(Iterator<Card> iter = cardList.iterator();iter.hasNext();) {
			card = iter.next();
			if (index++ == i) {
				iter.remove();
				break;
			}
		}
		return card;
	}

	// 이전 플레이어에게 카드 한장을 받고 같은 숫자 카드 두개를 버린다.
	void draw(Player prevPlayer) {
		Card givenCard = prevPlayer.giveRandomCard(); // 이전 플레이어의 무작위 카드
		System.out.println(prevPlayer.getName()+ " to give " + givenCard +" get!");
		System.out.print(name+": ");
		// 플레이어의 카드리스트에서 같은 숫자를 찾아본다.
		for(Card card: this.cardList)    
			if(card.equals(givenCard)) { // 같은 숫자 카드가 있을 때
				System.out.println( "Dump" + card + " and " + givenCard + " in hand");
				this.cardList.remove(card);
				return;
			}
		// 같은 숫자 카드가 없을 때
		System.out.println("There are no pair with same number");
		cardList.add(givenCard);	
	}

	// cardList의 카드를 모두 보여준다.
	public void showCards() {
		if (cardList != null) {
			System.out.print(name + ": ");
			Iterator<Card> iter = cardList.iterator();
			while (iter.hasNext())
				System.out.print(iter.next() +" ");
			System.out.println();
		}
	} 
}
