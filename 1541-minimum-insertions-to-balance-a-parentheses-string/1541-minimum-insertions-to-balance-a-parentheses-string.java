class Solution {
    public int minInsertions(String s) {
        int res = 0;   // number of insertions
        int need = 0;  // number of ')' needed

        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                need += 2; // each '(' needs two ')'
                if (need % 2 == 1) { // odd, fix by inserting one ')'
                    res++;
                    need--;
                }
            } else { // ch == ')'
                need--;
                if (need < 0) { // too many ')'
                    res++;      // insert '('
                    need = 1;   // now one ')' is still needed
                }
            }
        }
        return res + need; // add remaining needed ')'
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        String s = "(()))";
        System.out.println(sol.minInsertions(s)); // Output: 1
    }
}
