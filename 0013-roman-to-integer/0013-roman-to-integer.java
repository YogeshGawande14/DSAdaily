class Solution {
    public int romanToInt(String s) {
        int total = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            int curr = valueOf(s.charAt(i));
            if (i + 1 < n && curr < valueOf(s.charAt(i + 1))) {
                total -= curr;
            } else {
                total += curr;
            }
        }

        return total;
    }

    private int valueOf(char c) {
        return switch (c) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            default -> 0;
        };
    }
}