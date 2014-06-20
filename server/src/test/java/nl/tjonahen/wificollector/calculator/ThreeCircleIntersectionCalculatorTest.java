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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class ThreeCircleIntersectionCalculatorTest {
    private final ThreeCircleIntersectionCalculator calc = new ThreeCircleIntersectionCalculator();
    
    @Test
    public void testCalcLife() {
        final Point p = calc.recalculate(0.000000, 0.000000, 1.722709, 3.000000,
                        0.000000, 15.353661, 0.000000, 3.000000, 0.000000);
        
        assertNotNull(p);
        assertFalse(p.isValid());

    }
    @Test
    public void testCalc() {
        final Point p = calc.recalculate(0.000000,0.000000,3.0,3.000000,0.000000,3.0,0.000000,3.000000,3.000000);
        
        assertNotNull(p);
        assertTrue(p.isValid());
        assertEquals(1.5, p.getX(), 0);
        assertEquals(2.598, p.getY(), 3);
        
    }
    @Test
    public void testCalcNoIntercetion() {
        final Point p = calc.recalculate(0.0,0.0,3.0,
                20.0,0.0,3.0,
                0.0,10.0,3.0);
        
        assertNotNull(p);
        assertFalse(p.isValid());
        assertTrue(Double.isNaN(p.getX()));
        assertTrue(Double.isNaN(p.getY()));
        
    }
   @Test
    public void testCalcIntercetTop() {
        final Point p = calc.recalculate(0.0,0.0,20.0,
                20.0,0.0,20.0,
                0.0,20.0,10.35276180410083);
        
        assertNotNull(p);
        assertTrue(p.isValid());
        assertEquals(10.0, p.getX(), 0);
        assertEquals(17.320, p.getY(), 3);
        
    }
   @Test
    public void testCalcIntercetBottom() {
        final Point p = calc.recalculate(0.0,0.0,20.0,
                20.0,0.0,20.0,
                0.0,20.0,38.63703305156274);
        
        assertNotNull(p);
        assertTrue(p.isValid());
        assertEquals(10.0, p.getX(), 0);
        assertEquals(17.320, p.getY(), 3);
        
    }
}
