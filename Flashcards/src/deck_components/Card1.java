package deck_components;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
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
     * The image representation of this.
     */
    private final JLabel img;

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
         * doing it manually like this does work.
         */
        if (f.length() > 150 || b.length() > 150) {
            throw new AssertionError("Violation of: String length <= 150");
        }
        this.front = f;
        this.back = b;

        this.fronthtml = gethtmlWithBreaks(this.front);
        this.backhtml = gethtmlWithBreaks(this.back);
        ImageIcon icon = new ImageIcon("img/card.png");
        this.img = new JLabel(this.fronthtml, icon, SwingConstants.CENTER);
        this.img.setFont(new Font("Card Font", Font.BOLD, 24));
        this.img.setVerticalTextPosition(SwingConstants.CENTER);
        this.img.setHorizontalTextPosition(SwingConstants.CENTER);
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

    @Override
    public final void flip() {
        String temp = this.front;
        this.front = this.back;
        this.back = temp;

        temp = this.fronthtml;
        this.fronthtml = this.backhtml;
        this.backhtml = temp;
    }

    @Override
    public final JLabel getLabel() {
        return this.img;
    }

    private static String gethtmlWithBreaks(String text) {
        return "<html><center>" + text + "</center></html>";
    }

    private static String gethtmlWithBreaks2(String text) {
        String html = "<html><center>";
        String line = "";
        String temp = "";
        int index = 0;
        JFrame widthTesterFrame = new JFrame();
        JLabel widthTesterLabel = new JLabel();
        widthTesterLabel.setFont(new Font("Card Font", Font.BOLD, 24));
        widthTesterFrame.add(widthTesterLabel);
        widthTesterFrame.pack();
        while (index < text.length()) {
            String s = nextWordOrSeparator(text, index);
            index += s.length();

            temp = line.concat(s);
            widthTesterLabel.setText(temp);
            widthTesterFrame.pack();
            int width = widthTesterFrame.getWidth();
            if (width > 450) {
                html = html.concat(s + "</center><center>");
                line = s;
            } else {
                html = html.concat(s);
            }

        }
        return html + "</center></html>";
    }

    private static String nextWordOrSeparator(String text, int index) {
        String next = "";
        char nextChar = text.charAt(index);
        if (nextChar == ' ') {
            next = next.concat(String.valueOf(nextChar));
        } else {
            int i = index;
            while (nextChar != ' ') {
                next = next.concat(String.valueOf(nextChar));
                i++;
                if (i == text.length()) {
                    nextChar = ' ';
                } else {
                    nextChar = text.charAt(i);
                }
            }
        }
        return next;
    }
}
