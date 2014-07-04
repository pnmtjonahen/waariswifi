/*
 * Copyright (C) 2014 Philippe Tjon-A-Hen philippe@tjonahen.nl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.tjonahen.wificollector.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class EndpointDistanceMapping {

    private static final int EXPIRED_MINUTES = 5;
    private final Map<String, Distance> data = new HashMap<>();
    private final ArrayList<String> keys = new ArrayList<>();

    String getEndpoint(final int currentIndex) {
        int index = currentIndex % keys.size();
        return keys.get(index);
    }

    Distance getDistanceByMacadres(final String ep) {
        if (data.containsKey(ep)) {
            return data.get(ep);
        }
        return new Distance(EXPIRED_MINUTES, 0);
    }

    int maxNumberEndpoints() {
        return data.entrySet().size();
    }

    boolean containsKey(final String endpointmac) {
        return data.containsKey(endpointmac);
    }

    Distance get(final String endpointmac) {
        return data.get(endpointmac);
    }

    void put(final String endpointmac, final Distance distanceToP) {
        keys.add(endpointmac);
        data.put(endpointmac, distanceToP);
    }

}
