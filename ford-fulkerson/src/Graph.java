
import java.util.*;

import java.io.*;

public class Graph {
    public Node start = null;
    public Node terminal = null;
    public Set<Node> nodes = new HashSet<>();
    public Set<Arc> arcs = new HashSet<>();

    // CHANGE "filename" TO SELECT THE TEST GRAPH
    public static String filename = "testGraph3"; // "testGraph2";
    public static String SRC = "src/" + filename + ".txt";

    /******** INITIALIZING ORIGINAL GRAPH ********/
    /*
     * Returns: null
     */
    public void initOriginalGraph() throws FileNotFoundException {
        File file = new File(SRC);
        Scanner sc = new Scanner(file);
        int iter = 0;
        Set<Integer> nodesNames = new HashSet<Integer>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] split = line.split(" ");

            if (iter == 0) {
                int first = Integer.parseInt(split[0]);
                nodesNames.add(first);
                Node start = new Node(first);
                nodes.add(start);
                this.start = start;
                int second = Integer.parseInt(split[1]);
                nodesNames.add(second);
                Node terminal = new Node(second);
                nodes.add(terminal);
                this.terminal = terminal;
                iter++;
            } else {
                String arcName = split[0];
                int first = Integer.parseInt(split[1]);
                int second = Integer.parseInt(split[2]);
                int u = Integer.parseInt(split[3]);
                Node n1 = null;
                Node n2 = null;
                if (!nodesNames.contains(first)) {
                    n1 = new Node(first);
                    nodesNames.add(first);
                    nodes.add(n1);
                } else {
                    for (Node cur : nodes) {
                        if (cur.name == first) {
                            n1 = cur;
                        }
                    }
                }
                if (!nodesNames.contains(second)) {
                    n2 = new Node(second);
                    nodesNames.add(second);
                    nodes.add(n2);
                } else {
                    for (Node cur : nodes) {
                        if (cur.name == second) {
                            n2 = cur;
                        }
                    }
                }
                Arc arc = new Arc(arcName, n1, n2, u);
                arcs.add(arc);
            }
        }
    }

    /******** INITIALIZING ORIGINAL GRAPH ********/
    /*
     * Returns: null
     */
    public void initResidualGraph(Graph g) {
        this.nodes = g.nodes;
        this.start = g.start;
        this.terminal = g.terminal;
        for (Arc arc : g.arcs) {
            this.arcs.add(new Arc(arc.name, arc.n1, arc.n2, arc.u));
            this.arcs.add(new Arc("r" + arc.name, arc.n2, arc.n1, 0));
        }
    }

    // public void updateResidualGraph(Graph g) {
    // for (Arc arc : g.arcs) {
    // for (Arc rarc : this.arcs) {
    // if (arc.n1.equals(rarc.n1) && arc.n2.equals(rarc.n2)) {
    // rarc.u = arc.u - arc.x;
    // } else if (arc.n1.equals(rarc.n2) && arc.n2.equals(rarc.n1)) {
    // rarc.u = arc.x;
    // }
    // }
    // }
    // }

    /******** helper func - findPathFromSourceToSink ********/
    /* Function: searches the residual graph to see if there is a 
     * path from source to sink with positive risidual capacity.
     * 
     * Returns: True or false if there is a path that exists
     * 
     * Updates: Parent params of nodes for backtracing and MinResCap
     * for updating capacity along arcs in residual graph
     */
    public boolean findPathFromSourceToSink() { // BFS
        int minResCap = Integer.MAX_VALUE; // stores
        for (Node node : nodes) {
            node.viewed = false;
            node.parent = null;
        }

        LinkedList<Node> queue = new LinkedList<Node>();
        start.viewed = true;
        start.parent = null;
        queue.add(start);

        while (queue.size() != 0) {
            Node cur = queue.poll();
            // System.out.println("searched: " + cur.name);
            for (Arc arc : arcs) {
                if (arc.n1.equals(cur) && !arc.n2.viewed && arc.u > 0) {
                    if (arc.u < minResCap) {
                        minResCap = arc.u;
                        System.out.println("updated minrescap to " + minResCap + " from arc " + arc.name);
                    }
                    arc.n2.parent = arc.n1;

                    if (arc.n2.equals(terminal)) {
                        System.out.println("final minrescap: " + minResCap);
                        arc.n2.minResCap = minResCap;
                        return true;
                    }

                    queue.add(arc.n2);
                    arc.n2.viewed = true;
                }
            }
        }
        return false;
    }

    public int fordFulkerson(Graph r) {
        while (r.findPathFromSourceToSink()) {
            // System.out.println(terminal.minResCap);
            for (Node cur = terminal; !cur.equals(start); cur = cur.parent) {
                System.out.print(cur.name + ", ");
                for (Arc rarc : r.arcs) {
                    if (rarc.n1.equals(cur) && rarc.n2.equals(cur.parent)) {
                        rarc.u += terminal.minResCap;
                        // System.out.println(rarc.name + ": " + rarc.u);
                    } else if (rarc.n2.equals(cur) && rarc.n1.equals(cur.parent)) {
                        rarc.u -= terminal.minResCap;
                        // System.out.println(rarc.name + ": " + rarc.u);

                    }
                }
            }
        }

        int sum = 0;
        for (Arc arc : this.arcs) {
            for (Arc rarc : r.arcs) {
                if (arc.n1.equals(rarc.n1) && arc.n2.equals(rarc.n2)) {
                    arc.x = arc.u - rarc.u;
                    if (arc.n2.equals(terminal))
                        sum += arc.x;
                }
            }
        }

        return sum;
    }

    public static void main(String[] args) throws Exception {
        Graph g = new Graph();
        g.initOriginalGraph();
        Graph r = new Graph();
        r.initResidualGraph(g);
        // for (Node node : r.nodes) {
        // System.out.println(node.name);
        // }
        System.out.println("Maximum Flow: " + g.fordFulkerson(r));
        for (Arc arc : g.arcs) {
            System.out.println(arc.name + ": " + arc.x);
        }
    }
}
