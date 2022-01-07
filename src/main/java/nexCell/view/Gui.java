package nexCell.view;

import nexCell.view.customElement.panel.InfoPanel;
import nexCell.view.customElement.panel.SheetsView;
import nexCell.view.menu.MenuBar;
import nexCell.view.rowHeader.RowHeader;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    private static final JScrollPane SCROLLPANE = new JScrollPane();
    private final InfoPanel INFO = new InfoPanel();
    private final SheetsView SHEETS = new SheetsView(INFO.getCELLSELECTED());
    private final JPanel PANELBOTTOM = new JPanel();

    public Gui() {
        super("NexCell");
        this.setLayout(new BorderLayout());
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(600, 400));
        this.setResizable(true);

        this.setJMenuBar(new MenuBar(this));

        this.add(INFO, BorderLayout.NORTH);

        SCROLLPANE.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        SCROLLPANE.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        SCROLLPANE.setRowHeaderView(new RowHeader(SHEETS.getModel(), SHEETS.getSHEETS()));
        SCROLLPANE.setColumnHeaderView(SHEETS.getSHEETS().getTableHeader());
        SCROLLPANE.getViewport().add(SHEETS);
        this.add(SCROLLPANE, BorderLayout.CENTER);

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public SheetsView getSHEETS() {
        return SHEETS;
    }
}
