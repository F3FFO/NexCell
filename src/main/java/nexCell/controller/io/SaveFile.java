package nexCell.controller.io;

import nexCell.model.cell.Cell;

import java.io.*;
import java.util.List;

public class SaveFile implements Runnable {

    private final File fileSelected;
    private final List<List<Cell>> matrix;

    public SaveFile(File fileSelected, List<List<Cell>> matrix) {
        this.fileSelected = fileSelected;
        this.matrix = matrix;
    }

    public void run() {
        try {
            ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(fileSelected));
            objOut.writeObject(matrix);
            objOut.close();
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        } catch (IOException e2) {
            System.out.println("errore chiusura stream");
        }
    }
}
