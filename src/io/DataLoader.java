package io;

import java.util.HashMap;
import java.util.Map;

import graph.Airfield;

public class DataLoader {

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
