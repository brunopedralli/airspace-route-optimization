package graph;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import model.Airfield;

public class Edge implements Comparable<Edge> {
    private Airfield v;
    private Airfield w;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private long weight;
    private String companyIcao;
    private String flightNumber;
    private String aircraftIcao;

    public Edge(Airfield v, Airfield w, LocalDateTime departure, LocalDateTime arrival,
                String companyIcao, String flightNumber, String aircraftIcao) {
        this.v = v;
        this.w = w;
        this.departure = departure;
        this.arrival = arrival;
        this.weight = ChronoUnit.MINUTES.between(departure, arrival);
        this.companyIcao = companyIcao;
        this.flightNumber = flightNumber;
        this.aircraftIcao = aircraftIcao;
    }

    public Airfield getV() { return v; }
    public Airfield getW() { return w; }
    public LocalDateTime getDeparture() { return departure; }
    public LocalDateTime getArrival() { return arrival; }
    public long getWeight() { return weight; }
    public String getCompanyIcao() { return companyIcao; }
    public String getFlightNumber() { return flightNumber; }
    public String getAircraftIcao() { return aircraftIcao; }

    @Override
    public int compareTo(Edge other) {
        return Long.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return v.getIcao() + "-" + w.getIcao() + " (" + weight + ")";
    }
}
