import java.util.*;

/**
 *  플레이어 정보를 설정하고 플레이어 목록을 만들어 조작한다.
 *  주요 변수: totalPlayer, playerList, currentPlayer
 */
class ControlPlayer {
	private int totalPlayer  = 2;  // 전체 플레이어 수 나중에 조절가능
	Player previousPlayer; // 바로 전 턴을 진행한 플레이어
	Player currentPlayer;  // 현재 턴을 진행 중인 플레이어
	ArrayList<Player> playerList = new ArrayList<>(totalPlayer);

	void setTotalPlayer(int totalPlayer) { this.totalPlayer = totalPlayer; }
	int getTotalPlayer() { return totalPlayer; }

	// 다음 플레이어를 currentPlayer로 현재 플레이어를 이전 플레이어로 설정
	void nextPlayer() {
		synchronized (this) {
			previousPlayer = currentPlayer;
			if(currentPlayer == playerList.get(totalPlayer-1)) // 마지막 플레이어
				currentPlayer = playerList.get(0);
			else
				currentPlayer = playerList.get(currentPlayer.getLocation() + 1);
		}
	}

	// currentPlayer를 시작 플레이어로 변경
	// 시작 플레이어를 찾고 previousPlayer까지 변경
	void firstPlayer() {
		for(int i = 0; i<totalPlayer; i++) {
			currentPlayer = playerList.get(i);
			if(currentPlayer.isFirst()){
				if(i == 0)
					previousPlayer = playerList.get(totalPlayer-1);
				else
					previousPlayer = playerList.get(i-1);
				break;
			}
		}
	}

	// 이름과 위치를 받아 플레이어 생성
	void addPlayer(String name, int location) {
		playerList.add(new Player(name, location));
	}
	// addPlayer와 같은 역할로, User 플레이어 생성
	void addUser(String name, int location) {
		playerList.add(new User(name, location));
	}

	void resetPlayerList() {
		firstPlayer();
		currentPlayer.setFirst(false); // 시작 플레이어 없앰
		Iterator<Player> iter = playerList.iterator();
		while (iter.hasNext()) { // 플레이어가 가지고 있는 카드 모두 버림
			Player player = iter.next();
			player.cardList.clear();
		}
	}

	// Find player who has joker
	Player findJoker() {
		for(int i=0; i < totalPlayer; i++) {
			for (Card card : playerList.get(i).cardList)
				if (card.getNumber() == 0) return playerList.get(i);
		}
		return null;
	}

	// Distribute the deck to players and set a first player
	void shareCards(int rangeOfCards, Vector<Card> deck) {
		int randNum = (int)(Math.random()*totalPlayer);
		currentPlayer = playerList.get(randNum);
		currentPlayer.setFirst(true); // 카드를 첫번째로 받는 플레이어를 시작 플레이어로 설정
		int totalCards = rangeOfCards*4 + 1; // 전체 카드 수
		int shareCards = (int)(totalCards/totalPlayer);
		int startIndex = 0;
		int endIndex   = shareCards;

		// 모든 인원이 똑같이 받는 카드들
		for(int i=0; i<totalPlayer; i++) {
			List<Card> bundle = deck.subList(startIndex, endIndex);
			currentPlayer.setCardList(new Vector<Card>(bundle));
			startIndex += shareCards;
			endIndex   += shareCards;
			nextPlayer();
		}
		// 나머지 카드
		for(int i=0; i < totalCards%shareCards; i++) {
			currentPlayer.cardList.add(deck.get(startIndex+i));
			nextPlayer();
		}
	}
}