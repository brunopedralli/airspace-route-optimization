package model;

public class Company {

    private String icao;
    private String iata;
    private String name;
    private String country;

    public Company(String icao, String iata, String name, String country) {
        this.icao = icao;
        this.iata = iata;
        this.name = name;
        this.country = country;
    }

    public String getIcao() {
        return this.icao;
    }

    public String getIata() {
        return this.iata;
    }

    public String getName() {
        return this.name;
    }

    public String getCountry() {
        return this.country;
    }

    @Override
    public String toString() {
        return String.format("%s - %s - %s - %s",
                this.icao, this.iata, this.name, this.country);
    }
}
