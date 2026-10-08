class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder sb = new StringBuilder();
        int opened = 0;
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                // If depth > 1 after incrementing, it's not an outermost parenthesis
                if (++opened > 1) {
                    sb.append(c);
                }
            } else {
                // If depth > 0 after decrementing, it's not an outermost parenthesis
                if (--opened > 0) {
                    sb.append(c);
                }
            }
        }
        
        return sb.toString();
    }
}