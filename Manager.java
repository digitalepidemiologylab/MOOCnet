package MOOCnet;

import edu.uci.ics.jung.graph.Graph;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ellscampbell
 * Date: 5/13/13
 * Time: 7:54 PM
 */
public class Manager {

    private Graph<Node,Edge> graph ;
    private Node[]           nodes ;
    private Edge[]           edges ;

    public static final int   RANDOM_NET     = 0 ;
    public static final int   SMALLWORLD_NET = 1 ;

    public static void main(String[] args) throws IOException {
        Manager manager             = new Manager()                ;
        double  refusalCoverage     = 0.20                         ;
        double  targetAssortativity = 0.99999                      ;
        double  targetDegreeCV      = 2.0                          ;
        int     networkType         = SMALLWORLD_NET               ;
        double  rewire              = 0                            ;
        int     numberOfNodes       = 500                          ;
        int     meanDegree          = 5                            ;
        if (networkType == SMALLWORLD_NET) rewire = 0.50           ;
        manager.initHighCVNetwork(networkType         ,
                                  numberOfNodes       ,
                                  meanDegree          ,
                                  targetDegreeCV      ,
                                  rewire              ,
                                  targetAssortativity ,
                                  refusalCoverage     )            ;
    }

    private void initHighCVNetwork(int networkType, int numberOfNodes, int meanDegree, double targetDegreeCV, double rewire, double targetAssortativity, double refusalCoverage) {
        Network net = new Network()                                        ;
        Settings.getInstance().setMeanDegree(meanDegree)                   ;
        Settings.getInstance().setNumberOfNodes(numberOfNodes)             ;
        Settings.getInstance().setTargetCV(targetDegreeCV)                 ;
        Settings.getInstance().setNetworkType(networkType)                 ;
        Settings.getInstance().setRefusalCoverage(refusalCoverage)         ;
        Settings.getInstance().setAssortativityTarget(targetAssortativity) ;
        Settings.getInstance().setSmallWorldRewireProbability(rewire)      ;

        if (networkType == RANDOM_NET)     {
            net.initRandomGraph() ;
            net.runRandom();
            net.assignVaccinationSentimentStatus();
            net.increaseAssortativity();
        }
        if (networkType == SMALLWORLD_NET) {
            net.initSmallWorldGraph()  ;
            net.runSmallWorld();
            net.assignVaccinationSentimentStatus();
            net.increaseAssortativity();

        }
        net.printResultsToFile();
    }
}
