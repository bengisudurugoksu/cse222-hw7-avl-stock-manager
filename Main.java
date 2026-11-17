import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
/**
 * Main class to perform AVL tree performance analysis.
 */
public class Main {
    /**
     * The main method that prompts the user to choose an operation and performs the selected analysis.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        performanceanalysis perform = new performanceanalysis();
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to perform?\n1-Random generated AVL tree analysis.\n2-Performance analysis from file.");
        int choice = scanner.nextInt();
        switch(choice){
            case 1:
                System.out.print("Please enter the total number of nodes: ");
                int totalNodes = scanner.nextInt();
                System.out.print("Please enter the number of REMOVE operations: ");
                int numRemove = scanner.nextInt();
                if(numRemove>totalNodes){
                    throw new IllegalArgumentException("REMOVE operation number should not be greater than ADD operation number.");
                }
                System.out.print("Please enter the number of SEARCH operations: ");
                int numSearch = scanner.nextInt();
                System.out.print("Please enter the number of UPDATE operations: ");
                int numUpdate = scanner.nextInt();
                if(numUpdate>totalNodes){
                    throw new IllegalArgumentException("UPDATE operation number should not be greater than ADD operation number.");
                }
                String inputFile = "input.txt";
                script.generateRandomFile(inputFile, totalNodes, numRemove, numSearch, numUpdate);

                StockDataManager manager = new StockDataManager();

                try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        processCommand(line, manager);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                perform.performPerformanceAnalysis(manager);
                System.out.println("\nInOrder Traversal\n");
                manager.getAvlTree().inOrderTraversal();
                System.out.println("\nPreOrder Traversal\n");
                manager.getAvlTree().preorder();
                System.out.println("\nPostOrder Traversal\n");
                manager.getAvlTree().postorder();

                break;
            case 2:
                System.out.print("Please enter the total number of nodes: ");
                int totalNodes2 = scanner.nextInt();
                System.out.print("Please enter the number of REMOVE operations: ");
                int numRemove2 = scanner.nextInt();
                if(numRemove2>totalNodes2){
                    throw new IllegalArgumentException("REMOVE operation number should not be greater than ADD operation number.");
                }
                System.out.print("Please enter the number of SEARCH operations: ");
                int numSearch2 = scanner.nextInt();
                System.out.print("Please enter the number of UPDATE operations: ");
                int numUpdate2 = scanner.nextInt();
                if(numUpdate2>totalNodes2){
                    throw new IllegalArgumentException("UPDATE operation number should not be greater than ADD operation number.");
                }
                String inputFile2 = "input2.txt";
                script.generateRandomFile(inputFile2, totalNodes2, numRemove2, numSearch2, numUpdate2);

                StockDataManager manager2 = new StockDataManager();

                try (BufferedReader br = new BufferedReader(new FileReader(inputFile2))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        processCommand(line, manager2);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.print("Please enter the range: ");
                int range = scanner.nextInt();
                perform.performanceanalysisfromfile(totalNodes2, range);
                System.out.println("\nInOrder Traversal\n");
                manager2.getAvlTree().inOrderTraversal();
                System.out.println("\nPreOrder Traversal\n");
                manager2.getAvlTree().preorder();
                System.out.println("\nPostOrder Traversal\n");
                manager2.getAvlTree().postorder();

                break;
            default:
                throw new IllegalArgumentException("Not a valid choice.");
        }

    }
    /**
     * Processes a single command line and performs the corresponding action on the stock data manager.
     *
     * @param line the command line to process
     * @param manager the stock data manager to perform actions on
     */
    private static void processCommand(String line, StockDataManager manager) {
        String[] tokens = line.split(" ");
        String command = tokens[0];

        switch (command) {
            case "ADD":
                manager.addOrUpdateStock(tokens[1], Double.parseDouble(tokens[2]), Long.parseLong(tokens[3]), Long.parseLong(tokens[4]));
                break;
            case "REMOVE":
                manager.removeStock(tokens[1]);
                break;
            case "SEARCH":
                Stock stock = manager.searchStock(tokens[1]);
                if (stock != null) {
                    System.out.println(stock);
                } else {
                    System.out.println("Stock not found: " + tokens[1]);
                }
                break;
            case "UPDATE":
                manager.updateStock(tokens[1], tokens[2], Double.parseDouble(tokens[3]), Long.parseLong(tokens[4]), Long.parseLong(tokens[5]));
                break;
            default:
                System.out.println("Unknown command: " + command);
                break;
        }
    }

}



