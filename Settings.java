package MOOCnet;

/**
 * Created with IntelliJ IDEA.
 * User: ellscampbell
 * Date: 5/7/13
 * Time: 3:06 PM
 */
public class Settings {

    private static Settings ourInstance = new Settings() ;
    private int    meanDegree                  = 5       ;
    private double targetCV                    = 2       ;
    private int    numberOfNodes               = 500     ;
    private int    networkType                 = 0       ;
    private double refusalCoverage             = 0.20    ;
    private double assortativityTarget         = 0.99999 ;
    private double smallWorldRewireProbability = 0.05    ;

    public static Settings getInstance() {
        return ourInstance;
    }

    private Settings() {
    }

    public void setSmallWorldRewireProbability(double rewireProbability) {
        this.smallWorldRewireProbability = rewireProbability;
    }

    public double getSmallWorldRewireProbability() {
        return this.smallWorldRewireProbability;
    }

    public void setAssortativityTarget(double assortativityTarget) {
        this.assortativityTarget = assortativityTarget;
    }

    public double getAssortativityTarget() {
        return this.assortativityTarget;
    }

    public void setRefusalCoverage(double refusalCoverage) {
        this.refusalCoverage = refusalCoverage;
    }

    public double getRefusalCoverage() {
        return this.refusalCoverage;
    }

    public void setNetworkType(int networkType) {
        this.networkType = networkType;
    }

    public int getNetworkType() {
        return this.networkType;
    }

    public void setMeanDegree(int meanDegree) {
        this.meanDegree = meanDegree;
    }

    public int getMeanDegree() {
        return this.meanDegree;
    }

    public void setTargetCV(double targetCV) {
        this.targetCV = targetCV;
    }

    public double getTargetCV() {
        return this.targetCV;
    }

    public void setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
    }

    public int getNumberOfNodes() {
        return this.numberOfNodes;
    }
}