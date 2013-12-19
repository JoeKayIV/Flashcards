package quiz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import deck_components.Card;
import deck_components.Card1;

/**
 * The GUI for the quiz component of the flashcards application.
 * 
 * @author Joe Kay IV
 * 
 */
public class QuizView extends JFrame implements KeyListener, ActionListener {

    /**
     * Controller object registered with this view to observe user-interaction
     * events.
     */
    private QuizController controller;

    /**
     * The top panel, containing the displaced card.
     */
    private final JPanel topPanel;

    /**
     * The score display panel.
     */
    private final JPanel displayPanel;

    /**
     * The bottom panel, containing the answer bar and the answer button.
     */
    private final JPanel bottomPanel;

    /**
     * The answer button.
     */
    private final JButton enterButton;

    /**
     * The text area in which the user enters their answer.
     */
    private final JTextField answerBar;

    /**
     * The one, single JPanel that contains the top and botom panels.
     */
    private final JPanel global;

    /**
     * The card currently being displayed.
     */
    private final JLabel currentCard;

    /**
     * The score to be displayed.
     */
    private JLabel score;

    /**
     * The maximum possible score.
     */
    private JLabel maxScore;

    /**
     * Whether or not the window is currently displaying the anwser.
     */
    private boolean isDisplayingAnswer;

    /**
     * The constructor.
     */
    public QuizView() {
        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Quiz");

        /*
         * Declare the display panels.
         */
        this.global = new JPanel();
        this.topPanel = new JPanel();
        this.bottomPanel = new JPanel();
        this.displayPanel = new JPanel();

        /*
         * Create initial card.
         */
        Card c = new Card1("", "");
        Border cardBorder = BorderFactory.createEtchedBorder(
                EtchedBorder.RAISED, Color.BLACK, Color.BLACK);
        Border cardMargin = BorderFactory.createEmptyBorder(10, 10, 0, 10);
        this.currentCard = c.getLabel();
        this.currentCard.setBorder(BorderFactory.createCompoundBorder(
                cardMargin, cardBorder));

        /*
         * Create the score display area.
         */
        this.displayPanel.setPreferredSize(new Dimension(300, 300));
        this.score = new JLabel("0", SwingConstants.CENTER);
        this.maxScore = new JLabel("0", SwingConstants.CENTER);

        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(2, 1));
        scorePanel.add(this.score);
        scorePanel.add(this.maxScore);

        JLabel scoreTitle = new JLabel("Score:", SwingConstants.CENTER);

        Font scoreTitleFont = new Font("scoreTitleFont", Font.BOLD, 90);
        scoreTitle.setFont(scoreTitleFont);
        scoreTitle.setHorizontalTextPosition(SwingConstants.CENTER);

        Font scoreFont = new Font("scoreFont", Font.BOLD, 60);
        this.score.setFont(scoreFont);
        this.maxScore.setFont(scoreFont);

        /*
         * Add the score title and score panel to the display panel.
         */
        this.displayPanel.setLayout(new BoxLayout(this.displayPanel,
                BoxLayout.Y_AXIS));
        this.displayPanel.add(scoreTitle);
        this.displayPanel.add(scorePanel);

        /*
         * Create the answer area.
         */
        this.answerBar = new JTextField(50);
        this.answerBar.setEditable(true);

        /*
         * Create the answer button.
         */
        this.enterButton = new JButton("Next");

        /*
         * Put the top panel together.
         */
        this.topPanel.setLayout(new BoxLayout(this.topPanel, BoxLayout.X_AXIS));
        this.topPanel.add(this.currentCard);
        this.topPanel.add(this.displayPanel);

        /*
         * Put the bottom panel together.
         */
        this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel,
                BoxLayout.X_AXIS));
        this.bottomPanel.add(this.answerBar);
        this.bottomPanel.add(this.enterButton);
        this.bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
                10));

        /*
         * Put the global window together.
         */
        this.global.setLayout(new BoxLayout(this.global, BoxLayout.Y_AXIS));
        this.global.add(this.topPanel);
        this.global.add(this.bottomPanel);
        /*
         * Put the whole window together.
         */
        this.setLayout(new GridLayout(1, 1));
        this.add(this.global);
        /*
         * Register this as the action listener for GUI events.
         */
        this.answerBar.addKeyListener(this);
        this.enterButton.addActionListener(this);

        /*
         * this is not currently displaying the answer, so set
         * isDisplayingAnswer to false.
         */
        this.isDisplayingAnswer = false;

        /*
         * Make sure the window is appropriately sized, cannot be resized, exits
         * this program on close, and becomes visible to the user.
         */
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Registers {@code controller} as the listener for GUI events in
     * {@code this}.
     * 
     * @param controller
     *            The controller to be registered.
     */
    public final void registerObserver(QuizController controller) {
        this.controller = controller;
    }

    /**
     * Returns the user-entered answer.
     * 
     * @return The user's answer.
     */
    public final String getAnswer() {
        return this.answerBar.getText();
    }

    /**
     * Shows the user whether or not their answer is correct and displays the
     * answer.
     * 
     * @param isCorrect
     *            Whether or not the user is correct. True if they are, false if
     *            not.
     * @param correctAnswer
     *            The correct answer.
     */
    public final void showCorrect(boolean isCorrect, String correctAnswer) {
        /*
         * Switch the window to answer display mode.
         */
        this.isDisplayingAnswer = true;
        this.answerBar.setEditable(false);

        /*
         * Check if the user is correct and output whether they are right or
         * wrong. If wrong, output the right answer as well.
         */
        if (isCorrect) {
            this.currentCard.setText("Correct!");
        } else {
            this.currentCard.setText("<html><center>Wrong! "
                    + "The correct answer is:</center><center>" + correctAnswer
                    + "</center></html>");
        }
    }

    /**
     * Sets {@code this.score} to {@code newScore}.
     * 
     * @param newScore
     *            The new score to be displayed.
     */
    public final void setScore(int newScore) {
        this.score.setText(String.valueOf(newScore));
    }

    /**
     * Sets {@code this.maxScore} to {@code newMaxScore}.
     * 
     * @param newMaxScore
     *            The new max score to be displayed.
     */
    public final void setMaxScore(int newMaxScore) {
        this.maxScore.setText(String.valueOf(newMaxScore));
    }

    /**
     * Sets the displayed card in {@code this} to {@code c}.
     * 
     * @param c
     *            The new card to be displayed.
     */
    public final void setCard(Card c) {
        this.currentCard.setText(c.getFronthtml());
    }

    @Override
    public final void keyPressed(KeyEvent key) {
        /*
         * If the source of the event is the enter key, remove the newLine
         * character that was just entered into the answer bar.
         */
        char c = key.getKeyChar();
        if (c == '\n') {
            String text = this.answerBar.getText();
            this.answerBar.setText(removeNewLines(text));
        }
    }

    @Override
    public final void keyReleased(KeyEvent key) {
        /*
         * If the source of the key event is the enter key, check whether or not
         * the window is displaying an answer. If it is, then show the next card
         * in the deck and make the answer bar editable.If not, then get the
         * text from the answer bar and check it by calling the controller. Then
         * clear the answer bar.
         */
        char c = key.getKeyChar();
        if (c == '\n') {
            if (!this.isDisplayingAnswer) {

                /*
                 * Process the answer and clear the answer bar.
                 */
                this.controller.processAnswerEvent();
                this.answerBar.setText("");
            } else {
                /*
                 * Show the next card in the deck, make the answer bar editable,
                 * and take the window out of answer display mode.
                 */
                this.controller.showNextCard();
                this.answerBar.setEditable(true);
                this.isDisplayingAnswer = false;
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent key) {
        /*
         * If the source of the event is the enter key, remove the newLine
         * character that was just entered into the answer bar.
         */
        char c = key.getKeyChar();
        if (c == '\n') {
            String text = this.answerBar.getText();
            this.answerBar.setText(removeNewLines(text));
        }
    }

    @Override
    public final void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        /*
         * If the event source is the enter button, check whether or not the
         * window is displaying an answer. If it is, then show the next card in
         * the deck and make the answer bar editable.If not, then get the text
         * from the answer bar and check it by calling the controller. Then
         * clear the answer bar.
         */
        if (source == this.enterButton) {
            if (!this.isDisplayingAnswer) {
                /*
                 * Process the answer and clear the answer bar.
                 */
                this.controller.processAnswerEvent();
                this.answerBar.setText("");
            } else {
                /*
                 * Show the next card in the deck, make the answer bar editable,
                 * and take the window out of answer display mode.
                 */
                this.controller.showNextCard();
                this.answerBar.setEditable(true);
                this.isDisplayingAnswer = false;
            }
        }
    }

    /**
     * Removes all '\n' from {@code s}.
     * 
     * @param s
     *            The string to remove all newLines from.
     * 
     * @return The string without newLines.
     */
    private static String removeNewLines(String s) {
        int i = 0;
        while (i >= 0) {
            i = s.indexOf('\n');
            if (i >= 0) {
                s = s.substring(0, i) + s.substring(i + 1, s.length());
            }
        }
        return s;
    }
}
