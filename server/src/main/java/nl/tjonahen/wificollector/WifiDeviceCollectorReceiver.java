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

import java.util.concurrent.ExecutorService;
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
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@ApplicationScoped
@Path("/data")
public class WifiDeviceCollectorReceiver {
    @Inject
    private Event<WifiDevicePayload> wsEvent; 

    private final ExecutorService executorService;

    public WifiDeviceCollectorReceiver() {
        this.executorService = java.util.concurrent.Executors.newCachedThreadPool();
    }

    @POST
    @Path("/{endpointmac}/{devicemac}/")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response process(
            @PathParam(value = "endpointmac") final String endpointMac, 
            @PathParam(value = "devicemac") final String deviceMac, 
            final String data) 
    {
        final String[] fields = data.split(":");
        if ("50:46:5d:16:8a:69".equals(deviceMac)) {
            wsEvent.fire(new WifiDevicePayload("MY NEXUS 7", 0, calculateDistance(Math.abs(Double.valueOf(fields[1])), Double.valueOf(fields[2]))));
        } else {
            wsEvent.fire(new WifiDevicePayload(deviceMac, 0, calculateDistance(Math.abs(Double.valueOf(fields[1])), Double.valueOf(fields[2]))));
        }
        return Response.ok().build();
    }
    
    public double calculateDistance(double levelInDb, double freqInMHz)    {
       double exp = (27.55 - (20 * Math.log10(freqInMHz)) + levelInDb) / 20.0;
       return Math.pow(10.0, exp);
    }    
}
