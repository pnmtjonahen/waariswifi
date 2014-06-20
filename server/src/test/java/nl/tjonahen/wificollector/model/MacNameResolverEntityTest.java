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

import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class MacNameResolverEntityTest {
    

    /**
     * Test of get/set method, of class MacNameResolverEntity.
     */
    @Test
    public void testGetMac() {
        MacNameResolverEntity instance = new MacNameResolverEntity();
        
        instance.setMac("aa:bb:cc");
        assertEquals("aa:bb:cc", instance.getMac());
        instance.setName("P1");
        assertEquals("P1", instance.getName());
    }


    /**
     * Test of hashCode method, of class MacNameResolverEntity.
     */
    @Test
    public void testHashCode() {
        MacNameResolverEntity instance = new MacNameResolverEntity();
        instance.setMac("aa:bb:cc");
        
        assertEquals("aa:bb:cc".hashCode(), instance.hashCode());
        
    }

    /**
     * Test of equals method, of class MacNameResolverEntity.
     */
    @Test
    public void testEquals() {
        assertFalse(new MacNameResolverEntity().equals(null));
        assertFalse(new MacNameResolverEntity().equals(""));
        final MacNameResolverEntity macNameResolverEntity = new MacNameResolverEntity();
        macNameResolverEntity.setMac("aa:bb:cc");
        assertFalse(macNameResolverEntity.equals(new MacNameResolverEntity()));
        assertTrue(macNameResolverEntity.equals(macNameResolverEntity));
        
    }
    
}
