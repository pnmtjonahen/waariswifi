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
public class EndpointMapping {
    private final EndpointDevice P1 = new EndpointDevice("18:3d:a2:57:e3:50", 0, 0);
    private final EndpointDevice P2 = new EndpointDevice("00:16:0a:26:a7:06", 3.2, 0);
    private final EndpointDevice P3 = new EndpointDevice("unknown", Double.NaN, Double.NaN);
    private double p1p3;
    private double p2p3;
    
    public void update(final String endpointMac, final String deviceMac, final double distance) {
        if (P1.isEndpoint(endpointMac) && P2.isEndpoint(deviceMac)) {
            P2.setX(distance);
        } else if (P1.isEndpoint(endpointMac) && P3.isEndpoint(deviceMac)) {
            p1p3 = distance;
            recalcP3();
        } else if (P2.isEndpoint(endpointMac) && P3.isEndpoint(deviceMac)) {
            p2p3 = distance;
            recalcP3();
        }
    }

    public EndpointDevice getP1() {
        return P1;
    }

    public EndpointDevice getP2() {
        return P2;
    }

    public EndpointDevice getP3() {
        return P3;
    }

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
    private void recalcP3() {
        final double p1p2 = P2.getX();
    
        double a = p1p2;
        double b = p2p3;
        double c = p1p3;
        
        double A = Math.acos((Math.pow(a, 2) - Math.pow(b, 2) - Math.pow(c, 2))/-(2*b*c)) * 180/Math.PI;
        double B = Math.acos((Math.pow(b, 2) - Math.pow(a, 2) - Math.pow(c, 2))/-(2*a*c)) * 180/Math.PI;
        double C = 180.0 - A - B;
        
        double h = c * Math.sin(Math.toRadians( B ));
        
        P3.setY(h);
        
        P3.setX(Math.sqrt(Math.pow(c, 2) - Math.pow(h, 2)));
        
    }
    
    
}
