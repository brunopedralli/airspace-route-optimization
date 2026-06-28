package graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DijkstraSP {

    private static final int INTERVAL_TIME = 45;
    private static final int MAIN_HUBS_INTERVAL_TIME = 60;

    private Map<Airfield, Edge> edgeTo;
    private Map<Airfield, Double> distTo;
    private IndexMinHeap<Airfield, Double> pq;

    public DijkstraSP(TemporalWeightedDigraph g, Airfield s) {
        edgeTo = new HashMap<>();
        distTo = new HashMap<>();
        pq = new IndexMinHeap<>();

        for (Airfield v : g.getVerts())
            distTo.put(v, Double.POSITIVE_INFINITY);
        distTo.put(s, 0.0);

        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            Airfield v = pq.delMin();
            for (Edge e : g.getAdj(v))
                relax(e);
        }
    }

    public boolean hasPathTo(Airfield v) {
        return distTo.get(v) != Double.POSITIVE_INFINITY;
    }

    public double distTo(Airfield v) {
        if (!hasPathTo(v))
            throw new UnsupportedOperationException("No path to " + v.getIcao() + "!");
        return distTo.get(v);
    }

    public Iterable<Edge> pathTo(Airfield v) {
        LinkedList<Edge> path = new LinkedList<>();
        if (!hasPathTo(v))
            return path;
        Edge e = edgeTo.get(v);
        while (e != null) {
            path.addFirst(e);
            e = edgeTo.get(e.getV());
        }
        return path;
    }

    private void relax(Edge e) {
        Airfield v = e.getV();
        Airfield w = e.getW();
        double edgeWeight = distTo.get(v) + e.getWeight();
        if (distTo.get(w) > edgeWeight) {
            distTo.put(w, edgeWeight);
            edgeTo.put(w, e);
            if (!pq.contains(w))
                pq.insert(w, edgeWeight);
            else
                pq.decreaseValue(w, edgeWeight);
        }
    }
}
