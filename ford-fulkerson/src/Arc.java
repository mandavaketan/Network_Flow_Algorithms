
public class Arc {
    public String name;

    public Node n1;

    public Node n2;

    public int u;

    public int x;
    
    public Arc(String name, Node n1, Node n2, int u) {
        this.name = name;
        this.n1 = n1;
        this.n2 = n2;
        this.u = u;
        this.x = 0;
    }
}