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
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class CalculatorTest {
    
    /**
     * Test recalculate 4 distances to a device.
     */
    @Test
    public void test() {
        Calculator calculator = new ThreeCircleIntersectionCalculator();
        Point p = new Point();
        p.add(calculator.recalculate(0, 0, 100, 
                                    200, 0, 100, 
                                    100, 100, 100));
        
        assertEquals(100.0, p.getX(), 0.2);
        assertEquals(0.0, p.getY(), 0.2);

        p.add(calculator.recalculate(200, 0, 100, 
                                    100, 100, 100,
                                    100, -100, 100));
        assertEquals(100.0, p.getX(), 0.2);
        assertEquals(0.0, p.getY(), 0.2);

        p.add(calculator.recalculate(100, 100, 100,
                                    100, -100, 100,
                                    0, 0, 100));
        assertEquals(100.0, p.getX(), 0.2);
        assertEquals(0.0, p.getY(), 0.2);

        p.add(calculator.recalculate(100, -100, 100,
                                    0, 0, 100,
                                    200, 0, 100));
        assertEquals(100.0, p.getX(), 0.2);
        assertEquals(0.0, p.getY(), 0.2);
    }
    private static final double DISTANCE = 100;
    @Test
    public void testOutside() {
        Calculator calculator = new SmartTechCalculator();
        Point p = calculator.recalculate(50.00, 90.00, DISTANCE, 
                                         150.00, 90.00, DISTANCE, 
                                         0.00, 0.00, DISTANCE); 
        
        assertEquals(50, p.getX(), 0.0);
        assertEquals(0, p.getY(), 0.0);
        
    }
}
