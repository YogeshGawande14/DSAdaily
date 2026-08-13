import java.util.*;

class Solution {
    // Helper to add an interval length to our frequency map
    private void addLen(TreeMap<Integer, Integer> lenMap, int len) {
        lenMap.put(len, lenMap.getOrDefault(len, 0) + 1);
    }

    // Helper to remove an interval length from our frequency map
    private void removeLen(TreeMap<Integer, Integer> lenMap, int len) {
        int count = lenMap.get(len);
        if (count == 1) {
            lenMap.remove(len);
        } else {
            lenMap.put(len, count - 1);
        }
    }

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        char[] chars = s.toCharArray();
        int n = chars.length;
        int k = queryIndices.length;

        // Intervals stored as int[]{start, end} ordered by start index
        TreeSet<int[]> set = new TreeSet<>((a, b) -> Integer.compare(a[0], b[0]));
        TreeMap<Integer, Integer> lenMap = new TreeMap<>();

        // Initialize contiguous intervals from initial string
        int start = 0;
        for (int i = 1; i <= n; i++) {
            if (i == n || chars[i] != chars[start]) {
                int[] interval = new int[]{start, i - 1};
                set.add(interval);
                addLen(lenMap, i - start);
                start = i;
            }
        }

        int[] result = new int[k];

        for (int q = 0; q < k; q++) {
            int idx = queryIndices[q];
            char newChar = queryCharacters.charAt(q);

            if (chars[idx] != newChar) {
                // 1. Locate interval containing idx
                int[] curr = set.floor(new int[]{idx, idx});
                
                // Remove existing interval from set and lenMap
                set.remove(curr);
                removeLen(lenMap, curr[1] - curr[0] + 1);

                // Split current interval around idx
                int l1 = curr[0], r1 = idx - 1;
                int l2 = idx + 1, r2 = curr[1];

                if (l1 <= r1) {
                    set.add(new int[]{l1, r1});
                    addLen(lenMap, r1 - l1 + 1);
                }
                if (l2 <= r2) {
                    set.add(new int[]{l2, r2});
                    addLen(lenMap, r2 - l2 + 1);
                }

                // Update actual character array
                chars[idx] = newChar;

                // 2. Create single element interval for idx and try merging with neighbors
                int newL = idx, newR = idx;

                // Check left neighbor
                if (idx > 0 && chars[idx - 1] == newChar) {
                    int[] leftInterval = set.floor(new int[]{idx - 1, idx - 1});
                    set.remove(leftInterval);
                    removeLen(lenMap, leftInterval[1] - leftInterval[0] + 1);
                    newL = leftInterval[0];
                }

                // Check right neighbor
                if (idx < n - 1 && chars[idx + 1] == newChar) {
                    int[] rightInterval = set.ceiling(new int[]{idx + 1, idx + 1});
                    set.remove(rightInterval);
                    removeLen(lenMap, rightInterval[1] - rightInterval[0] + 1);
                    newR = rightInterval[1];
                }

                // Add merged new interval
                set.add(new int[]{newL, newR});
                addLen(lenMap, newR - newL + 1);
            }

            // Max length is always the highest key in lenMap
            result[q] = lenMap.lastKey();
        }

        return result;
    }
}