# Airspace Route Optimization

Java command-line application for exploring scheduled air routes with time-aware shortest-path search. The project loads the included CSV datasets for airfields, companies, aircraft, and March 2026 flights, builds a temporal weighted directed graph, identifies the main hubs, and then finds a valid route between two airfields while respecting departure times and minimum connection times.

## What the project does

The application in [src/main/App.java](src/main/App.java) starts an interactive terminal session that:

- loads the local data in [aerial_network_data/](aerial_network_data/)
- lists the five most connected airfield hubs
- lets you remove one of those hubs before searching
- finds a route from an origin ICAO code to a destination ICAO code
- applies time constraints through the temporal shortest-path search in [src/graph/DijkstraSP.java](src/graph/DijkstraSP.java)
- writes route visualizations to `graph.txt` and `route.txt` in DOT format

The graph and parsing logic live in [src/graph/TemporalWeightedDigraph.java](src/graph/TemporalWeightedDigraph.java) and [src/io/DataLoader.java](src/io/DataLoader.java).

## Why the project is useful

This project is helpful when you need to reason about flight connections instead of just static distances. It takes into account:

- scheduled departure and arrival times
- flight duration as edge weight
- minimum connection windows at intermediate stops
- extra layover time at main hubs
- local airline and aircraft metadata for each leg

That makes it useful for demonstrations, route analysis, and experiments with temporal graph algorithms on real CSV data.

## How to get started

### Prerequisites

- Java 17 or newer is recommended. The code uses modern Java collection APIs such as `Stream.toList()`.
- Run the application from the repository root so the relative CSV paths resolve correctly.

### Build and run

Compile all sources into an output directory:

```bash
mkdir -p out
javac -d out $(find src -name '*.java')
```

Run the interactive CLI:

```bash
java -cp out main.App
```

### What the data files contain

- [aerial_network_data/airfields.csv](aerial_network_data/airfields.csv) stores airfield ICAO/IATA codes, names, location, and coordinates
- [aerial_network_data/companies.csv](aerial_network_data/companies.csv) stores airline/operator metadata
- [aerial_network_data/aircrafts.csv](aerial_network_data/aircrafts.csv) stores aircraft metadata
- [aerial_network_data/flights_mar2026.csv](aerial_network_data/flights_mar2026.csv) stores the scheduled flights used to build the graph

### Example workflow

1. Start the app.
2. Review the five most connected hubs printed on screen.
3. Optionally remove one hub from the graph.
4. Enter the origin ICAO, destination ICAO, and desired departure date/time.
5. Review the route legs and total travel time.
6. Open `graph.txt` or `route.txt` in a DOT viewer or Graphviz if you want a visual representation.

## Project Structure

```
airspace-route-optimization/
├── README.md
├── aerial_network_data/
│   ├── aircrafts.csv
│   ├── airfields.csv
│   ├── companies.csv
│   └── flights_mar2026.csv
└── src/
	├── graph/
	│   ├── DijkstraSP.java
	│   ├── Edge.java
	│   ├── IndexMinHeap.java
	│   └── TemporalWeightedDigraph.java
	├── io/
	│   ├── DataLoader.java
	│   └── In.java
	├── main/
	│   └── App.java
	└── model/
		├── Aircraft.java
		├── Airfield.java
		└── Company.java
```

There is no separate API reference or troubleshooting guide in this repository. For usage questions, inspect the source or open an issue in the repository.

## Maintainers And Contributions

Maintainer:

- [@brunopedralli](https://github.com/brunopedralli)
