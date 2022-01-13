
import java.util.AbstractMap;

public class KThread extends MatrixThread{

    public KThread(int startingRow, int startingColumn, int numberOfElements, Matrix matrixA, Matrix matrixB, Matrix matrixC, int K) {
        super(startingRow, startingColumn, numberOfElements, matrixA, matrixB, matrixC, K);
    }

    @Override
    public void populate() {
        int i = startingRow;
        int j = startingColumn;
        int count = numberOfElements;
        while (count > 0 && i < matrixC.getRow() && j < matrixC.getColumn()) {
            elements.add(new AbstractMap.SimpleEntry<>(i, j));
            count--;
            i += (j + K) / matrixC.getColumn();
            j = (j + K) % matrixC.getColumn();
        }
    }
}