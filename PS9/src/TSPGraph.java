import java.util.*;

public class TSPGraph implements IApproximateTSP {

    TSPMap map = null;
    int[] parent;

    // Empty constructor
    TSPGraph() {
    }

    @Override
    public void MST() {
        // TODO: implement this method
        // Prim
        // problem - no edge list, point class got no weight

        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());
        Node start = new Node(0, 0);
        pq.add(start);

        for (int i = 1; i < map.getCount(); i++) {
            Node newNode = new Node(i, Double.POSITIVE_INFINITY);
            pq.add(newNode);
        }

        // must need container to add things back into pq so that it will update
        PriorityQueue<Node> container = new PriorityQueue<>(new NodeComparator());

        while (!pq.isEmpty()) {
            Node currNode = pq.poll();

            for (Node node: pq) {
                double distance = map.getPoint(currNode.getId()).distance(map.getPoint(node.getId()));
                if (distance < node.getWeight()) {
                    node.setWeight(distance);
                    parent[node.getId()] = currNode.getId();
                }
                container.add(node);
            }
            pq.clear();
            int containerSize = container.size();
            for (int i = 0; i < containerSize; i++) {
                pq.add(container.poll());
            }
        }

        for (int i = 1; i < map.getCount(); i++) {
            map.setLink(i, parent[i], false);
        }
        map.setLink(0, 0, false);
        map.redraw();
    }

    LinkedList<Integer> list = new LinkedList<>();

    public void dfs(int[] arr, int index) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == index) {
                list.add(i);
                dfs(arr, i);
            }
        }
    }


    @Override
    public void TSP() {
        // TODO: implement this method
        // call MSP() to generate parent arr
        this.MST();

        int initial = 0;
        // add initial index to linkedlist
        list.add(initial);

        for (int i = 1; i < this.map.getCount(); i++) {
            // if we get the initial value from the parent arr, add this index and recurse the dfs visit
            if (parent[i] == initial) {
                list.add(i);
                dfs(parent, i);
            }
        }
        // set the links in the linked list to form the tour
        for (int i = 0; i < list.size() - 1; i++) {
            map.setLink(list.get(i), list.get(i + 1), false);
        }

        if (map.getCount() > 0) {
            map.setLink(list.get(list.size() - 1), 0, false);
        }
        map.redraw();


    }

    @Override
    public void initialize(TSPMap map) {
        // TODO: implement this method
        this.map = map;
        this.parent = new int[map.getCount()];

    }

    public boolean directSearch(int point, int count) {
        if (count == map.getCount()) {
            return true;
        }
        int nextPoint = map.getPoint(point).getLink();
        if (nextPoint == -1) {
            return false;
        } else {
            return directSearch(nextPoint, count + 1);
        }
    }

    @Override
    public boolean isValidTour() {
       
        if (map != null) {
            if (map.getCount() == 1) {
                return true;
            }
            return directSearch(0, 1);
        } else {
            return false;
        }

    }
    int startPoint = 0;

    public double tourDistanceHelper(int point, double distance) {
        int nextPoint = map.getPoint(point).getLink();
        double newD = map.pointDistance(point, nextPoint);
        if (nextPoint == startPoint) {
            return distance + newD;
        } else {
            return tourDistanceHelper(nextPoint, distance + newD);
        }
    }

    @Override
    public double tourDistance() {
        // TODO: implement this method
        // returns the total length of the tour

        if (isValidTour()) {
            double distance = 0;
            return tourDistanceHelper(startPoint, distance);
        } else {
            return -1;
        }

    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "twentypoints.txt");
        TSPGraph graph = new TSPGraph();
        graph.initialize(map);

        //graph.MST();

        graph.TSP();
        //System.out.println(map.getM_points().toString());

        System.out.println(graph.isValidTour());
        System.out.println(graph.tourDistance());
    }
}
