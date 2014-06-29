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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import nl.tjonahen.wificollector.endpointdevice.EndpointMapping;
import nl.tjonahen.wificollector.calculator.Calculator;
import nl.tjonahen.wificollector.calculator.Point;
import nl.tjonahen.wificollector.model.EndpointEntity;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

/**
 * A device mapped or triangulated.
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class Device {

    private static final int MAX_NUMBER_OF_DISTANCES = 5;
    private static final int EXPIRED_MINUTES = 5;

    private final Map<String, Distance> macDistanceMapping = new HashMap<>();

    private Point p = new Point(Double.NaN, Double.NaN);

    private final Calculator calculator;
    private final EndpointMapping endpointMapping;
    private final String name;

    private DateTime lastupdated;

    /**
     *
     * @param name -
     * @param endpointMapping -
     * @param calculator -
     */
    public Device(final String name, final EndpointMapping endpointMapping, final Calculator calculator) {
        this.endpointMapping = endpointMapping;
        this.calculator = calculator;
        this.name = name;

        this.lastupdated = DateTime.now();
    }

    /**
     * Update the device with a new distance to a endpoint. Recalculate the location of this device.
     *
     * @param endpointmac -
     * @param distance -
     */
    public void update(final String endpointmac, final double distance) {
        lastupdated = DateTime.now();
        if (macDistanceMapping.containsKey(endpointmac)) {
            macDistanceMapping.get(endpointmac).add(distance);
        } else {
            final Distance distanceToP = new Distance(MAX_NUMBER_OF_DISTANCES);
            distanceToP.add(distance);
            macDistanceMapping.put(endpointmac, distanceToP);
        }
        final String p1 = getXdeEntry(1);
        final String p2 = getXdeEntry(2);
        final String p3 = getXdeEntry(3);
        recalculate(p1, p2, p3);
    }

    private void recalculate(final String p1, final String p2, final String p3) {

        final Distance distanceToP1 = getDistanceByMacadres(p1);
        final Distance distanceToP2 = getDistanceByMacadres(p2);
        final Distance distanceToP3 = getDistanceByMacadres(p3);
        
        final EndpointEntity epP1 = endpointMapping.get(p1);
        final EndpointEntity epP2 = endpointMapping.get(p2);
        final EndpointEntity epP3 = endpointMapping.get(p3);
        
        if (!Double.isNaN(distanceToP1.getAverage())
                && !Double.isNaN(distanceToP2.getAverage())
                && !Double.isNaN(distanceToP3.getAverage())) 
        {
            p = calculator.recalculate(
                    epP1.getX(),
                    epP1.getY(),
                    distanceToP1.getAverage(),
                    epP2.getX(),
                    epP2.getY(),
                    distanceToP2.getAverage(),
                    epP3.getX(),
                    epP3.getY(),
                    distanceToP3.getAverage());
        }
    }

    public double getX() {
        return p.getX();
    }

    public double getY() {
        return p.getY();
    }

    public boolean isValid() {
        return p.isValid();
    }

    /**
     *
     * @return true if the device is expired. Aka not updated within a time span
     */
    public boolean expired() {
        int minutes = Minutes.minutesBetween(lastupdated, DateTime.now()).getMinutes();
        return minutes > EXPIRED_MINUTES;
    }

    /**
     * Get the distance to a endpoint
     *
     * @param endpointMac -
     * @return -
     */
    public double getDistance(String endpointMac) {
        if (macDistanceMapping.containsKey(endpointMac)) {
            return macDistanceMapping.get(endpointMac).getAverage();
        }
        return 0;
    }


    private String getXdeEntry(int index) {
        if (macDistanceMapping.entrySet().size() >= index) {
            int count = 0;
            for (final Iterator<Map.Entry<String, Distance>> it = macDistanceMapping.entrySet().iterator(); 
                    it.hasNext();) 
            {
                Map.Entry<String, Distance> object = it.next();
                count++;
                if (count == index) {
                    return object.getKey();
                }
            }
        }
        return "";
    }

    private Distance getDistanceByMacadres(final String ep) {
        if (macDistanceMapping.containsKey(ep)) {
            return macDistanceMapping.get(ep);
        }
        return new Distance(EXPIRED_MINUTES, 0);
    }

}
