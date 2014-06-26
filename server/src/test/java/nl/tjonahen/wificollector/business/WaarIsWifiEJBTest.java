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
import nl.tjonahen.wificollector.model.EndpointEntity;
import nl.tjonahen.wificollector.model.MacNameResolverEntity;
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
    }

    /**
     * Test of getEndpointMapping method, of class WaarIsWifiEJB.
     */
    @Test
    public void testGetEndpointMapping() {
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
