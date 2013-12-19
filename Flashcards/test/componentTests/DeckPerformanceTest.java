package componentTests;
import org.junit.Test;

import components.stopwatch.Stopwatch;
import components.stopwatch.Stopwatch1;

import deck_components.Card;
import deck_components.Card1;
import deck_components.Deck;
import deck_components.Deck1;
import deck_components.Deck2;
import deck_components.Deck3;

public class DeckPerformanceTest {

    @Test
    public void testDeckPerformance() {
        this.testShuffleTime(100);
        System.out.println("\n");
        this.testShuffleDistribution();
    }

    public void testShuffleTime(int n) {
        System.out.println("Time tests:");
        System.out.println();
        this.testDeck1ShuffleTime(n);
        this.testDeck2ShuffleTime(n);
        this.testDeck3ShuffleTime(n);
    }

    public void testShuffleDistribution() {
        System.out.println("Distribution tests:");
        System.out.println();
        this.testDeck1ShuffleDistribution();
        this.testDeck2ShuffleDistribution();
        this.testDeck3ShuffleDistribution();
    }

    public void testDeck1Shuffle() {
        Deck deck = newDeck1();
        deck.shuffle();
        System.out.println(deck.toString());
    }

    public void testDeck2Shuffle() {
        Deck deck = newDeck2();
        deck.shuffle();
        System.out.println(deck.toString());
    }

    public void testDeck3Shuffle() {
        Deck deck = newDeck3();
        deck.shuffle();
        System.out.println(deck.toString());
    }

    public void testDeck1ShuffleTime(int n) {
        Deck deck = new Deck1();
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                Card c = new Card1("derp", "herp");
                deck.add(c);
            }
            if (i == n) {
                System.out.println("Deck1:\tSize= " + n);
                Stopwatch timer = new Stopwatch1();
                timer.start();
                deck.shuffle();
                timer.stop();
                System.out.println("\tAbout " + timer.elapsed()
                        + " milliseconds");
            }
        }
    }

    public void testDeck2ShuffleTime(int n) {
        Deck deck = new Deck2();
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                Card c = new Card1("derp", "herp");
                deck.add(c);
            }
            if (i == n) {
                System.out.println("Deck2:\tSize= " + n);
                Stopwatch timer = new Stopwatch1();
                timer.start();
                deck.shuffle();
                timer.stop();
                System.out.println("\tAbout " + timer.elapsed()
                        + " milliseconds");
            }
        }
    }

    public void testDeck3ShuffleTime(int n) {
        Deck deck = newDeck3();
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                Card c = new Card1("derp", "herp");
                deck.add(c);
            }
            if (i == n) {
                System.out.println("Deck3:\tSize= " + n);
                Stopwatch timer = new Stopwatch1();
                timer.start();
                deck.shuffle();
                timer.stop();
                System.out.println("\tAbout " + timer.elapsed()
                        + " milliseconds");
            }
        }
    }

    public void testDeck1ShuffleDistribution() {
        System.out.println("Deck1:");
        int[][] array = getZeros();
        for (int x = 0; x < 1000; x++) {
            Deck deck = newDeck1();
            deck.shuffle();
            for (int index = 0; index < 10; index++) {
                Card card = deck.draw();
                int c = Integer.parseInt(card.getFront());
                array[index][c]++;
            }
        }
        printProbs(array);
    }

    public void testDeck2ShuffleDistribution() {
        System.out.println("Deck2:");
        int[][] array = getZeros();
        for (int x = 0; x < 1000; x++) {
            Deck deck = newDeck2();
            deck.shuffle();
            for (int index = 0; index < 10; index++) {
                Card card = deck.draw();
                int c = Integer.parseInt(card.getFront());
                array[index][c]++;
            }
        }
        printProbs(array);
    }

    public void testDeck3ShuffleDistribution() {
        System.out.println("Deck3:");
        int[][] array = getZeros();
        for (int x = 0; x < 1000; x++) {
            Deck deck = newDeck3();
            deck.shuffle();
            for (int index = 0; index < 10; index++) {
                Card card = deck.draw();
                int c = Integer.parseInt(card.getFront());
                array[index][c]++;
            }
        }
        printProbs(array);
    }

    public static Deck newDeck1() {
        Deck deck = new Deck1();
        for (int i = 0; i < 10; i++) {
            Card c = new Card1(Integer.toString(i), Integer.toString(i));
            deck.add(c);
        }
        return deck;
    }

    public static Deck newDeck2() {
        Deck deck = new Deck2();
        for (int i = 0; i < 10; i++) {
            Card c = new Card1(Integer.toString(i), Integer.toString(i));
            deck.add(c);
        }
        return deck;
    }

    public static Deck newDeck3() {
        Deck deck = new Deck3();
        for (int i = 0; i < 10; i++) {
            Card c = new Card1(Integer.toString(i), Integer.toString(i));
            deck.add(c);
        }
        return deck;
    }

    public static int[][] getZeros() {
        int[][] a = new int[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                a[x][y] = 0;
            }
        }
        return a;
    }

    public static void printProbs(int[][] array) {
        System.out.print("\t\t\t\t\tIndex:\n\t\t");
        for (int x = 0; x < 10; x++) {
            System.out.print(x + "\t");
        }
        System.out.println();
        System.out.println("------------------------------------------------"
                + "------------------------------------------");
        // System.out.println("\t" + " |");
        for (int card = 0; card < 10; card++) {
            if (card == 4) {
                System.out.print("Card:");
            }
            System.out.print("\t" + card + "|");
            for (int index = 0; index < 10; index++) {
                System.out.print("\t" + array[index][card]);
            }
            System.out.println();
            System.out.println("\t"
                    + " |------------------------------------------------"
                    + "--------------------------------");
        }
    }
}
