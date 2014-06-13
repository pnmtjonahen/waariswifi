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
package nl.tjonahen.wificollector.endpointdevice;

/**
 *
 * Fixed locations and mac adresses are loaded from a property file
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class EndpointMapping {
    // fixed locations

    private EndpointDevice endpointP1;
    private EndpointDevice endpointP2;
    private EndpointDevice endpointP3;
    private double p1p3;
    private double p2p3;

    /**
     * default.
     */
    public EndpointMapping() {
    }

    /**
     * 
     * @param endpointMac -
     * @param deviceMac -
     * @param distance -
     */
    public void update(final String endpointMac, final String deviceMac, final double distance) {
        if (endpointP1.isEndpoint(endpointMac) && endpointP2.isEndpoint(deviceMac)) {
            endpointP2.setX(distance);
        } else if (endpointP1.isEndpoint(endpointMac) && endpointP3.isEndpoint(deviceMac)) {
            p1p3 = distance;
            recalcP3();
        } else if (endpointP2.isEndpoint(endpointMac) && endpointP3.isEndpoint(deviceMac)) {
            p2p3 = distance;
            recalcP3();
        }
    }

    public EndpointDevice getP1() {
        return endpointP1;
    }

    public EndpointDevice getP2() {
        return endpointP2;
    }

    public EndpointDevice getP3() {
        return endpointP3;
    }

    public void setP1(final EndpointDevice epP1) {
        this.endpointP1 = epP1;
    }

    public void setP2(final EndpointDevice epP2) {
        this.endpointP2 = epP2;
    }

    public void setP3(final EndpointDevice epP3) {
        this.endpointP3 = epP3;
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
        final double p1p2 = endpointP2.getX();

        double a = p1p2;
        double b = p2p3;
        double c = p1p3;

        //CHECKSTYLE:OFF
        double pA = Math.acos((Math.pow(a, 2) - Math.pow(b, 2) - Math.pow(c, 2)) / -(2 * b * c)) * 180 / Math.PI;
        double pB = Math.acos((Math.pow(b, 2) - Math.pow(a, 2) - Math.pow(c, 2)) / -(2 * a * c)) * 180 / Math.PI;
        //CHECKSTYLE:ON
        double h = c * Math.sin(Math.toRadians(pB));

        endpointP3.setY(h);

        endpointP3.setX(Math.sqrt(Math.pow(c, 2) - Math.pow(h, 2)));

    }
}
