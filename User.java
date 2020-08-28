import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class User extends Player {
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
			BoardPanel.showRight("" + card + " from " + name + "\n");
			return card;
	}

	// 유저가 이전 플레이어의 카드를 선택해서 뽑음
	@Override
	void draw(Player prevPlayer) {
			previousPlayer = prevPlayer;
			prevPlayer.panel.setBackground(Color.white);
			panel.setBackground(Color.GRAY);
			BoardPanel.showRight("Please select " + prevPlayer.name + "'s card\n");

			// 이전 플레이어의 카드를 클릭할 수 있게 만듦
			for(Card card: prevPlayer.cardList)
				card.enableClick(new CardMouseListener());
			// 선택한 카드를 이전 플레이어 카드 리스트에서 삭제하는 리스너 활성화
			prevPlayer.enableMouseListener();
	}
	
	class CardMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Card card = (Card)e.getSource();
			card.setForth();
			card.removeMouseListener(this);
			card.clickable = false;
			cardList.add(card);
			panel.refresh();
		}
	}
}