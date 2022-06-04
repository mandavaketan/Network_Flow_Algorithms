public class Node {
	public int name;
	public int supply;
	

	//for bellman-ford
	public int dist;
	public boolean marked;
	public Node parent; //also for ford fulkerson
	public boolean inNegCycle;
	
	//for ford-fulkerson
	public int minResCap; //also for bellman-ford
	public Boolean viewed;

	

    public Node(int name, int supply) {
        this.name = name;
		this.parent = null;
		viewed = false;
		minResCap = Integer.MAX_VALUE;
		dist = Integer.MAX_VALUE;
		this.supply = supply;
		marked = false;
		inNegCycle = false;
    }
}