public class ComputeSingleElement {

    public static int computeElement(Matrix matrixA, Matrix matrixB, int rowA, int columnB) {
        int value = 0;
        if (rowA < matrixA.getRow() && columnB < matrixB.getColumn() && matrixA.getColumn() == matrixB.getRow()) {
            for (int i = 0; i < matrixA.getColumn(); i++) {
                value += matrixA.get(rowA, i) * matrixB.get(i, columnB);
            }
            return value;
        } else {
            throw new RuntimeException();
        }
    }
}