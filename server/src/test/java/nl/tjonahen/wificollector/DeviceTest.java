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

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class DeviceTest {
 
    
    @Test
    public void testValid() {
        final EndpointMapping endpointMapping = new EndpointMapping();
        final Device n = Device.create(endpointMapping.getP1(), endpointMapping.getP2(), endpointMapping.getP3());
        
        Assert.assertFalse("New Device should not be valid", n.isValid());
        
    }
    
    @Test
    public void testCalc() {
        //Info:   {"device":"34:51:c9:4c:6e:9e", "x":"NaN", "y":"NaN", "endpoint":"18:3d:a2:57:e3:50", "distance":"96.875053", "triangulated":false}
//Info:   {"device":"34:51:c9:4c:6e:9e", "x":"NaN", "y":"NaN", "endpoint":"00:16:0a:26:a7:06", "distance":"48.552540", "triangulated":false}
        final EndpointMapping endpointMapping = new EndpointMapping();
        final Device n = Device.create(endpointMapping.getP1(), endpointMapping.getP2(), endpointMapping.getP3());

        n.update("18:3d:a2:57:e3:50", 96.875053);
        Assert.assertFalse("First distance Device should not be valid", n.isValid());
        n.update("00:16:0a:26:a7:06", 48.552540);
        Assert.assertTrue("Second distance Device should be valid", n.isValid());
        
    }
}
