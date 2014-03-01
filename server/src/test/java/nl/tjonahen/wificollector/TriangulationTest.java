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
public class TriangulationTest {
    @Test
    public void testCalcDistance() {
        Triangulation triangulate = new Triangulation();
        
        Assert.assertEquals("0.01", String.format("%.2f", triangulate.calculateDistance(0, 2460)));
        Assert.assertEquals("96.95", String.format("%.2f", triangulate.calculateDistance(80, 2460)));
        Assert.assertEquals("969.54", String.format("%.2f", triangulate.calculateDistance(100, 2460)));
    }
    
    @Test 
    public void determineLocation() {
        final String p1 = "18:3d:a2:57:e3:50";
        final String p2 = "00:16:0a:26:a7:06";
        final String p3 = "84:51:81:a7:44:47";
        final String data1 = "1393000149.488284000:-44:2462";
        final String data2 = "1393000400.395984000:-57:2462";
        final String data3 ="1393001370.977584000:-76:2462";
        Triangulation triangulate = new Triangulation();

        Assert.assertEquals(2, triangulate.determineLocation(p1, p2, data1).length);
        Assert.assertNotNull(triangulate.determineLocation(p1, p3, data2));
        final WifiDevicePayload[] determineLocation = triangulate.determineLocation(p2, p3, data3);
        Assert.assertNotNull(determineLocation);
        Assert.assertEquals(1, determineLocation.length);
        
        Assert.assertEquals("84:51:81:a7:44:47", determineLocation[0].getDeviceMac());
        Assert.assertEquals(Double.valueOf("5.0"), Double.valueOf(determineLocation[0].getSize()));
        Assert.assertEquals(Double.valueOf("-1200.610677326515"), Double.valueOf(determineLocation[0].getX()));
        Assert.assertEquals(Double.NaN, determineLocation[0].getY(), 0);
        
        
    }

}