/*
 * Copyright 2022 F3FFO - Federico Pierantoni
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nexCell.view;

import nexCell.view.panel.InfoPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 * This class is a redefinition of generic JTextField.
 *
 * @author Federico Pierantoni
 */
public class TextFieldCell extends JTextField {

    /**
     * Construct the JTextField and add a DocumentListener.
     *
     * @param FORMULA {@link InfoPanel#FORMULA}
     * @see InfoPanel
     * @see DocumentListener
     */
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

    /**
     * Set the text of JTextField.
     *
     * @param FORMULA       {@link InfoPanel#FORMULA}
     * @param documentEvent contains the data entered by the user
     */
    private void setText(JTextField FORMULA, DocumentEvent documentEvent) {
        try {
            FORMULA.setText(documentEvent.getDocument().getText(0, documentEvent.getDocument().getLength()));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
