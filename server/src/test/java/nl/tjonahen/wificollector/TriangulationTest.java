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

import java.io.IOException;
import nl.tjonahen.wificollector.endpointdevice.EndpointMapping;
import java.util.List;
import nl.tjonahen.wificollector.model.EndpointEntity;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */

public class TriangulationTest {
    
    @Test 
    public void determineLocation() throws IOException {
        final String p1 = "18:3d:a2:57:e3:50";
        final String p2 = "00:16:0a:26:a7:06";
        final String p3 = "84:51:81:a7:44:47";
        
        final WifiData d1 = new WifiData();
        d1.setEndpointmac("P1");
        d1.setDevicemac(p2);
        d1.setDb("-44");
        d1.setFreq("2462");
        
        final WifiData d2 = new WifiData();
        d2.setEndpointmac("P1");
        d2.setDevicemac(p3);
        d2.setDb("-57");
        d2.setFreq("2462");
        
        final WifiData d3 = new WifiData();
        d3.setEndpointmac("P2");
        d3.setDevicemac(p3);
        d3.setDb("-76");
        d3.setFreq("2462");
        
        
        
        final EndpointMapping endpointMapping = new EndpointMapping();
     
        endpointMapping.set(new EndpointEntity("", "P1", 0, 0));
        endpointMapping.set(new EndpointEntity("", "P2", 100, 50));
        endpointMapping.set(new EndpointEntity("", "P3", 50, 100));
        final Triangulation triangulate = Triangulation.getInstance(endpointMapping, null);

        Assert.assertEquals(1, triangulate.determineLocation(d1).size());
        Assert.assertNotNull(triangulate.determineLocation(d2));
        final List<WifiDevicePayload> determineLocation = triangulate.determineLocation(d3);
        Assert.assertNotNull(determineLocation);
        Assert.assertEquals(1, determineLocation.size());
        
        Assert.assertEquals("84:51:81:a7:44:47", determineLocation.get(0).getDeviceMac());
    }
    

    @Test
    public void test() {
        final EndpointMapping endpointMapping = new EndpointMapping();
     
        endpointMapping.set(new EndpointEntity("", "P1", 0, 0));
        endpointMapping.set(new EndpointEntity("", "P2", 0, 0));
        endpointMapping.set(new EndpointEntity("", "P3", 0, 0));
        final Triangulation triangulate = Triangulation.getInstance(endpointMapping, null);

        assertNotNull(triangulate.getExpiredDevices());
    }
}
