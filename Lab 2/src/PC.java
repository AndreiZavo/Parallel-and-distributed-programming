import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class PC {

    private final BlockingQueue<Integer> iValues = new LinkedBlockingDeque<>();
    private final BlockingQueue<Integer> jValues = new LinkedBlockingDeque<>();
    private static final int MAX_CAPACITY = 2;
    private static final int MILLISECONDS = 1000;
    private static final int RANGE = 25;
    Random random = new Random();

    public void produce() throws InterruptedException {
        int vectorIndex = 1;
        while (true) {
            synchronized (this) {
                int valueI = random.nextInt(RANGE) - RANGE / 2;
                int valueJ = random.nextInt(RANGE) - RANGE / 2;

                while (iValues.size() == MAX_CAPACITY || jValues.size() == MAX_CAPACITY)
                    wait();

                System.out.println("Producer: v" + vectorIndex + " = "
                        + valueI + "i + " + valueJ + "j");

                vectorIndex++;

                iValues.add(valueI);
                jValues.add(valueJ);

                notify();

                Thread.sleep(MILLISECONDS);
            }
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            int sum;
            synchronized (this) {
                while (iValues.size() <= 1 || jValues.size() <= 1)
                    wait();

                int firstVectorI = iValues.poll();
                int firstVectorJ = jValues.poll();
                sum = firstVectorI * iValues.poll() + firstVectorJ * jValues.poll();

                System.out.println("Consumer consumed: sum= "
                        + sum + '\n');

                notify();

                Thread.sleep(MILLISECONDS);
            }
        }
    }
}
