package quiz_components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import deck_components.Deck;
import deck_components.Deck3;

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
        this.buttonPanel = new JPanel();

        /*
         * Create the border and set the font for the prompt label.
         */
        Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED,
                Color.GRAY, Color.GRAY);
        Border promptPadding = BorderFactory.createEmptyBorder(10, 10, 15, 10);
        this.prompt.setBorder(BorderFactory.createCompoundBorder(promptPadding,
                border));

        this.prompt.setFont(new Font("Prompt Font", Font.BOLD, 16));
        /*
         * Add the buttons to the button panel.
         */
        GridLayout buttonLayout = new GridLayout(2, 2);

        buttonLayout.setHgap(10);
        buttonLayout.setVgap(10);

        this.buttonPanel.setLayout(buttonLayout);
        this.buttonPanel.add(this.quizWithFullButton);
        this.buttonPanel.add(this.quizWithWrongButton);
        this.buttonPanel.add(this.quizWithNewDeckButton);
        this.buttonPanel.add(this.noButton);

        /*
         * Set the borders of the button panel.
         */

        this.buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 10,
                30));

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