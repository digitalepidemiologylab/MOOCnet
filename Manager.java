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
        double  refusalCoverage     = 0.20                                    ;
        double  targetAssortativity = 0.99999                               ;
        double  targetDegreeCV      = 2.0                                      ;
        double  rewire              = 42.0                                    ;
        int     numberOfNodes       = 500                                     ;
        int     meanDegree          = 6                                       ;
        int     networkType         = RANDOM_NET                              ;
        if (networkType == SMALLWORLD_NET) rewire = 0.50                      ;
        manager.setInitialConditions(networkType                              ,
                                     numberOfNodes                            ,
                                     meanDegree                               ,
                                     targetDegreeCV                           ,
                                     rewire                                   ,
                                     targetAssortativity                      ,
                                     refusalCoverage)                         ;
        manager.run()                                                         ;
    }

    private void setInitialConditions(int    networkType         ,
                                      int    numberOfNodes       ,
                                      int    meanDegree          ,
                                      double targetDegreeCV      ,
                                      double rewire              ,
                                      double targetAssortativity ,
                                      double refusalCoverage     )         {
        Settings.getInstance().setMeanDegree(meanDegree)                   ;
        Settings.getInstance().setNumberOfNodes(numberOfNodes)             ;
        Settings.getInstance().setTargetCV(targetDegreeCV)                 ;
        Settings.getInstance().setNetworkType(networkType)                 ;
        Settings.getInstance().setRefusalCoverage(refusalCoverage)         ;
        Settings.getInstance().setAssortativityTarget(targetAssortativity) ;
        Settings.getInstance().setSmallWorldRewireProbability(rewire)      ;
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

    private void increaseAssortativity() {
        this.net.increaseAssortativity() ;
    }
}
