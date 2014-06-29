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
package nl.tjonahen.wificollector;

import nl.tjonahen.wificollector.endpointdevice.EndpointMapping;
import nl.tjonahen.wificollector.model.EndpointEntity;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class EndpointMapppingTest {

    /**
     * test mapping 
     */
    @Test
    public void testMapping() {
        final EndpointMapping endpointMapping = new EndpointMapping();
        final EndpointEntity endpointEntity = new EndpointEntity();
        endpointEntity.setMac("P1");
        endpointEntity.setName("P1.name");
        endpointEntity.setX(0);
        endpointEntity.setY(0);
        endpointMapping.set(endpointEntity);

        assertEquals(0, endpointMapping.get("P1").getY(), 2);
        assertEquals(0, endpointMapping.get("P1").getX(), 2);

    }
    /**
     * test mapping 
     */
    @Test
    public void testMappingUnknonw() {
        final EndpointMapping endpointMapping = new EndpointMapping();

        assertNull(endpointMapping.get("P1"));

    }
}
