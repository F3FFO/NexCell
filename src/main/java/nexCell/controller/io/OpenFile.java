package nexCell.controller.io;

import nexCell.controller.MyDataModel;
import nexCell.model.cell.Cell;

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

    public void run() {
        try {
            ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(fileSelected));
            model.setDataVector(((List<List<Cell>>) objIn.readObject()).stream().map(u -> u.toArray(new Cell[0])).toArray(Object[][]::new), model.getColIdentifiers().toArray());
            objIn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("File non aperto");
        } catch (IOException e2) {
            System.out.println("errore chiusura stream");
        }
    }
}
