package flashcards;

import java.awt.Color;
import java.awt.Dimension;
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

/**
 * A window that displays an error message to the user.
 * 
 * @author Joe Kay IV
 * 
 */
public class ErrorWindow extends JFrame implements ActionListener, KeyListener {
    /**
     * The error message.
     */
    private JLabel text;

    /**
     * The "OK" button to close the window.
     */
    private JButton enterButton;

    /**
     * The upper JPanel containing {@code this.text}.
     */
    private JPanel topPanel;

    /**
     * The lower JPanel containing {@code this.enterButton}.
     */
    private JPanel bottomPanel;

    /**
     * The global JPanel that contains {@code this.topPanel} and
     * {@code this.bottomPanel}.
     */
    private JPanel window;

    /**
     * Constructor.
     * 
     * @param message
     *            The error message to be displayed.
     */
    public ErrorWindow(String message) {
        /*
         * Create a JFrame by calling the super class constructor.
         */
        super("Error");

        /*
         * Declare and initialize the instance variables.
         */
        this.text = new JLabel(message);
        this.enterButton = new JButton("OK");
        this.topPanel = new JPanel();
        this.bottomPanel = new JPanel();
        this.window = new JPanel();

        /*
         * Set the text color to red.
         */
        this.text.setForeground(Color.RED);

        /*
         * Set up borders for the top and bottom panels, then add the text to
         * the top panel and the enter button to the bottom panel.
         */
        this.topPanel
                .setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        this.bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
                10));
        this.topPanel.add(this.text);
        this.bottomPanel.add(this.enterButton);

        /*
         * Set up the window panel.
         */
        this.window.setLayout(new GridLayout(2, 1));
        this.window.add(this.topPanel);
        this.window.add(this.bottomPanel);
        this.window.setPreferredSize(new Dimension(220, 100));

        /*
         * Register this as an action listener for gui and keybord events.
         */
        this.enterButton.addActionListener(this);
        this.enterButton.addKeyListener(this);

        /*
         * Make sure the window is appropriately sized, cannot be resized, exits
         * this program on close, and becomes visible to the user.
         */
        this.add(this.window);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void keyPressed(KeyEvent key) {
        /*
         * If the source of the key event is the enter key, close the window.
         */
        char c = key.getKeyChar();
        if (c == '\n') {
            this.setVisible(false);
            this.dispose();
        }
    }

    @Override
    public final void keyReleased(KeyEvent key) {

    }

    @Override
    public void keyTyped(KeyEvent key) {
    }

    @Override
    public final void actionPerformed(ActionEvent event) {
        /*
         * If the source of the key event is the enter button, close the window.
         */
        Object source = event.getSource();
        if (source == this.enterButton) {
            this.setVisible(false);
            this.dispose();
        }
    }

}
