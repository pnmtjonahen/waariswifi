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

package nl.tjonahen.wificollector.device;

import java.io.IOException;
import nl.tjonahen.wificollector.calculator.ThreeCircleIntersectionCalculator;
import nl.tjonahen.wificollector.endpointdevice.EndpointMapping;
import nl.tjonahen.wificollector.model.EndpointEntity;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class DeviceTest {
 
    
    @Test
    public void testValid() throws IOException {
        final Device n = new Device("test", null, null);
        
        Assert.assertFalse("New Device should not be valid", n.isValid());
        
    }
    
    @Test
    public void testCalc() throws IOException {
//Info:   {"device":"34:51:c9:4c:6e:9e", "x":"NaN", "y":"NaN", "endpoint":"18:3d:a2:57:e3:50", "distance":"96.875053", "triangulated":false}
//Info:   {"device":"34:51:c9:4c:6e:9e", "x":"NaN", "y":"NaN", "endpoint":"00:16:0a:26:a7:06", "distance":"48.552540", "triangulated":false}
        final EndpointMapping endpointMapping = new EndpointMapping();
     
        endpointMapping.set(new EndpointEntity("P1", "18:3d:a2:57:e3:50", 0, 0));
        endpointMapping.set(new EndpointEntity("P2", "00:16:0a:26:a7:06", 3, 0));
        endpointMapping.set(new EndpointEntity("P3", "ff:ff:ff:ff:ff:ff", 0, 3));        
        
        final Device n = new Device("test", endpointMapping, new ThreeCircleIntersectionCalculator());

        for (int i = 0; i < 6; i++) {
            // perform 5 updates to get a average distance
            n.update("18:3d:a2:57:e3:50", 100.0);
            Assert.assertFalse("First distance Device should not be valid", n.isValid());
            n.update("00:16:0a:26:a7:06", 50.0);
            Assert.assertFalse("Second distance Device should not be valid", n.isValid());
            n.update("ff:ff:ff:ff:ff:ff", 60.0);
            Assert.assertFalse("Third distance Device should not be valid", n.isValid());
        }
        
        assertEquals(100.0, n.getDistance("18:3d:a2:57:e3:50"), 0.0);
        assertEquals(50.0, n.getDistance("00:16:0a:26:a7:06"), 0.0);
        assertEquals(60.0, n.getDistance("ff:ff:ff:ff:ff:ff"), 0.0);
        
        assertFalse(n.expired());
    }
}
