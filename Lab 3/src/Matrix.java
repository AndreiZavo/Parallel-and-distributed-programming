import java.util.Random;

public class Matrix {
    private final int row;
    private final int column;
    private final int[][] matrix;

    public Matrix(int row, int column) {
        this.row = row;
        this.column = column;
        matrix = new int[row][column];
    }

    public void populate() {
        Random random = new Random();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j] = random.nextInt(10);
            }
        }
    }

    public int get(int row, int column) {
        return matrix[row][column];
    }

    public void set(int row, int column, int value) {
        matrix[row][column] = value;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                stringBuilder.append(matrix[i][j]).append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}