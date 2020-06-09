import java.awt.*;
import java.awt.event.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import static javax.swing.ScrollPaneConstants.*;

public class Autocomplete extends JPanel implements DocumentListener, ActionListener {

    protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";
    private Trie trie;

    final static String CANCEL_ACTION = "cancel-search";

    public Autocomplete(String fileName) {
        super(new GridBagLayout());

        textField = new JTextField(20);
        textField.getDocument().addDocumentListener(this);
        InputMap im = textField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = textField.getActionMap();
        im.put(KeyStroke.getKeyStroke("ESCAPE"), CANCEL_ACTION);
        am.put(CANCEL_ACTION, new Autocomplete.CancelAction());
        textField.addActionListener(this);

        textArea = new JTextArea(50, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15, 15, 0, 15);
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, c);

        initializeTrie(fileName);
    }

    public void actionPerformed(ActionEvent evt) {
        search(false);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Autocomplete");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new Autocomplete("words.txt"));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    void search(boolean complete) {
        textArea.setText("");
        String lookup = textField.getText();

        if (complete) lookup = lookup + ".*";

        String[] results = trie.patternSearch(lookup, 500);

        for (String word : results) {
            textArea.append(word + newline);
        }
        textArea.setCaretPosition(0);
    }

    // DocumentListener methods
    public void insertUpdate(DocumentEvent ev) {
        search(true);
    }

    public void removeUpdate(DocumentEvent ev) {
        search(true);
    }

    public void changedUpdate(DocumentEvent ev) {
    }

    class CancelAction extends AbstractAction {
        public void actionPerformed(ActionEvent ev) {
            textField.setText("");
        }
    }

    void initializeTrie(String fileName) {
        trie = new Trie();

        try {
            InputStream is = new FileInputStream(fileName);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line;
            while (buf.ready()) {
                line = buf.readLine().trim();
                trie.insert(line);
            }
        } catch (Exception e) {
            System.out.println("Error opening file.");
        }
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
