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

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nl.tjonahen.wificollector.business.WaarIsWifiEJB;

/**
 * Main entry point for receiving endpoint data. 
 * This REST service is called by the collector application to process captured data.
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@ApplicationScoped
@Path("/data")
public class WifiDeviceCollectorReceiver {
    @Inject
    private Event<WifiDevicePayload> wsEvent; 
    
    @EJB
    private WaarIsWifiEJB waarIsWifiEJB;
    
    @Inject
    private TriangulationFactory triangulationFactory;


    /**
     * Handles the processing of the captured data.
     * @param wifiData data
     * @return HTTP 200
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response process(final WifiData wifiData) {
        final Triangulation triangulation = triangulationFactory.create(waarIsWifiEJB.getEndpointMapping(), 
                                                                            waarIsWifiEJB.getMacNameResolver());
        for (WifiDevicePayload p : triangulation.determineLocation(wifiData)) {
            wsEvent.fire(p);
        }
        for (WifiDevicePayload p : triangulation.getExpiredDevices()) {
            wsEvent.fire(p);
        }
        return Response.ok().build();
    }
    
}
