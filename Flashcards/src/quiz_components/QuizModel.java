package quiz_components;

import deck_components.Card;
import deck_components.Deck;
import deck_components.Deck3;

/**
 * The model for the quiz component of teh flashcards application.
 * 
 * @author Joe Kay IV
 * 
 */
public class QuizModel {
    /**
     * The user's current score.
     */
    private int score;

    /**
     * The max possible score.
     */
    private int maxScore;
    /**
     * The file path of the Deck.
     */
    private final String deckPath;

    /**
     * The deck being used for quizzing.
     */
    private final Deck deck;

    /**
     * The deck containing the cards that were answered wrong.
     */
    private Deck wrong;
    /**
     * The card currently being displayed.
     */
    private Card currentCard;

    /**
     * Default constructor.
     * 
     * @param path
     *            The file path from which to create a new deck at the end of
     *            the quiz.
     * @param deck
     *            The deck to quiz the user on.
     */
    public QuizModel(String path, Deck deck) {
        this.deckPath = path;
        this.wrong = new Deck3();
        deck.shuffle();
        this.score = 0;
        this.maxScore = 0;
        this.deck = deck;
        this.currentCard = deck.draw();
    }

    /**
     * Returns the card currently being shown.
     * 
     * @return {@code this.currentCard}
     */
    public final Card getCurrentCard() {
        return this.currentCard;
    }

    /**
     * Sets {@code this.currentCard} to the next next card from
     * {@code this.deck} and returns the old value of {@code this.currentCard}.
     * 
     * @return Old value of {@code this.currentCard}
     */
    public final Card draw() {
        Card next = this.deck.draw();
        this.currentCard = next;
        return next;
    }

    /**
     * Adds 1 to the score.
     */
    public final void incrementScore() {
        this.score++;
    }

    /**
     * Adds one to the maxScore.
     */
    public final void incrementMaxScore() {
        this.maxScore++;
    }

    /**
     * Checks if {@code this.deck} has another card left in it.
     * 
     * @return {@code this.deck.size() > 0}
     */
    public final boolean hasNext() {
        return this.deck.size() > 0;
    }

    /**
     * Returns the score.
     * 
     * @return {@code this.score}
     */
    public final int getScore() {
        return this.score;
    }

    /**
     * Returns the maximum possible score.
     * 
     * @return {@code this.maxScore}
     */
    public final int getMaxScore() {
        return this.maxScore;
    }

    /**
     * Adds {@code c}to the deck of incorrectly answered cards.
     * 
     * @param c
     *            The card to be added.
     */
    public final void addToWrong(Card c) {
        this.wrong.add(c);
    }

    /**
     * Returns the deck of incorrectly answered cards.
     * 
     * @return {@code this.wrong}
     */
    public final Deck getWrongDeck() {
        return this.wrong;
    }

    /**
     * Returns the file path of the xml file from which the deck was made.
     * 
     * @return {@code this.deckPath}
     */
    public final String getDeckName() {
        return this.deckPath;
    }
}
