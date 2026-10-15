class Solution:
    def reverse(self, x: int) -> int:
        # Define 32-bit signed integer limits
        INT_MIN, INT_MAX = -2**31, 2**31 - 1
        
        # Handle negative sign
        sign = -1 if x < 0 else 1
        x = abs(x)
        
        # Reverse the digits by converting to string and back to int
        rev = int(str(x)[::-1]) * sign
        
        # Check for 32-bit overflow/underflow
        if rev < INT_MIN or rev > INT_MAX:
            return 0
            
        return rev