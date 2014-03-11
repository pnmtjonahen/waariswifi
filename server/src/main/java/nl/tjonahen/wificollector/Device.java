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

package nl.tjonahen.wificollector;

import nl.tjonahen.wificollector.calculator.Calculator;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class Device {
    
    private double distanceToP1 = 0;
    private double distanceToP2 = 0;
    private double distanceToP3 = 0;
    

    private double x = Double.NaN;
    private double y = Double.NaN;
    
    private boolean valid;
    
    private final Calculator calculator;
    private final EndpointMapping endpointMapping;
    
    private DateTime lastupdated;
    
    public Device(final EndpointMapping endpointMapping, final Calculator calculator) {
        this.endpointMapping = endpointMapping;
        this.calculator = calculator;
        lastupdated = DateTime.now();
    }

    public void update(final String endpointmac, final double distance) {
        lastupdated = DateTime.now();
        if (endpointMapping.getP1().isEndpoint(endpointmac)) {
            distanceToP1 = distance;
        } else if (endpointMapping.getP2().isEndpoint(endpointmac)) {
            distanceToP2 = distance;
        } else if (endpointMapping.getP3().isEndpoint(endpointmac)) {
            distanceToP3 = distance;
        }
        recalculate();
    }

    private void recalculate() {
        calculator.recalculate(this);
        valid = isXValid() && isYValid();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
            
    public boolean isValid() {
        return valid;
    }

    private boolean isYValid() {
        return !Double.isNaN(y);
    }

    private boolean isXValid() {
        return !Double.isNaN(x);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDistanceToP1() {
        return distanceToP1;
    }

    public double getDistanceToP2() {
        return distanceToP2;
    }

    public double getDistanceToP3() {
        return distanceToP3;
    }

    public EndpointDevice getP1() {
        return endpointMapping.getP1();
    }

    public EndpointDevice getP2() {
        return endpointMapping.getP2();
    }

    public EndpointDevice getP3() {
        return endpointMapping.getP3();
    }
    
    public boolean expired() {
        int minutes = Minutes.minutesBetween(lastupdated, DateTime.now()).getMinutes();
        System.out.println("age:" + minutes);
        return minutes > 5;
    }
        
    

    
}
