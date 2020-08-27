import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Scanner;

public class User extends Player {
	Player previousPlayer;
	// 부모 생성자 호출
	User (String name, int location) {
		super(name, location);
		user = true;
	}
	@Override
	Card giveRandomCard() {
			int i = (int)(Math.random()*cardList.size());
			Card card = cardList.remove(i);
			panel.refresh();
			BoardPanel.showRight("took " + card + " from " + name + "\n");
			return card;
	}

	// 유저가 전 플레이어의 카드를 선택해서 뽑음
	@Override
	void draw(Player prevPlayer) {
			previousPlayer = prevPlayer;
			prevPlayer.panel.setBackground(Color.white);
			panel.setBackground(Color.GRAY);
			BoardPanel.showRight("Please select " + prevPlayer.name + "'s card\n");
			for(Card card: prevPlayer.cardList)
				card.enableClick(new CardMouseListener());
			prevPlayer.enableMouseListener();
	}
	
	// User가 가지고 있는 카드들을 모두 출력 (테스트용)
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

	class CardMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Card card = (Card)e.getSource();
			BoardPanel.showRight("took " + card);
			card.setForth();
			card.removeMouseListener(this);
			card.clickable = false;
			previousPlayer.isWin();
			dump(card);
			panel.refresh();
			isWin();
		}
	}
}