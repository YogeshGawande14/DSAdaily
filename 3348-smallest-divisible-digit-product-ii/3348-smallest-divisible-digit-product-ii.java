
class Solution {

    private static final int[][] FACTORS = {
        {},             // 0
        {},             // 1
        {2},            // 2
        {3},            // 3
        {2, 2},         // 4
        {5},            // 5
        {2, 3},         // 6
        {7},            // 7
        {2, 2, 2},      // 8
        {3, 3}          // 9
    };

    public String smallestNumber(String num, long t) {

        // Required prime-factor counts of t:
        int[] need = new int[4]; // 2,3,5,7
        int[] primes = {2, 3, 5, 7};

        for (int i = 0; i < 4; i++) {
            while (t % primes[i] == 0) {
                need[i]++;
                t /= primes[i];
            }
        }

        // t has a prime factor other than 2,3,5,7.
        if (t != 1) {
            return "-1";
        }

        // Minimum number of digits required to satisfy need.
        int[] minDigits = makeDigits(need);

        if (count(minDigits) > num.length()) {
            return build(minDigits, num.length());
        }

        // Count prime factors present in the whole num.
        int[] suffix = new int[4];

        for (int i = 0; i < num.length(); i++) {
            addFactors(suffix, num.charAt(i) - '0');
        }

        int firstZero = num.indexOf('0');

        if (firstZero == -1) {
            firstZero = num.length();

            // num itself already satisfies the condition.
            if (contains(suffix, need)) {
                return num;
            }
        }

        /*
         * Work from right to left.
         *
         * At position i:
         * - keep num[0...i-1]
         * - replace num[i] with the smallest bigger digit
         * - fill the rest with the smallest possible suffix
         */
        for (int i = num.length() - 1; i >= 0; i--) {

            int current = num.charAt(i) - '0';

            // Remove current digit from prefix/suffix accounting.
            removeFactors(suffix, current);

            int remainingSlots = num.length() - i - 1;

            // Positions after the first zero cannot be kept unchanged.
            if (i > firstZero) {
                continue;
            }

            for (int bigger = current + 1; bigger <= 9; bigger++) {

                int[] remaining = new int[4];

                for (int k = 0; k < 4; k++) {
                    remaining[k] = need[k];
                }

                // Remove factors already supplied by prefix.
                for (int k = 0; k < 4; k++) {
                    remaining[k] = Math.max(
                        0,
                        remaining[k] - suffix[k]
                    );
                }

                // Remove factors supplied by the bigger digit.
                addFactorsToRemaining(remaining, bigger);

                int[] digits = makeDigits(remaining);

                int required = count(digits);

                if (required <= remainingSlots) {

                    StringBuilder ans = new StringBuilder(num.length());

                    // Original prefix.
                    ans.append(num, 0, i);

                    // Increased digit.
                    ans.append(bigger);

                    // 1's are the smallest possible filler.
                    for (int x = required; x < remainingSlots; x++) {
                        ans.append('1');
                    }

                    // Required factor digits.
                    ans.append(buildDigits(digits));

                    return ans.toString();
                }
            }
        }

        /*
         * No answer with the same length.
         * Therefore use length + 1.
         */
        int[] required = makeDigits(need);

        int ones = num.length() + 1 - count(required);

        StringBuilder ans = new StringBuilder(num.length() + 1);

        for (int i = 0; i < ones; i++) {
            ans.append('1');
        }

        ans.append(buildDigits(required));

        return ans.toString();
    }

    // ------------------------------------------------------------
    // Convert prime-factor requirements into minimum digit counts.
    // ------------------------------------------------------------

    private int[] makeDigits(int[] c) {

        int two = c[0];
        int three = c[1];
        int five = c[2];
        int seven = c[3];

        int[] result = new int[10];

        // 2^3 -> 8
        result[8] = two / 3;
        two %= 3;

        // 3^2 -> 9
        result[9] = three / 2;
        three %= 2;

        // 2^2 -> 4
        result[4] = two / 2;
        two %= 2;

        // 2 * 3 -> 6
        if (two == 1 && three == 1) {
            result[6]++;
            two = 0;
            three = 0;
        }

        /*
         * Special case:
         * 3 + 4 = 2 * 6
         *
         * This gives the same factors using fewer/better digits.
         */
        if (three == 1 && result[4] == 1) {
            result[2]++;
            result[6]++;
            three = 0;
            result[4] = 0;
        }

        if (two == 1) {
            result[2]++;
        }

        if (three == 1) {
            result[3]++;
        }

        result[5] = five;
        result[7] = seven;

        return result;
    }

    // ------------------------------------------------------------

    private void addFactors(int[] count, int digit) {

        for (int factor : FACTORS[digit]) {

            if (factor == 2) {
                count[0]++;
            } else if (factor == 3) {
                count[1]++;
            } else if (factor == 5) {
                count[2]++;
            } else {
                count[3]++;
            }
        }
    }

    private void removeFactors(int[] count, int digit) {

        for (int factor : FACTORS[digit]) {

            if (factor == 2) {
                count[0]--;
            } else if (factor == 3) {
                count[1]--;
            } else if (factor == 5) {
                count[2]--;
            } else {
                count[3]--;
            }
        }
    }

    /*
     * Subtract factors supplied by a digit.
     */
    private void addFactorsToRemaining(int[] remaining, int digit) {

        for (int factor : FACTORS[digit]) {

            if (factor == 2) {
                remaining[0] = Math.max(0, remaining[0] - 1);
            } else if (factor == 3) {
                remaining[1] = Math.max(0, remaining[1] - 1);
            } else if (factor == 5) {
                remaining[2] = Math.max(0, remaining[2] - 1);
            } else {
                remaining[3] = Math.max(0, remaining[3] - 1);
            }
        }
    }

    // ------------------------------------------------------------

    private boolean contains(int[] have, int[] need) {

        for (int i = 0; i < 4; i++) {
            if (have[i] < need[i]) {
                return false;
            }
        }

        return true;
    }

    // ------------------------------------------------------------

    private int count(int[] digits) {

        int sum = 0;

        for (int i = 0; i < digits.length; i++) {
            sum += digits[i];
        }

        return sum;
    }

    // ------------------------------------------------------------

    private String buildDigits(int[] digits) {

        StringBuilder sb = new StringBuilder();

        // Increasing order gives the smallest suffix.
        for (int d = 2; d <= 9; d++) {

            for (int i = 0; i < digits[d]; i++) {
                sb.append((char) ('0' + d));
            }
        }

        return sb.toString();
    }

    // ------------------------------------------------------------

    private String build(int[] digits, int length) {

        int required = count(digits);

        StringBuilder sb = new StringBuilder(length);

        // Fill unused positions with 1.
        for (int i = 0; i < length - required; i++) {
            sb.append('1');
        }

        sb.append(buildDigits(digits));

        return sb.toString();
    }
}

