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
    
    private EndpointDevice P1 = new EndpointDevice("18:3d:a2:57:e3:50", 0, 0);
    private EndpointDevice P2 = new EndpointDevice("00:16:0a:26:a7:06", 3.2d, 0);
    private EndpointDevice P3 = null;
    

    private final Map<String, Device> nodeMap;
    public Triangulation() {
        nodeMap = new TreeMap<>();
    }
    
    public WifiDevicePayload[] determineLocation(final String endpointMac, final String deviceMac, final String data) {

        final String[] fields = data.split(":");
        final double distance = calculateDistance(Math.abs(Double.valueOf(fields[1])), Double.valueOf(fields[2]));
        if ("18:3d:a2:57:e3:50".equals(endpointMac) && "00:16:0a:26:a7:06".equals(deviceMac)) {
            final WifiDevicePayload[] result = {
                                    new WifiDevicePayload(true, "P1", P1.getX(), P1.getY(), "P1", 0), 
                                    new WifiDevicePayload(true, "P2", P2.getX(), P2.getX(), "P1", distance)
            };
            return result;
        } else {
            if (nodeMap.containsKey(deviceMac)) {
                final Device n = nodeMap.get(deviceMac);
                n.update(endpointMac, distance);
                final WifiDevicePayload[] result = {
                                new WifiDevicePayload(true, deviceMac, n.getX(), n.getY(), endpointMac, distance)
                };
                return result;
            } else {
                final Device n = Device.create(P1, P2, P3);
                n.update(endpointMac, distance);
                nodeMap.put(deviceMac, n);
            }
//           return null;
            final String name = (macNameResolver == null ? deviceMac : macNameResolver.resolve(deviceMac));
            final WifiDevicePayload[] result = { new WifiDevicePayload(false, 
                                                                name, 
                                                                0, 
                                                                0, 
                                                                endpointMac, 
                                                                distance) };
            return result;
        }
        
        
    }
    
    
    double calculateDistance(double levelInDb, double freqInMHz)    {
       double exp = (27.55 - (20 * Math.log10(freqInMHz)) + levelInDb) / 20.0;
       return Math.pow(10.0, exp);
    } 
}
