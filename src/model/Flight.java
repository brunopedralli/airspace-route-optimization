package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Flight {

    private String serviceType;
    private LocalDateTime scheduledArrival;
    private LocalDateTime scheduledDeparture;
    private LocalDate reference;
    private int stepNumber;
    private int flightNumber;
    private String expectedSeats;
    private Company company;
    private Aircraft aircraft;
    private Airfield destinationAirfield;
    private Airfield originAirfield;
    private String codeshare;

    public Flight(String serviceType, LocalDateTime scheduledArrival, LocalDateTime scheduledDeparture,
            LocalDate reference, int stepNumber, int flightNumber, String expectedSeats,
            Company company, Aircraft aircraft, Airfield destinationAirfield,
            Airfield originAirfield, String codeshare) {
        this.serviceType = serviceType;
        this.scheduledArrival = scheduledArrival;
        this.scheduledDeparture = scheduledDeparture;
        this.reference = reference;
        this.stepNumber = stepNumber;
        this.flightNumber = flightNumber;
        this.expectedSeats = expectedSeats;
        this.company = company;
        this.aircraft = aircraft;
        this.destinationAirfield = destinationAirfield;
        this.originAirfield = originAirfield;
        this.codeshare = codeshare;
    }

    public String getServiceType() {
        return serviceType;
    }

    public LocalDateTime getScheduledArrival() {
        return scheduledArrival;
    }

    public LocalDateTime getScheduledDeparture() {
        return scheduledDeparture;
    }

    public LocalDate getReference() {
        return reference;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public String getExpectedSeats() {
        return expectedSeats;
    }

    public Company getcompany() {
        return company;
    }

    public Aircraft getaircraft() {
        return aircraft;
    }

    public Airfield getdestinationAirfield() {
        return destinationAirfield;
    }

    public Airfield getoriginAirfield() {
        return originAirfield;
    }

    public String getCodeshare() {
        return codeshare;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s %s -> %s %s | %s\n%d - %d\n%s - %s - %s\n%s",
                this.serviceType, this.originAirfield.getIcao(), this.scheduledDeparture.toString(),
                this.destinationAirfield.getIcao(),
                this.scheduledArrival.toString(), this.reference.toString(), this.stepNumber, this.flightNumber,
                this.expectedSeats, this.company.getIcao(), this.aircraft.getIcao(), this.codeshare);
    }
}
