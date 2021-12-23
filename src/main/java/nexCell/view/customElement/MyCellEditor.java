package nexCell.view.customElement;

import nexCell.controller.SheetStructure;
import nexCell.model.cell.CellFormula;

import javax.swing.*;
import java.awt.*;

public class MyCellEditor extends DefaultCellEditor {

    private SheetStructure sheetStructure;

    public static MyCellEditor make(SheetStructure sheetStructure) {
        JTextField field = new JTextField("");
        field.setBorder(null);
        return new MyCellEditor(field, sheetStructure);
    }

    public MyCellEditor(JTextField textField, SheetStructure sheetStructure) {
        super(textField);
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
