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
import nl.tjonahen.wificollector.endpointdevice.EndpointDevice;
import nl.tjonahen.wificollector.endpointdevice.EndpointMapping;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class EndpointMapppingTest {
    
    @Test
    public void testMapping() throws IOException {
        final EndpointMapping endpointMapping = new EndpointMapping();
     
        endpointMapping.setP1(new EndpointDevice("P1", 0, 0));
        endpointMapping.setP2(new EndpointDevice("P2", 0, 0));
        endpointMapping.setP3(new EndpointDevice("P3", 0, 0));
        
        // zet P1,P2, P3 zodat we een geleikzijdige driehoek krijgen alle hoeken zijn dan 60 graden
        
        // P1 -> P2 = 10 meter
        endpointMapping.update(endpointMapping.getP1().getMac(), endpointMapping.getP2().getMac(), 10.0d);
        // P1 -> P3 = 10 meter
        endpointMapping.update(endpointMapping.getP1().getMac(), endpointMapping.getP3().getMac(), 10.0d);
        // P2 -> P3 = 10 meter
        endpointMapping.update(endpointMapping.getP2().getMac(), endpointMapping.getP3().getMac(), 10.0d);
        
        // P1 = 0,0
        Assert.assertEquals(0, endpointMapping.getP1().getX(), 0);
        Assert.assertEquals(0, endpointMapping.getP1().getY(), 0);

        // P2 = 10,0
        Assert.assertEquals(10, endpointMapping.getP2().getX(), 0);
        Assert.assertEquals(0, endpointMapping.getP2().getY(), 0);

        // P3 = ?,?
        Assert.assertEquals(8.66, endpointMapping.getP3().getY(), 2);
        Assert.assertEquals(5, endpointMapping.getP3().getX(), 2);

    }
}
