package best_time_to_buy_and_sell_stock;

public class BestTimeToBuyAndSellStockMain {
    public static void main(String[] args) {
        BestTimeToBuyAndSellStockSolution solution = new BestTimeToBuyAndSellStockSolution();
        int[] prices = {7, 1, 5, 3, 6, 4};
        int profit = solution.maxProfit(prices);
        System.out.println("Maximum profit: " + profit);
    }
}





