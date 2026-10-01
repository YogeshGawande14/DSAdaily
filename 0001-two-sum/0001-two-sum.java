import java.util.HashMap;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            
            // जर हवी असलेली संख्या आधीच map मध्ये असेल, तर उत्तर सापडले
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            
            // नाहीतर वर्तमान संख्या आणि तिचा इंडेक्स map मध्ये साठवा
            map.put(nums[i], i);
        }
        
        return new int[] {};
    }
}