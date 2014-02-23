package deck_components;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import components.queue.Queue;
import components.queue.Queue2;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * {@code Deck} represented as a Singly linked list of {@code Card}.
 * 
 * @author Joe Kay IV
 * 
 */
public class Deck3 implements Deck {

    private static final class Node {

        /**
         * Data in node.
         */
        private Card data;

        /**
         * Next node in singly linked list, or null.
         */
        private Node next;

    }

    /**
     * "Smart node" before top node of singly linked list.
     */
    private Node aboveTop;

    /**
     * Bottom node of singly linked list.
     */
    private Node bottom;

    /**
     * The size of the deck, or one less than the number of nodes.
     */
    private int size;

    /**
     * Creates a new representation.
     */
    private void createNewRep() {
        this.aboveTop = new Node();
        this.aboveTop.next = null;
        this.bottom = this.aboveTop;
        this.size = 0;
    }

    /*
     * ------------ Constructors ------------
     */

    /**
     * Default constructor.
     */
    public Deck3() {
        this.createNewRep();
    }

    /**
     * Consturctor from one or more {@code Card}.
     * 
     * @param args
     *            The {@code Card} to be placed in this.
     */
    public Deck3(Card... args) {
        this.createNewRep();
        for (Card c : args) {
            this.add(c);
        }
    }

    /**
     * Creates a new Deck from an xml file.
     * 
     * @param file
     *            Directory path to a valid xml file.
     */
    public Deck3(String file) {
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
        assert source instanceof Deck3 : ""
                + "Violation of: Source is of Dynamic Type Deck1.";

        this.clear();
        Iterator<Card> i = source.iterator();
        while (i.hasNext()) {
            this.add(i.next());
        }

    }

    @Override
    public final String toString() {
        String str = "";
        Iterator<Card> i = this.iterator();
        while (i.hasNext()) {
            Card c = i.next();
            str = str.concat(c.toString() + " ");
        }
        str = str.trim();
        return str;
    }

    @Override
    public final int size() {
        return this.size;
    }

    @Override
    public final void add(Card c) {
        Node newBottom = new Node();
        Node oldBottom = this.bottom;
        newBottom.data = c;
        newBottom.next = null;
        oldBottom.next = newBottom;
        this.bottom = newBottom;
        this.size++;
    }

    @Override
    public final Card draw() {
        assert this.size() > 0 : "Violation of: this.size() > 0";
        Node p = this.aboveTop;
        Node q = p.next;
        Card result = q.data;
        this.aboveTop = q;
        this.size--;
        return result;
    }

    @Override
    public final void shuffle() {
        if (this.size() > 1) {
            mergeShuffle(this);
        }
    }

    /*
     * ------------ Private Methods ------------
     */

    /**
     * Rearranges the cards in {@code deck} in a new order by recursively
     * dividing the deck and reassembling it in a random order.
     * 
     * @param deck
     *            The Deck to be shuffled.
     */
    private static void mergeShuffle(Deck deck) {
        if (deck.size() > 1) {
            Random rand = new Random();
            if (deck.size() > 2) {
                Deck a = new Deck3();
                Deck b = new Deck3();
                while (deck.size() > 0) {
                    Card card = deck.draw();
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
                Card c1 = deck.draw();
                Card c2 = deck.draw();
                int r = rand.nextInt(2);
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
     * Concatenates two Decks to the end of a third.
     * 
     * @param array
     *            The Deck to be added on to.
     * @param a
     *            The first Deck.
     * @param b
     *            The second Deck.
     * 
     */
    private static void merge(Deck deck, Deck a, Deck b) {
        assert deck != null : "Violation of: array is not null.";
        assert a != null : "Violation of: a is not null.";
        assert b != null : "Violation of: b is not null.";

        int aLength = a.size();
        int bLength = b.size();
        for (int i = 0; i < aLength; i++) {
            Card c = a.draw();
            deck.add(c);
        }
        for (int j = 0; j < bLength; j++) {
            Card c = b.draw();
            deck.add(c);
        }
    }

    /*
     * ------------ Iterator Shizzle ------------
     */
    @Override
    public final Iterator<Card> iterator() {
        return new Deck3Iterator();
    }

    private static Queue<Card> toQueue(Deck d) {
        Queue<Card> q = new Queue2<Card>();
        int size = d.size();
        for (int i = 0; i < size; i++) {
            Card c = d.draw();
            q.enqueue(c);
            d.add(c);
        }
        return q;
    }

    private final class Deck3Iterator implements Iterator<Card> {

        /**
         * Representation iterator.
         */
        private final Iterator<Card> iterator;

        /**
         * Default constructor.
         */
        private Deck3Iterator() {
            Queue<Card> elements = Deck3.toQueue(Deck3.this);
            this.iterator = elements.iterator();
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
