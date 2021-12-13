package nexCell.view.rowHeader;

import nexCell.view.customElement.MyJTable;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class RowRenderer extends JLabel implements ListCellRenderer<Integer> {

    public RowRenderer(MyJTable SHEETS) {
        JTableHeader header = SHEETS.getTableHeader();
        this.setOpaque(true);
        this.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        this.setHorizontalAlignment(CENTER);
        this.setBackground(header.getBackground());
        this.setForeground(header.getForeground());
        this.setFont(header.getFont());
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Integer> jList, Integer value, int index, boolean selected, boolean focused) {
        this.setText((value == null) ? "" : value.toString());
        return this;
    }
}
