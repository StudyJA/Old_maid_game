import java.util.Iterator;
import java.util.Scanner;

public class User extends Player {
	Scanner scanner = new Scanner(System.in); // giveRandomCard()에서 뽑을 카드 번호를 사용자가 입력하기 위한 스캐너
	public UserPanel panel;
	// 부모 생성자 호출
	User (String name, int location) { super(name, location); }

	// giveRandomCard를 굳이 오버라이딩할 필요없이 User만의 메소드 selectCard로 이름 변경
	Card selectCard(Player prevPlayer) {
		System.out.print("Enter a number of card to draw(1~"+prevPlayer.cardList.size()+"): ");
		int i = scanner.nextInt()-1;
		prevPlayer.panel.minusBack(1); // 이전 플레이어 패널에서 카드 한장 제거
		return prevPlayer.cardList.remove(i); // remove는 삭제한 원소를 반환
	}

	@Override
	Card giveRandomCard() { //유저의 카드를 뽑아가면 그 카드가 보이게끔 giveRandomCard 오버라이딩
		int i = (int)(Math.random()*this.cardList.size());
		Card card = this.cardList.remove(i);
		System.out.println("took " + card + " from " + this.getName());
		return card;
	}

	// 유저가 선택한 카드 한 장과 같은 숫자의 카드를 찾아 버림. 중간에 뽑은 카드도 보여줌
	@Override
	void draw(Player prevPlayer) {
		Card givenCard = selectCard(prevPlayer);  // 이전 플레이어의 카드를 뽑는다.
		System.out.println("You have taken " + givenCard + " from " + prevPlayer.getName());
		//System.out.println(prevPlayer.getName()+ " to give " + givenCard +" get!"); ***다인님 이 코드가 뭔지 설명 해주세요***
		System.out.print(name+": ");
		// 유저의 카드 중에서 같은 숫자를 탐색
		for(Card card: this.cardList)    
			if(card.equals(givenCard)) { // 같은 숫자 카드가 있을 때
				System.out.println( "Dump " + card + " and " + givenCard + " from hand");
				this.cardList.remove(card);
				return;
			}
		// 같은 숫자 카드가 없을 때
		System.out.println("There are no pair with same number");
		cardList.add(givenCard); // 카드 패에 카드 추가
		panel.refresh();
	}
	
	// User가 가지고 있는 카드들을 모두 출력
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