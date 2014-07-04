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
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class PointTest {

    /**
     * Test of getX method, of class Point.
     */
    @Test
    public void testGetXY() {
        Point p = new Point(10, 20);
        assertEquals(10.0, p.getX(), 0.0);
        assertEquals(20.0, p.getY(), 0.0);
    }


    /**
     * Test of isValid method, of class Point.
     */
    @Test
    public void testIsValid() {
        assertTrue(new Point(10, 20).isValid());
        assertFalse(new Point().isValid());
    }

    /**
     * Test of add method, of class Point.
     */
    @Test
    public void testAddNewPoint() {
        Point p = new Point();
        
        p.add(new Point());
        
        assertFalse(p.isValid());
        
        p.add(new Point(10, 20));
        assertTrue(p.isValid());
        assertEquals(10.0, p.getX(), 0.0);
        assertEquals(20.0, p.getY(), 0.0);

        p.add(new Point());
        assertTrue(p.isValid());
        assertEquals(10.0, p.getX(), 0.0);
        assertEquals(20.0, p.getY(), 0.0);

        p.add(new Point(20, 30));
        assertTrue(p.isValid());
        assertEquals(15.0, p.getX(), 0.0);
        assertEquals(25.0, p.getY(), 0.0);
    }
    
}
