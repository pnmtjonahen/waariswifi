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

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class Device {
    
    private double distanceToP1;
    private double distanceToP2;
    private double distanceToP3;
    
    private EndpointDevice P1;
    private EndpointDevice P2;
    private EndpointDevice P3;

    private double x;
    private double y;
    
    public static Device create(final EndpointDevice P1, final EndpointDevice P2, final EndpointDevice P3) {
        final Device device = new Device();
        device.P1 = P1;
        device.P2 = P2;
        device.P3 = P3;
        return device;
    }

    public void update(final String endpointmac, final double distance) {
        if (P1.isEndpoint(endpointmac)) {
            distanceToP1 = distance;
        } else if (P2.isEndpoint(endpointmac)) {
            distanceToP2 = distance;
        } else if (P2.isEndpoint(endpointmac)) {
            distanceToP3 = distance;
        }
        recalculate();
    }

    private void recalculate() {
        System.out.println(String.format("%.4f, %.4f, %.4f", P2.getX(), distanceToP1, distanceToP2) );

//        x = (Math.pow(distanceToP1, 2) - Math.pow(distanceToP2, 2) + Math.pow(P2.getX(), 2)) / (2*P2.getX());
//        y = Math.sqrt(Math.pow(distanceToP1, 2) - Math.pow(x, 2));      
    /*
                    A
                    /\
                 c /  \ b
                  /    \
                 B------C
                     a    
    
    P1 = B
    P2 = C
    P3 = A
    
    */        
        double a = P2.getX();
        double b = distanceToP2;
        double c = distanceToP1;
        
        double A = Math.acos((Math.pow(a, 2) - Math.pow(b, 2) - Math.pow(c, 2))/-(2*b*c)) * 180/Math.PI;
        double B = Math.acos((Math.pow(b, 2) - Math.pow(a, 2) - Math.pow(c, 2))/-(2*a*c)) * 180/Math.PI;
        double C = 180.0 - A - B;
        
        y = c * Math.sin(Math.toRadians( B ));
        x = Math.sqrt(Math.pow(c, 2) - Math.pow(y, 2));
        
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
            
    public boolean isValid() {
        return x != Double.NaN && y != Double.NaN;
    }
    
}
