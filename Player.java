import java.util.*;

public class Player extends Card {
	private String name; //이름
	boolean isFirst; //순서
	int score=0; //점수
	
	String getName() {return name;} //player의 이름 반환
	void setName() {} //player 이름 설정
	void setFirst() {} //순서 설정
	
	void draw() {} //
	void showCards() {}
}
