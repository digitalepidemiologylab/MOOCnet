package MOOCnet;
/**
 * Created with IntelliJ IDEA.
 * User: ellscampbell
 * Date: 5/7/13
 * Time: 12:22 PM
 */
import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

import java.util.*;

public class Network {

    private Random random = new Random();
    private Graph<Node, Edge> graph;
    private Node[] nodes ;
    private Edge[] edges ;


    public static void main(String[] args) {
        Network net = new Network();
        net.run();
    }

    public void run() {
        double targetCV = Settings.getInstance().getTargetCV();
        this.initSmallWorldGraph();
        //this.printAdjacencyMatrix();
        this.printEdgeList();
        while (this.getCV() < targetCV) {
            this.rewire();
            //System.out.println(this.getMeanDegree() + "\t" + this.getCV());
        }
        System.out.println("POST CV-OPTIMIZATION NETWORK BELOW");
        //this.printAdjacencyMatrix();
        this.printEdgeList();

    }

    public void initSmallWorldGraph() {
        Set components;
        int numberOfNodes = Settings.getInstance().getNumberOfNodes();
        int k = Settings.getInstance().getK();
        this.nodes = new Node[numberOfNodes];
        do {
            this.graph = new SparseGraph<Node, Edge>();
            for (int i = 0; i < numberOfNodes; i++) {
                Node node = new Node(i);
                this.nodes[i] = node;
                this.graph.addVertex(node);
            }
            for (int i = 0; i < numberOfNodes; i++) {
                for (int ii = 0; ii < k; ii++) {
                    int diff = ii/2 + 1;
                    if (ii%2 == 1) diff *= -1;
                    int newIndex = i + diff;
                    if (newIndex < 0) newIndex += numberOfNodes;
                    if (newIndex >= numberOfNodes) newIndex -= numberOfNodes;
                    Edge newEdge = new Edge();
                    this.graph.addEdge(newEdge, this.nodes[i], this.nodes[newIndex], EdgeType.UNDIRECTED);
                }
            }
            for (Edge edge:this.graph.getEdges()) {
                if (this.random.nextDouble() < 0.05) {
                    Node source = this.graph.getEndpoints(edge).getFirst();
                    Node newDestination;
                    do {
                        newDestination = this.nodes[this.random.nextInt(numberOfNodes)];
                    }
                    while (this.graph.isNeighbor(source,newDestination) || source.equals(newDestination));
                    this.graph.removeEdge(edge);
                    Edge newEdge = new Edge();
                    this.graph.addEdge(newEdge, source, newDestination, EdgeType.UNDIRECTED);
                }
            }
            WeakComponentClusterer wcc = new WeakComponentClusterer();
            components = wcc.transform(this.graph);
        }
        while (components.size() > 1);
        this.remakeEdgeList();
    }

    public void printAdjacencyMatrix() {
        for (Node node : this.graph.getVertices()) {
            System.out.print(node.getID() + ":\t");

            Collection neighbors = this.graph.getNeighbors(node);
            Iterator   neighborIterator = neighbors.iterator();
            while (neighborIterator.hasNext()) {
                Node neighbor = (Node)neighborIterator.next();
                System.out.print(neighbor.getID() + "\t");
            }
            System.out.println();
        }
    }
    public void printEdgeList() {
        for (Edge edge : this.graph.getEdges()) {
            System.out.println(this.graph.getEndpoints(edge).getFirst().getID() + "\t" + this.graph.getEndpoints(edge).getSecond().getID());
        }
    }

    public double getMeanDegree() {
        int sumDegree = 0;
        for (Node node : this.graph.getVertices()) {
            sumDegree += this.graph.degree(node);
        }
        double meanDegree = (1.0*sumDegree) / this.graph.getVertexCount();
        return meanDegree;
    }

    public double getStandardDeviation() {
        double standardDeviation;
        double meanDegree = this.getMeanDegree();
        double sumDifSquares = 0;
        for (Node node : this.graph.getVertices()) {
            double dif = this.graph.degree(node) - meanDegree;
            double difSquared = dif*dif;
            sumDifSquares += difSquared;
        }
        standardDeviation = Math.pow(sumDifSquares / this.graph.getVertexCount(), 0.5);
        return standardDeviation;
    }

    private double getCV() {
        double stDev = this.getStandardDeviation();
        double meanDeg = this.getMeanDegree();
        return stDev/meanDeg;
    }

    private void remakeEdgeList() {
        int i = 0;
        this.edges = new Edge[this.graph.getEdgeCount()];
        for (Edge edge : this.graph.getEdges()) {
            this.edges[i] = edge;
            i++;
        }
    }

    private void rewire() {
        double currentCV = this.getCV();
        Edge edge = this.edges[this.random.nextInt(this.edges.length)];

        Node source = this.graph.getEndpoints(edge).getFirst();
        Node destination = this.graph.getEndpoints(edge).getSecond();
        // we don't want to remove an individual's last remaining edge
        if (this.graph.getNeighborCount(destination) == 1) return;

        // pick a new destination that is not already connected to the source
        Node newDestination;
        do{
            newDestination = this.nodes[this.random.nextInt(this.nodes.length)];
        }
        while (this.graph.getNeighbors(source).contains(newDestination));

        if (this.graph.getNeighbors(source).contains(newDestination)) return;
        if (source.getID() == newDestination.getID()) return;

        //remove edge HERE (after we've confirmed that we're not about to make a self-edge or double-edge
        this.graph.removeEdge(edge);

        //draw the new edge
        Edge newEdge = new Edge();
        this.graph.addEdge(newEdge, source, newDestination);

        // decreases in CV are reverse
        if (this.getCV() < currentCV) {
            this.graph.removeEdge(newEdge);
            this.graph.addEdge(new Edge(), source, destination);
        }
        //regardless, we re-make the this.edges array to account for the changes and move to the next
        this.remakeEdgeList();
    }
}
