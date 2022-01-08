package nexCell.view.customElement;

import nexCell.controller.MyDataModel;
import nexCell.model.cell.CellFormula;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class MyJTable extends JTable {

    private JTextField CELLSELECTED;
    private JTextField FORMULA;

    public MyJTable(JTextField CELLSELECTED, JTextField FORMULA) {
        super.setCellSelectionEnabled(true);
        this.setShowGrid(true);
        this.setGridColor(Color.LIGHT_GRAY);
        this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.getTableHeader().setReorderingAllowed(Boolean.FALSE);
        this.CELLSELECTED = CELLSELECTED;
        this.FORMULA = FORMULA;
    }

    @Override
    public void doLayout() {
        if (tableHeader != null) {
            if (tableHeader.getResizingColumn() == null) {
                TableColumnModel tcm = getColumnModel();
                int lastColumn = tcm.getColumnCount() - 1;
                tableHeader.setResizingColumn(tcm.getColumn(lastColumn));
            }
        }
        super.doLayout();
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        boolean isSelected = false;
        boolean hasFocus = false;

        // Only indicate the selection and focused cell if not printing
        if (!isPaintingForPrint()) {
            isSelected = super.isCellSelected(row, column);
            boolean rowIsLead = (selectionModel.getLeadSelectionIndex() == row);
            boolean colIsLead = (columnModel.getSelectionModel().getLeadSelectionIndex() == column);
            hasFocus = (rowIsLead && colIsLead) && super.isFocusOwner();
        }

        Object value = super.getValueAt(row, column);
        JComponent cellRenderer = (JComponent) renderer.getTableCellRendererComponent(this, value, isSelected, hasFocus, row, column);
        Border border = new CompoundBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK), new EmptyBorder(0, 0, 0, 0));

        if (isSelected) {
            cellRenderer.setBackground(new Color(87, 87, 87, 100));
            this.CELLSELECTED.setText(this.getColumnName(column) + "" + (this.getSelectedRow() + 1));
        } else
            cellRenderer.setBackground(null);

        if ((isSelected && hasFocus)) {
            cellRenderer.setBackground(null);
            cellRenderer.setBorder(border);
            if (value == null)
                FORMULA.setText("");
            else {
                if (value.toString().equals(CellFormula.ERROR))
                    FORMULA.setText(((CellFormula) ((MyDataModel) this.getModel()).getSheetStructure().getMatrix().get(row).get(column)).getOriginalValue());
                else
                    FORMULA.setText(value.toString());
            }
        }
        return cellRenderer;
    }
}
