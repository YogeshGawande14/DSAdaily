/*
 Program to Search A Number in Array
 Linear Search Algorithm
 Date : 06/09/2025
 */

public class Linear_Search {
    public static int serch(int arra[],int key){
        for(int i=0;i<arra.length;i++){
            if(arra[i]==key){
                return i;
            }

        }
        return-1;
    }
    public static void main(String[] args) {
        int arra[]={1,2,3,4,5,8,9,7,6};
        int key=5;
        int recieve=serch(arra,key);
        if(recieve==-1){
            System.out.println("Sorry ! Not Found ");

        }
        else{

            System.out.println("element is found  at : " + recieve);
        }



    }

}
/*
🔍 Linear Search – Quick Revision
  Input:
     - Array arr[]
     - Key k to search

   Steps:
        - Loop i from 0 to arr.length - 1
        - If arr[i] == k, return i
    - If loop ends, return -1 (not found)


    Output:
        - Index of k if found
        - -1 if not found

*/

