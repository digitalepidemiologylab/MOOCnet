package MOOCnet;

/**
 * Created with IntelliJ IDEA.
 * User: ellscampbell
 * Date: 5/7/13
 * Time: 12:22 PM
 */
public class Node {

    private int id;
    private int status;
    public static final int NEUTRAL = 0;
    public static final int REFUSE  = 1;
    public static final int ACCEPT  = 2;

    public Node(int id) {
        this.id = id;
        this.status = NEUTRAL;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }
}
