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
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class EndpointDevice {

    private final String mac;
    private double x;
    private double y;

    public EndpointDevice(String mac, double x, double y) {
        this.mac = mac;
        this.x = x;
        this.y = y;
    }

    public String getMac() {
        return mac;
    }
    

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isEndpoint(final String endpointmac) {
        return mac.equals(endpointmac);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    
    
    
}
