package deck_components;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import components.queue.Queue;
import components.queue.Queue2;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * {@code Deck} represented as a {@code components.queue.Queue} of {@code Card}.
 * The card at the "front" of the queue is the one at the "top" of the deck. The
 * "back" of the queue corresponds to the "bottom" of the deck.
 * 
 * @author Joe Kay IV
 * 
 */
public class Deck2 implements Deck {

    /**
     * Representation of {@code this}.
     */
    private Queue<Card> rep;

    /**
     * Creates a new representation.
     */
    private void createNewRep() {
        this.rep = new Queue2<Card>();
    }

    /*
     * ------------ Constructors ------------
     */

    /**
     * Default constructor.
     */
    public Deck2() {
        this.createNewRep();
    }

    /**
     * Creates a new Deck from an xml file.
     * 
     * @param file
     *            Directory path to a valid xml file.
     */
    public Deck2(String file) {
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
    public Deck2(Card... args) {
        this.createNewRep();
        for (Card c : args) {
            this.rep.enqueue(c);
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
        assert source instanceof Deck2 : ""
                + "Violation of: Source is of Dynamic Type Deck1.";

        this.rep.clear();
        Iterator<Card> i = source.iterator();
        while (i.hasNext()) {
            this.add(i.next());
        }

    }

    @Override
    public final String toString() {
        String str = "";
        for (Card c : this.rep) {
            str = str.concat(c.toString() + " ");
        }
        str = str.trim();
        return str;
    }

    @Override
    public final int size() {
        return this.rep.length();
    }

    @Override
    public final void add(Card c) {
        this.rep.enqueue(c);
    }

    @Override
    public final Card draw() {
        return this.rep.dequeue();
    }

    @Override
    public final void shuffle() {
        if (this.size() > 1) {
            mergeShuffle(this.rep);
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
    private static void mergeShuffle(Queue<Card> deck) {
        if (deck.length() > 1) {
            Random rand = new Random();
            if (deck.length() > 2) {
                Queue<Card> a = deck.newInstance();
                Queue<Card> b = deck.newInstance();
                while (deck.length() > 0) {
                    Card card = deck.dequeue();
                    int r = rand.nextInt(2);
                    if (r == 0) {
                        a.enqueue(card);
                    } else {
                        b.enqueue(card);
                    }

                }
                mergeShuffle(a);
                mergeShuffle(b);
                int r = rand.nextInt(2);
                if (r == 0) {
                    deck.append(a);
                    deck.append(b);
                } else {
                    deck.append(b);
                    deck.append(a);
                }
            } else {
                Card c1 = deck.dequeue();
                Card c2 = deck.dequeue();
                int r = rand.nextInt(2);
                if (r == 0) {
                    deck.enqueue(c1);
                    deck.enqueue(c2);
                } else {
                    deck.enqueue(c2);
                    deck.enqueue(c1);
                }
            }
        }

    }

    /*
     * ------------ Iterator Shizzle ------------
     */
    @Override
    public final Iterator<Card> iterator() {
        return new Deck2Iterator();
    }

    private final class Deck2Iterator implements Iterator<Card> {

        /**
         * Representation iterator.
         */
        private final Iterator<Card> iterator;

        /**
         * Default constructor.
         */
        private Deck2Iterator() {
            this.iterator = Deck2.this.rep.iterator();
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
