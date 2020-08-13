import java.util.*;

public class Player {
	// User 클래스에서 사용하기 위해 name과 location을 protected로 변경
	protected String name;  // 이름
	protected int location; // 게임 보드에서 위치
	private boolean first = false; // 시작 플레이어인지 확인
	private int score = 0;  // 점수
	List<Card> cardList; 	// 플레이어가 가지고 있는 카드들

	Player() {} // 기본 생성자 추가
	Player (String name, int location) {
		this.name = name;
		this.location = location;
	}
	public String getName()  { return name; }
	public int getLocation() { return location; }
	public void setFirst(boolean first) { this.first = first; }
	public boolean isFirst() { return this.first; }
	public void setCardList(List<Card> cardList) { this.cardList = cardList; }
	public void plusScore() { this.score++; } // 점수를 1점 올린다.

	// 이전 플레이어의 cardList에서 랜덤한 카드 한 장을 삭제하고 그를 반환한다.
	Card giveRandomCard(Player prevPlayer) {
		Card card = null;
		int index = 0;
		int i = (int)(Math.random()*prevPlayer.cardList.size());
		for(Iterator<Card> iter = prevPlayer.cardList.iterator();iter.hasNext();) {
			card = iter.next();
			if (index++ == i) {
				iter.remove();
				break;
			}
		}
		return card;
	}

	// 이전 플레이어에게 카드 한 장을 받고 같은 숫자 카드 두 개를 버리며, 이 과정이 출력되지 않도록 한다.
	void draw(Player prevPlayer) {
		Card givenCard = giveRandomCard(prevPlayer); // 이전 플레이어의 무작위 카드
		System.out.println("took a card from "+prevPlayer.getName());
		// 플레이어의 카드리스트에서 같은 숫자를 찾아본다.
		for(Card card: this.cardList)    
			if(card.equals(givenCard)) { // 같은 숫자 카드가 있을 때
				System.out.println("Dump " + card + " and " + givenCard + " from hand");
				this.cardList.remove(card);
				return;
			}
		// 같은 숫자 카드가 없을 때
		cardList.add(givenCard);	
	}
	
	// cardList의 카드가 몇 장인지 출력한다.
	public void showCards() {
		System.out.print(name + ": has " + cardList.size() + " cards");
		System.out.println();
	}
	
	// 자신이 가진 카드 중 중복 숫자 카드를 모두 버린다.
	public void dumpAll() {
		boolean checkedAll=false;
		int j=0;
		while(!checkedAll) {
			if(j>=cardList.size()-1) {
				checkedAll=true;
				continue;
			}
			else {
				Card card1 = cardList.get(j);
				for(int i=j+1; i < cardList.size(); i++) {
					Card card2 = cardList.get(i);
					if(card1.equals(card2)) { // 같은 숫자 카드가 있을 때
						System.out.print(getName()+": ");
						System.out.println("Dump " + card1 + " and " + card2 + " from hand");
						//card1, card2를 cardList에서 삭제
						this.cardList.remove(j);
						this.cardList.remove(i);
						break;
					}
					j++;
				}
			}
		}
	}
}