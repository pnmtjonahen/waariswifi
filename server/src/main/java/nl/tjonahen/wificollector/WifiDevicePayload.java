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
    private final double size;
    private final boolean triangulated;

    public WifiDevicePayload(final boolean triangulated, 
                             final String deviceMac, 
                             final double x, 
                             final double y, 
                             final double size) {
        this.deviceMac = deviceMac;
        this.x = x;
        this.y = y;
        this.size = size;
        this.triangulated = triangulated;
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

    public double getSize() {
        return size;
    }

    public boolean isTriangulated() {
        return triangulated;
    }

    
}
