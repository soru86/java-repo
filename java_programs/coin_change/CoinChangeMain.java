package coin_change;

public class CoinChangeMain {
    public static void main(String[] args) {
        CoinChangeSolution solution = new CoinChangeSolution();
        int[] coins = {1, 2, 5};
        int amount = 11;
        System.out.println("Minimum coins: " + solution.coinChange(coins, amount));
    }
}

