import java.util.AbstractMap;

public class ColumnThread extends MatrixThread {

    public ColumnThread(int startingRow, int startingColumn, int numberOfElements, Matrix matrixA, Matrix matrixB, Matrix matrixC) {
        super(startingRow, startingColumn, numberOfElements, matrixA, matrixB, matrixC);
    }

    @Override
    protected void populate() {
        int i = startingRow;
        int j = startingColumn;
        int noElements = numberOfElements;
        while (noElements > 0 && i < matrixC.getRow() && j < matrixC.getColumn()) {
            elements.add(new AbstractMap.SimpleEntry<>(i, j));
            i++;
            noElements--;
            if (i == matrixA.getRow()) {
                i = 0;
                j++;
            }
        }
    }
}