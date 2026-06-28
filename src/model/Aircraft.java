package model;

public class Aircraft {

    private String icao;
    private String iata;
    private String model;
    private String critical;

    public Aircraft(String icao, String iata, String model, String critical) {
        this.icao = icao;
        this.iata = iata;
        this.model = model;
        this.critical = critical;
    }

    public String getIcao() {
        return this.icao;
    }

    public String getIata() {
        return this.iata;
    }

    public String getModel() {
        return this.model;
    }

    public String getCritical() {
        return this.critical;
    }

    @Override
    public String toString() {
        return String.format("%s - %s - %s - %s",
                this.icao, this.iata, this.model, this.critical);
    }
}
