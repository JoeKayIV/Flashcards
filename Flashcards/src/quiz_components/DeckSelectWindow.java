package quiz_components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import deck_components.Deck;
import deck_components.Deck2;
import flashcards.ErrorWindow;

public class DeckSelectWindow extends JFrame implements ActionListener,
        KeyListener {
    /**
     * The text label that contains the instruction to the user to enter a deck
     * name.
     */
    private JLabel textLabel;

    /**
     * The text bar.
     */
    private JTextField textBar;

    /**
     * The enter button.
     */
    private JButton enterButton;

    /**
     * The Top Panel that contains {@code this.textLabel}.
     */
    private JPanel topPanel;

    /**
     * The bottom panel that contains {@code this.textBar} and
     * {@code this.enterButton}.
     */
    private JPanel bottomPanel;

    /**
     * The JPanel that contains the topPanel and bottomPanel.
     */
    private JPanel window;

    /**
     * Constructor.
     */
    public DeckSelectWindow() {
        /*
         * Create a new JFrame by calling the superclass constructor.
         */
        super("Select Deck");

        /*
         * Declare and initialize the components.
         */
        this.textLabel = new JLabel("Type the name of the deck to quiz with: ");
        this.textBar = new JTextField(30);
        this.textBar.setEditable(true);
        this.enterButton = new JButton("Enter");
        this.topPanel = new JPanel();
        this.bottomPanel = new JPanel();
        this.window = new JPanel();

        /*
         * Set up the text label.
         */
        // TODO: Figure out a way to change textLabel Font without changing the
        // height of textBar.
        this.textLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        this.textLabel.setHorizontalAlignment(SwingConstants.LEFT);
        this.textLabel.setFont(new Font("DeckSelectFont", Font.BOLD, 16));

        /*
         * Set up the top panel.
         */
        this.topPanel.setLayout(new BoxLayout(this.topPanel, BoxLayout.X_AXIS));
        this.topPanel.add(this.textLabel);

        /*
         * Set up the text bar and bottom panel.
         */
        this.textBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel,
                BoxLayout.X_AXIS));
        this.bottomPanel.add(this.textBar);
        this.bottomPanel.add(this.enterButton);

        /*
         * Set up the window panel.
         */
        this.window.setLayout(new GridLayout(2, 1));
        this.window.add(this.topPanel);
        this.window.add(this.bottomPanel);

        this.window.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        /*
         * Add the window panel to this.
         */
        this.add(this.window);

        /*
         * Add this as a key listener and an action listener.
         */
        this.textBar.addKeyListener(this);
        this.enterButton.addActionListener(this);

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

    @Override
    public final void keyPressed(KeyEvent key) {
        /*
         * If the source of the event is the enter key, remove the newLine
         * character that was just entered into the answer bar.
         */
        char c = key.getKeyChar();
        if (c == '\n') {
            String text = this.textBar.getText();
            this.textBar.setText(removeNewLines(text));
        }
    }

    @Override
    public final void keyReleased(KeyEvent key) {
        /*
         * If the source of the event is the enter key, look up the file.
         */
        char c = key.getKeyChar();
        if (c == '\n') {
            this.fileLookup();
        }
    }

    @Override
    public final void keyTyped(KeyEvent key) {
        /*
         * If the source of the event is the enter key, remove the newLine
         * character that was just entered into the answer bar.
         */
        char c = key.getKeyChar();
        if (c == '\n') {
            String text = this.textBar.getText();
            this.textBar.setText(removeNewLines(text));
        }
    }

    @Override
    public final void actionPerformed(ActionEvent event) {
        /*
         * If the source of the event is the enter button, look up the file.
         */
        Object source = event.getSource();
        if (source == this.enterButton) {
            this.fileLookup();
        }
    }

    /**
     * Look up the entered deck name and form a quiz from it.
     */
    private void fileLookup() {
        /*
         * Set the cursor to indicate that the computer is "busy".
         */
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        /*
         * Get the text from the text bar.
         */
        String name = removeNewLines(this.textBar.getText());
        String fileName = "decks/" + name + ".xml";
        /*
         * Create a file to check if the file at the designated path exists. If
         * it doesn't exist, show the user an error message.
         */
        File checker = new File(fileName);
        if (checker.exists()) {
            /*
             * Try to create a deck and a quiz from the file path, and if it
             * fails, show the user an error message.
             */
            try {
                /*
                 * Create the deck. Any file reading errors will occur here,
                 * skipping to the catch block and preventing the rest of the
                 * code in the try block from executing.
                 */
                Deck d = new Deck2(fileName);

                /*
                 * Create the quiz.
                 */
                QuizModel model = new QuizModel(fileName, d);
                QuizView view = new QuizView();
                QuizController controller = new QuizController(model, view);
                view.registerObserver(controller);

                /*
                 * Delete this window, as it is no longer necessarry.
                 */
                this.setVisible(false);
                this.dispose();

            } catch (AssertionError error) {
                String message = error.getMessage();
                /*
                 * Output the error message that corresponds to the error,
                 * whether it be unreadable xml or a card with text that is too
                 * long.
                 */
                String errorText = "";
                if (message.equals("Violation of: input is valid XML")) {
                    errorText = "<html><center>ERROR: Could not read file "
                            + name
                            + ".xml.</center<center>Make sure xml is formatted "
                            + "correctly</center></html>";
                } else {
                    if (message.equals("Violation of: String length <= 150")) {
                        errorText = "<html><center>ERROR: One or more cards has</center>"
                                + "<center>text that is too long.</center></html>";
                    }
                }
                new ErrorWindow(errorText);
            }

            /*
             * Reset the cursor.
             */
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            /*
             * Reset the cursor.
             */
            this.setCursor(Cursor.getDefaultCursor());
            new ErrorWindow("ERROR: File does not exist");
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
        /*
         * Iterate through all indexes of the character '\n' in the string and
         * remove them.
         */
        while (i >= 0) {
            i = s.indexOf('\n');
            if (i >= 0) {
                s = s.substring(0, i) + s.substring(i + 1, s.length());
            }
        }
        return s;
    }
}