package graph;

public class Edge implements Comparable<Edge> {
    private Airfield v;
    private Airfield w;
    private double weight;

    public Edge(Airfield v, Airfield w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public Airfield getV() {
        return v;
    }

    public Airfield getW() {
        return w;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return v.getIcao() + "-" + w.getIcao() + " (" + weight + ")";
    }
}
