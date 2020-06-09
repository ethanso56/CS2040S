public class Pair {

    private int x;
    private int y;
    private Pair parent;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void plusX() {
        x++;
    }

    public void plusY() {
        y++;
    }

    public Pair getParent() {
        return parent;
    }

    public void setParent(Pair parent) {
        this.parent = parent;
    }
}
