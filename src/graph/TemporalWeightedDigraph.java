package graph;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.DataLoader;
import io.In;
import model.Airfield;
import model.Aircraft;
import model.Company;

public class TemporalWeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Map<Airfield, List<Edge>> graph;
    private Set<Airfield> vertices;
    private int totalVertices;
    private int totalEdges;

    private Map<String, Company> companies;
    private Map<String, Aircraft> aircrafts;

    public TemporalWeightedDigraph() {
        this.graph = new HashMap<>();
        this.vertices = new HashSet<>();
        this.companies = new HashMap<>();
        this.aircrafts = new HashMap<>();
        this.totalVertices = totalEdges = 0;
    }

    public TemporalWeightedDigraph(String filename) {
        this();
        DataLoader loader = new DataLoader();
        Map<String, Airfield> airfields = loader.loadAirfields("aerial_network_data/airfields.csv");
        this.companies = loader.loadCompanies("aerial_network_data/companies.csv");
        this.aircrafts = loader.loadAircrafts("aerial_network_data/aircrafts.csv");

        In in = new In(filename);
        String line = in.readLine();

        while ((line = in.readLine()) != null) {
            String[] edge = line.trim().replace("\"", "").split(",");

            LocalDateTime scheduledArrival = LocalDateTime.parse(edge[1], DATETIME_FMT);
            LocalDateTime scheduledDeparture = LocalDateTime.parse(edge[2], DATETIME_FMT);

            String flightNumber = edge[5].trim();
            String companyIcao = edge[7].trim();
            String aircraftIcao = edge[8].trim();

            Airfield destinationAirfield = airfields.get(edge[9]);
            Airfield originAirfield = airfields.get(edge[10]);

            addEdge(originAirfield, destinationAirfield, scheduledDeparture, scheduledArrival,
                    companyIcao, flightNumber, aircraftIcao);
        }

        in.close();
    }

    public void addEdge(Airfield v, Airfield w, LocalDateTime departure, LocalDateTime arrival,
                        String companyIcao, String flightNumber, String aircraftIcao) {
        Edge e = new Edge(v, w, departure, arrival, companyIcao, flightNumber, aircraftIcao);
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

    public Airfield removeVertice(Airfield v) {
        int edges = 0;
        List<Edge> removed = graph.remove(v);
        if (removed != null)
            edges += removed.size();

        for (List<Edge> list : graph.values()) {
            int before = list.size();
            list.removeIf(e -> e.getW().equals(v));
            edges += before - list.size();
        }

        vertices.remove(v);
        totalVertices--;
        totalEdges -= edges;
        return v;
    }

    public List<Airfield> getMainHubs(int count) {
        Map<Airfield, Integer> degree = new HashMap<>();

        for (List<Edge> list : graph.values()) {
            for (Edge e : list) {
                degree.put(e.getV(), degree.getOrDefault(e.getV(), 0) + 1);
                degree.put(e.getW(), degree.getOrDefault(e.getW(), 0) + 1);
            }
        }

        return degree.keySet().stream()
                .sorted((a, b) -> degree.get(b) - degree.get(a))
                .limit(count)
                .toList();
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

    public Airfield findByIcao(String icao) {
        for (Airfield v : vertices) {
            if (v.getIcao().equalsIgnoreCase(icao))
                return v;
        }
        return null;
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

    public Map<String, Company> getCompanies() {
        return companies;
    }

    public Map<String, Aircraft> getAircrafts() {
        return aircrafts;
    }

    public String toDot(Set<Edge> highlighted) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {" + NEWLINE);
        sb.append("rankdir = LR;" + NEWLINE);
        sb.append("node [shape = circle];" + NEWLINE);
        for (Edge e : getEdges()) {
            String color = highlighted.contains(e) ? " color=red" : "";
            sb.append(String.format("\"%s\" -> \"%s\" [label=\"%d\"%s]",
                    e.getV().getIcao(), e.getW().getIcao(), e.getWeight(), color)
                    + NEWLINE);
        }
        sb.append("}" + NEWLINE);
        return sb.toString();
    }
}
