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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class EndpointDistanceMappingTest {
    

    /**
     * Test 
     */
    @Test
    public void test() {
        final EndpointDistanceMapping systemUnderTest = new EndpointDistanceMapping();
        
        systemUnderTest.put("1", new Distance(0));
        systemUnderTest.put("2", new Distance(0));
        systemUnderTest.put("3", new Distance(0));
        systemUnderTest.put("4", new Distance(0));
        
        assertTrue(systemUnderTest.containsKey("1"));
        assertFalse(systemUnderTest.containsKey("5"));
        assertEquals(4, systemUnderTest.maxNumberEndpoints());
        
        assertEquals("1", systemUnderTest.getEndpoint(0));
        assertEquals("2", systemUnderTest.getEndpoint(1));
        assertEquals("3", systemUnderTest.getEndpoint(2));
        assertEquals("4", systemUnderTest.getEndpoint(3));
        assertEquals("1", systemUnderTest.getEndpoint(4));
        assertEquals("2", systemUnderTest.getEndpoint(5));
        assertEquals("3", systemUnderTest.getEndpoint(6));
        assertEquals("4", systemUnderTest.getEndpoint(7));
        assertEquals("1", systemUnderTest.getEndpoint(8));
    }
    
}
