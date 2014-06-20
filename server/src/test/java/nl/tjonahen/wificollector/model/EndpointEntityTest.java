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

package nl.tjonahen.wificollector.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class EndpointEntityTest {
    

    /**
     * Test of get/set method, of class EndpointEntity.
     */
    @Test
    public void testGetSet() {
        EndpointEntity instance = new EndpointEntity();
        instance.setMac("mac");
        assertEquals("mac", instance.getMac());
        
        instance.setName("name");
        assertEquals("name", instance.getName());
        
        instance.setX(10.0);
        assertEquals(10.0, instance.getX(), 0.0);

        instance.setY(20.0);
        assertEquals(20.0, instance.getY(), 0.0);
        
        
    }
    
}
