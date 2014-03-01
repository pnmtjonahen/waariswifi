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

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@ApplicationScoped
public class Triangulation {
    @Inject 
    private MacNameResolver macNameResolver;
    
    private double p1p2distance;

    private final Map<String, Node> nodeMap;
    public Triangulation() {
        nodeMap = new TreeMap<>();
    }
    static class Point {
        final double x;
        final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
        
    }
    
    public WifiDevicePayload[] determineLocation(final String endpointMac, final String deviceMac, final String data) {

        final String[] fields = data.split(":");
        final double distance = calculateDistance(Math.abs(Double.valueOf(fields[1])), Double.valueOf(fields[2]));
        if ("18:3d:a2:57:e3:50".equals(endpointMac) && "00:16:0a:26:a7:06".equals(deviceMac)) {
            p1p2distance = distance;
            final WifiDevicePayload[] result = {new WifiDevicePayload(true, "P1", 0, 0, 5), new WifiDevicePayload(true, "P2", distance, 0, 5)};
            return result;
        } else {
            if (nodeMap.containsKey(deviceMac)) {
                final Node n = nodeMap.get(deviceMac);
                n.getVectors().add(new Vector(endpointMac, distance));
                if (n.getVectors().size() >= 2) {
                    final Point P1 = new Point(0,0);
                    final Point P2 = new Point(p1p2distance, 0);
                    final Iterator<Vector> it = n.getVectors().iterator();
                    final Vector v1 = it.next();
                    final Vector v2 = it.next();
                    double r1 = v1.getDistance();
                    double r2 = v2.getDistance();
                    if ("00:16:0a:26:a7:06".equals(v1.getEndnode())) {
                        r1 = v2.getDistance();
                        r2 = v1.getDistance();
                    }
                    System.out.println(String.format("%.4f, %.4f, %.4f", p1p2distance, r1, r2) );
                    double x = (Math.pow(r1, 2) - Math.pow(r2, 2) + Math.pow(P2.getX(), 2)) / (2*P2.getX());
                    double y = Math.sqrt(Math.pow(r1, 2) - Math.pow(x, 2));                    
                    final WifiDevicePayload[] result = {new WifiDevicePayload(true, deviceMac, x, y, 5)};
                    return result;
                }
            } else {
                final Node n = new Node(deviceMac);
                n.getVectors().add(new Vector(endpointMac, distance));
                nodeMap.put(deviceMac, n);
            }
//           return null;
            final String name = (macNameResolver == null ? deviceMac : macNameResolver.resolve(deviceMac));
            final WifiDevicePayload[] result = { new WifiDevicePayload(false, 
                                                                name, 
                                                                0, 
                                                                0, 
                                                                distance) };
            return result;
        }
        
        
    }
    
    
    double calculateDistance(double levelInDb, double freqInMHz)    {
       double exp = (27.55 - (20 * Math.log10(freqInMHz)) + levelInDb) / 20.0;
       return Math.pow(10.0, exp);
    } 
}
