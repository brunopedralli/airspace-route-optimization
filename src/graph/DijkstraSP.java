package graph;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import model.Airfield;

public class DijkstraSP {

    private static final int INTERVAL_TIME = 45;
    private static final int MAIN_HUBS_INTERVAL_TIME = 60;

    private Map<Airfield, Edge> edgeTo;
    private Map<Airfield, LocalDateTime> distTo;
    private IndexMinHeap<Airfield, LocalDateTime> pq;
    private Set<Airfield> hubs;
    private LocalDate travelDate;

    public DijkstraSP(TemporalWeightedDigraph g, Airfield origin, Airfield destination,
            LocalDateTime startTime, Set<Airfield> hubs) {
        this.edgeTo = new HashMap<>();
        this.distTo = new HashMap<>();
        this.pq = new IndexMinHeap<>();
        this.hubs = hubs;
        this.travelDate = startTime.toLocalDate();

        distTo.put(origin, startTime);
        pq.insert(origin, startTime);

        while (!pq.isEmpty()) {
            Airfield v = pq.delMin();
            if (v.equals(destination))
                break;
            for (Edge e : g.getAdj(v))
                relax(e);
        }
    }

    public boolean hasPathTo(Airfield v) {
        return distTo.containsKey(v);
    }

    public LocalDateTime distTo(Airfield v) {
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

        LocalDateTime arrivalAtV = distTo.get(v);
        if (arrivalAtV == null)
            return;

        if (!e.getDeparture().toLocalDate().equals(travelDate))
            return;

        int tmin = edgeTo.containsKey(v) ? (hubs.contains(v) ? MAIN_HUBS_INTERVAL_TIME : INTERVAL_TIME) : 0;
        LocalDateTime departure = arrivalAtV.plus(tmin, ChronoUnit.MINUTES);

        if (e.getDeparture().isBefore(departure))
            return;

        LocalDateTime arrivalAtW = e.getArrival();
        if (!distTo.containsKey(w) || arrivalAtW.isBefore(distTo.get(w))) {
            distTo.put(w, arrivalAtW);
            edgeTo.put(w, e);
            if (!pq.contains(w))
                pq.insert(w, arrivalAtW);
            else
                pq.decreaseValue(w, arrivalAtW);
        }
    }
}
