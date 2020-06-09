public class Node {

    private final int id;
    private double weight;
    private boolean visited;

    public Node(int id, double weight) {
        this.id = id;
        this.weight = weight;
        this.visited = false;
    }

    public int getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
