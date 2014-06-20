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

package nl.tjonahen.waariswifi.collector;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class WifiCollectorClientTest {
    
    @Mock
    private Client client;
    
    @Mock
    private WebTarget webTarget;

    @Mock
    private Builder builder;
    
    @Mock
    private Response response;
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    /**
     * Test of process method, of class WifiCollectorClient.
     */
    @Test
    public void testProcess() {
        WifiCollectorClient instance = new WifiCollectorClient(client, "aa:aa:aa:aa:aa", "http://localhost");
        instance.process("");
        when(client.target("http://localhost/aa:aa:aa:aa:aa/84:51:81:a7:44:47")).thenReturn(webTarget);
        when(webTarget.request()).thenReturn(builder);
        when(builder.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(200);
        instance.process("1403116369.961803000\t84:51:81:a7:44:47\t-56\t2417");
        
    }
    
}
