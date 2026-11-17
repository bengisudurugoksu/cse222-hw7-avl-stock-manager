import java.util.List;
/**
 * Manages stock data using an AVL tree for efficient storage and retrieval.
 */
public class StockDataManager {
    /**
     * Constructs a new StockDataManager with an empty AVL tree.
     */
    private AVLTree avlTree;

    public StockDataManager() {
        avlTree = new AVLTree();
    }

    /**
     * Adds a new stock or updates an existing stock's details.
     *
     * @param symbol    the stock symbol
     * @param price     the stock price
     * @param volume    the stock volume
     * @param marketCap the stock market capitalization
     */
    public void addOrUpdateStock(String symbol, double price, long volume, long marketCap) {
        Stock existingStock = avlTree.search(symbol);
        String newSymbol = "SYM" + 10;
        if (existingStock != null) {
            existingStock.setSymbol(newSymbol);
            existingStock.setPrice(price);
            existingStock.setVolume(volume);
            existingStock.setMarketCap(marketCap);
        } else {
            Stock newStock = new Stock(symbol, price, volume, marketCap);
            avlTree.insert(newStock);
        }
    }
    /**
     * Returns the AVL tree used by the manager.
     *
     * @return the AVL tree
     */
    public AVLTree getAvlTree(){
        return avlTree;
    }

    /**
     * Removes a stock from the AVL tree.
     *
     * @param symbol the stock symbol to remove
     */
    public void removeStock(String symbol) {
        avlTree.delete(symbol);
    }

    /**
     * Searches for a stock in the AVL tree.
     *
     * @param symbol the stock symbol to search for
     * @return the stock if found, null otherwise
     */
    public Stock searchStock(String symbol) {
        return avlTree.search(symbol);
    }
    /**
     * Retrieves a portion of stocks from the AVL tree in in-order traversal.
     *
     * @param n the number of stocks to retrieve
     * @return a list of stocks
     */
    public List<Stock> getstockportion(int n){
        return avlTree.inOrderTraversal(n);
    }
    /**
     * Updates the details of an existing stock.
     *
     * @param symbol       the stock symbol
     * @param newPrice     the new price
     * @param newVolume    the new volume
     * @param newMarketCap the new market capitalization
     */
    public void updateStock(String symbol, String newSymbol, double newPrice, long newVolume, long newMarketCap) {
        Stock stock = avlTree.search(symbol);
        if (stock != null) {
            stock.setSymbol(newSymbol);
            stock.setPrice(newPrice);
            stock.setVolume(newVolume);
            stock.setMarketCap(newMarketCap);
        }
    }

    /**
     * Main method for testing the StockDataManager class.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        StockDataManager manager = new StockDataManager();
        manager.addOrUpdateStock("AAPL", 150.0, 1000000, 2500000000L);
        manager.addOrUpdateStock("GOOGL", 2800.0, 500000, 1500000000L);
        System.out.println(manager.searchStock("AAPL"));
        manager.removeStock("AAPL");
        System.out.println(manager.searchStock("AAPL"));
    }
}
