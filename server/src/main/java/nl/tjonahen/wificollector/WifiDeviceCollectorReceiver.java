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
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Main entry pint for receiving enpoint data. This is called by the collector application to process captured data.
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@ApplicationScoped
@Path("/data")
public class WifiDeviceCollectorReceiver {
    @Inject
    private Event<WifiDevicePayload> wsEvent; 
    
    private Triangulation triangulation;


    /**
     * Handles the processing of the captured data.
     * @param endpointMac capturing endpoint
     * @param deviceMac device
     * @param data data
     * @return HTTP 200
     */
    @POST
    @Path("/{endpointmac}/{devicemac}/")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response process(
            @PathParam(value = "endpointmac") final String endpointMac, 
            @PathParam(value = "devicemac") final String deviceMac, 
            final String data) 
    {

        for (WifiDevicePayload p : triangulation.determineLocation(endpointMac, deviceMac, data)) {
            wsEvent.fire(p);
        }
        for (WifiDevicePayload p : triangulation.getExpiredDevices()) {
            wsEvent.fire(p);
        }
        return Response.ok().build();
    }

}
