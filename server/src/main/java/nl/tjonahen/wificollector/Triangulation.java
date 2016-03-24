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

import nl.tjonahen.wificollector.device.Device;
import nl.tjonahen.wificollector.device.DeviceFactory;
import nl.tjonahen.wificollector.endpointdevice.EndpointMapping;
import nl.tjonahen.wificollector.devicenameing.MacNameResolver;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * Handles the trilangulation of device nodes and endpoints.
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
//CHECKSTYLE:OFF 
// class is not final as it then can't b mocked
public class Triangulation {
//CHECKSTYLE:ON    
    private MacNameResolver macNameResolver;
    private EndpointMapping endpointMapping;
    private final Map<String, Device> nodeMap;
    
    private static Triangulation instance = null;
    
    /**
     * Singleton to get a triangulation instance.
     * @param endpointMapping -
     * @param macNameResolver -
     * @return -
     */
    public static synchronized Triangulation getInstance(final EndpointMapping endpointMapping, 
                                                         final MacNameResolver macNameResolver) 
    {
        if (instance == null) {
            instance = new Triangulation();
        }
        instance.endpointMapping = endpointMapping;
        instance.macNameResolver = macNameResolver;
        return instance;
    }
    
    private Triangulation() {
        this.nodeMap = new TreeMap<>();
    }


    
    /**
     * Determines the location of a device. If the device was one of the 
     * endpoints a new endpoint collection is returned
     * @param wifiData -
     * @return collection of WifiDevicePayloads
     */
    public List<WifiDevicePayload> determineLocation(final WifiData wifiData) 
    {
        
//        final double distance = calculateDistance(Math.abs(Double.valueOf(wifiData.getDb())), Double.valueOf(wifiData.getFreq()));
        
    
        if (nodeMap.containsKey(wifiData.getDevicemac())) {
            return processDeviceUpdate(wifiData);
        }

        return processNewDevice(wifiData);
    }

    private List<WifiDevicePayload> processNewDevice(final WifiData wifiData) 
    {
        final List<WifiDevicePayload> result = new ArrayList<>();
        final String name = (macNameResolver == null ? wifiData.getDevicemac() : macNameResolver.resolve(wifiData.getDevicemac()));
        final Device n = DeviceFactory.create(wifiData.getDevicemac(), endpointMapping);
        n.update(wifiData);
        nodeMap.put(wifiData.getDevicemac(), n);

        result.add(new WifiDevicePayload(false, name, 0, 0, wifiData.getEndpointmac(),  0));
        return result;
    }

    private List<WifiDevicePayload> processDeviceUpdate(final WifiData wifiData) 
    {
        final List<WifiDevicePayload> result = new ArrayList<>();
        final String name = (macNameResolver == null ? wifiData.getDevicemac() : macNameResolver.resolve(wifiData.getDevicemac()));
        final Device n = nodeMap.get(wifiData.getDevicemac());
        n.update(wifiData);
        result.add(new WifiDevicePayload(n.isValid(), name, n.getX(), n.getY(), wifiData.getEndpointmac(), 
                                                                                n.getDistance(wifiData.getEndpointmac())));
        return result;
    }
    
    /**
     * 
     * @return list of wifidevicepayload for nodes that are expired (no update for 5 minutes). 
     */
    public List<WifiDevicePayload> getExpiredDevices() {
        final List<WifiDevicePayload> result = new ArrayList<>();
        
        final List<String> removed = new ArrayList<>();
        
        for (Entry<String, Device> entry : nodeMap.entrySet()) {
             if (entry.getValue().expired()) {
                 removed.add(entry.getKey());
             }   
        }
        
        for (String key: removed) {
            result.add(new WifiDevicePayload(key));
            nodeMap.remove(key);
        }
        return result;
    }
}
