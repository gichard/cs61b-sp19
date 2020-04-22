import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hug.
 */
public class Experiments {
    public static void experiment1() {
        int EXP = 5000;
        List<Double> avgDepth = new ArrayList<>();
        List<Double> optDepth = new ArrayList<>();
        BST<Double> randomBST = new BST<>();
        List<Integer> bstSize = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < EXP; i++) {
            randomBST.add(r.nextDouble());
            if ((i + 1) % 50 == 0) {
                avgDepth.add(randomBST.avgDepth());
                bstSize.add(i + 1);
                optDepth.add(ExperimentHelper.optimalAverageDepth(i + 1));
            }
        }

        XYChart bstChart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        bstChart.addSeries("average depth of random BST", bstSize, avgDepth);
        bstChart.addSeries("optimal average depth", bstSize, optDepth);

        new SwingWrapper(bstChart).displayChart();
    }

    /** You should see that for some number of operations, the average depth actually drops as we randomly insert and delete.
     *  However, as the insertion/deletion cycle continues, you should see the depth climb well above the starting depth.
     */
    public static void experiment2() {
        int INITSIZE = 5000;
        BST<Double> randomBST = new BST<>();
        List<Double> avgDepth = new ArrayList<>();
        int OPS = 1000;
        Random r = new Random();
        List<Integer> numOP = new ArrayList<>();
        for (int i = 0; i < INITSIZE; i++) {
            randomBST.add(r.nextDouble());
        }

        for (int i = 0; i < OPS; i++) {
            randomBST.deleteTakingSuccessor(randomBST.getRandomKey());
            randomBST.add(r.nextDouble());
            avgDepth.add(randomBST.avgDepth());
            numOP.add(i + 1);
        }

        XYChart bstChart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        bstChart.addSeries("average depth after random Asymmetric deletion and insertion", numOP, avgDepth);

        new SwingWrapper(bstChart).displayChart();
    }

    /** You should see that the average depth drops and stays down. It should converge to 88% of the starting depth.
     * Nobody knows why this happens. It’s OK if your results are off by a little bit.
     */
    public static void experiment3() {
        int INITSIZE = 5000;
        BST<Double> randomBST = new BST<>();
        List<Double> avgDepth = new ArrayList<>();
        int OPS = 1000;
        Random r = new Random();
        List<Integer> numOP = new ArrayList<>();
        for (int i = 0; i < INITSIZE; i++) {
            randomBST.add(r.nextDouble());
        }

        for (int i = 0; i < OPS; i++) {
            randomBST.deleteTakingRandom(randomBST.getRandomKey());
            randomBST.add(r.nextDouble());
            avgDepth.add(randomBST.avgDepth());
            numOP.add(i + 1);
        }

        XYChart bstChart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        bstChart.addSeries("average depth after random Symmetric deletion and insertion", numOP, avgDepth);

        new SwingWrapper(bstChart).displayChart();
    }

    public static void main(String[] args) {
//        experiment1();
//        experiment2();
        experiment3();
    }
}
