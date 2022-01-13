import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public abstract class MatrixThread extends Thread {
    protected final List<AbstractMap.SimpleEntry<Integer, Integer>> elements;
    protected final int startingRow;
    protected final int startingColumn;
    protected final int numberOfElements;
    protected final Matrix matrixA;
    protected final Matrix matrixB;
    protected final Matrix matrixC;
    protected int K;

    protected MatrixThread(int startingRow, int startingColumn, int numberOfElements, Matrix matrixA, Matrix matrixB, Matrix matrixC) {
        this.startingRow = startingRow;
        this.startingColumn = startingColumn;
        this.numberOfElements = numberOfElements;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixC = matrixC;
        this.elements = new ArrayList<>();
        populate();
    }

    protected MatrixThread(int startingRow, int startingColumn, int numberOfElements, Matrix matrixA, Matrix matrixB, Matrix matrixC, int K) {
        this.startingRow = startingRow;
        this.startingColumn = startingColumn;
        this.numberOfElements = numberOfElements;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixC = matrixC;
        this.K = K;
        this.elements = new ArrayList<>();
        populate();
    }


    @Override
    public void run() {
        for (AbstractMap.SimpleEntry<Integer, Integer> element : elements) {
            int row = element.getKey();
            int column = element.getValue();
            matrixC.set(row, column, ComputeSingleElement.computeElement(matrixA, matrixB, row, column));
        }
    }

    protected void populate() {
        throw new RuntimeException();
    }
}