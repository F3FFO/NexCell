package nexCell.view;

import nexCell.view.customElement.SheetsView;
import nexCell.view.menu.MenuBar;
import nexCell.view.rowHeader.RowHeader;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    private final JPanel PANELTOP = new JPanel();
    private final SheetsView PANELMID = new SheetsView();
    private final JPanel PANELBOTTOM = new JPanel();

    private static final JScrollPane SCROLLPANE = new JScrollPane();

    public Gui() {
        super("NexCell");
        this.setLayout(new BorderLayout());
        this.setExtendedState(Frame.MAXIMIZED_BOTH);

        this.setJMenuBar(new MenuBar());

        SCROLLPANE.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        SCROLLPANE.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        SCROLLPANE.setRowHeaderView(new RowHeader(PANELMID.getModel(), PANELMID.getSHEETS()));
        SCROLLPANE.setColumnHeaderView(PANELMID.getSHEETS().getTableHeader());
        SCROLLPANE.getViewport().add(PANELMID);
        this.add(SCROLLPANE, BorderLayout.CENTER);

        this.setResizable(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
