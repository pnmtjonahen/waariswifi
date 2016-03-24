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
import nl.tjonahen.wificollector.WifiData;
import nl.tjonahen.wificollector.calculator.ThreeCircleIntersectionCalculator;
import nl.tjonahen.wificollector.endpointdevice.EndpointMapping;
import nl.tjonahen.wificollector.model.EndpointEntity;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
        WifiData d1 = createWifiData("18:3d:a2:57:e3:50");
        final WifiData d2 = new WifiData();
        d2.setEndpointmac("00:16:0a:26:a7:06");
        d2.setDb("-74");
        d2.setFreq("2460");
        final WifiData d3 = new WifiData();
        d3.setEndpointmac("ff:ff:ff:ff:ff:ff");
        d3.setDb("-76");
        d3.setFreq("2460");

        n.update(d1);
        Assert.assertFalse("First distance Device should not be valid", n.isValid());
        n.update(d2);
        Assert.assertFalse("Second distance Device should not be valid", n.isValid());
        n.update(d3);
        Assert.assertFalse("Third distance Device should not be valid", n.isValid());

        assertEquals(100.0, n.getDistance("18:3d:a2:57:e3:50"), 5.0);
        assertEquals(50.0, n.getDistance("00:16:0a:26:a7:06"), 5.0);
        assertEquals(60.0, n.getDistance("ff:ff:ff:ff:ff:ff"), 5.0);

        assertFalse(n.expired());
    }

    @Test
    public void testCalc4Points() throws IOException {
        final EndpointMapping endpointMapping = new EndpointMapping();

        endpointMapping.set(new EndpointEntity("P1", "18:3d:a2:57:e3:50", 0, 0));
        endpointMapping.set(new EndpointEntity("P2", "00:16:0a:26:a7:06", 135, 0));
        endpointMapping.set(new EndpointEntity("P3", "ff:ff:ff:ff:ff:ff", 0, 135));
        endpointMapping.set(new EndpointEntity("P4", "aa:aa:aa:aa:aa:aa", 135, 135));

        final Device n = new Device("test", endpointMapping, new ThreeCircleIntersectionCalculator());

        n.update(createWifiData("18:3d:a2:57:e3:50"));
        n.update(createWifiData("00:16:0a:26:a7:06"));
        n.update(createWifiData("ff:ff:ff:ff:ff:ff"));
        n.update(createWifiData("aa:aa:aa:aa:aa:aa"));


        assertEquals(96, n.getDistance("18:3d:a2:57:e3:50"), 1.0);
        assertEquals(96, n.getDistance("00:16:0a:26:a7:06"), 1.0);
        assertEquals(96, n.getDistance("ff:ff:ff:ff:ff:ff"), 1.0);
        assertEquals(96, n.getDistance("aa:aa:aa:aa:aa:aa"), 1.0);

        assertFalse(n.expired());

        assertTrue(n.isValid());

        // KLOPT NIET moet iets anders zijn (rond de 70, 70)
        assertEquals(71.0, n.getX(), 1.0);
        assertEquals(94.0, n.getY(), 0.5);
    }

    private WifiData createWifiData(String macadress) {
        final WifiData d1 = new WifiData();
        d1.setEndpointmac(macadress);
        d1.setDb("-80");
        d1.setFreq("2460");
        return d1;
    }

    @Test
    public void testCalc8Points() throws IOException {
        final EndpointMapping endpointMapping = new EndpointMapping();

        endpointMapping.set(new EndpointEntity("P1", "18:3d:a2:57:e3:50", 0, 0));
        endpointMapping.set(new EndpointEntity("P2", "00:16:0a:26:a7:06", 200, 0));
        endpointMapping.set(new EndpointEntity("P3", "ff:ff:ff:ff:ff:ff", 100, -100));
        endpointMapping.set(new EndpointEntity("P4", "aa:aa:aa:aa:aa:aa", 100, 100));

        endpointMapping.set(new EndpointEntity("P5", "bb:aa:aa:aa:aa:aa", 50, -90));
        endpointMapping.set(new EndpointEntity("P6", "cc:aa:aa:aa:aa:aa", 150, -90));
        endpointMapping.set(new EndpointEntity("P7", "dd:aa:aa:aa:aa:aa", 50, 90));
        endpointMapping.set(new EndpointEntity("P8", "ee:aa:aa:aa:aa:aa", 150, 90));

        final Device n = new Device("test", endpointMapping, new ThreeCircleIntersectionCalculator());
        n.update(createWifiData("18:3d:a2:57:e3:50"));
        n.update(createWifiData("00:16:0a:26:a7:06"));
        n.update(createWifiData("ff:ff:ff:ff:ff:ff"));
        n.update(createWifiData("aa:aa:aa:aa:aa:aa"));

        n.update(createWifiData("bb:aa:aa:aa:aa:aa"));
        n.update(createWifiData("cc:aa:aa:aa:aa:aa"));
        n.update(createWifiData("dd:aa:aa:aa:aa:aa"));
        n.update(createWifiData("ee:aa:aa:aa:aa:aa"));

        final double distance = 96.95;

        assertEquals(distance, n.getDistance("18:3d:a2:57:e3:50"), 0.1);
        assertEquals(distance, n.getDistance("00:16:0a:26:a7:06"), 0.1);
        assertEquals(distance, n.getDistance("ff:ff:ff:ff:ff:ff"), 0.1);
        assertEquals(distance, n.getDistance("aa:aa:aa:aa:aa:aa"), 0.1);

        assertEquals(distance, n.getDistance("bb:aa:aa:aa:aa:aa"), 0.1);
        assertEquals(distance, n.getDistance("cc:aa:aa:aa:aa:aa"), 0.1);
        assertEquals(distance, n.getDistance("dd:aa:aa:aa:aa:aa"), 0.1);
        assertEquals(distance, n.getDistance("ee:aa:aa:aa:aa:aa"), 0.1);

        assertFalse(n.expired());

        assertTrue(n.isValid());

        assertEquals(104, n.getX(), 1.0);
        assertEquals(56.2, n.getY(), 0.2);
    }
}
