import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
/**
 * This class performs performance analysis on stock data operations using an AVL tree.
 */
public class performanceanalysis {
    /**
     * Perform performance analysis by running various operations (ADD, SEARCH, REMOVE, UPDATE)
     * on the stock data manager and measuring the time taken for each operation.
     *
     * @param manager The stock data manager on which operations will be performed.
     */
    public void performPerformanceAnalysis(StockDataManager manager) {
        long starttime, endtime;
        List<Integer> xInputSizes = new ArrayList<>();
        List<Long> addOperationTimes = new ArrayList<>();
        List<Long> searchOperationTimes = new ArrayList<>();
        List<Long> removeOperationTimes = new ArrayList<>();
        List<Long> updateOperationTimes = new ArrayList<>();

        for (int i = 10; i <= 1000; i += 50) {
            long addTime = 0, searchTime = 0, removeTime = 0, updateTime=0;
            /** Repeat each operation 'repetitions' times for averaging */

            for (int r = 0; r < 100; r++) {
                /** Add operation */
                starttime = System.nanoTime();
                for (int addop = 0; addop < i; addop++) {
                    manager.addOrUpdateStock("SYM" + i + addop, Math.random() * 100, (long) (Math.random() * 1000000), (long) (Math.random() * 1000000000));
                }
                endtime = System.nanoTime();
                addTime += (endtime - starttime);
                /** Search operation*/
                starttime = System.nanoTime();
                for (int searchopp = 0; searchopp < i; searchopp++) {
                    manager.searchStock("SYM" + i + searchopp);
                }
                endtime = System.nanoTime();
                searchTime += (endtime - starttime);

                /** Remove operation*/
                starttime = System.nanoTime();
                for (int removeopp = 0; removeopp < i; removeopp++) {
                    manager.removeStock("SYM" + i + removeopp);
                }
                endtime = System.nanoTime();
                removeTime += (endtime - starttime);
                /** Update operation*/
                starttime = System.nanoTime();
                for (int updateopp = 0; updateopp < i; updateopp++) {
                    manager.updateStock("SYM" + i + updateopp, "SYM" + i + updateopp, Math.random() * 100, (long) (Math.random() * 1000000), (long) (Math.random() * 1000000000));
                }
                endtime = System.nanoTime();
                updateTime += (endtime - starttime);
            }
            /** Average the times over the repetitions*/
            xInputSizes.add(i);
            addOperationTimes.add(addTime / 100);
            searchOperationTimes.add(searchTime / 100);
            removeOperationTimes.add(removeTime / 100);
            updateOperationTimes.add(updateTime / 100);

            System.out.println("Size: " + i);
            System.out.println("Average ADD time: " + addOperationTimes.get(addOperationTimes.size() - 1) + " ns");
            System.out.println("Average SEARCH time: " + searchOperationTimes.get(searchOperationTimes.size() - 1) + " ns");
            System.out.println("Average REMOVE time: " + removeOperationTimes.get(removeOperationTimes.size() - 1) + " ns");
            System.out.println("Average UPDATE time: " + updateOperationTimes.get(updateOperationTimes.size() - 1) + " ns");
            System.out.println();
        }
        /** Display results in GUI*/
        SwingUtilities.invokeLater(() -> {
            GUIVisualization addPlot = new GUIVisualization("scatter", xInputSizes, addOperationTimes,"ADD Operation Performance Random Generated");
            addPlot.setVisible(true);

            GUIVisualization searchPlot = new GUIVisualization("scatter", xInputSizes, searchOperationTimes, "SEARCH Operation Performance Random Generated");
            searchPlot.setVisible(true);

            GUIVisualization removePlot = new GUIVisualization("scatter", xInputSizes, removeOperationTimes, "REMOVE Operation Performance Random Generated");
            removePlot.setVisible(true);

            GUIVisualization updatePlot = new GUIVisualization("scatter", xInputSizes, removeOperationTimes, "UPDATE Operation Performance Random Generated");
            updatePlot.setVisible(true);
        });
    }

    /**
     * Perform performance analysis from a file by running various operations (ADD, SEARCH, REMOVE, UPDATE)
     * on subsets of stock data from the manager and measuring the time taken for each operation.
     *
     * @param Nodesize The total number of nodes to be analyzed.
     * @param range    The range of nodes to be processed in each iteration.
     */
    public void performanceanalysisfromfile( int Nodesize, int range) {
        StockDataManager manager = new StockDataManager();

        long starttime, endtime;

        List<Integer> xInputSizes = new ArrayList<>();
        List<Long> addOperationTimes = new ArrayList<>();
        List<Long> searchOperationTimes = new ArrayList<>();
        List<Long> removeOperationTimes = new ArrayList<>();
        List<Long> updateOperationTimes = new ArrayList<>();
        long SumAddTime = 0;
        long SumSearchTime = 0;
        long SumRemoveTime = 0;
        long SumUpdateTime = 0;

        for (int i = 0; i < Nodesize; i += range) {
            List<Stock> subsetStocks = manager.getstockportion(range);
            AVLTree avlTree = new AVLTree();
            for (Stock stock : subsetStocks) {
                avlTree.insert(stock);
            }
            Stock NewStock  = new Stock("SYM" + Nodesize+i, Math.random() * 100, (long) (Math.random() * 1000000), (long) (Math.random() * 1000000000));
            for (int j = 0; j < 10; j++) {
                starttime = System.nanoTime();
                avlTree.insert(NewStock);
                endtime = System.nanoTime();
                SumAddTime += (endtime - starttime);
                starttime = System.nanoTime();
                avlTree.search(NewStock.getSymbol());
                endtime = System.nanoTime();
                SumSearchTime += (endtime - starttime);

                starttime = System.nanoTime();
                avlTree.delete(NewStock.getSymbol());
                endtime = System.nanoTime();
                SumRemoveTime += (endtime - starttime);

                avlTree.insert(NewStock);
                starttime = System.nanoTime();
                avlTree.update(NewStock);
                endtime = System.nanoTime();
                SumUpdateTime += (endtime - starttime);
                avlTree.delete(NewStock.getSymbol());
            }
            xInputSizes.add(i);
            addOperationTimes.add(SumAddTime/ 10);
            removeOperationTimes.add(SumRemoveTime /10);
            searchOperationTimes.add(SumSearchTime / 10);
            updateOperationTimes.add(SumUpdateTime /10);
        }
        /** Display results in GUI*/
        SwingUtilities.invokeLater(() -> {
            GUIVisualization addPlot = new GUIVisualization("scatter", xInputSizes, addOperationTimes, "ADD PERFORMANCE GRAPH FROM FILE");
            addPlot.setVisible(true);
            GUIVisualization removePlot = new GUIVisualization("scatter", xInputSizes, removeOperationTimes, "REMOVE PERFORMANCE GRAPH FROM FILE");
            removePlot.setVisible(true);
            GUIVisualization searchPlot = new GUIVisualization("scatter", xInputSizes, searchOperationTimes, "SEARCH PERFORMANCE GRAPH FROM FILE");
            searchPlot.setVisible(true);
            GUIVisualization updatePlot = new GUIVisualization("scatter", xInputSizes, updateOperationTimes, "UPDATE PERFORMANCE GRAPH FROM FILE");
            updatePlot.setVisible(true);
        });



    }
}
