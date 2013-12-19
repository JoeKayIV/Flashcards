package quiz;

import java.awt.Cursor;
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
import javax.swing.SwingConstants;

import deck_components.Deck;
import deck_components.Deck3;
import flashcards.DeckSelectWindow;

/**
 * A window that opens at the end of a quiz. The user is asked if they wish to
 * quiz again with the full deck, quiz again with only the cards that they
 * answered wrong, or exit the program.
 * 
 * @author Joe Kay IV
 * 
 */
public class PromptWindow extends JFrame implements ActionListener, KeyListener {
    /**
     * The button for quizzing with the incorrectly answered cards.
     */
    private final JButton quizWithWrongButton;

    /**
     * The button for quizzing with the full deck.
     */
    private final JButton quizWithFullButton;

    /**
     * The button for quizzing with a new deck.
     */
    private final JButton quizWithNewDeckButton;
    /**
     * The button for exiting the program.
     */
    private final JButton noButton;

    /**
     * The prompt text.
     */
    private final JLabel prompt;

    /**
     * The container for {@code this.quizWithWrongButton}.
     */
    private final JPanel quizWithWrongPanel;

    /**
     * The container for {@code this.quizWithFullButton}.
     */
    private final JPanel quizWithFullPanel;

    /**
     * The container for {@code this.quizWithNewDeckButton}.
     */
    private final JPanel quizWithNewDeckPanel;

    /**
     * The container for {@code this.noButton}.
     */
    private final JPanel noPanel;

    /**
     * The container for all the buttons.
     */
    private final JPanel buttonPanel;

    /**
     * The deck of incorrectly answered cards.
     */
    private final Deck wrongDeck;

    /**
     * The filepath from which to recreate the deck previously quizzed.
     */
    private final String filePath;

    /**
     * Constructor.
     * 
     * @param filePath
     *            The path to the current deck file.
     * @param wrong
     *            The deck of incorrectly answered cards.
     */
    public PromptWindow(String filePath, Deck wrong) {
        /*
         * Create a new JFrame by calling the super class constructor.
         */
        super("Quiz again?");
        this.wrongDeck = wrong;
        this.filePath = filePath;
        this.setLayout(new GridLayout(2, 1));

        /*
         * Declare the components.
         */
        this.prompt = new JLabel("Would you like to quiz yourself again?",
                SwingConstants.CENTER);
        this.quizWithWrongButton = new JButton("Quiz with missed cards");
        this.quizWithFullButton = new JButton("Quiz with all cards");
        this.quizWithNewDeckButton = new JButton("Quiz with a different deck");
        this.noButton = new JButton("Close");
        this.quizWithWrongPanel = new JPanel();
        this.quizWithFullPanel = new JPanel();
        this.quizWithNewDeckPanel = new JPanel();
        this.noPanel = new JPanel();
        this.buttonPanel = new JPanel();

        /*
         * Add each button to its respective panel.
         */
        this.quizWithWrongPanel.add(this.quizWithWrongButton);
        this.noPanel.add(this.noButton);
        this.quizWithNewDeckPanel.add(this.quizWithNewDeckButton);
        this.quizWithFullPanel.add(this.quizWithFullButton);

        /*
         * Add the buttons to the button panel.
         */
        this.buttonPanel.setLayout(new BoxLayout(this.buttonPanel,
                BoxLayout.X_AXIS));
        this.buttonPanel.add(this.quizWithFullPanel);
        this.buttonPanel.add(this.quizWithWrongPanel);
        this.buttonPanel.add(this.quizWithNewDeckPanel);
        this.buttonPanel.add(this.noPanel);

        /*
         * Set the borders of each panel.
         */
        this.prompt.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        this.buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
                10));

        /*
         * Add the two panels to this.
         */
        this.add(this.prompt);
        this.add(this.buttonPanel);

        /*
         * Register this as an action listener for each button, and as a key
         * listener.
         */
        this.quizWithWrongButton.addActionListener(this);
        this.quizWithNewDeckButton.addActionListener(this);
        this.quizWithFullButton.addActionListener(this);
        this.noButton.addActionListener(this);
        this.quizWithFullButton.addKeyListener(this);

        /*
         * If there were no cards answered incorrectly, disable the Quiz with
         * Wrong Answers button.
         */
        if (this.wrongDeck.size() < 1) {
            this.quizWithWrongButton.setEnabled(false);
        }

        /*
         * Make sure the window is appropriately sized, cannot be resized, exits
         * this program on close, and becomes visible to the user.
         */
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void keyPressed(KeyEvent key) {
    }

    @Override
    public final void keyReleased(KeyEvent key) {
        char c = key.getKeyChar();
        if (c == '\n') {
            Deck fullDeck = new Deck3(this.filePath);
            this.createNewQuiz(fullDeck);
            this.setVisible(false);
            this.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent key) {
    }

    @Override
    public final void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == this.quizWithFullButton) {
            Deck fullDeck = new Deck3(this.filePath);
            this.createNewQuiz(fullDeck);
        } else if (source == this.quizWithWrongButton) {
            this.createNewQuiz(this.wrongDeck);
        } else if (source == this.quizWithNewDeckButton) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            new DeckSelectWindow();
            this.setVisible(false);
            this.dispose();
            this.setCursor(Cursor.getDefaultCursor());
        } else if (source == this.noButton) {
            this.setVisible(false);
            this.dispose();
        }

    }

    /**
     * Create a new quiz from a specified deck.
     * 
     * @param d
     */
    private void createNewQuiz(Deck d) {
        /*
         * Set the cursor to indicate that the computer is "busy".
         */
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        /*
         * Create a new model, view, and controller.
         */
        QuizModel model = new QuizModel(this.filePath, d);
        QuizView view = new QuizView();
        QuizController controller = new QuizController(model, view);

        /*
         * Register the controller as an observer of GUI events in the view.
         */
        view.registerObserver(controller);

        /*
         * Reset the cursor.
         */
        this.setCursor(Cursor.getDefaultCursor());

        /*
         * Get rid of this window.
         */
        this.setVisible(false);
        this.dispose();
    }
}
