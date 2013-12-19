package quiz;

import deck_components.Card;

/**
 * A controller for the Quiz component of the flashcards application.
 * 
 * @author Joe Kay IV
 * 
 */
public class QuizController {
    /**
     * The model object.
     */
    private final QuizModel model;

    /**
     * The GUI object.
     */
    private final QuizView view;

    /**
     * Constructor.
     * 
     * @param model
     *            The model.
     * @param view
     *            The GUI.
     */
    public QuizController(QuizModel model, QuizView view) {
        /*
         * Assign model and view.
         */
        this.model = model;
        this.view = view;

        /*
         * Display the first card in the deck.
         */
        this.view.setCard(this.model.getCurrentCard());
    }

    /**
     * 
     */
    public void processAnswerEvent() {
        /*
         * Get the user's answer from the window.
         */
        String answer = this.view.getAnswer();
        /*
         * Get the correct answer.
         */
        Card current = this.model.getCurrentCard();
        String correct = current.getBack();

        /*
         * If the user's answer matches the correct answer, tell them that they
         * are correct and add 1 to their current score. Otherwise, tell them
         * the right answer.
         */
        if (answer.equals(correct)) {
            this.model.incrementScore();
            this.view.showCorrect(true, current.getBackhtml());
        } else {
            this.model.addToWrong(current);
            this.view.showCorrect(false, current.getBackhtml());
        }
        /*
         * Add one to the total possible score, and update the veiw to match the
         * model.
         */
        this.model.incrementMaxScore();
        this.view.setScore(this.model.getScore());
        this.view.setMaxScore(this.model.getMaxScore());
    }

    public void showNextCard() {
        /*
         * If there is another card left in the deck, draw it and update the
         * view. Otherwise, close the window.
         */
        if (this.model.hasNext()) {
            Card next = this.model.draw();
            this.view.setCard(next);
        } else {
            /*
             * Create a prompt window that will ask the user if he or she wants
             * to quiz again.
             */
            new PromptWindow(this.model.getDeckName(),
                    this.model.getWrongDeck());
            this.view.setVisible(false);
            this.view.dispose();
        }
    }
}
