package flashcards;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FlashcardsView extends JFrame implements ActionListener {

    private FlashcardsController controller;

    private final JTextArea topScreen, bottomScreen;

    private final JButton bQuiz, bNew, bLoad, bSave, bEnter;

    public FlashcardsView() {

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Flashcards");

        /*
         * Set up the Text areas.
         */
        this.topScreen = new JTextArea(5, 40);
        this.bottomScreen = new JTextArea(5, 40);

        /*
         * Set up buttons.
         */
        this.bEnter = new JButton("Enter");
        this.bQuiz = new JButton("Quiz");
        this.bSave = new JButton("Save");
        this.bNew = new JButton("New");
        this.bLoad = new JButton("Load");

        /*
         * Add menu buttons to mode panel.
         */
        JPanel menu = new JPanel(new GridLayout(1, 3));
        menu.add(this.bNew);
        menu.add(this.bLoad);
        menu.add(this.bQuiz);

        /*
         * Add action buttons to action panel.
         */
        JPanel actions = new JPanel(new GridLayout(1, 2));
        actions.add(this.bEnter);
        actions.add(this.bSave);

        /*
         * Make scroll panes
         */
        JScrollPane topPane = new JScrollPane(this.topScreen);
        JScrollPane bottomPane = new JScrollPane(this.bottomScreen);

        /*
         * Add all the stuff to this.
         */
        this.setLayout(new GridLayout(4, 1));
        this.add(menu);
        this.add(topPane);
        this.add(bottomPane);
        this.add(actions);

        /*
         * We want the starting mode to be "load". In this mode, load, save, and
         * the top text area are not clickable.
         */
        this.bLoad.setEnabled(false);
        this.bSave.setEnabled(false);
        this.topScreen.setEditable(false);

        // Set up the observers ----------------------------------------------

        /*
         * Register this object as the observer for all GUI events
         */
        this.bNew.addActionListener(this);
        this.bLoad.addActionListener(this);
        this.bQuiz.addActionListener(this);
        this.bEnter.addActionListener(this);
        this.bSave.addActionListener(this);

        // Set up the main application window --------------------------------

        /*
         * Make sure the main window is appropriately sized, exits this program
         * on close, and becomes visible to the user
         */
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        Object source = event.getSource();
        if (source == this.bNew) {
            this.controller.setMode("new");
        } else {
            if (source == this.bLoad) {
                this.controller.setMode("load");
            } else {
                if (source == this.bQuiz) {
                    this.controller.setMode("quiz");
                }
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    public void registerObserver(FlashcardsController controller) {
        this.controller = controller;
    }

    public void updateTopDisplay(String input) {
        this.topScreen.setText(input);

    }

    public void updateBottomDisplay(String output) {
        this.bottomScreen.setText(output);
    }

}
