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
