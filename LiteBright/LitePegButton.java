import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;


/*
 * Custom JButton that cycles through colors when clicked.
 * @author Hector Mendez-Garcia
 */

 public class LitePegButton extends JButton {

    private static final Color[] COLORS = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    private int colorIndex;

    public LitePegButton() {
        colorIndex = 0;
        setBackground(COLORS[colorIndex]);
        addActionListener(new LitePegListener());
    }

    private class LitePegListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            colorIndex = (colorIndex + 1) % COLORS.length;
            setBackground(COLORS[colorIndex]);
        }
    }

    public void reset() {
        colorIndex = 0;
        setBackground(COLORS[colorIndex]);
    }

}
