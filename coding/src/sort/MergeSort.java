package sort;

import java.util.Arrays;

public class MergeSort {
    int[] arr;
    MergeSort(int[] vals){
        arr = Arrays.copyOf(vals,vals.length);
        mergeSort(0,vals.length-1);
    }
    public void printArray(){
        for(int i:arr) {
            System.out.print(i+" ");
        }
    }
    private void mergeSort(int start, int end){
        if(start >= end) return;
        int mid = start + (end - start)/2;
        mergeSort(start,mid);
        mergeSort(mid+1,end);
        // create L and R
        int[] L = new int[mid - start + 1];
        int[] R = new int[end - mid];
        for(int i = 0;i<L.length;i++){
            L[i] = arr[start+i];
        }
        for(int i = 0;i<R.length;i++){
            R[i] = arr[mid+i+1];
        }
        int i=0,j=0,cur = start;
        while(i<L.length&&j<R.length){
            if(L[i]<R[j]){
                arr[cur++] = L[i++];
            }else{
                arr[cur++] = R[j++];
            }
        }
        while(i<L.length){
            arr[cur++] = L[i++];
        }
        while(j<R.length){
            arr[cur++] = R[j++];
        }
    }
}
