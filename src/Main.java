import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        int[] arr = generateNumbers(50_000_000);

        ForkJoinPool pool = new ForkJoinPool();
        long startParallel = System.nanoTime();
        int[] parallelArr = pool.invoke(new SortTask(arr));
        long endParallel = System.nanoTime();
        System.out.println("Parallel Time: " + (endParallel - startParallel) / 1_000_000 + " ms");

        long startSerial = System.nanoTime();
        int[] SerialArr = sortArray(arr);
        long endSerial = System.nanoTime();
        System.out.println("Serial Time: " + (endSerial - startSerial) / 1_000_000 + " ms");
    }
    public static int[] generateNumbers(int count) {
        int[] numbers = new int[count];
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            numbers[i] = random.nextInt();
        }
        return numbers;
    }

    public static int[] sortArray(int[] arr){
        if(arr.length == 1){
            return arr;
        }
        int[] left = sortArray(Arrays.copyOfRange(arr, 0, arr.length / 2));
        int[] right = sortArray(Arrays.copyOfRange(arr, arr.length / 2, arr.length));

        return SortTask.merge(left, right);
    }

}