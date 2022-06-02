/* Dijkstra's Algorithm for Shortest Path
 */

import java.util.*;
import java.io.*;

public class Graph {
    public Node start = null;
    public Set<Node> nodes = new HashSet<>();
    public Set<Arc> arcs = new HashSet<>();
    public int[] shortestPath;

    // CHANGE "filename" TO SELECT THE TEST GRAPH
    public static String filename = "testGraph1"; // "testGraph2";
    public static String SRC = "/Users/ketanmandava/Documents/independent-study-ketanm-code/dijkstra-and-bellman-ford/src/"
            + filename + ".txt";

    /******** DIJKSTRA'S ALGORITHM ********/
    /*
     * Returns: integer, the shortest distance from source node to terminal node
     */
    public int dijkstra() {
        // 1) create unmarked set and fill with all nodes (all initially unmarked)
        Set<Node> unmarked = new HashSet<>();
        for (Node node : nodes) {
            unmarked.add(node);
        }

        // 2) set distance for start node to 0, distance to itself is 0
        start.dist = 0;
        start.parent = new Node(-1, false, false); // NOT NECESSARY, but helpful for testing so nullpointer wasn't hit
                                                   // on accident

        // 3) while there are unmarked nodes, do the following...
        while (!unmarked.isEmpty()) {

            // 4) find unmarked node with smallest predicted distance (initially will be our
            // starting node)
            Node cur = null;
            int min = Integer.MAX_VALUE;
            for (Node node : unmarked) {
                if (node.dist < min) {
                    min = node.dist;
                    cur = node;
                }
            }

            // 5) iterate through all arcs to find nodes neighboring our selected node and
            // test shortest distance
            for (Arc arc : arcs) {
                if (arc.n1.equals(cur) && unmarked.contains(arc.n2)) {
                    Node n2 = arc.n2;
                    int c = arc.cost;
                    if ((n2.dist > cur.dist + c) && cur.dist != Integer.MAX_VALUE) {
                        n2.dist = cur.dist + c;
                        n2.parent = cur;
                    }
                }
            }

            // 6) mark cur
            unmarked.remove(cur);
            cur.marked = true;
        }

        // 7) pull shortest distance from the terminal node
        for (Node node : nodes) {
            if (node.terminal) {
                return node.dist;
            }
        }
        return 0;
    }

    /******** BELLMAN-FORD ALGORITHM ********/
    /*
     * Returns: integer, the shortest distance from source node to terminal node
     */
    public int bellmanFord() {
        // 1) set distance for start node to 0, distance to itself is 0
        this.start.dist = 0;
        this.start.parent = new Node(Integer.MAX_VALUE, false, false); // NOT NECESSARY, but helpful for testing so
                                                                       // nullpointer wasn't hit on accident

        // 2) Relax each index in our node set by updating shortest distance to each
        // node |nodes| - 1 times
        for (int i = 1; i < nodes.size() - 1; i++) {
            for (Arc arc : arcs) {
                Node n1 = arc.n1;
                Node n2 = arc.n2;
                int c = arc.cost;
                if ((n2.dist > n1.dist + c) && n1.dist != Integer.MAX_VALUE) {
                    n2.dist = n1.dist + c;
                    n2.parent = n1;
                }
            }
        }

        // 3) Repeat to detect any negative cycles, return 0 if detected (step 2 should
        // be optimal, so if there is another shorter path, there must be a negative
        // cycle)
        for (Arc arc : arcs) {
            Node n1 = arc.n1;
            Node n2 = arc.n2;
            int c = arc.cost;
            if ((n2.dist > n1.dist + c) && n1.dist != Integer.MAX_VALUE) {
                return 0;
            }
        }
        // 4) pull shortest distance from the terminal node
        for (Node node : nodes) {
            if (node.terminal) {
                return node.dist;
            }
        }
        return 0;
    }

    /******** INITIALIZING GRAPH ********/
    /*
     * Returns: null
     */
    public void initGraph() throws FileNotFoundException {
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
                Node start = new Node(first, true, false);
                nodes.add(start);
                this.start = start;
                int second = Integer.parseInt(split[1]);
                nodesNames.add(second);
                Node terminal = new Node(second, false, true);
                nodes.add(terminal);
                iter++;
            } else {
                char arcName = split[0].charAt(0);
                int first = Integer.parseInt(split[1]);
                int second = Integer.parseInt(split[2]);
                int cost = Integer.parseInt(split[3]);
                Node n1 = null;
                Node n2 = null;
                if (!nodesNames.contains(first)) {
                    n1 = new Node(first, false, false);
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
                    n2 = new Node(second, false, false);
                    nodesNames.add(second);
                    nodes.add(n2);
                } else {
                    for (Node cur : nodes) {
                        if (cur.name == second) {
                            n2 = cur;
                        }
                    }
                }
                Arc arc = new Arc(arcName, n1, n2, cost);
                arcs.add(arc);
            }
        }
    }

    /******** GETTING SHORTEST PATH ********/
    /*
     * Returns: integer array with shortest path, beginning with source node and
     * ending with terminal node
     */
    public int[] getShortestPath() {
        List<Node> backpath = new ArrayList<Node>();
        Node term = null;
        for (Node node : nodes) {
            if (node.terminal) {
                term = node;
            }
        }

        backpath.add(term);
        while (!term.start) {
            term = term.parent;
            backpath.add(term);
        }

        int[] path = new int[backpath.size()];
        Iterator<Node> iter = backpath.iterator();
        int i = 0;
        while (iter.hasNext()) {
            path[backpath.size() - 1 - i] = iter.next().name;
            i++;
        }
        return path;
    }

    public static void main(String[] args) throws Exception {
        Graph g = new Graph();
        g.initGraph();
        System.out.println("Shortest distance with Dijkstra: " + g.dijkstra());
        System.out.println("Path with Dijkstra: " + Arrays.toString(g.getShortestPath()));

        g.initGraph();
        System.out.println("Shortest distance with Bellman-Ford: " + g.bellmanFord());
        System.out.println("Path with Bellman-Ford: " + Arrays.toString(g.getShortestPath()));
    }
}