public class Node {
	public int name;

	public Node parent;

	public int dist;

	public boolean marked;

	public boolean start;

	public boolean terminal;

    public Node(int name, boolean start, boolean terminal) {
        this.name = name;
        this.marked = false;
        this.parent = null;
		this.dist = Integer.MAX_VALUE;
		this.start = start;
		this.terminal = terminal;
    }
}