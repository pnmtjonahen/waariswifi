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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@ApplicationScoped
@Path("/data")
public class WifiDeviceCollectorReceiver {
    @Inject
    private Event<WifiDevicePayload> wsEvent; 
    
    @Inject 
    private Triangulation triangulation;

    
    @POST
    @Path("/{endpointmac}/{devicemac}/")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response process(
            @PathParam(value = "endpointmac") final String endpointMac, 
            @PathParam(value = "devicemac") final String deviceMac, 
            final String data) 
    {

        processData(endpointMac, deviceMac, data);
        // TODO: remove when 3 point are available
        if ("00:16:0a:26:a7:06".equals(endpointMac)) {
            processData("ff:ff:ff:ff:ff:ff", deviceMac, data);
        }
        return Response.ok().build();
    }

    private void processData(final String endpointMac, final String deviceMac, final String data) {
        final WifiDevicePayload[] devicePayload = triangulation.determineLocation(endpointMac, deviceMac, data);
        
        if (devicePayload != null) {
            for (WifiDevicePayload p : devicePayload) {
                wsEvent.fire(p);
            }
        }
    }
    

    
    @GET
    @Path("/{endpointmac}/")
    public String processGet(@PathParam(value = "endpointmac") final String endpointMac) {
        wsEvent.fire(newWifiDevicePayload("P1", 0, 0));
        wsEvent.fire(newWifiDevicePayload("P2", 0, 100));
        wsEvent.fire(newWifiDevicePayload("P3", 100, 0));
        wsEvent.fire(newWifiDevicePayload("P4", -100, 0));
        wsEvent.fire(newWifiDevicePayload("P5", 0, -100));
        
        return "done....";
    }
    
    private WifiDevicePayload newWifiDevicePayload(String name, double x, double y) {
        return new WifiDevicePayload(true, name, x, y, "home", 5);
    }
}
