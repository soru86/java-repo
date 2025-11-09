package valid_sudoku;

import java.util.HashSet;
import java.util.Set;

public class ValidSudokuSolution {
    public boolean isValidSudoku(char[][] board) {
        Set<String> seen = new HashSet<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char value = board[r][c];
                if (value == '.') {
                    continue;
                }
                if (!seen.add(value + " in row " + r)
                    || !seen.add(value + " in col " + c)
                    || !seen.add(value + " in box " + (r / 3) + "-" + (c / 3))) {
                    return false;
                }
            }
        }
        return true;
    }
}

