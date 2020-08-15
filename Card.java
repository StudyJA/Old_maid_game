public class Card {
	private int number;    // Manager can set range of cards, 0(joker)
	private String shape;  // dia heart spade clover Joker

	Card (String shape, int number) {
		this.shape = shape;
		this.number = number;
	}

	public int getNumber() { return number;}
	public String getShape() { return shape;}

	@Override
	public boolean equals(Object o) {
		Card c = (Card) o;
		if (c.getNumber() == this.getNumber())
			return true;
		else
			return false;
	} // 두 카드의 숫자가 같으면 참
	@Override
	public String toString() {
		return this.shape + this.number;
	}
}