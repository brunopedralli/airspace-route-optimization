package model;

public class Company {
    private final String icao;
    private final String iata;
    private final String name;
    private final String country;

    public Company(String icao, String iata, String name, String country) {
        this.icao = icao;
        this.iata = iata;
        this.name = name;
        this.country = country;
    }

    public String getIcao() { return icao; }
    public String getIata() { return iata; }
    public String getName() { return name; }
    public String getCountry() { return country; }
}
