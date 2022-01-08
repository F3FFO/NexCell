package nexCell.view.rowHeader;

import nexCell.controller.MyDataModel;
import nexCell.view.customElement.MyJTable;

import javax.swing.*;

public class RowHeader extends JList<Integer> {

    public RowHeader(MyJTable SHEETS) {
        this.setListData(((MyDataModel) SHEETS.getModel()).getRowIdentifiers());
        this.setFixedCellWidth(50);
        this.setFixedCellHeight(SHEETS.getRowHeight());
        this.setCellRenderer(new RowRenderer(SHEETS));
    }
}
