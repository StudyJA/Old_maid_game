import java.util.*;

/**
 *  덱을 생성하거나 섞는 메소드를 모아놓은 클래스
 *  주요 변수: deck
 */
class ControlCard {
	int rangeOfCards = 13;    // 카드 숫자 범위
	private Vector<Card> deck;

	// 덱 전체 크기 벡터를 생성하고 카드 객체들을 삽입
	void setDeck(int sizeOfDeck) {
		String[] shapes = {"dia", "heart", "spade", "clover"};
		deck = new Vector<>(sizeOfDeck);
		for(String shape : shapes)
			for(int i=1; i <= rangeOfCards; i++) {
				deck.add(new Card(shape, i));
			}
		deck.add(new Card("joker", 0));
	}

	void resetDeck() {
		for(Card card: deck) {
			card.setBack();
			card.clickable = false;
		}
	}

	Vector<Card> getDeck() { return deck; }

	// 덱을 섞는다.
	void shuffle() { Collections.shuffle(deck); }
}
