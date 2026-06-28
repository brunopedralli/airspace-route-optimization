package graph;

public class Edge implements Comparable<Edge> {
    private Airfield v;
    private Airfield w;
    private double weight;
    private String color;

    public Edge(Airfield v, Airfield w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
        this.color = "";
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

    public String getColor() {
        return color;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return v.getIcao() + "-" + w.getIcao() + " (" + weight + ")" + color;
    }
}
