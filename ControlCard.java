import java.util.*;

/**
 *  덱을 생성하고 플레이어에게 카드를 나눠주는 메소드를 모아놓은 클래스
 *  주요 변수: cardList
 */
class ControlCard {
	int rangeOfCards = 13;    // 카드 숫자 범위
	private Vector<Card> deck;

	void setRangeOfCards(int rangeOfCards) { this.rangeOfCards = rangeOfCards; }
	int getRangeOfCards() { return rangeOfCards; }

	void setDeck(int sizeOfDeck) {
		String[] shapes = {"dia", "heart", "spade", "clover"};
		this.deck = new Vector<Card>(sizeOfDeck);
		for(String shape : shapes)
			for(int i=1; i <= rangeOfCards; i++) {
				this.deck.add(new Card(shape, i));
			}
		this.deck.add(new Card("joker", 0));
	}

	Vector<Card> getDeck() { return deck; }
	// deck 을 섞는다.
	void shuffle() {
		Collections.shuffle(this.deck);
	}
}
