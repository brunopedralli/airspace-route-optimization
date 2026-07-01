package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import graph.DijkstraSP;
import graph.Edge;
import graph.TemporalWeightedDigraph;
import model.Aircraft;
import model.Airfield;
import model.Company;

public class App {
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        TemporalWeightedDigraph graph = new TemporalWeightedDigraph("aerial_network_data/flights_mar2026.csv");
        List<Airfield> hubs = graph.getMainHubs(5);

        System.out.println("Welcome to the Brazilian Airspace Routes System!");
        System.out.println("Developed by: Bruno Pedralli");
        System.out.println("\n=============\n");

        System.out.println("5 main airspace hubs:");
        for (int i = 0; i < hubs.size(); i++)
            System.out.printf("%d - %s (%s)\n", i + 1, hubs.get(i).getIcao(), hubs.get(i).getName());

        System.out.println();
        System.out.println("If you want to eliminate one of the hubs above from the graph, type 1");
        System.out.println("If not, type 0");

        int choice;
        do {
            choice = in.nextInt();
            if (choice != 0 && choice != 1)
                System.out.print("Invalid choice. Please, select again: ");
        } while (choice != 0 && choice != 1);

        if (choice == 1) {
            System.out.print("Wonderful! Now, type the number of the one you want to remove (based on the list above): ");
            int airfield;
            do {
                airfield = in.nextInt();
                if (airfield > hubs.size() || airfield < 1)
                    System.out.print("Invalid choice. Please, select again: ");
            } while (airfield > hubs.size() || airfield < 1);

            Airfield removed = hubs.get(airfield - 1);
            graph.removeVertice(removed);
            hubs = graph.getMainHubs(5);

            System.out.printf("\nHub removed: %s\n", removed.getIcao());
            System.out.println("Main hubs remaining:");
            for (int i = 0; i < hubs.size(); i++)
                System.out.printf("%d - %s (%s)\n", i + 1, hubs.get(i).getIcao(), hubs.get(i).getName());
        }

        System.out.println("\n=============\n");
        System.out.println("Route search");

        in.nextLine();

        System.out.print("\nType the ICAO code of the airfield you want to departure from: ");
        String originCode = in.nextLine().trim().toUpperCase();

        System.out.print("Type the ICAO code of the airfield you want to arrive at: ");
        String destinationCode = in.nextLine().trim().toUpperCase();

        LocalDateTime startTime = null;
        do {
            System.out.print("Type the date you want to departure (dd/mm/yyyy): ");
            String date = in.nextLine().trim();

            System.out.print("And at what time (hh:mm): ");
            String time = in.nextLine().trim();

            try {
                startTime = LocalDateTime.parse(date + " " + time, DATETIME_FMT);
            } catch (Exception e) {
                System.out.println("Invalid date. Please, try again.");
            }
        } while (startTime == null);

        Airfield origin = graph.findByIcao(originCode);
        Airfield destination = graph.findByIcao(destinationCode);

        if (origin == null) {
            System.out.println("Origin airfield not found: " + originCode);
            in.close();
            return;
        }
        if (destination == null) {
            System.out.println("Destination airfield not found: " + destinationCode);
            in.close();
            return;
        }
        if (origin.equals(destination)) {
            System.out.println("Origin and destination are the same.");
            in.close();
            return;
        }

        Set<Airfield> hubSet = new HashSet<>(hubs);
        DijkstraSP sp = new DijkstraSP(graph, origin, destination, startTime, hubSet);

        if (!sp.hasPathTo(destination)) {
            System.out.println("No route found from " + originCode + " to " + destinationCode + " on "
                    + startTime.toLocalDate());
            in.close();
            return;
        }

        System.out.println("\nRoute found:");
        List<Edge> path = (List<Edge>) sp.pathTo(destination);
        long totalMinutes = 0;
        LocalDateTime prev = startTime;
        int count = 1;

        for (Edge e : path) {
            long wait = ChronoUnit.MINUTES.between(prev, e.getDeparture());
            long flight = e.getWeight();

            Company company = graph.getCompanies().get(e.getCompanyIcao());
            Aircraft aircraft = graph.getAircrafts().get(e.getAircraftIcao());

            String companyName = company.getName();
            String aircraftModel = aircraft.getModel();

            System.out.printf("%d - %s -> %s | %s | flight %s%s | %s | dep: %s | arr: %s | flight: %d min | wait: %d min\n",
                    count,
                    e.getV().getIcao(), e.getW().getIcao(),
                    companyName, e.getCompanyIcao(),
                    e.getFlightNumber(), aircraftModel,
                    e.getDeparture().format(DATETIME_FMT),
                    e.getArrival().format(DATETIME_FMT),
                    flight, wait);

            totalMinutes += wait + flight;
            prev = e.getArrival();
            count++;
        }

        System.out.printf("\nTotal trip duration: %d min (%dh %02dm)\n",
                totalMinutes, totalMinutes / 60, totalMinutes % 60);

        System.out.println("\n=============\n");

        Path outputFile = Path.of("graph.txt");
        try {
            Files.writeString(outputFile, graph.toDot(new HashSet<>(path)));
        } catch (IOException e) {
            System.out.println("Failed to write DOT graph");
        }

        in.close();
    }
}
