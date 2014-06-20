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

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class FixedSizeListTest {
    

    /**
     * Test of add method, of class FixedSizeList.
     */
    @Test
    public void testAdd_GenericType() {
        FixedSizeList<String> instance = new FixedSizeList<>(1);
        assertTrue(instance.add("one"));
        assertTrue(instance.add("two"));
        assertTrue(instance.add("three"));
        
        assertEquals(1, instance.size());
        assertEquals(1, instance.getMaxSize());
        assertEquals("three", instance.get(0));
    }

    /**
     * Test of add method, of class FixedSizeList.
     */
    @Test
    public void testAdd_int_GenericType() {
        FixedSizeList<String> instance = new FixedSizeList<>(3);
        assertTrue(instance.add("one"));
        assertTrue(instance.add("two"));
        assertTrue(instance.add("three"));

        instance.add(1, "four");
        assertEquals(3, instance.size());
        assertEquals(3, instance.getMaxSize());
        assertEquals("four", instance.get(0));
        assertEquals("two", instance.get(1));
        assertEquals("three", instance.get(2));
    }

    /**
     * Test of addAll method, of class FixedSizeList.
     */
    @Test
    public void testAddAll_Collection() {
        FixedSizeList<String> instance = new FixedSizeList<>(2);
        
        List<String> collection = new ArrayList<>();
        collection.add("one");
        collection.add("two");
        collection.add("three");
        
        instance.addAll(collection);
        
        assertEquals(2, instance.size());
        assertEquals("two", instance.get(0));
        assertEquals("three", instance.get(1));
        
    }

    /**
     * Test of addAll method, of class FixedSizeList.
     */
    @Test
    public void testAddAll_int_Collection() {
        FixedSizeList<String> instance = new FixedSizeList<>(4);
        assertTrue(instance.add("one"));
        assertTrue(instance.add("two"));
        assertTrue(instance.add("three"));
        List<String> collection = new ArrayList<>();
        collection.add("A");
        collection.add("B");
        collection.add("C");
        instance.addAll(2, collection);
        
        assertEquals(4, instance.size());
        assertEquals("A", instance.get(0));
        assertEquals("B", instance.get(1));
        assertEquals("C", instance.get(2));
        assertEquals("three", instance.get(3));
        
    }

    /**
     * Test of trimToSize method, of class FixedSizeList.
     */
    @Test
    public void testTrimToSize() {
        FixedSizeList<String> instance = new FixedSizeList<>(6);
        assertTrue(instance.add("one"));
        assertTrue(instance.add("two"));
        assertTrue(instance.add("three"));
        
        assertEquals(6, instance.getMaxSize());
        assertEquals(3, instance.size());
        instance.trimToSize();
        assertEquals(3, instance.getMaxSize());
        
    }
    
}
