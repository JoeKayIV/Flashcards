package deck_components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * {@code Deck} represented as a {@code java.util.ArrayList} of {@code Card}.
 * The card at index [0] is the "top" of the deck, and the card at index
 * [this.rep.length() - 1] is the bottom.
 * 
 * @author Joe Kay IV
 * 
 */
public class Deck1 implements Deck {

    /**
     * Representation of {@code this}.
     */
    private ArrayList<Card> rep;

    /**
     * Creates a new representation.
     */
    private void createNewRep() {
        this.rep = new ArrayList<Card>();
    }

    /*
     * ------------ Constructors ------------
     */

    /**
     * Default constructor.
     */
    public Deck1() {
        this.createNewRep();
    }

    /**
     * Creates a new Deck from an xml file.
     * 
     * @param file
     *            Directory path to a valid xml file.
     */
    public Deck1(String file) {
        this.createNewRep();
        XMLTree xml = new XMLTree1(file);
        // XMLTree deck = xml.child(0);
        for (int c = 0; c < xml.numberOfChildren(); c++) {
            XMLTree card = xml.child(c);
            String f = card.attributeValue("front");
            String b = card.attributeValue("back");
            this.add(new Card1(f, b));
        }
    }

    /**
     * Consturctor from one or more {@code Card}.
     * 
     * @param args
     *            The {@code Card} to be placed in this.
     */
    public Deck1(Card... args) {
        this.createNewRep();
        for (Card c : args) {
            this.rep.add(c);
        }
    }

    /*
     * ------------ Interface Methods ------------
     */

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public void copyFrom(Deck source) {
        assert source != null : "Violation of: Source is not null.";
        assert source instanceof Deck1 : ""
                + "Violation of: Source is of Dynamic Type Deck1.";

        this.rep.clear();
        Iterator<Card> i = source.iterator();
        while (i.hasNext()) {
            this.add(i.next());
        }

    }

    @Override
    public final String toString() {
        assert this != null : "Violation of: this is not null.";
        String str = "";
        for (Card c : this.rep) {
            str = str.concat(c.toString() + " ");
        }
        str = str.trim();
        return str;
    }

    @Override
    public final int size() {
        assert this != null : "Violation of: this is not null.";
        return this.rep.size();
    }

    @Override
    public final void add(Card c) {
        assert this != null : "Violation of: this is not null.";
        this.rep.add(c);
    }

    @Override
    public final Card draw() {
        assert this != null : "Violation of: this is not null.";
        assert this.rep.size() > 0 : "Violation of: this.size > 0";
        return this.rep.remove(0);
    }

    @Override
    public final void shuffle() {
        assert this != null : "Violation of: this is not null.";
        if (this.size() > 1) {
            mergeShuffle(this.rep);
        }
    }

    /*
     * ------------ Private Methods ------------
     */

    /**
     * Rearranges the cards in {@code deck} in a new order by iterating through
     * the deck and moving each card to a new randomly selected index.
     * 
     * More efficient than mergeshuffle on decks of size less than 150, but the
     * resulting order is less random.
     * 
     * @param deck
     *            The Deck to be shuffled.
     */
    private static void insertionShuffle(ArrayList<Card> deck) {
        assert deck != null : "Violation of: deck is not null.";
        assert deck.size() > 1 : "Violation of: deck.size() > 1";
        int length = deck.size();
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            assert deck != null : "Violation of: deck is not null.";
            assert deck.size() > 1 : "Violation of: deck.size() > 1";
            int newIndex = r.nextInt(length);
            Card c = deck.remove(i);
            deck.add(newIndex, c);
        }
    }

    /**
     * Rearranges the cards in {@code deck} in a new order by recursively
     * dividing the deck and reassembling it in a random order.
     * 
     * Not as efficient as insertionShuffle for decks less than size 150, but
     * the resulting order is near perfectly random.
     * 
     * @param deck
     *            The Deck to be shuffled.
     */
    private static void mergeShuffle(ArrayList<Card> deck) {
        assert deck != null : "Violation of: deck is not null.";
        if (deck.size() > 1) {
            Random rand = new Random();
            if (deck.size() > 2) {
                ArrayList<Card> a = new ArrayList<Card>();
                ArrayList<Card> b = new ArrayList<Card>();

                while (deck.size() > 0) {
                    Card card = deck.remove(0);
                    int r = rand.nextInt(2);
                    if (r == 0) {
                        a.add(card);
                    } else {
                        b.add(card);
                    }

                }
                mergeShuffle(a);
                mergeShuffle(b);
                int r = rand.nextInt(2);
                if (r == 0) {
                    merge(deck, a, b);
                } else {
                    merge(deck, b, a);
                }
            } else {
                int r = rand.nextInt(2);
                Card c1 = deck.remove(0);
                Card c2 = deck.remove(0);
                if (r == 0) {
                    deck.add(c1);
                    deck.add(c2);
                } else {
                    deck.add(c2);
                    deck.add(c1);
                }
            }
        }
    }

    /**
     * Concatenates two ArrayLists to the end of a third.
     * 
     * @param array
     *            The ArrayList to be added on to.
     * @param a
     *            The first ArrayList.
     * @param b
     *            The second ArrayList.
     * 
     */
    private static void merge(ArrayList<Card> array, ArrayList<Card> a,
            ArrayList<Card> b) {
        assert array != null : "Violation of: array is not null.";
        assert a != null : "Violation of: a is not null.";
        assert b != null : "Violation of: b is not null.";

        int aLength = a.size();
        int bLength = b.size();
        for (int i = 0; i < aLength; i++) {
            Card c = a.remove(0);
            array.add(i, c);
        }
        for (int j = 0; j < bLength; j++) {
            Card c = b.remove(0);
            array.add(aLength + j, c);
        }
    }

    /*
     * ------------ Iterator Shizzle ------------
     */
    @Override
    public final Iterator<Card> iterator() {
        return new Deck1Iterator();
    }

    private final class Deck1Iterator implements Iterator<Card> {

        /**
         * Representation iterator.
         */
        private final Iterator<Card> iterator;

        /**
         * Default constructor.
         */
        private Deck1Iterator() {
            this.iterator = Deck1.this.rep.iterator();
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public Card next() {
            assert this.hasNext() : "Violation of: ~this.unseen /= <>";
            if (!this.hasNext()) {
                /*
                 * Exception is supposed to be thrown in this case, but with
                 * assertion-checking enabled it cannot happen because of assert
                 * above.
                 */
                throw new NoSuchElementException();
            }
            return this.iterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "remove operation not supported");
        }

    }
}
