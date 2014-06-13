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
public class WifiDevicePayload {
    private final String deviceMac;
    private final double x;
    private final double y;
    private final String endpointMac;
    private final double distance;
    private final boolean triangulated;
    private final boolean expired;

    /**
     * 
     * @param triangulated true if the payload is correct triangulated
     * @param deviceMac the mac adres of the device
     * @param x the current x
     * @param y the current y
     * @param endpointMac the mac adress of the scanning device
     * @param distance the distance to the scanning device
     */
    public WifiDevicePayload(final boolean triangulated, 
                             final String deviceMac, 
                             final double x, 
                             final double y,
                             final String endpointMac,
                             final double distance) 
    {
        this.deviceMac = deviceMac;
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.triangulated = triangulated;
        this.endpointMac = endpointMac;
        this.expired = false;
    }
    
    /**
     * 
     * @param deviceMac the mac adres of the device 
     */
    public WifiDevicePayload(final String deviceMac) {
        this.deviceMac = deviceMac;
        this.x = 0;
        this.y = 0;
        this.distance = 0;
        this.triangulated = false;
        this.endpointMac = "";
        this.expired = true;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDistance() {
        return distance;
    }

    public boolean isTriangulated() {
        return triangulated;
    }

    public String getEndpointMac() {
        return endpointMac;
    }

    public boolean isExpired() {
        return expired;
    }

}
