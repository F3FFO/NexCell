package nexCell.view.customElement;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class TextFieldCell extends JTextField {

    public TextFieldCell(JTextField FORMULA) {
        super("");
        this.setBorder(null);
        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                setText(FORMULA, documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                setText(FORMULA, documentEvent);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
            }
        });
    }

    private void setText(JTextField FORMULA, DocumentEvent documentEvent) {
        try {
            FORMULA.setText(documentEvent.getDocument().getText(0, documentEvent.getDocument().getLength()));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
