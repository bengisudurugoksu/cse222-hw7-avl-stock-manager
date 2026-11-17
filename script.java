import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class script {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int SYMBOL_LENGTH = 3;
    private static final Random RANDOM = new Random();

    public static void generateRandomFile(String filename, int totalNodes, int numRemove, int numSearch, int numUpdate) {
        ArrayList<String> commands = generateRandomCommands(totalNodes, numRemove, numSearch, numUpdate);
        writeCommandsToFile(filename, commands);
    }

    private static ArrayList<String> generateRandomCommands(int totalNodes, int numRemove, int numSearch, int numUpdate) {
        ArrayList<String> commands = new ArrayList<>();
        Set<String> symbols = new HashSet<>();
        Set<String> addedSymbols = new HashSet<>(); // To track added symbols

        // Generate ADD commands
        for (int i = 0; i < totalNodes; i++) {
            String symbol = generateSymbol();
            while (symbols.contains(symbol)) {
                symbol = generateSymbol(); // Ensure unique symbols
            }
            double price = generatePrice();
            int volume = generateVolume();
            int marketCap = generateMarketCap();
            commands.add(String.format("ADD %s %.2f %d %d", symbol, price, volume, marketCap));
            symbols.add(symbol);
            addedSymbols.add(symbol); // Track added symbols
        }

        ArrayList<String> symbolList = new ArrayList<>(addedSymbols);
        int removeCount = 0, searchCount = 0, updateCount = 0;

        // Round-robin generation of REMOVE, SEARCH, and UPDATE commands
        while (removeCount < numRemove || searchCount < numSearch || updateCount < numUpdate) {
            if (removeCount < numRemove && !symbolList.isEmpty()) {
                String symbol = symbolList.remove(RANDOM.nextInt(symbolList.size()));
                commands.add(String.format("REMOVE %s", symbol));
                addedSymbols.remove(symbol); // Remove from added symbols
                removeCount++;
            }

            if (updateCount < numUpdate && !symbolList.isEmpty()) {
                String symbol = symbolList.get(RANDOM.nextInt(symbolList.size()));
                String newSymbol = generateSymbol();
                while (symbols.contains(newSymbol)) {
                    newSymbol = generateSymbol(); // Ensure unique new symbols
                }
                double newPrice = generatePrice();
                int newVolume = generateVolume();
                int newMarketCap = generateMarketCap();
                commands.add(String.format("UPDATE %s %s %.2f %d %d", symbol, newSymbol, newPrice, newVolume, newMarketCap));
                addedSymbols.remove(symbol); // Remove old symbol
                addedSymbols.add(newSymbol); // Add new symbol
                symbols.add(newSymbol); // Track all symbols to avoid duplication
                symbolList.add(newSymbol); // Add new symbol to list
                updateCount++;
            }

            if (searchCount < numSearch && !addedSymbols.isEmpty()) {
                String symbol = symbolList.get(RANDOM.nextInt(symbolList.size()));
                commands.add(String.format("SEARCH %s", symbol));
                searchCount++;
            }
        }

        return commands;
    }

    private static String generateSymbol() {
        StringBuilder symbol = new StringBuilder(SYMBOL_LENGTH);
        for (int i = 0; i < SYMBOL_LENGTH; i++) {
            symbol.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return symbol.toString();
    }

    private static double generatePrice() {
        return 5.0 + (95.0 * RANDOM.nextDouble());
    }

    private static int generateVolume() {
        return 100000 + RANDOM.nextInt(4900001);
    }

    private static int generateMarketCap() {
        return 1000000 + RANDOM.nextInt(99000001);
    }

    private static void writeCommandsToFile(String filename, ArrayList<String> commands) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (String command : commands) {
                writer.write(command + "\n");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
