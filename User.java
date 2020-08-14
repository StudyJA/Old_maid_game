import java.util.Iterator;
import java.util.Scanner;

public class User extends Player {
	User() {} // �⺻ ������
	User (String name, int location) {
		this.name = name;
		this.location = location;
	}
	
	Scanner scanner=new Scanner(System.in); // giveRandomCard()���� ���� ī�� ��ȣ�� ����ڷκ��� �Է¹ޱ� ���� ��ĳ�� �߰�
	
	// ���� �÷��̾��� cardList���� User�� ������ ��ȣ�� ī�� �� ���� �����ϰ� �׸� ��ȯ�Ѵ�.
	// User �÷��̾��� ���ʿ� �������ε��� �޼ҵ尡 ����ǰ� �ϱ� ���ؼ� prevPlayer�� �Ű������� �޴� ���·� �����߾��
	@Override
	Card giveRandomCard(Player prevPlayer) {
		System.out.print("Enter a number of card to draw(1~"+prevPlayer.cardList.size()+"): ");
		int i = scanner.nextInt()-1;
		Card card = prevPlayer.cardList.get(i);
		prevPlayer.cardList.remove(i);
		return card;
	} // User Ŭ���������� �� �޼ҵ尡 ������ ī�带 ���� �����ϱ� ���߿� �޼ҵ� �̸��� �����ϸ� ���� �� ���ƿ�!
	
	// ���� �÷��̾�� ī�� �� ���� �ް� ���� ���� ī�� �� ���� ������.
	@Override
	void draw(Player prevPlayer) {
		Card givenCard = giveRandomCard(prevPlayer); // ���� �÷��̾��� ������ ī��
		System.out.println("You have taken " + givenCard + " from " + prevPlayer.getName());
		//System.out.println(prevPlayer.getName()+ " to give " + givenCard +" get!");
		System.out.print(name+": ");
		// �÷��̾��� ī�帮��Ʈ���� ���� ���ڸ� ã�ƺ���.
		for(Card card: this.cardList)    
			if(card.equals(givenCard)) { // ���� ���� ī�尡 ���� ��
				System.out.println( "Dump " + card + " and " + givenCard + " from hand");
				this.cardList.remove(card);
				return;
			}
		// ���� ���� ī�尡 ���� ��
		System.out.println("There are no pair with same number");
		cardList.add(givenCard);	
	}
	
	// cardList�� ī�带 ��� �����ش�.
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
