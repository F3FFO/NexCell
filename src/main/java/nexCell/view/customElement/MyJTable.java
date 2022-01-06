package nexCell.view.customElement;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class MyJTable extends JTable {

    public MyJTable() {
        super.setCellSelectionEnabled(true);
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
        Border redBorder = new CompoundBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK), new EmptyBorder(0, 0, 0, 0));

        if (isSelected)
            cellRenderer.setBackground(new Color(87, 87, 87, 100));
        else
            cellRenderer.setBackground(null);

        if (hasFocus)
            cellRenderer.setBorder(redBorder);

        if ((isSelected && hasFocus))
            cellRenderer.setBorder(redBorder);
        else
            cellRenderer.setBorder(super.getBorder());

        return cellRenderer;
    }
}
