package flashcards;

import quiz.QuizController;
import quiz.QuizModel;
import quiz.QuizView;
import deck_components.Deck;
import deck_components.Deck3;

/**
 * A program that simulates a deck of flashcards.
 * 
 * @author Joseph Kay IV
 */
public final class Flashcards {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private Flashcards() {
        // no code needed here
    }

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        /*
         * Code for final export.
         */
        new DeckSelectWindow();

        /*
         * Code for immediate quiz access
         */
        // quiz();

        /*
         * Code for immediate prompt access.
         */
        // new PromptWindow("decks/test.xml", new Deck3("decks/test.xml"));
    }

    private static void quiz() {
        Deck d = new Deck3("decks/test.xml");
        QuizModel model = new QuizModel("decks/test.xml", d);
        QuizView view = new QuizView();
        QuizController controller = new QuizController(model, view);
        view.registerObserver(controller);
    }
}
