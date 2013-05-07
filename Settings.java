package MOOCnet;

/**
 * Created with IntelliJ IDEA.
 * User: ellscampbell
 * Date: 5/7/13
 * Time: 3:06 PM
 */
public class Settings {

    private static Settings ourInstance = new Settings();
    private int    k = 10;
    private double targetCV = 0.50;
    private int    numberOfNodes = 500;


    public static Settings getInstance() {
        return ourInstance;
    }

    private Settings() {

    }

    public void setK(int k) {
        this.k = k;
    }

    public int getK() {
        return this.k;
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