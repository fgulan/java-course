package hr.fer.zemris.java.hw10.jnotepadpp;

import hr.fer.zemris.java.hw10.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LJStatusBar;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * JNotepadPPDocument class represent JNotpeadPP document.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class JNotepadPPDocument {

    /** Main JNotepadPP frame. */
    private JNotepadPP observer;
    /** Text editor. */
    private JTextArea editor;
    /** Document path. */
    private Path filePath;
    /** Is saved. */
    private boolean saved;
    /** Is changed. */
    private boolean changed;

    /**
     * Creates empty document.
     * 
     * @param observer
     *            Main JNotepadPP frame.
     * @throws IOException
     *             On reading a file.
     */
    public JNotepadPPDocument(JNotepadPP observer) throws IOException {
        this(observer, null, null);
    }

    /**
     * Opens document from given path in new editor.
     * 
     * @param observer
     *            Main JNotepadPP frame.
     * @param filePath
     *            Document path.
     * @throws IOException
     *             On reading a file.
     */
    public JNotepadPPDocument(JNotepadPP observer, Path filePath)
            throws IOException {
        this(observer, filePath, null);

    }

    /**
     * Private constructor used in public constructors.
     * 
     * @param observer
     *            Main JNotepadPP frame.
     * @param filePath
     *            Document path.
     * @param text
     *            Editor content.
     * @throws IOException
     *             On reading a file.
     */
    private JNotepadPPDocument(JNotepadPP observer, Path filePath, String text)
            throws IOException {

        this.observer = observer;
        this.filePath = filePath;
        changed = true;
        editor = editorFactory();
        if (text != null) {
            editor.setText(text);
        } else if (filePath != null) {
            if (!openDocument()) {
                throw new IOException("Unable to read given file!");
            }
        }
        changed = false;
    }

    /**
     * Update observers icon on change.
     */
    private void updateChange() {
        if (!changed) {
            changed = true;
            updateIcon();
        }
    }

    /**
     * Update tab icon.
     */
    private void updateIcon() {
        int index = observer.getEditors().indexOf(this);
        ((TabComponent) observer.getTabs().getTabComponentAt(index))
                .updateTabIcon();
    }

    /**
     * Creates a new editor.
     * 
     * @return New {@link JTextArea} component as editor.
     */
    private JTextArea editorFactory() {
        JTextArea editor = new JTextArea();

        editor.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateChange();
            }
        });

        editor.getCaret().addChangeListener(statusBarListener);
        return editor;
    }

    /** Status bar listener for change in editor. */
    private ChangeListener statusBarListener = new ChangeListener() {

        @Override
        public void stateChanged(ChangeEvent e) {
            LJStatusBar bar = observer.getStatusBar();
            int linenum = 1;
            int columnnum = 1;
            int selection = Math.abs(editor.getCaret().getDot()
                    - editor.getCaret().getMark());

            try {
                int caretpos = editor.getCaretPosition();
                linenum = editor.getLineOfOffset(caretpos);
                columnnum = caretpos - editor.getLineStartOffset(linenum);
                linenum++;
            } catch (Exception ex) {
            }
            bar.updateText(editor.getText().length(), linenum, columnnum,
                    selection);
            if (selection > 0) {
                observer.setActions(true);
            } else {
                observer.setActions(false);
            }
        }
    };

    /**
     * Saves editor content.
     * 
     * @param saveAs
     *            <code>true</code> for save as, <code>false</code> for save.
     * @return <code>true</code> if file is successfully saved,
     *         <code>false</code> otherwise.
     */
    public boolean saveDocument(boolean saveAs) {
        if (filePath == null || saveAs) {
            JFileChooser fc = new JFileChooser();
            FormLocalizationProvider flp = observer.flp;
            fc.setDialogTitle(flp.getString("saveDialog"));
            if (fc.showSaveDialog(observer) != JFileChooser.APPROVE_OPTION) {
                saved = false;
                return false;
            }
            Path file = fc.getSelectedFile().toPath();
            if (Files.exists(file)) {
                int r = JOptionPane
                        .showConfirmDialog(observer,
                                flp.getString("overwriteFile"),
                                flp.getString("warningTitle"),
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                if (r != JOptionPane.YES_OPTION) {
                    saved = false;
                    return false;
                }
            }
            filePath = file;
        }
        byte[] data = editor.getText().getBytes(StandardCharsets.UTF_8);
        try {
            Files.write(filePath, data);
        } catch (Exception e1) {
            saved = false;
            return false;
        }
        saved = true;
        changed = false;
        int index = observer.getEditors().indexOf(this);
        ((TabComponent) observer.getTabs().getTabComponentAt(index))
                .updateTabIcon();
        return saved;
    }

    /**
     * Opens a new document in current editor.
     * 
     * @return <code>true</code> if file is successfully opened,
     *         <code>false</code> otherwise.
     */
    public boolean openDocument() {
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(filePath);
        } catch (IOException e1) {
            return false;
        }
        editor.setText(new String(bytes, StandardCharsets.UTF_8));
        changed = false;
        return true;
    }

    /**
     * Get save status.
     * 
     * @return <code>true</code> if editor text is saved, <code>false</code>
     *         otherwise.
     */
    public boolean isSaved() {
        return saved;
    }

    /**
     * Get changed status.
     * 
     * @return <code>true</code> if editor text is changed since last save,
     *         <code>false</code> otherwise.
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * Get document name.
     * 
     * @return Document name.
     */
    public String getTitle() {
        if (filePath != null) {
            return filePath.getFileName().toString();
        }
        return "";
    }

    /**
     * Get current editor document path.
     * 
     * @return Document path.
     */
    public Path getFilePath() {
        return filePath;
    }

    /**
     * Get editor.
     * 
     * @return Editor.
     */
    public JTextArea getEditor() {
        return editor;
    }

    /**
     * Update status bar with current editor data.
     */
    public void updateStatusBar() {
        statusBarListener.stateChanged(null);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((editor == null) ? 0 : editor.hashCode());
        result = prime * result
                + ((filePath == null) ? 0 : filePath.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof JNotepadPPDocument) {
            Path path = ((JNotepadPPDocument) obj).getFilePath();
            if (filePath != null && path != null && filePath.equals(path)) {
                return true;
            }
        }
        return false;
    }

}
