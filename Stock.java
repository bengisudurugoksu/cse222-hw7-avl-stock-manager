/**
 * This class represents a stock with a symbol, price, volume, and market capitalization.
 */
public class Stock implements Comparable<Stock>{
    private String symbol;
    private double price;
    private long volume;
    private long marketCap;

    // Constructor
    /**
     * Constructs a Stock with the specified symbol, price, volume, and market capitalization.
     *
     * @param symbol    the stock symbol
     * @param price     the stock price
     * @param volume    the stock trading volume
     * @param marketCap the stock market capitalization
     */
    public Stock(String symbol, double price, long volume, long marketCap) {
        this.symbol = symbol;
        this.price = price;
        this.volume = volume;
        this.marketCap = marketCap;
    }

    // Getters and Setters
    /**
     * Returns the stock symbol.
     *
     * @return the stock symbol
     */
    public String getSymbol() {
        return symbol;
    }
    /**
     * Sets the stock symbol.
     *
     * @param symbol the new stock symbol
     */

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    /**
     * Returns the stock price.
     *
     * @return the stock price
     */
    public double getPrice() {
        return price;
    }
    /**
     * Sets the stock price.
     *
     * @param price the new stock price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Returns the stock trading volume.
     *
     * @return the stock trading volume
     */
    public long getVolume() {
        return volume;
    }
    /**
     * Sets the stock trading volume.
     *
     * @param volume the new stock trading volume
     */

    public void setVolume(long volume) {
        this.volume = volume;
    }
    /**
     * Returns the stock market capitalization.
     *
     * @return the stock market capitalization
     */

    public long getMarketCap() {
        return marketCap;
    }
    /**
     * Sets the stock market capitalization.
     *
     * @param marketCap the new stock market capitalization
     */

    public void setMarketCap(long marketCap) {
        this.marketCap = marketCap;
    }
    /**
     * Returns a string representation of the stock.
     *
     * @return a string representation of the stock
     */

    @Override
    public String toString() {
        return "Stock [symbol=" + symbol + ", price=" + price + ", volume=" + volume + ", marketCap=" + marketCap + "]";
    }
    /**
     * Compares this stock to another stock based on the stock symbol.
     *
     * @param other the other stock to compare to
     * @return a negative integer, zero, or a positive integer as this stock symbol
     *         is less than, equal to, or greater than the specified stock symbol
     */
    @Override
    public int compareTo(Stock other) {
        return this.symbol.compareTo(other.symbol);
    }

}