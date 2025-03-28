import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class SortTask extends RecursiveTask<int[]> {
    private final int[] arr;
    private final int THRESHOLD;
    public SortTask(int[] arr){
        this.arr = arr;
        THRESHOLD = 1_000_000;
    }
    @Override
    protected int[] compute() {
        if(arr.length == 1){
            return arr;
        }
        SortTask left = new SortTask(Arrays.copyOfRange(arr, 0, arr.length / 2));
        SortTask right = new SortTask(Arrays.copyOfRange(arr, arr.length / 2, arr.length));
        int[] result = null;

        try {
            //don't fork if less than threshold
            if(left.arr.length < THRESHOLD || right.arr.length < THRESHOLD)
                result = merge(left.arr, right.arr);
            else {
                result = merge(left.fork().join(), right.fork().join());
            }
        }catch (Exception ex){
            //do nothing
        }

        return result;
    }
    public static int[] merge(int[] arr1, int[] arr2){
        int pointer1 = 0, pointer2 = 0, pointerResult = 0;
        int[] result = new int[arr1.length + arr2.length];
        while(true){
            if(pointerResult == result.length){
                break;
            }
            if(pointer1 < arr1.length && pointer2 < arr2.length) {
                if (arr1[pointer1] <= arr2[pointer2]) {
                    result[pointerResult++] = arr1[pointer1++];
                }
                else if (arr2[pointer2] < arr1[pointer1]) {
                    result[pointerResult++] = arr2[pointer2++];
                }
            } else if (pointer1 >= arr1.length) {
                result[pointerResult++] = arr2[pointer2++];
            } else {
                result[pointerResult++] = arr1[pointer1++];
            }
        }
        return result;
    }
}
