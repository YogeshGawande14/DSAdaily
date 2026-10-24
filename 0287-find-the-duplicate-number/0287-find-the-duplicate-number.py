class Solution:
    def findDuplicate(self, nums: list[int]) -> int:
        low = 1
        high = len(nums) - 1
        
        while low < high:
            mid = low + (high - low) // 2
            
            # Count how many numbers are less than or equal to mid
            count = 0
            for num in nums:
                if num <= mid:
                    count += 1
            
            # If count exceeds mid, the duplicate is in the left half
            if count > mid:
                high = mid
            else:
                # Otherwise, the duplicate is in the right half
                low = mid + 1
                
        return low