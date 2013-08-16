package MOOCnet;

import edu.uci.ics.jung.graph.Graph;
import java.io.IOException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: ellscampbell
 * Date: 5/13/13
 * Time: 7:54 PM
 */
public class Manager {

    private Graph<Node,Edge> graph                  ;
    private Node[]           nodes                  ;
    private Edge[]           edges                  ;
    private Random           random = new Random()  ;
    private Network          net    = new Network() ;


    public static final int   RANDOM_NET     = 0 ;
    public static final int   SMALLWORLD_NET = 1 ;

    public static void main(String[] args) throws IOException                 {
        Manager manager             = new Manager()                           ;
        double  refusalCoverage     = 0                                       ;
        double  targetAssortativity = 1                                       ;
        double  targetDegreeCV      = 0                                       ;
        double  rewire              = 1.0                                     ;
        int     numberOfNodes       = 500                                     ;
        int     meanDegree          = 10                                      ;
        int     maxDegree           = 13                                      ;
        int     minDegree           = 8                                       ;
        int     networkType         = SMALLWORLD_NET                          ;
        if (networkType == SMALLWORLD_NET) rewire = 0.10                      ;
        manager.setInitialConditions(networkType                              ,
                                     numberOfNodes                            ,
                                     meanDegree                               ,
                                     targetDegreeCV                           ,
                                     rewire                                   ,
                                     targetAssortativity                      ,
                                     refusalCoverage                          ,
                                     minDegree                                ,
                                     maxDegree)                               ;
        manager.run()                                                         ;
    }

    private void setInitialConditions(int    networkType         ,
                                      int    numberOfNodes       ,
                                      int    meanDegree          ,
                                      double targetDegreeCV      ,
                                      double rewire              ,
                                      double targetAssortativity ,
                                      double refusalCoverage     ,
                                      int    minDegree           ,
                                      int    maxDegree           )         {
        Settings.getInstance().setMeanDegree(meanDegree)                   ;
        Settings.getInstance().setNumberOfNodes(numberOfNodes)             ;
        Settings.getInstance().setTargetCV(targetDegreeCV)                 ;
        Settings.getInstance().setNetworkType(networkType)                 ;
        Settings.getInstance().setRefusalCoverage(refusalCoverage)         ;
        Settings.getInstance().setAssortativityTarget(targetAssortativity) ;
        Settings.getInstance().setSmallWorldRewireProbability(rewire)      ;
        Settings.getInstance().setMinimumDegree(minDegree);                ;
        Settings.getInstance().setMaximumDegree(maxDegree);                ;
    }

    private void run() {
        int networkType = Settings.getInstance().getNetworkType() ;
        System.out.println(networkType)                           ;
        this.generate_low_and_high_CV_networks(networkType)       ;
        this.initGraph()                                          ;
        this.assignVaccinationRefusal()                           ;
        this.increaseAssortativity()                              ;
        this.net.printResultsToFile()                             ;

    }
    private void generate_low_and_high_CV_networks(int networkType) {
        if (networkType == RANDOM_NET) {
            this.net.initRandomGraph() ;
            this.net.runRandom()       ;
        }
        if (networkType == SMALLWORLD_NET) {
            this.net.initSmallWorldGraph() ;
            this.net.runSmallWorld()       ;
        }
    }

    private void initGraph() {
        this.graph = this.net.returnGraph() ;
    }

    private void assignVaccinationRefusal() {
        this.net.assignVaccinationSentimentStatus() ;
    }

    private double increaseAssortativity() {
        double finalR = this.net.increaseAssortativity() ;
        return finalR;
    }
}
