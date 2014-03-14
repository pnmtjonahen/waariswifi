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

package nl.tjonahen.wificollector.calculator;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class PhilippeCalculatorTest {   
    private final PhilippeCalculator calc = new PhilippeCalculator();

    @Test
    public void testCalculateLife() {
        
        Point p = calc.recalculate(0.000000,0.000000,1.722709,3.000000,0.000000,15.353661,0.000000,3.000000,0.000000);
        
        Assert.assertNotNull(p);
        Assert.assertFalse(p.isValid());

    }
    
    @Test
    public void testCalc() {
        final Point p = calc.recalculate(0.000000, 0.000000, 3.0, 3.000000, 0.000000, 3.0, 0.000000, 3.000000, 3.000000);

        Assert.assertNotNull(p);
        Assert.assertTrue(p.isValid());
        Assert.assertEquals(1.5, p.getX(), 0);
        Assert.assertEquals(2.598, p.getY(), 3);

    }

}
