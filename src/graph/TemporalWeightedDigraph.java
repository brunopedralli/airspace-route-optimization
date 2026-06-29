package graph;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.DataLoader;
import io.In;

public class TemporalWeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private Map<Airfield, List<Edge>> graph;
    private Set<Airfield> vertices;
    private int totalVertices;
    private int totalEdges;

    private DataLoader loader;
    private Map<String, Airfield> airfields;

    public TemporalWeightedDigraph() {
        this.graph = new HashMap<>();
        this.vertices = new HashSet<>();
        this.totalVertices = totalEdges = 0;

        loader = new DataLoader();
        this.airfields = loader.loadAirfields("aerial_network_data/airfields.csv");
    }

    public TemporalWeightedDigraph(String filename) {
        this();
        In in = new In(filename);
        String line = in.readLine();

        while ((line = in.readLine()) != null) {
            String[] edge = line.trim().replace("\"", "").split(",");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime scheduledArrival = LocalDateTime.parse(edge[1], formatter);
            LocalDateTime scheduledDeparture = LocalDateTime.parse(edge[2], formatter);
            long minutesDiff = ChronoUnit.MINUTES.between(scheduledDeparture, scheduledArrival);

            Airfield destinationAirfield = airfields.get(edge[9]);
            Airfield originAirfield = airfields.get(edge[10]);

            addEdge(originAirfield, destinationAirfield, minutesDiff);
        }

        in.close();
    }

    public void addEdge(Airfield v, Airfield w, long weight) {
        Edge e = new Edge(v, w, weight);
        addToList(v, e);
        if (!vertices.contains(v)) {
            vertices.add(v);
            totalVertices++;
        }
        if (!vertices.contains(w)) {
            vertices.add(w);
            totalVertices++;
        }
        totalEdges++;
    }

    private List<Edge> addToList(Airfield v, Edge e) {
        List<Edge> list = graph.get(v);
        if (list == null)
            list = new LinkedList<>();
        list.add(e);
        graph.put(v, list);
        return list;
    }

    public Iterable<Edge> getEdges() {
        Set<Edge> ed = new HashSet<>();
        for (Airfield v : getVerts()) {
            for (Edge e : getAdj(v)) {
                if (!ed.contains(e))
                    ed.add(e);
            }
        }
        return ed;
    }

    public Iterable<Edge> getAdj(Airfield v) {
        List<Edge> res = graph.get(v);
        if (res == null)
            res = new LinkedList<>();
        return res;
    }

    public int getConnections(Airfield v) {
        List<Edge> edges = graph.get(v);
        if (edges == null) return 0;
        return edges.size();
    }

    public Set<Airfield> getVerts() {
        return this.vertices;
    }

    public int getTotalVerts() {
        return this.totalVertices;
    }

    public int getTotalEdges() {
        return this.totalEdges;
    }

    public String toDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {" + NEWLINE);
        sb.append("rankdir = LR;" + NEWLINE);
        sb.append("node [shape = circle];" + NEWLINE);
        for (Edge e : getEdges())
            sb.append(String.format("\"%s\" -> \"%s\" [label=\"%.3f\"]",
                    e.getV().getIcao(), e.getW().getIcao(), e.getWeight())
                    + NEWLINE);
        sb.append("}" + NEWLINE);
        return sb.toString();
    }
}
