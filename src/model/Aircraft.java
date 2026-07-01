package model;

public class Aircraft {
    private final String icao;
    private final String iata;
    private final String model;
    private final String criticalAircraft;

    public Aircraft(String icao, String iata, String model, String criticalAircraft) {
        this.icao = icao;
        this.iata = iata;
        this.model = model;
        this.criticalAircraft = criticalAircraft;
    }

    public String getIcao() { return icao; }
    public String getIata() { return iata; }
    public String getModel() { return model; }
    public String getCriticalAircraft() { return criticalAircraft; }
}
