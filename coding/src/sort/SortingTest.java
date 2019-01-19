package sort;

public class SortingTest {
    public static void main(String[] args){
        int[] arr = new int[]{2,10,3,4};
        MergeSort s1 = new MergeSort(arr);
        s1.printArray();
    }
}
