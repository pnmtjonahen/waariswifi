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
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class DistanceTest {

    @Test
    public void testAverage() {
        final Distance d = new Distance(10);
        final WifiData data = new WifiData();
        data.setDb("-10");
        data.setFreq("2426");
        Assert.assertTrue(Double.isNaN(d.getAverage()));
        d.add(data);
        Assert.assertFalse(Double.isNaN(d.getAverage()));
        d.add(data);
        Assert.assertFalse(Double.isNaN(d.getAverage()));
        d.add(data);
        Assert.assertFalse(Double.isNaN(d.getAverage()));
        d.add(data);
        Assert.assertFalse(Double.isNaN(d.getAverage()));
        d.add(data);
        Assert.assertFalse(Double.isNaN(d.getAverage()));
        d.add(data);
        Assert.assertFalse(Double.isNaN(d.getAverage()));
        d.add(data);
        Assert.assertFalse(Double.isNaN(d.getAverage()));
        d.add(data);
        Assert.assertFalse(Double.isNaN(d.getAverage()));
        d.add(data);
        Assert.assertFalse(Double.isNaN(d.getAverage()));
        d.add(data);
        Assert.assertFalse(Double.isNaN(d.getAverage()));
        System.out.println(d.getAverage());

    }

    @Test
    public void testCalcDistance() throws IOException {

        final Distance distance = new Distance(0);

        Assert.assertEquals("0.01", String.format("%.2f", distance.calculateDistance(0, 2460)));
        Assert.assertEquals("96.95", String.format("%.2f", distance.calculateDistance(80, 2460)));
        Assert.assertEquals("969.54", String.format("%.2f", distance.calculateDistance(100, 2460)));
        Assert.assertEquals(100, distance.calculateDistance(80.3, 2460), 0.5);
        Assert.assertEquals(50, distance.calculateDistance(74.3, 2460), 0.5);
        
        for (int i = 0; i <= 100; i++) {
            System.out.println(String.format("%03d - >%.2f", i, distance.calculateDistance(i, 2460)));
        }
    }

}
