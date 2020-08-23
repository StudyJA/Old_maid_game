import javax.swing.*;
import java.awt.*;

/**
 *  Player의 카드 뒷면만 보여주는 패널
 */
public class PlayerPanel extends JPanel {
    ImageIcon back = new ImageIcon("img/back.jpg");
    int cardListSize;
    PlayerPanel(int cardListSize) {
        this.cardListSize = cardListSize;
        setBackground(Color.white);
        setLayout(new FlowLayout());
        for(int i=0; i<cardListSize; i++) {
            JLabel j = new JLabel(back);
            j.setOpaque(true);
            j.setSize(back.getIconWidth(), back.getIconHeight());
            this.add(j);
        }
    }

    public void minusBack(int cardsForRemove) {
        Component[] components = this.getComponents();
        if(components != null)
            for(int i=0; i<cardsForRemove; i++) {
                if(cardListSize > 0)
                    this.remove(--cardListSize);
            }
        this.revalidate();
        this.repaint();
    }

    public void plusBack(int cardsForAdd) {
        for(int i=0; i<cardsForAdd; i++) {
            JLabel j = new JLabel(back);
            j.setOpaque(true);
            j.setSize(back.getIconWidth(), back.getIconHeight());
            this.add(j);
            cardListSize++;
        }
        this.revalidate();
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
