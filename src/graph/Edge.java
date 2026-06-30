package graph;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Edge implements Comparable<Edge> {
    private Airfield v;
    private Airfield w;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private long weight;

    public Edge(Airfield v, Airfield w, LocalDateTime departure, LocalDateTime arrival) {
        this.v = v;
        this.w = w;
        this.departure = departure;
        this.arrival = arrival;
        this.weight = ChronoUnit.MINUTES.between(departure, arrival);
    }

    public Airfield getV() {
        return v;
    }

    public Airfield getW() {
        return w;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public long getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Long.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return v.getIcao() + "-" + w.getIcao() + " (" + weight + ")";
    }
}
