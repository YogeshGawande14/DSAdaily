class Solution {

    int n;
    int[] suffix;
    int[][] dp;

    public int stoneGameII(int[] piles) {

        n = piles.length;

        // suffix[i] = total stones from i to n-1
        suffix = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        // dp[i][M] = maximum stones current player can collect
        dp = new int[n][n + 1];

        return solve(0, 1);
    }

    private int solve(int i, int M) {

        // All remaining stones can be taken
        if (2 * M >= n - i) {
            return suffix[i];
        }

        // Already calculated
        if (dp[i][M] != 0) {
            return dp[i][M];
        }

        int best = 0;

        // Try taking X piles
        for (int X = 1; X <= 2 * M; X++) {

            int newM = Math.max(M, X);

            // Opponent's maximum
            int opponent = solve(i + X, newM);

            // Total remaining - opponent's stones
            int current = suffix[i] - opponent;

            best = Math.max(best, current);
        }

        return dp[i][M] = best;
    }
}