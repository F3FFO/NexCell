package nexCell.controller.io;

import nexCell.controller.MyDataModel;
import nexCell.model.cell.Cell;
import nexCell.model.cell.CellFormula;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class OpenFile implements Runnable {

    private final File fileSelected;
    private final MyDataModel model;

    public OpenFile(File fileSelected, MyDataModel model) {
        this.fileSelected = fileSelected;
        this.model = model;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(fileSelected));
            List<List<Cell>> list = (List<List<Cell>>) objIn.readObject();

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++)
                    if (list.get(i).get(j) instanceof CellFormula)
                        model.setValueAt(((CellFormula) list.get(i).get(j)).getOriginalValue(), i, j);
                    else
                        model.setValueAt(list.get(i).get(j).getValue(), i, j);
            }
            objIn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("File non aperto");
        } catch (IOException e2) {
            System.out.println("errore chiusura stream");
        }
    }
}
