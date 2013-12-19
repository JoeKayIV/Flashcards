package deck_components;

import java.util.Iterator;

/**
 * 
 * @author Joe Kay IV
 * 
 */
public interface Deck extends Iterable<Card> {

    /**
     * Empties the deck.
     */
    void clear();

    /**
     * Changes {@code this} to
     * 
     * @param source
     * @return
     */
    void copyFrom(Deck source);

    /**
     * Returns a String representation of this.
     * 
     * @return A String
     */

    @Override
    String toString();

    /**
     * Returns the number of cards in the deck.
     * 
     * @return The number of cards.
     * @ensures {@code getNumberOfCards} = [The number of cards in {@code this}
     *          ]
     */
    int size();

    /**
     * Adds a card to the bottom of the deck.
     * 
     * @param c
     *            The card to be added.
     */
    void add(Card c);

    /**
     * Removes a card from the top of the deck.
     * 
     * @return The card at the top of the deck.
     */
    Card draw();

    /**
     * Goes through every card in the deck and switches the front and back
     * sides.
     * 
     * @ensures [All Cards in {@code this} are flipped]
     */
    void flipCards();

    /**
     * Changes the order of the cards in this.
     * 
     * @ensures {@code #this != this}
     * 
     * 
     */
    void shuffle();

    /**
     * returns an iterator over the cards in this.
     */
    @Override
    Iterator<Card> iterator();

}
