package hr.fer.zemris.java.hw10.jnotepadpp;

import hr.fer.zemris.java.hw10.jnotepadpp.actions.CloseCurrentTabAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.ExitAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.NewDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.StatisticsAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.editor.CopyAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.editor.CutAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.editor.InvertCaseAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.editor.LowerCaseAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.editor.PasteAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.editor.SortLinesAscendingAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.editor.SortLinesDescendingAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.editor.UniqueLinesAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.editor.UpperCaseAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.localization.DeLanguageAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.localization.EnLanguageAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.localization.HrLanguageAction;
import hr.fer.zemris.java.hw10.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LJMenu;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LJStatusBar;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * JNotepadPP program is easy-to-use text editor with support for editing
 * multiple document at the same time.
 * 
 * <p>
 * JNotepadPP is translated into three languages: Croatian, English and German.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class JNotepadPP extends JFrame {

    /** Serial. */
    private static final long serialVersionUID = -2251112875444054056L;
    /** JNotepadPP tabs. */
    private JTabbedPane tabs;
    /** Status bar. */
    private LJStatusBar statusBar;
    /** Current active editor */
    private JNotepadPPDocument activeEditor;

    /** Sort ascending action. */
    private Action sortLinesAsc;
    /** Sort descending action. */
    private Action sortLinesDesc;
    /** Remove duplicate lines action. */
    private Action uniqueLinesAction;

    /** Reverse case action. */
    private Action reverseCaseAction;
    /** Upper case action. */
    private Action upperCaseAction;
    /** Lower case action. */
    private Action lowerCaseAction;
    /** Copy action. */
    private Action copyAction;
    /** Cut action. */
    private Action cutAction;
    /** Paste action. */
    private Action pasteAction;

    /** Statistics action. */
    private Action statisticsAction;
    /** New document action. */
    private Action newDocumentAction;
    /** Save document action. */
    private Action saveDocumentAction;
    /** Save as action. */
    private Action saveAsDocumentAction;
    /** Open document action. */
    private Action openDocumentAction;
    /** Exit application action. */
    private Action exitAction;
    /** Close current tab. */
    private Action closeCurrentTab;
    /** English language. */
    private Action enLanguageAction;
    /** Croatian language. */
    private Action hrLanguageAction;
    /** German language. */
    private Action deLanguageAction;

    /** Current editors. */
    private List<JNotepadPPDocument> editors = new ArrayList<>();
    /** Localization provider. */
    FormLocalizationProvider flp = new FormLocalizationProvider(
            LocalizationProvider.getInstance(), this);

    /** WindowAdapter listener. */
    private WindowAdapter listener = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            saveAllDocuments();
            statusBar.getTimer().stop();
            dispose();
        }
    };

    /**
     * Constructor for JNotepadPP class.
     */
    public JNotepadPP() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(700, 500);
        setLocation(100, 100);
        setTitle("JNotepad++");

        try {
            initGUI();
        } catch (IOException e) {
            errorMessage(e);
        }
    }

    /**
     * Initializes GUI for JNotepadPP application.
     * 
     * @throws IOException
     *             On file reading error.
     */
    private void initGUI() throws IOException {
        editors = new ArrayList<JNotepadPPDocument>();
        tabbedPaneFactory();

        getContentPane().setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        statusBar = new LJStatusBar(flp);

        panel.add(tabs, BorderLayout.CENTER);
        panel.add(statusBar, BorderLayout.PAGE_END);

        getContentPane().add(panel, BorderLayout.CENTER);

        addNewTab(new JNotepadPPDocument(this));

        createEditorActions();
        createToolsActions();
        setActions(false);
        createMenus();
        createToolbar();

        addWindowListener(listener);
    }

    /**
     * Save all changed documents in opened editors.
     */
    public void saveAllDocuments() {
        int size = editors.size();
        for (int i = 0; i < size; i++) {
            if (editors.get(i).isChanged()) {
                tabs.setSelectedIndex(i);

                int answer = JOptionPane.showConfirmDialog(this,
                        flp.getString("saveMessage"),
                        flp.getString("saveDialog"), JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    editors.get(i).saveDocument(false);
                } else {
                    continue;
                }
            }
        }
    };

    /**
     * Creates new {@link JTabbedPane} with {@link ChangeListener}.
     */
    private void tabbedPaneFactory() {
        tabs = new JTabbedPane();
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabs.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                int index = tabs.getSelectedIndex();
                activeEditor = editors.get(index);
                if (editors.size() > 1) {
                    activeEditor.updateStatusBar();
                }
                updateFrameTitle();
            }

        });
    }

    /**
     * Adds a new editor as a tab to {@link JTabbedPane} component.
     * 
     * @param editor
     *            New editor.
     */
    public void addNewTab(JNotepadPPDocument editor) {
        activeEditor = editor;
        editors.add(editor);
        String title;
        String path;
        if (editor.getFilePath() == null) {
            title = flp.getString("untitledDocument");
            path = flp.getString("unsaved");
        } else {
            title = editor.getTitle();
            path = editor.getFilePath().toAbsolutePath().toString();
        }

        tabs.addTab(title, new JScrollPane(editor.getEditor()));
        int index = tabs.getTabCount() - 1;
        tabs.setTabComponentAt(index, new TabComponent(tabs, this, flp));
        tabs.setSelectedIndex(index);
        tabs.setToolTipTextAt(index, path);
    }

    /**
     * Update current editor tab title.
     * 
     * @param document
     *            Current editor.
     */
    public void updateTabTitle(JNotepadPPDocument document) {
        int index = editors.indexOf(document);
        if (document.getFilePath() == null) {
            tabs.setTitleAt(index, flp.getString("untitledDocument"));
            tabs.setToolTipTextAt(index, "");
        } else {
            String path = document.getFilePath().toAbsolutePath().toString();
            tabs.setTitleAt(index, document.getTitle());
            tabs.setToolTipTextAt(index, path);
            tabs.repaint();
        }
    }

    /**
     * Updates frame title based on current active editor.
     */
    public void updateFrameTitle() {
        if (activeEditor.getFilePath() != null) {
            String path = activeEditor.getFilePath().toAbsolutePath()
                    .toString();
            JNotepadPP.this.setTitle(path + " - JNotepad++");
        } else {
            setTitle("JNotepad++");
        }
    }

    /**
     * Creates menu for current frame.
     */
    private void createMenus() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new LJMenu(flp, "fileMenu");
        menuBar.add(fileMenu);

        fileMenu.add(newDocumentAction);
        fileMenu.add(openDocumentAction);
        fileMenu.add(saveDocumentAction);
        fileMenu.add(saveAsDocumentAction);
        fileMenu.addSeparator();
        fileMenu.add(closeCurrentTab);
        fileMenu.add(exitAction);

        JMenu editMenu = new LJMenu(flp, "editMenu");
        menuBar.add(editMenu);

        editMenu.add(cutAction);
        editMenu.add(copyAction);
        editMenu.add(pasteAction);
        editMenu.addSeparator();
        editMenu.add(statisticsAction);

        JMenu languageMenu = new LJMenu(flp, "languageMenu");
        languageMenu.add(hrLanguageAction);
        languageMenu.add(enLanguageAction);
        languageMenu.add(deLanguageAction);
        menuBar.add(languageMenu);

        JMenu toolsMenu = new LJMenu(flp, "toolsMenu");

        JMenu changeCaseMenu = new LJMenu(flp, "changeCase");
        changeCaseMenu.add(upperCaseAction);
        changeCaseMenu.add(lowerCaseAction);
        changeCaseMenu.add(reverseCaseAction);

        JMenu sortMenu = new LJMenu(flp, "sortMenu");
        sortMenu.add(sortLinesAsc);
        sortMenu.add(sortLinesDesc);

        toolsMenu.add(sortMenu);
        toolsMenu.add(changeCaseMenu);
        toolsMenu.addSeparator();
        toolsMenu.add(uniqueLinesAction);

        menuBar.add(toolsMenu);
        this.setJMenuBar(menuBar);
    }

    /**
     * Creates new toolbar for current frame.
     */
    private void createToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(true);

        toolBar.add(newDocumentAction);
        toolBar.add(openDocumentAction);
        toolBar.add(saveDocumentAction);
        toolBar.add(saveAsDocumentAction);
        toolBar.add(closeCurrentTab);
        toolBar.addSeparator();

        toolBar.add(copyAction);
        toolBar.add(cutAction);
        toolBar.add(pasteAction);
        toolBar.addSeparator();

        toolBar.add(sortLinesAsc);
        toolBar.add(sortLinesDesc);
        toolBar.add(uniqueLinesAction);
        toolBar.add(upperCaseAction);
        toolBar.add(lowerCaseAction);
        toolBar.add(reverseCaseAction);
        toolBar.addSeparator();

        toolBar.add(statisticsAction);
        this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }

    /**
     * Enables or disables editors actions for sorting and changing case.
     * 
     * @param state
     *            <code>true</code> to enable, <code>false</code> to disable.
     */
    public void setActions(boolean state) {
        sortLinesAsc.setEnabled(state);
        sortLinesDesc.setEnabled(state);
        uniqueLinesAction.setEnabled(state);
        upperCaseAction.setEnabled(state);
        lowerCaseAction.setEnabled(state);
        reverseCaseAction.setEnabled(state);
    }

    /**
     * Creates editor actions for sorting lines, reversing case, copying,
     * cutting, pasting and deleting text.
     */
    private void createEditorActions() {
        sortLinesAsc = new SortLinesAscendingAction("sortAscending", flp, this);
        sortLinesDesc = new SortLinesDescendingAction("sortDescending", flp,
                this);
        uniqueLinesAction = new UniqueLinesAction("uniqueLines", flp, this);
        reverseCaseAction = new InvertCaseAction("invertCase", flp, this);
        upperCaseAction = new UpperCaseAction("upperCaseAction", flp, this);
        lowerCaseAction = new LowerCaseAction("lowerCaseAction", flp, this);
        copyAction = new CopyAction("copyAction", flp);
        cutAction = new CutAction("cutAction", flp);
        pasteAction = new PasteAction("pasteAction", flp);

        sortLinesAsc.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/sortascend.png")));
        sortLinesAsc.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control shift A"));
        sortLinesDesc.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/sortdescend.png")));
        sortLinesDesc.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control shift D"));

        upperCaseAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/arrow_up.png")));
        upperCaseAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control shift U"));
        lowerCaseAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/arrow_down.png")));
        lowerCaseAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control U"));

        uniqueLinesAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/duplicate.png")));
        uniqueLinesAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control shift R"));
        

        reverseCaseAction.putValue(Action.NAME, flp.getString("invertCase"));
        reverseCaseAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control F3"));
        reverseCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
        reverseCaseAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/toggle.png")));

        copyAction.putValue(Action.NAME, flp.getString("copyAction"));
        copyAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control C"));
        copyAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/copy.png")));

        cutAction.putValue(Action.NAME, flp.getString("cutAction"));
        cutAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control X"));
        cutAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/cut.png")));

        pasteAction.putValue(Action.NAME, flp.getString("pasteAction"));
        pasteAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control V"));
        pasteAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/paste.png")));
    }

    /**
     * Creates tools actions for opening, saving, closing or creating a new
     * document. Also it creates actions for changing language and exiting
     * application.
     */
    private void createToolsActions() {
        statisticsAction = new StatisticsAction("statistics", flp, this);
        newDocumentAction = new NewDocumentAction("newDocument", flp, this);
        saveDocumentAction = new SaveDocumentAction("saveDocument", flp, this);
        saveAsDocumentAction = new SaveAsDocumentAction("saveAsDocument", flp,
                this);
        openDocumentAction = new OpenDocumentAction("openDocument", flp, this);
        exitAction = new ExitAction("exitApp", flp, this);
        enLanguageAction = new EnLanguageAction("enLanguage", flp);
        hrLanguageAction = new HrLanguageAction("hrLanguage", flp);
        deLanguageAction = new DeLanguageAction("deLanguage", flp);
        closeCurrentTab = new CloseCurrentTabAction("closeCurrentTab", flp, this);

        newDocumentAction.putValue(Action.NAME, flp.getString("newDocument"));
        newDocumentAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control N"));
        newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
        newDocumentAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/new.png")));

        closeCurrentTab.putValue(Action.NAME, flp.getString("closeCurrentTab"));
        closeCurrentTab.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control F4"));
        closeCurrentTab.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/tab_close.png")));
        
        openDocumentAction.putValue(Action.NAME, flp.getString("openDocument"));
        openDocumentAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control O"));
        openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
        openDocumentAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/open.png")));

        saveDocumentAction.putValue(Action.NAME, flp.getString("saveDocument"));
        saveDocumentAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control S"));
        saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        saveDocumentAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/save.png")));

        saveAsDocumentAction.putValue(Action.NAME,
                flp.getString("saveAsDocument"));
        saveAsDocumentAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK
                        | KeyEvent.ALT_DOWN_MASK));
        saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        saveAsDocumentAction.putValue(Action.SMALL_ICON, new ImageIcon(
                getClass().getResource("res/saveas.png")));

        exitAction.putValue(Action.NAME, flp.getString("exitApp"));
        exitAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("alt F4"));
        exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
        exitAction.putValue(Action.SMALL_ICON, new ImageIcon(
                getClass().getResource("res/exit.png")));

        statisticsAction.putValue(Action.NAME, flp.getString("statistics"));
        statisticsAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control L"));
        statisticsAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/statistics.png")));

        hrLanguageAction.putValue(Action.NAME, flp.getString("hrLanguage"));
        hrLanguageAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/hr.png")));
        enLanguageAction.putValue(Action.NAME, flp.getString("enLanguage"));
        enLanguageAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/en.png")));
        deLanguageAction.putValue(Action.NAME, flp.getString("deLanguage"));
        deLanguageAction.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
                .getResource("res/de.png")));
    }

    /**
     * Returns current opened editors.
     * 
     * @return Current opened editors.
     */
    public List<JNotepadPPDocument> getEditors() {
        return editors;
    }

    /**
     * Returns {@link LJStatusBar} component.
     * 
     * @return {@link LJStatusBar} component.
     */
    public LJStatusBar getStatusBar() {
        return statusBar;
    }

    /**
     * Returns {@link JTabbedPane} component.
     * 
     * @return {@link JTabbedPane} component.
     */
    public JTabbedPane getTabs() {
        return tabs;
    }

    /**
     * Returns current active editor.
     * 
     * @return Current active editor.
     */
    public JNotepadPPDocument getActiveEditor() {
        return activeEditor;
    }

    /**
     * Sets given editor as active editor.
     * 
     * @param activeEditor
     *            New active editor.
     */
    public void setActiveEditor(JNotepadPPDocument activeEditor) {
        this.activeEditor = activeEditor;
    }

    /**
     * Prints error message.
     * 
     * @param e1
     *            Exception.
     */
    public void errorMessage(Exception e1) {
        JOptionPane.showMessageDialog(JNotepadPP.this, e1.getMessage(),
                flp.getString("errorTitle"), JOptionPane.ERROR_MESSAGE);
        return;
    }



    /**
     * Start point of program JNotepadPP.
     * 
     * @param args
     *            Command line arguments. Not used.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JNotepadPP();
            frame.setVisible(true);
        });
    }

}
