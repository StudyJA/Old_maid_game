public class Card {
	private int number;    // 1~13까지의 수, 0(조커)
	private String shape;  // 다이아몬드, 하트, 스페이드, 클로버, 조커

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
	} // 두 개의 카드의 숫자가 같으면 true 반환
	@Override
	public String toString() {
		return this.shape + this.number;
	} 
	// Player가 Card를 굳이 상속받을 필요가 없을 것 같아요 나중에 User와 Computer가 서로 나눠진다면 그때
	// Player를 상속받을 수 있겠네요!
}