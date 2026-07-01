package io;

import java.util.HashMap;
import java.util.Map;

import model.Airfield;
import model.Aircraft;
import model.Company;

public class DataLoader {

    public Map<String, Company> loadCompanies(String path) {
        Map<String, Company> map = new HashMap<>();
        In in = new In(path);
        in.readLine();

        String line;
        while ((line = in.readLine()) != null) {
            String[] f = line.split(";");
            if (f.length < 4) continue;
            Company company = new Company(f[0].trim(), f[1].trim(), f[2].trim(), f[3].trim());
            map.put(company.getIcao(), company);
        }

        in.close();
        return map;
    }

    public Map<String, Aircraft> loadAircrafts(String path) {
        Map<String, Aircraft> map = new HashMap<>();
        In in = new In(path);
        in.readLine();

        String line;
        while ((line = in.readLine()) != null) {
            String[] f = line.split(";");
            if (f.length < 4) continue;
            Aircraft aircraft = new Aircraft(f[0].trim(), f[1].trim(), f[2].trim(), f[3].trim());
            map.put(aircraft.getIcao(), aircraft);
        }

        in.close();
        return map;
    }

    public Map<String, Airfield> loadAirfields(String path) {
        Map<String, Airfield> map = new HashMap<>();
        In in = new In(path);
        String line = in.readLine();

        while ((line = in.readLine()) != null) {
            String[] f = line.split(";");
            double lat = Double.parseDouble(f[7].trim().replace(',', '.'));
            double lon = Double.parseDouble(f[8].trim().replace(',', '.'));
            Airfield af = new Airfield(
                    f[0].trim(), f[1].trim(), f[2].trim(),
                    f[3].trim(), f[4].trim(), f[5].trim(),
                    f[6].trim(), lat, lon);
            map.put(af.getIcao(), af);
        }

        in.close();
        return map;
    }
}
