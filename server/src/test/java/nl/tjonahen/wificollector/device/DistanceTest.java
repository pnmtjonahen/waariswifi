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

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class DistanceTest {
 
    @Test
    public void testAverage() {
        Distance d = new Distance(10);
        
        Assert.assertTrue(Double.isNaN(d.getAverage()));
        d.add(10);
        Assert.assertTrue(Double.isNaN(d.getAverage()));
        d.add(10);
        Assert.assertTrue(Double.isNaN(d.getAverage()));
        d.add(10);
        Assert.assertTrue(Double.isNaN(d.getAverage()));
        d.add(10);
        Assert.assertTrue(Double.isNaN(d.getAverage()));
        d.add(10);
        Assert.assertTrue(Double.isNaN(d.getAverage()));
        d.add(10);
        Assert.assertTrue(Double.isNaN(d.getAverage()));
        d.add(10);
        Assert.assertTrue(Double.isNaN(d.getAverage()));
        d.add(10);
        Assert.assertTrue(Double.isNaN(d.getAverage()));
        d.add(10);
        Assert.assertTrue(Double.isNaN(d.getAverage()));
        d.add(10);
        Assert.assertFalse(Double.isNaN(d.getAverage()));
        System.out.println(d.getAverage());
        
    }
}
