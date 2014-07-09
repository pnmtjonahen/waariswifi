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

 

    private final EndpointDistanceMapping endpointDistanceMapping = new EndpointDistanceMapping();

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
     * @param endpointmac mac adress of the endpoint
     * @param distance distance between this device and the endpoint.
     */
    public void update(final String endpointmac, final double distance) {
        lastupdated = DateTime.now();
        updateDistanceToEndpoint(endpointmac, distance);
        
        for (int i = 0; i < endpointDistanceMapping.maxNumberEndpoints(); i++) {
            final String p1 = endpointDistanceMapping.getEndpoint(i);
            final String p2 = endpointDistanceMapping.getEndpoint(i+1);
            final String p3 = endpointDistanceMapping.getEndpoint(i+2);

            recalculate(p1, p2, p3);
        }
    }

    private void updateDistanceToEndpoint(final String endpointmac, final double distance) {
        if (endpointDistanceMapping.containsKey(endpointmac)) {
            endpointDistanceMapping.get(endpointmac).add(distance);
        } else {
            final Distance distanceToP = new Distance(MAX_NUMBER_OF_DISTANCES);
            distanceToP.add(distance);
            endpointDistanceMapping.put(endpointmac, distanceToP);
        }
    }

    private void recalculate(final String p1, final String p2, final String p3) {

        final Distance distanceToP1 = endpointDistanceMapping.getDistanceByMacadres(p1);
        final Distance distanceToP2 = endpointDistanceMapping.getDistanceByMacadres(p2);
        final Distance distanceToP3 = endpointDistanceMapping.getDistanceByMacadres(p3);
        
        final EndpointEntity epP1 = endpointMapping.get(p1);
        final EndpointEntity epP2 = endpointMapping.get(p2);
        final EndpointEntity epP3 = endpointMapping.get(p3);
        
        if (!Double.isNaN(distanceToP1.getAverage())
                && !Double.isNaN(distanceToP2.getAverage())
                && !Double.isNaN(distanceToP3.getAverage())) 
        {
            p.add(calculator.recalculate(
                    epP1.getX(),
                    epP1.getY(),
                    distanceToP1.getAverage(),
                    epP2.getX(),
                    epP2.getY(),
                    distanceToP2.getAverage(),
                    epP3.getX(),
                    epP3.getY(),
                    distanceToP3.getAverage()));
            
            System.out.println(String.format("%.2f, %.2f, DISTANCE, %.2f, %.2f, DISTANCE, %.2f, %.2f, DISTANCE -> p(%.2f, %.2f)", 
                    epP1.getX(),
                    epP1.getY(),
                    epP2.getX(),
                    epP2.getY(),
                    epP3.getX(),
                    epP3.getY(),
                    p.getX(), 
                    p.getY()));
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
        if (endpointDistanceMapping.containsKey(endpointMac)) {
            return endpointDistanceMapping.get(endpointMac).getAverage();
        }
        return 0;
    }

}
