public class Node {
	public int name;

	public Node parent;

	public int minResCap;

	public Boolean viewed;

    public Node(int name) {
        this.name = name;
		this.parent = null;
		viewed = false;
		minResCap = Integer.MAX_VALUE;
    }
}