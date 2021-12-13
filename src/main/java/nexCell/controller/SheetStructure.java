package nexCell.controller;

import nexCell.model.cell.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SheetStructure {

    private int ROW = 1000;
    private int COLUMN = 26;
    private List<List<Cell>> matrice;

    public SheetStructure() {
        this.matrice = new ArrayList<>();
        for (int i = 0; i < this.ROW; i++) {
            List<Cell> inRow = new ArrayList<>();
            for (int j = 0; j < this.COLUMN; j++)
                inRow.add(new Cell(i, j));

            this.matrice.add(inRow);
        }
    }

    public int getROW() {
        return ROW;
    }

    public int getCOLUMN() {
        return COLUMN;
    }

    public List<List<Cell>> getMatrice() {
        return matrice;
    }

    public int checkTypeCell(Object value) {
        String castedVal = (String) value;
        try {
            Integer.parseInt(castedVal);
            return 1;
        } catch (NumberFormatException e) {
            try {
                Double.parseDouble(castedVal);
                return 2;
            } catch (NumberFormatException e2) {
                String pattern = "^=[A-Z][0-9]+?[+|-][A-Z][0-9]+?$";
                if (!Pattern.matches(pattern, castedVal)) {
                    return 3;
                } else {
                    return 4;
                }
            }
        }
    }

    public void convertCell(int row, int col, Object value, int type) {
        Cell general;
        if (type == 1)
            general = new CellInt(row, col, (int) value);
        else if (type == 2)
            general = new CellDouble(row, col, (double) value);
        else if (type == 3)
            general = new CellString(row, col, (String) value);
        else
            general = new CellFormula(row, col);

        this.matrice.get(row).add(col, general);
    }
}
