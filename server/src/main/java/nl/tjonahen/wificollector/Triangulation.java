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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
    
    @Inject
    private EndpointMapping endpointMapping;

    void setEndpointMapping(EndpointMapping endpointMapping) {
        this.endpointMapping = endpointMapping;
    }
    

    private final Map<String, Device> nodeMap;
    
    public Triangulation() {
        nodeMap = new TreeMap<>();
    }
    
    public List<WifiDevicePayload> determineLocation(final String endpointMac, final String deviceMac, final String data) {
        final List<WifiDevicePayload> result = new ArrayList<WifiDevicePayload>();
        final String[] fields = data.split(":");
        final double distance = calculateDistance(Math.abs(Double.valueOf(fields[1])), Double.valueOf(fields[2]));
        
        if (endpointMapping.getP1().isEndpoint(endpointMac) && endpointMapping.getP2().isEndpoint(deviceMac)) {
            endpointMapping.update(endpointMac, deviceMac, distance);
            
            result.add(new WifiDevicePayload(true, "P1", 
                            endpointMapping.getP1().getX(), endpointMapping.getP1().getY(), "P1", 0));
            result.add(new WifiDevicePayload(true, "P2", 
                            endpointMapping.getP2().getX(), endpointMapping.getP2().getX(), "P2", 0));
            result.add(new WifiDevicePayload(true, "P3", 
                            endpointMapping.getP3().getX(), endpointMapping.getP3().getX(), "P3", 0));
                                    
            
            return result;
        } else {
            final String name = (macNameResolver == null ? deviceMac : macNameResolver.resolve(deviceMac));
            if (nodeMap.containsKey(deviceMac)) {
                final Device n = nodeMap.get(deviceMac);
                n.update(endpointMac, distance);
                result.add(new WifiDevicePayload(n.isValid(), name, n.getX(), n.getY(), endpointMac, distance));
                
                return result;
            }
            final Device n = DeviceFactory.create(endpointMapping);
            n.update(endpointMac, distance);
            nodeMap.put(deviceMac, n);
            
            result.add(new WifiDevicePayload(false, 
                                                name, 
                                                0, 
                                                0, 
                                                endpointMac, 
                                                distance));
            return result;
        }
        
        
    }
    
    
    double calculateDistance(double levelInDb, double freqInMHz)    {
       final double exp = (27.55 - (20 * Math.log10(freqInMHz)) + levelInDb) / 20.0;
       return Math.pow(10.0, exp);
    } 

    public List<WifiDevicePayload> getExpiredDevices() {
        final List<WifiDevicePayload> result = new ArrayList<WifiDevicePayload>();
        
        final List<String> removed = new ArrayList<String>();
        
        for (Entry<String, Device> entry : nodeMap.entrySet() ) {
             if (entry.getValue().expired()) {
                 removed.add(entry.getKey());
             }   
        }
        
        for (String key: removed) {
            result.add(new WifiDevicePayload(key));
            nodeMap.remove(key);
        }
        System.out.println("Found expired nodes " + result.size());
        return result;
    }
}
