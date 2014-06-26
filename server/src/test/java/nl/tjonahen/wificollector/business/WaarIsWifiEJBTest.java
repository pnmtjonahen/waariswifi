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
package nl.tjonahen.wificollector.business;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import nl.tjonahen.wificollector.endpointdevice.EndpointMapping;
import nl.tjonahen.wificollector.model.EndpointEntity;
import nl.tjonahen.wificollector.model.MacNameResolverEntity;
import static org.junit.Assert.assertEquals;
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
public class WaarIsWifiEJBTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery query;

    @InjectMocks
    private WaarIsWifiEJB systemUnderTest;

    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of getAll method, of class WaarIsWifiEJB.
     */
    @Test
    public void testGetAll() {
        when(entityManager.createNamedQuery("EndpointEntity.selectAll", EndpointEntity.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList());

        assertNotNull(systemUnderTest.getAll());
    }

    /**
     * Test of get method, of class WaarIsWifiEJB.
     */
    @Test
    public void testGet() {
        final EndpointEntity endpointEntity = new EndpointEntity();
        when(entityManager.find(EndpointEntity.class, "P1")).thenReturn(endpointEntity);
        final EndpointEntity result = systemUnderTest.get("P1");
        assertNotNull(result);
        assertTrue(result == endpointEntity);
    }

    /**
     * Test of update method, of class WaarIsWifiEJB.
     */
    @Test
    public void testUpdate_EndpointEntity() {
        final EndpointEntity endpointEntity = new EndpointEntity();
        endpointEntity.setMac("P1");
        endpointEntity.setName("P1");
        endpointEntity.setX(10);
        endpointEntity.setY(20);
        
        when(entityManager.find(EndpointEntity.class, "P1")).thenReturn(endpointEntity);
        final EndpointEntity updated = new EndpointEntity();
        updated.setMac("P2");
        updated.setName("P1");
        updated.setX(30);
        updated.setY(40);
        systemUnderTest.update(updated);
        
        assertEquals("P1", endpointEntity.getName());
        assertEquals("P2", endpointEntity.getMac());
        assertEquals(30, endpointEntity.getX(), 0.0);
        assertEquals(40, endpointEntity.getY(), 0.0);
        
        verify(entityManager).merge(endpointEntity);
    }
    /**
     * Test of update method, of class WaarIsWifiEJB.
     */
    @Test
    public void testUpdate_EndpointEntityNew() {
        
        when(entityManager.find(EndpointEntity.class, "P1")).thenReturn(null);
        final EndpointEntity updated = new EndpointEntity();
        updated.setMac("P1");
        updated.setName("P1");
        updated.setX(10);
        updated.setY(20);
        systemUnderTest.update(updated);
        
        verify(entityManager).persist(updated);
    }

    /**
     * Test of getMacNameResolverEntity method, of class WaarIsWifiEJB.
     */
    @Test
    public void testGetMacNameResolverEntity() {
        final MacNameResolverEntity macNameResolverEntity = new MacNameResolverEntity();
        when(entityManager.find(MacNameResolverEntity.class, "aa:aa:aa")).thenReturn(macNameResolverEntity);
        final MacNameResolverEntity result = systemUnderTest.getMacNameResolverEntity("aa:aa:aa");
        assertNotNull(result);
        assertTrue(result == macNameResolverEntity);
    }

    /**
     * Test of update method, of class WaarIsWifiEJB.
     */
    @Test
    public void testUpdate_MacNameResolverEntity() {
        final MacNameResolverEntity macNameResolverEntity = new MacNameResolverEntity();
        macNameResolverEntity.setMac("aa:aa:aa");
        macNameResolverEntity.setName("test");
        when(entityManager.find(MacNameResolverEntity.class, "aa:aa:aa")).thenReturn(macNameResolverEntity);
        final MacNameResolverEntity updated = new MacNameResolverEntity();
        updated.setMac("aa:aa:aa");
        updated.setName("dummy");
        
        systemUnderTest.update(updated);
        
        assertEquals("aa:aa:aa", macNameResolverEntity.getMac());
        assertEquals("dummy", macNameResolverEntity.getName());

        verify(entityManager).merge(macNameResolverEntity);
    }
    /**
     * Test of update method, of class WaarIsWifiEJB.
     */
    @Test
    public void testUpdate_MacNameResolverEntityNew() {
        when(entityManager.find(MacNameResolverEntity.class, "aa:aa:aa")).thenReturn(null);
        final MacNameResolverEntity updated = new MacNameResolverEntity();
        updated.setMac("aa:aa:aa");
        updated.setName("dummy");
        
        systemUnderTest.update(updated);
        

        verify(entityManager).persist(updated);
    }

    /**
     * Test of getEndpointMapping method, of class WaarIsWifiEJB.
     */
    @Test
    public void testGetEndpointMapping() {
        final EndpointEntity endpointEntity = new EndpointEntity();
        when(entityManager.find(EndpointEntity.class, "P1")).thenReturn(endpointEntity, null);
        when(entityManager.find(EndpointEntity.class, "P2")).thenReturn(endpointEntity, null);
        when(entityManager.find(EndpointEntity.class, "P3")).thenReturn(endpointEntity, null);

        EndpointMapping em = systemUnderTest.getEndpointMapping();
        assertNotNull(em);
        
        em = systemUnderTest.getEndpointMapping();
        assertEquals("P1.mac", em.getP1().getMac());
        assertEquals("P2.mac", em.getP2().getMac());
        assertEquals("P3.mac", em.getP3().getMac());
    }

    /**
     * Test of getAllMacNameResolvers method, of class WaarIsWifiEJB.
     */
    @Test
    public void testGetAllMacNameResolvers() {
        when(entityManager.createNamedQuery("MacNameResolverEntity.selectAll", 
                                            MacNameResolverEntity.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<MacNameResolverEntity>());
        
        assertNotNull(systemUnderTest.getAllMacNameResolvers());
    }

    /**
     * Test of delete method, of class WaarIsWifiEJB.
     */
    @Test
    public void testDelete() {
        final MacNameResolverEntity macNameResolverEntity = new MacNameResolverEntity();
        
        systemUnderTest.delete(macNameResolverEntity);
        
        verify(entityManager).remove(macNameResolverEntity);
    }

    /**
     * Test of getMacNameResolver method, of class WaarIsWifiEJB.
     */
    @Test
    public void testGetMacNameResolver() {
        when(entityManager.createNamedQuery("MacNameResolverEntity.selectAll", 
                                            MacNameResolverEntity.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<MacNameResolverEntity>());
        
        assertNotNull(systemUnderTest.getMacNameResolver());
    }

}
