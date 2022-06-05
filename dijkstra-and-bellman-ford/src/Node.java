public class Node {
	public int name;

	public Node parent;

	public int dist;

	public boolean marked;

    public Node(int name) {
        this.name = name;
        this.marked = false;
        this.parent = null;
		this.dist = Integer.MAX_VALUE;
    }
}