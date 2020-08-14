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
	public void plusScore() { this.score++; } 	// Winner gets 1 point
	public void minusScore() { this.score++; }  // Joker gets -1 point

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
	
	// Dump same number pair
	public void dumpAll() {
		final int size = cardList.size();
		int number_index_list[][] = new int[size][2];
		boolean to_delete[] = new boolean[size];
		for(int i=0; i<size; i++) {
			number_index_list[i][0] = cardList.get(i).getNumber();
			number_index_list[i][1] = i; // cardList's index
			to_delete[i] = false;
		}
		Arrays.sort(number_index_list, Comparator.comparingDouble(o -> o[0]));

		// When same number pair appeared sequentially
		// in sorted list of card number
		// <to_delete> matching both card's index are set true
		System.out.print(name + ": Dump cards  ");
		int prev, prev_index, current, current_index;
		for(int i=1; i<size; i++) {

			prev = number_index_list[i-1][0];
			prev_index = number_index_list[i-1][1];
			current = number_index_list[i][0];
			current_index = number_index_list[i][1];

			if(prev == current) {
				System.out.print(cardList.get(prev_index) + "&" + cardList.get(current_index) + " ");
				to_delete[prev_index] = true;
				to_delete[current_index] = true;
				i++; // Skip next card
			}
		}
		System.out.println();

		// By reverse order. Remove that to_delete is true
		for(int j=size-1; j>=0; j--)
			if(to_delete[j]) {
				cardList.remove(j);
			}
	}
}