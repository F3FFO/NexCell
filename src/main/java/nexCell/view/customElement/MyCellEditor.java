package nexCell.view.customElement;

import nexCell.controller.SheetStructure;
import nexCell.model.cell.CellFormula;

import javax.swing.*;
import java.awt.*;

public class MyCellEditor extends DefaultCellEditor {

    private final SheetStructure sheetStructure;

    public MyCellEditor(SheetStructure sheetStructure, JTextField FORMULA) {
        super(new TextFieldCell(FORMULA));
        this.sheetStructure = sheetStructure;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (sheetStructure.getMatrix().get(row).get(column) instanceof CellFormula) {
            String formula = ((CellFormula) sheetStructure.getMatrix().get(row).get(column)).getOriginalValue();
            return super.getTableCellEditorComponent(table, formula, isSelected, row, column);
        } else
            return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }
}
