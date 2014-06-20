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

import java.io.IOException;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class WifiEndpointTest {

    @Mock
    private Session session;
    
    @Mock
    private Basic basicRemote;

            
    /**
     * Init
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }        
    /**
     * Test of onOpen method, of class WifiEndpoint.
     */
    @Test
    public void testOnOpen() {
        WifiEndpoint instance = new WifiEndpoint();
        instance.onOpen(session);
    }

    /**
     * Test of onMessage method, of class WifiEndpoint.
     */
    @Test
    public void testOnMessage() {
        WifiEndpoint instance = new WifiEndpoint();
        instance.onMessage("", session);
    }

    /**
     * Test of onError method, of class WifiEndpoint.
     */
    @Test
    public void testOnError() {
        WifiEndpoint instance = new WifiEndpoint();
        instance.onError(new NullPointerException());
    }

    /**
     * Test of onClose method, of class WifiEndpoint.
     */
    @Test
    public void testOnClose() {
        WifiEndpoint instance = new WifiEndpoint();
        instance.onClose(session);
    }

    /**
     * Test of onJMSMessage method, of class WifiEndpoint.
     */
    @Test
    public void testOnJMSMessageClosed() {
        WifiDevicePayload msg = new WifiDevicePayload("aa:aa:aa:aa:aa");
        WifiEndpoint instance = new WifiEndpoint();
        
        
        instance.onOpen(session);
        when(session.isOpen()).thenReturn(Boolean.FALSE);

        instance.onJMSMessage(msg);
    }
    /**
     * Test of onJMSMessage method, of class WifiEndpoint.
     */
    @Test
    public void testOnJMSMessageOpen() {
        WifiDevicePayload msg = new WifiDevicePayload("aa:aa:aa:aa:aa");
        WifiEndpoint instance = new WifiEndpoint();
        
        
        instance.onOpen(session);
        
        when(session.isOpen()).thenReturn(Boolean.TRUE);
        when(session.getBasicRemote()).thenReturn(basicRemote);

        instance.onJMSMessage(msg);

    }
    /**
     * Test of onJMSMessage method, of class WifiEndpoint.
     */
    @Test
    public void testOnJMSMessageIOException() throws IOException {
        WifiDevicePayload msg = new WifiDevicePayload("aa:aa:aa:aa:aa");
        WifiEndpoint instance = new WifiEndpoint();
        
        
        instance.onOpen(session);
        when(session.isOpen()).thenReturn(Boolean.TRUE);
        when(session.getBasicRemote()).thenReturn(basicRemote);
        doThrow(IOException.class).when(basicRemote).sendText(any(String.class));
        
        instance.onJMSMessage(msg);
    }
    
}
