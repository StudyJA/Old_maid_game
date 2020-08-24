import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * User가 가지고 있는 손패를 보여주는 패널
 */
public class UserPanel extends JPanel {
    User user;
    UserPanel(User user) {
        setBackground(Color.GRAY);
        setLayout(new FlowLayout());
        this.user = user;
        for(Card card: this.user.cardList){
            String filename = "img/" + card + ".jpg";
            ImageIcon icon = new ImageIcon(filename);
            JLabel j = new JLabel(icon);
            j.setOpaque(true);
            j.setSize(icon.getIconWidth(),icon.getIconHeight());
            this.add(j);
        }
        this.revalidate();
        this.repaint();
    }

    public void refresh() {
        this.removeAll();
        for(Card card: this.user.cardList){
            String filename = "img/" + card + ".jpg";
            ImageIcon icon = new ImageIcon(filename);
            JLabel j = new JLabel(icon);
            j.setOpaque(true);
            j.setSize(icon.getIconWidth(),icon.getIconHeight());
            this.add(j);
        }
        this.revalidate();
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}
