/* Dijkstra's Algorithm for Shortest Path
 */

import java.util.*;
import java.io.*;

public class Graph {
    public Node start = null;
    public Node terminal = null;
    public Set<Node> nodes = new HashSet<>();
    public Set<Arc> arcs = new HashSet<>();

    // CHANGE "filename" TO SELECT THE TEST GRAPH
    public static String filename = "testGraph1"; // "testGraph2";
    public static String SRC = "src/" + filename + ".txt";

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
                char arcName = split[0].charAt(0);
                int first = Integer.parseInt(split[1]);
                int second = Integer.parseInt(split[2]);
                int cost = Integer.parseInt(split[3]);
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
                Arc arc = new Arc(arcName, n1, n2, cost);
                arcs.add(arc);
            }
        }
    }

    /******** DIJKSTRA'S ALGORITHM ********/
    /*
     * Returns: integer, the shortest distance from source node to terminal node
     */
    public int dijkstra() {
        // 1) create unmarked set and fill with all nodes (all initially unmarked)
        Set<Node> unmarked = new HashSet<>();
        for (Node node : nodes) {
            unmarked.add(node);
            node.dist = Integer.MAX_VALUE;
        }

        // 2) set distance for start node to 0, distance to itself is 0
        start.dist = 0;

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
                    System.out.println("testing " + arc.n2.name);
                    if ((arc.n2.dist > cur.dist + arc.cost) && cur.dist != Integer.MAX_VALUE) {
                        arc.n2.dist = cur.dist + arc.cost;
                        arc.n2.parent = cur;
                    }
                }
            }

            // 6) mark cur
            unmarked.remove(cur);
            System.out.println("marked " + cur.name);
            cur.marked = true;
        }

        // 7) pull shortest distance from the terminal node
        for (Node node : nodes) {
            if (node.equals(terminal)) {
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
        start.dist = 0;
        

        // 2) Relax each index in our node set by updating shortest distance to each
        // node |nodes| - 1 times
        for (int i = 1; i < nodes.size(); i++) {
            for (Arc arc : arcs) {
                if ((arc.n2.dist > arc.n1.dist + arc.cost) && (arc.n1.dist != Integer.MAX_VALUE)) {
                    arc.n2.dist = arc.n1.dist + arc.cost;
                    arc.n2.parent = arc.n1;
                }
            }
        }

        // 3) Repeat to detect any negative cycles, return 0 if detected (step 2 should
        // be optimal, so if there is another shorter path, there must be a negative
        // cycle)
        for (Arc arc : arcs) {
            if ((arc.n2.dist > arc.n1.dist + arc.cost) && (arc.n1.dist != Integer.MAX_VALUE)) {
                return 0;
            }
        }
        // 4) pull shortest distance from the terminal node
        for (Node node : nodes) {
            if (node.name == terminal.name) {
                return node.dist;
            }
        }
        return 0;
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
            if (node.equals(this.terminal)) {
                term = node;
            }
        }
        backpath.add(term);
        while (!term.equals(start)) {
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
        System.out.println("Shortest Path with Dijkstra: " + Arrays.toString(g.getShortestPath()));
        Graph g2 = new Graph();
        g2.initGraph();
        System.out.println("Shortest distance with Bellman-Ford: " + g2.bellmanFord());
        System.out.println("Shortest Path with Bellman-Ford: " + Arrays.toString(g2.getShortestPath()));
    }
}