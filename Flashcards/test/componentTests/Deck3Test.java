package componentTests;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import deck_components.Card;
import deck_components.Card1;
import deck_components.Deck;
import deck_components.Deck1;
import deck_components.Deck3;

public class Deck3Test {

    private Deck createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length % 2 == 0.";
        Deck d = new Deck1();
        for (int i = 0; i < args.length; i += 2) {
            Card c = new Card1(args[i], args[i + 1]);
            d.add(c);
        }
        return d;
    }

    private Deck createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length % 2 == 0.";
        Deck d = new Deck3();
        for (int i = 0; i < args.length; i += 2) {
            Card c = new Card1(args[i], args[i + 1]);
            d.add(c);
        }
        return d;
    }

    @Test
    public void testAddEmpty() {
        Deck test = this.createFromArgsTest();
        Deck expected = this.createFromArgsRef("1", "1");

        test.add(new Card1("1", "1"));

        assertEquals(expected.toString(), test.toString());
    }
}
