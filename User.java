import java.util.Iterator;
import java.util.Scanner;

public class User extends Player {
	User() {} // 기본 생성자
	User (String name, int location) {
		this.name = name;
		this.location = location;
	}
	
	Scanner scanner=new Scanner(System.in); // giveRandomCard()에서 뽑을 카드 번호를 사용자로부터 입력받기 위해 스캐너 추가
	
	// 이전 플레이어의 cardList에서 User가 선택한 번호의 카드 한 장을 삭제하고 그를 반환한다.
	// User 플레이어의 차례에 동적바인딩된 메소드가 실행되게 하기 위해서 prevPlayer를 매개변수로 받는 형태로 변경했어요
	@Override
	Card giveRandomCard(Player prevPlayer) {
		System.out.print("Enter a number of card to draw(1~"+prevPlayer.cardList.size()+"): ");
		int i = scanner.nextInt()-1;
		Card card = prevPlayer.cardList.get(i);
		prevPlayer.cardList.remove(i);
		return card;
	} // User 클래스에서는 이 메소드가 랜덤한 카드를 고르지 않으니까 나중에 메소드 이름을 수정하면 좋을 것 같아요!
	
	// 이전 플레이어에게 카드 한 장을 받고 같은 숫자 카드 두 개를 버린다.
	@Override
	void draw(Player prevPlayer) {
		Card givenCard = giveRandomCard(prevPlayer); // 이전 플레이어의 무작위 카드
		System.out.println("You have taken " + givenCard + " from " + prevPlayer.getName());
		//System.out.println(prevPlayer.getName()+ " to give " + givenCard +" get!");
		System.out.print(name+": ");
		// 플레이어의 카드리스트에서 같은 숫자를 찾아본다.
		for(Card card: this.cardList)    
			if(card.equals(givenCard)) { // 같은 숫자 카드가 있을 때
				System.out.println( "Dump " + card + " and " + givenCard + " from hand");
				this.cardList.remove(card);
				return;
			}
		// 같은 숫자 카드가 없을 때
		System.out.println("There are no pair with same number");
		cardList.add(givenCard);	
	}
	
	// cardList의 카드를 모두 보여준다.
	@Override
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