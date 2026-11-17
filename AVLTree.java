import java.util.ArrayList;
import java.util.List;
/**
 * A class representing an AVL Tree for managing Stock objects.
 * The AVL Tree is a self-balancing binary search tree.
 */
public class AVLTree {


    private class Node {
        Stock stock;
        Node left, right;
        int height;

        Node(Stock stock) {
            this.stock = stock;
            this.height = 1;
        }
    }

    private Node root;
    /**
     * Inserts a stock into the AVL Tree.
     *
     * @param stock the stock to be inserted
     */

    // Insertion
    public void insert(Stock stock) {
        root = insert(root, stock);
    }

    private Node insert(Node node, Stock stock) {
        // Step 1: Perform the normal BST insertion
        if (node == null) {
            return new Node(stock);
        }

        if (stock.compareTo(node.stock) < 0) {
            node.left = insert(node.left, stock);
        } else if (stock.compareTo(node.stock) > 0) {
            node.right = insert(node.right, stock);
        } else {
            // Duplicate stocks are not allowed in the AVL tree
            return node;
        }

        // Step 2: Update the height of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Step 3: Get the balance factor of this ancestor node to check whether this node became unbalanced
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && stock.compareTo(node.left.stock) < 0) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && stock.compareTo(node.right.stock) > 0) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && stock.compareTo(node.left.stock) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && stock.compareTo(node.right.stock) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }
    private int getBalance(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }
    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    /**
     * Deletes a stock from the AVL Tree based on the stock symbol.
     *
     * @param symbol the symbol of the stock to be deleted
     */

    // Deletion
    public void delete(String symbol) {
        root = delete(root, symbol);
    }

    private Node delete(Node node, String symbol) {
        // Implementation of AVL Tree deletion logic
        // Step 1: Perform the normal BST deletion
        if (node == null) {
            return node;
        }

        if (symbol.compareTo(node.stock.getSymbol()) < 0) {
            node.left = delete(node.left, symbol);
        } else if (symbol.compareTo(node.stock.getSymbol()) > 0) {
            node.right = delete(node.right, symbol);
        } else {
            // Node to be deleted is found

            // Node with only one child or no child
            if ((node.left == null) || (node.right == null)) {
                Node temp = null;
                if (temp == node.left) {
                    temp = node.right;
                } else {
                    temp = node.left;
                }

                // No child case
                if (temp == null) {
                    temp = node;
                    node = null;
                } else { // One child case
                    node = temp; // Copy the contents of the non-empty child
                }
            } else {
                // Node with two children: Get the inorder successor (smallest in the right subtree)
                Node temp = minValueNode(node.right);

                // Copy the inorder successor's data to this node
                node.stock = temp.stock;

                // Delete the inorder successor
                node.right = delete(node.right, temp.stock.getSymbol());
            }
        }

        // If the tree had only one node then return
        if (node == null) {
            return node;
        }

        // Step 2: Update the height of the current node
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        // Step 3: Get the balance factor of this node
        int balance = getBalance(node);

        // Step 4: Balance the node if it has become unbalanced

        // Left Left Case
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }

        // Left Right Case
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }

        // Right Left Case
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }
    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Search
    /**
     * Searches for a stock in the AVL Tree based on the stock symbol.
     *
     * @param symbol the symbol of the stock to search for
     * @return the Stock object if found, otherwise null
     */
    public Stock search(String symbol) {
        Node result = search(root, symbol);
        return (result != null) ? result.stock : null;
    }

    private Node search(Node node, String symbol) {
        // Implementation of AVL Tree search logic
        // Base case: root is null or the symbol is present at root
        if (node == null || node.stock.getSymbol().equals(symbol)) {
            return node;
        }

        // Symbol is smaller than node's symbol
        if (symbol.compareTo(node.stock.getSymbol()) < 0) {
            return search(node.left, symbol);
        }

        // Symbol is larger than node's symbol
        return search(node.right, symbol);
    }
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }
    private void updateHeight(Node node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }
    // Balancing methods (left rotation, right rotation, etc.)
    // Height update and balance factor calculations

    // In-order, pre-order, post-order traversals
    // For example:
    /**
     * Performs an in-order traversal of the AVL Tree and prints each stock.
     */
    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    private void inOrderTraversal(Node node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println(node.stock);
            inOrderTraversal(node.right);
        }
    }
    /**
     * Performs an in-order traversal of the AVL Tree and returns a list of stocks up to the specified portion.
     *
     * @param portion the maximum number of stocks to return
     * @return a list of stocks
     */
    public List<Stock> inOrderTraversal(int portion) {
        List<Stock> result = new ArrayList<>();
        inOrderTraversal(root, result, portion);
        return result;
    }
    private void inOrderTraversal(Node node, List<Stock> result, int portion) {
        if (node == null || result.size() >= portion) {
            return;
        }
        inOrderTraversal(node.left, result, portion);
        if (result.size() < portion) {
            result.add(node.stock);
        }
        inOrderTraversal(node.right, result, portion);
    }
    // Preorder traversal method
    /**
     * Performs a pre-order traversal of the AVL Tree and prints each stock.
     *
     * @param node the starting node for the traversal
     */
    public void preorder(Node node) {
        if (node != null) {
            System.out.print(node.stock + "\n");
            preorder(node.left);
            preorder(node.right);
        }
    }

    // Utility method to start preorder traversal from the root
    /**
     * Utility method to start a pre-order traversal from the root.
     */
    public void preorder() {
        preorder(root);
    }
    /**
     * Performs a post-order traversal of the AVL Tree and prints each stock.
     *
     * @param node the starting node for the traversal
     */
    public void postorder(Node node) {
        if (node != null) {
            postorder(node.left);
            postorder(node.right);
            System.out.print(node.stock + "\n");
        }
    }

    // Utility method to start postorder traversal from the root
    /**
     * Utility method to start a post-order traversal from the root.
     */
    public void postorder() {
        postorder(root);
    }
    /**
     * Updates a stock's information in the AVL Tree.
     *
     * @param updatedStock the updated stock information
     */
    public void update(Stock updatedStock) {
        Node node = searchNode(root, updatedStock.getSymbol());
        String newSymbol = "SYM" + 10;
        double newprice = Math.random() * 100;
        long newvolume = (long) (Math.random() * 1000000);
        long newmarketcap = (long) (Math.random() * 100000000);
        if (node != null) {
            updatedStock.setSymbol(newSymbol);	
            updatedStock.setPrice(newprice);
            updatedStock.setVolume(newvolume);
            updatedStock.setMarketCap(newmarketcap);
        }
    }

    private Node searchNode(Node node, String symbol) {
        if (node == null || node.stock.getSymbol().equals(symbol)) {
            return node;
        }

        if (symbol.compareTo(node.stock.getSymbol()) < 0) {
            return searchNode(node.left, symbol);
        }

        return searchNode(node.right, symbol);
    }
}
