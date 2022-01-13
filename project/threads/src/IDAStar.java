import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class IDAStar {

    private static final int NR_THREADS = 5;
    private static final int NR_TASKS = 5;

    private static ExecutorService serviceExecutor;

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        Matrix initialState = Matrix.fromFile();

        serviceExecutor = Executors.newFixedThreadPool(NR_THREADS);
        serviceExecutor.submit(IDAStar::diagnosticsTread);

        Matrix solution = solve(initialState);
        System.out.println(solution);
        serviceExecutor.shutdown();
        serviceExecutor.awaitTermination(1000000, TimeUnit.SECONDS);
    }

    public static Matrix solve(Matrix matrix) throws ExecutionException, InterruptedException {
        long time = System.currentTimeMillis();
        int threshold = matrix.getManhattan();
        int distance;
        while (true) {
            Pair<Integer, Matrix> solution = parallelSearch(matrix, 0, threshold, NR_TASKS);
            distance = solution.getFirst();
            if (distance == -1) {
                System.out.println("Solution in " + solution.getSecond().getNoOfSteps() + " steps");
                System.out.println("Execution in: " + (System.currentTimeMillis() - time) + "ms");
                return solution.getSecond();
            } else {
                System.out.println("Depth " + distance + " achieved in " + (System.currentTimeMillis() - time) + "ms");
            }
            threshold = distance;
        }
    }

    public static Pair<Integer, Matrix> parallelSearch(Matrix current, int noOfSteps, int threshold, int noOfThreads) throws ExecutionException, InterruptedException {
        if (noOfThreads <= 1) {
            return search(current, noOfSteps, threshold);
        }

        int estimation = noOfSteps + current.getManhattan();
        if (estimation > threshold) {
            return new Pair<>(estimation, current);
        }
        if (estimation > 80) {
            return new Pair<>(estimation, current);
        }
        if (current.getManhattan() == 0) {
            return new Pair<>(-1, current);
        }
        int min = Integer.MAX_VALUE;
        List<Matrix> moves = current.generateMoves();
        List<Future<Pair<Integer, Matrix>>> futures = new ArrayList<>();
        for (Matrix next : moves) {
            Future<Pair<Integer, Matrix>> f = serviceExecutor.submit(() -> parallelSearch(next, noOfSteps + 1, threshold, noOfThreads / moves.size()));
            futures.add(f);
        }
        for (Future<Pair<Integer, Matrix>> f : futures) {
            Pair<Integer, Matrix> result = f.get();
            int t = result.getFirst();
            if (t == -1) {
                return new Pair<>(-1, result.getSecond());
            }
            if (t < min) {
                min = t;
            }

        }
        return new Pair<>(min, current);
    }

    public static Pair<Integer, Matrix> search(Matrix current, int noOfSteps, int threshold) {
        int estimation = noOfSteps + current.getManhattan();
        if (estimation > threshold) {
            return new Pair<>(estimation, current);
        }
        if (estimation > 80) {
            return new Pair<>(estimation, current);
        }
        if (current.getManhattan() == 0) {
            return new Pair<>(-1, current);
        }
        int min = Integer.MAX_VALUE;
        Matrix solution = null;
        for (Matrix next : current.generateMoves()) {
            Pair<Integer, Matrix> result = search(next, noOfSteps + 1, threshold);
            int t = result.getFirst();
            if (t == -1) {
                return new Pair<>(-1, result.getSecond());
            }
            if (t < min) {
                min = t;
                solution = result.getSecond();
            }
        }
        return new Pair<>(min, solution);

    }

    public static void diagnosticsTread() {
        long startTime = System.currentTimeMillis();
        int k = 0;
        while (true) {
            // Add diagnostics here
            Matrix head = null;
            long endTime = System.currentTimeMillis();
            //System.out.println("Run time: " + (endTime - startTime) + "ms");
            return;
        }
    }


}
