import java.util.*;

public class Player {
	protected String name;  // 이름
	protected int location; // 게임 보드에서 위치
	private boolean first = false; // 시작 플레이어인지 확인
	private int score = 0;  // 점수
	public PlayerPanel panel; // GUI 출력을 위한 패널
	Vector<Card> cardList; 	// 플레이어의 카드들

	Player (String name, int location) {
		this.name = name;
		this.location = location;
	}
	public String getName()  { return name; }
	public int getLocation() { return location; }
	public void setFirst(boolean first) { this.first = first; }
	public boolean isFirst() { return this.first; }
	public void setCardList(Vector<Card> cardList) { this.cardList = cardList; }
	public void plusScore() { this.score++; } 	// Winner gets 1 point
	public void minusScore() { this.score--; }  // Joker gets -1 point

	// 자신의 cardList에서 무작위 카드 한 장을 뽑음
	Card giveRandomCard() {
		int i = (int)(Math.random()*this.cardList.size());
		System.out.println("took a card from " + this.getName());
		return this.cardList.remove(i);
	}

	// 이전 플레이어의 무작위 카드 한 장과 같은 숫자의 카드를 찾아 버림. 플레이어는 카드 출력 안함
	void draw(Player prevPlayer) {
		Card givenCard = prevPlayer.giveRandomCard(); // 이전 플레이어의 무작위 카드
		if(prevPlayer.getLocation() == 0) { // 이전 플레이어가 유저라면
			User user = (User)prevPlayer;
			user.panel.refresh(); // 유저의 패널을 갱신
		}
		else {
			prevPlayer.panel.minusBack(1); // 이전 플레이어 패널에서 카드 한장 제거
		}

		// 플레이어의 카드들에서 같은 숫자를 탐색
		for(Card card: this.cardList) {
			if (card.equals(givenCard)) { // 같은 숫자 카드가 있을 때
				System.out.println("Dump " + card + " and " + givenCard + " from hand");
				panel.minusBack(1); // 패널에서 카드 한 장 제거
				this.cardList.remove(card);
				return;
			}
		}
		// 같은 숫자 카드가 없을 때
		cardList.add(givenCard);
		panel.plusBack(1); // 패널에 카드 한장 추가
	}

	// 플레이어의 카드가 몇 장인지 출력
	public void showCards() {
		System.out.print(name + ": has " + cardList.size() + " cards");
		System.out.println();
	}

	// 게임 시작 전 중복 카드를 모두 제거
	public void dumpAll() {
		final int size = cardList.size();
		int cardNumberList[][] = new int[size][2];	// 카드 숫자와 cardList에서 몇번째 원소 인지를 함께 저장한 이차원 배열
		boolean toDelete[] = new boolean[size]; 	// cardList에서 삭제할 원소를 표시한 배열
		for(int i=0; i<size; i++) {	// cardNumberList를 만드는 과정
			cardNumberList[i][0] = cardList.get(i).getNumber();
			cardNumberList[i][1] = i;
			toDelete[i] = false;    // 어떤 원소도 삭제하지 않게 초기화
		}
		// 중복 숫자를 찾기 위해서 위에서 만든 배열을 숫자를 기준으로 정렬
		Arrays.sort(cardNumberList, Comparator.comparingDouble(o -> o[0]));

		// 같은 숫자가 연속으로 두번 나오면 중복이므로 toDelete에 둘다 표시
		System.out.print(name + ": Dump cards  ");
		int prev, previousIndex, current, currentIndex;
		for(int i=1; i<size; i++) { // 지금 카드와 이전 카드를 함께 보며 중복인지 확인

			prev = cardNumberList[i-1][0];
			previousIndex = cardNumberList[i-1][1];
			current = cardNumberList[i][0];
			currentIndex = cardNumberList[i][1];

			if(prev == current) {
				System.out.print(cardList.get(previousIndex) + "&" + cardList.get(currentIndex) + " ");
				toDelete[previousIndex] = true;
				toDelete[currentIndex] = true;
				i++; // 앞서 두개의 카드가 중복이면 다음 카드를 스킵하고 다다음 카드를 봄
			}
		}
		System.out.println();

		// 뒤에서 부터 toDelete가 참인 카드를 제거
		for(int j=size-1; j>=0; j--)
			if(toDelete[j]) cardList.remove(j);
	}
}