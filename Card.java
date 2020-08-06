public class Card {
	// 카드의 정보를 마음대로 변경하지 못하도록 private 접근 지정자 설정
	private int number; //1~13까지의 수, 0(조커)
	private String shape; //다이아몬드, 하트, 스페이드, 클로버, 조커

	Card (String shape, int number) {
		this.shape = shape;
		this.number = number;
	}

	public int getNumber() {return number;}
	public String getShape() {return shape;}
	@Override
	public boolean equals(Object o) {
		Card c = (Card) o;
		if (c.getNumber()==this.getNumber()) //카드 c의 숫자와 이 카드의 숫자가 동일하면
			return true;
		else
			return false;
	} // Object 클래스가 가지고 있는 디폴트 메소드 equals로 변경
	@Override
	public String toString() {
		return this.shape + this.number;
	} // print 출력을 위해 ♥2 형태로 문자열 생성
	// Player가 Card를 굳이 상속받을 필요가 없을 것 같아요 나중에 User와 Computer가 서로 나눠진다면 그때
	// Player를 상속받을 수 있겠네요!
}
