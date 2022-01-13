import java.util.AbstractMap;

public class RowThread extends MatrixThread {

    public RowThread(int startingRow, int startingColumn, int numberOfElements, Matrix matrixA, Matrix matrixB, Matrix matrixC) {
        super(startingRow, startingColumn, numberOfElements, matrixA, matrixB, matrixC);
    }

    @Override
    protected void populate() {
        int i = startingRow;
        int j = startingColumn;
        int noElements = numberOfElements;
        while (noElements > 0 && i < matrixC.getRow() && j < matrixC.getColumn()) {
            elements.add(new AbstractMap.SimpleEntry<>(i, j));
            j++;
            noElements--;
            if (j == matrixA.getColumn()) {
                j = 0;
                i++;
            }
        }
    }
}