package deck_components;

import javax.swing.JLabel;

/**
 * Interface for a card.
 * 
 * @author Joe Kay IV
 * 
 */
public interface Card {

    /**
     * Returns the text on the "front" side of the card.
     * 
     * @return The front of the card.
     */
    String getFront();

    /**
     * Returns the "front" side of the card with html text wrap.
     * 
     * @return Text wrapped front side of the card.
     */
    String getFronthtml();

    /**
     * Returns the text on the "back" side of the card.
     * 
     * @return The back of the card.
     */
    String getBack();

    /**
     * Returns the "back" side of the card with html text wrap.
     * 
     * @return Text wrapped back side of the card.
     */
    String getBackhtml();

    /**
     * Changes the "front" side of this card.
     * 
     * @param text
     *            The new "front" of the card.
     */
    void setFront(String text);

    /**
     * Changes the "back" side of this card.
     * 
     * @param text
     *            The new "back" of the card.
     */
    void setBack(String text);

    /**
     * Switches the "front" and "back" of the card.
     */
    void flip();

    /**
     * Returns {@code this} represented as an a JLabel.
     * 
     * @return
     */
    JLabel getLabel();

    /**
     * Returns {@code this} represented as a String.
     * 
     * @return A String representing {@code this}
     */
    @Override
    String toString();
}
