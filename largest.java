/*
Name : Find Largest numberay
Date :06/09/2004

 */
public class largest {
    public static int large(int arr[]){
        int largestNo=Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            if(arr[i]>largestNo){
                largestNo=arr[i];
            }
        }
        return largestNo;
    }

    public static void main(String[] args) {
        int arr[]={2,6,3,400,5,0,800,9,-1,-8,-65};
        int recive=large(arr);
        System.out.println(recive);
}
}
/*
   🔍 Largest Element – Quick Algorithm
Input:
    - Array arr[]
Steps:
  - Initialize largestNo = Integer.MIN_VALUE
  - Loop through each element arr[i]
  - If arr[i] > largestNo, update largestNo = arr[i]
  - After loop, return largestNo
Output:
   - Largest number in the array


 */
