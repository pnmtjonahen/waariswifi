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
import org.joda.time.DateTime;
import org.joda.time.Minutes;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class Device {
    
    private final Distance distanceToP1;
    private final Distance distanceToP2;
    private final Distance distanceToP3;
    

    private Point p = new Point(Double.NaN, Double.NaN);
    
    private boolean valid;
    
    private final Calculator calculator;
    private final EndpointMapping endpointMapping;
    private final String name;
    
    private DateTime lastupdated;
    
    public Device(final String name, final EndpointMapping endpointMapping, final Calculator calculator) {
        this.endpointMapping = endpointMapping;
        this.calculator = calculator;
        this.name = name;
        this.distanceToP1 = new Distance(5);
        this.distanceToP2 = new Distance(5, 0d);
        this.distanceToP3 = new Distance(5, 0d);
        this.lastupdated = DateTime.now();
    }

    public void update(final String endpointmac, final double distance) {
        lastupdated = DateTime.now();
        if (endpointMapping.getP1().isEndpoint(endpointmac)) {
            distanceToP1.add(distance);
        } else if (endpointMapping.getP2().isEndpoint(endpointmac)) {
            distanceToP2.add(distance);
        } else if (endpointMapping.getP3().isEndpoint(endpointmac)) {
            distanceToP3.add(distance);
        }
        recalculate();
    }

    private void recalculate() {
        
        if (!Double.isNaN(this.distanceToP1.getAverage())
                && !Double.isNaN(this.distanceToP2.getAverage())
                && !Double.isNaN(this.distanceToP3.getAverage())) {
            System.out.println(String.format("%s, %f,%f,%f,%f,%f,%f,%f,%f,%f", 
                    this.name,
                    this.endpointMapping.getP1().getX(),
                    this.endpointMapping.getP1().getY(),
                    this.distanceToP1.getAverage(),
                    this.endpointMapping.getP2().getX(),
                    this.endpointMapping.getP2().getY(),
                    this.distanceToP2.getAverage(),
                    this.endpointMapping.getP3().getX(),
                    this.endpointMapping.getP3().getY(),
                    this.distanceToP3.getAverage()));
            p = calculator.recalculate(
                    this.endpointMapping.getP1().getX(),
                    this.endpointMapping.getP1().getY(),
                    this.distanceToP1.getAverage(),
                    this.endpointMapping.getP2().getX(),
                    this.endpointMapping.getP2().getY(),
                    this.distanceToP2.getAverage(),
                    this.endpointMapping.getP3().getX(),
                    this.endpointMapping.getP3().getY(),
                    this.distanceToP3.getAverage());
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

    
    public boolean expired() {
        int minutes = Minutes.minutesBetween(lastupdated, DateTime.now()).getMinutes();
        return minutes > 5;
    }

    public double getDistance(String endpointMac) {
        if (endpointMapping.getP3().isEndpoint(endpointMac)) {
            return distanceToP3.getAverage();
        } else if (endpointMapping.getP2().isEndpoint(endpointMac)) {
            return distanceToP2.getAverage();
        }
        return distanceToP1.getAverage();
    }
        
    

    
}
