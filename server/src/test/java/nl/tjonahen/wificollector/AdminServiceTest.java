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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.core.Response;
import nl.tjonahen.wificollector.business.WaarIsWifiEJB;
import nl.tjonahen.wificollector.endpointdevice.EndpointMapping;
import nl.tjonahen.wificollector.model.EndpointEntity;
import nl.tjonahen.wificollector.model.MacNameResolverEntity;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class AdminServiceTest {

    @Mock
    private WaarIsWifiEJB waarIsWifiEJB;

    @Mock
    private EndpointMapping endpointMapping;

    @InjectMocks
    private AdminService systemUnderTest;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of processGet method, of class AdminService.
     */
    @Test
    public void testProcessGet() {
        when(waarIsWifiEJB.getEndpointMapping()).thenReturn(endpointMapping);
        when(endpointMapping.get("P1")).thenReturn(newEndpointEntity("P1", "", 0, 0));
        when(endpointMapping.get("P2")).thenReturn(newEndpointEntity("P2", "", 10, 0));
        when(endpointMapping.get("P3")).thenReturn(newEndpointEntity("P3", "", 0, 10));

        Response response = systemUnderTest.processGet("P1");
        assertNotNull(response);
        assertTrue(response.hasEntity());
        response = systemUnderTest.processGet("P2");
        assertNotNull(response);
        assertTrue(response.hasEntity());
        response = systemUnderTest.processGet("P3");
        assertNotNull(response);
        assertTrue(response.hasEntity());
        response = systemUnderTest.processGet("P4");
        assertNotNull(response);
        assertFalse(response.hasEntity());

    }

    /**
     * Test of processGetRoot method, of class AdminService.
     */
    @Test
    public void testProcessGetRoot() {
        when(waarIsWifiEJB.getEndpointMapping()).thenReturn(endpointMapping);
        final ArrayList<EndpointEntity> arrayList = new ArrayList<EndpointEntity>();
        arrayList.add(newEndpointEntity("P1", "", 0, 0));
        arrayList.add(newEndpointEntity("P2", "", 10, 0));
        arrayList.add(newEndpointEntity("P3", "", 0, 10));
        when(endpointMapping.getEndpoints()).thenReturn(arrayList);
        final Collection<EndpointEntity> result = systemUnderTest.processGetRoot();

        assertNotNull(result);
        assertEquals(3, result.size());

    }

    /**
     * Test of processPost method, of class AdminService.
     */
    @Test
    public void testProcessPost() {
        final List<EndpointEntity> input = new ArrayList<>();
        input.add(new EndpointEntity());

        Response response = systemUnderTest.processPost(input);
        assertNotNull(response);
        assertFalse(response.hasEntity());
        assertEquals(200, response.getStatus());
    }

    /**
     * Test of getMacNameResolvers method, of class AdminService.
     */
    @Test
    public void testGetMacNameResolvers() {

        when(waarIsWifiEJB.getAllMacNameResolvers()).thenReturn(new ArrayList<MacNameResolverEntity>());
        assertNotNull(systemUnderTest.getMacNameResolvers());

    }

    /**
     * Test of putMacNameResolvers method, of class AdminService.
     */
    @Test
    public void testPutMacNameResolversUpdate() {
        final ArrayList<MacNameResolverEntity> macNameResolvers = new ArrayList<MacNameResolverEntity>();
        final MacNameResolverEntity macNameResolverEntity = new MacNameResolverEntity();
        macNameResolverEntity.setMac("aa:aa:aa");
        macNameResolverEntity.setName("aa device");
        macNameResolvers.add(macNameResolverEntity);
        Response response = systemUnderTest.putMacNameResolvers(macNameResolvers);
        assertNotNull(response);
        assertFalse(response.hasEntity());
        assertEquals(200, response.getStatus());
        
        verify(waarIsWifiEJB).update(macNameResolverEntity);
        
    }
    /**
     * Test of putMacNameResolvers method, of class AdminService.
     */
    @Test
    public void testPutMacNameResolversDelete() {
        final ArrayList<MacNameResolverEntity> macNameResolvers = new ArrayList<>();
        final MacNameResolverEntity macNameResolverEntity = new MacNameResolverEntity();
        macNameResolverEntity.setMac("aa:aa:aa");
        macNameResolverEntity.setName("aa device");
        final ArrayList<MacNameResolverEntity> currentResolvers = new ArrayList<>();
        currentResolvers.add(macNameResolverEntity);
        when(waarIsWifiEJB.getAllMacNameResolvers()).thenReturn(currentResolvers);
        Response response = systemUnderTest.putMacNameResolvers(macNameResolvers);
        assertNotNull(response);
        assertFalse(response.hasEntity());
        assertEquals(200, response.getStatus());
        
        verify(waarIsWifiEJB).delete(macNameResolverEntity);
        
    }

    private EndpointEntity newEndpointEntity(String name, String mac, int x, int y) {
        final EndpointEntity ee = new EndpointEntity();
        ee.setName(name);
        ee.setMac(mac);
        ee.setX(x);
        ee.setY(y);
        return ee;
    }

}
