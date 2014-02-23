package deck_components;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * {@code} Card represented by a pair of character strings.
 * 
 * @author Joe Kay IV
 */
public class Card1 implements Card {
    /**
     * The text for the "front" side of the card.
     */
    private String front;
    /**
     * The text for the "back" side of the card.
     */
    private String back;

    private String fronthtml;
    private String backhtml;

    /**
     * Constructor Method.
     * 
     * @param f
     *            The text that will be on the "front" of the card.
     * @param b
     *            The text that will be on the "back" if the card.
     */
    public Card1(String f, String b) {
        /*
         * For some weird reason, assert statements do not throw assertion
         * errors in the exported program. Either that or DeckSelectWindow can't
         * read them in the try statement in the filLookup method. However,
         * doing it manually like this works.
         */
        if (f.length() > 150 || b.length() > 150) {
            throw new AssertionError("Violation of: String length <= 150");
        }
        this.front = f;
        this.back = b;

        this.fronthtml = gethtml(this.front);
        this.backhtml = gethtml(this.back);
    }

    @Override
    public final String toString() {
        return "[\"" + this.front + "\", \"" + this.back + "\"]";
    }

    @Override
    public final String getFront() {
        return this.front;
    }

    @Override
    public final String getFronthtml() {
        return this.fronthtml;
    }

    @Override
    public final String getBack() {
        return this.back;
    }

    @Override
    public final String getBackhtml() {
        return this.backhtml;
    }

    @Override
    public final void setFront(String text) {
        this.front = text;
    }

    @Override
    public final void setBack(String text) {
        this.back = text;
    }

    public static JLabel getCardImage() {
        ImageIcon icon = new ImageIcon("img/card.png");
        JLabel img = new JLabel("", icon, SwingConstants.CENTER);
        img.setFont(new Font("Card Font", Font.BOLD, 24));
        img.setVerticalTextPosition(SwingConstants.CENTER);
        img.setHorizontalTextPosition(SwingConstants.CENTER);
        return img;
    }

    private static String gethtml(String text) {
        return "<html><center>" + text + "</center></html>";
    }
}
