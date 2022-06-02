
public class Arc {
    public char name;

    public Node n1;

    public Node n2;

    public int cost;
    
    public Arc(char name, Node n1, Node n2, int c) {
        this.name = name;
        this.n1 = n1;
        this.n2 = n2;
        this.cost = c;
    }
}