public class Solution {
    public int findDuplicate(int[] nums) {
        int low = 1;
        int high = nums.length - 1;
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            // Count how many numbers are less than or equal to mid
            int count = 0;
            for (int num : nums) {
                if (num <= mid) {
                    count++;
                }
            }
            
            // If count exceeds mid, the duplicate is in the left half
            if (count > mid) {
                high = mid;
            } else {
                // Otherwise, the duplicate is in the right half
                low = mid + 1;
            }
        }
        
        return low;
    }
}