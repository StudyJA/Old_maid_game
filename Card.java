public class Card {
	int number; //1~13까지의 수, 0(조커)
	String shape; //다이아몬드, 하트, 스페이드, 클로버, 조커
	
	int getNumber() {return number;}
	String getShape() {return shape;}
	
	//Player가 Card를 상속받아서.. Card클래스에 master 변수는 생성이 안 될 것 같네요..
}
