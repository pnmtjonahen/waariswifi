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
import javax.enterprise.event.Event;
import javax.ws.rs.core.Response;
import nl.tjonahen.wificollector.business.WaarIsWifiEJB;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class WifiDeviceCollectorReceiverTest {

    @Mock
    private TriangulationFactory triangulationFactory;
    
    @Mock
    private Triangulation triangulation;
    
    @Mock
    private WaarIsWifiEJB waarIsWifiEJB;
    
    @Mock
    private Event<WifiDevicePayload> wsEvent; 
    
    @InjectMocks
    private WifiDeviceCollectorReceiver systemUnderTest;
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    /**
     * Test of process method, of class WifiDeviceCollectorReceiver.
     */
    @Test
    public void testProcess() {
        
        when(triangulationFactory.create(null, null)).thenReturn(triangulation);
        final ArrayList<WifiDevicePayload> deviceList = new ArrayList<>();
        deviceList.add(new WifiDevicePayload("bb:bb:bb:bb:bb"));
        when(triangulation.determineLocation("aa:aa:aa:aa:aa", "bb:bb:bb:bb:bb", "data"))
                .thenReturn(deviceList);
        when(triangulation.getExpiredDevices()).thenReturn(deviceList);
        
        Response result = systemUnderTest.process("aa:aa:aa:aa:aa", "bb:bb:bb:bb:bb", "data");
        
        
    }
    
}
