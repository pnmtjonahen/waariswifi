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
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import nl.tjonahen.wificollector.endpointdevice.EndpointDevice;
import nl.tjonahen.wificollector.endpointdevice.EndpointMapping;
import nl.tjonahen.wificollector.model.XMLEndpoint;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@ApplicationScoped
@Path("/admin")
public class AdminService {

    @Inject
    private EndpointMapping endpointMapping;

    @GET
    @Path("/{endpointmac}/")
    public Response processGet(@PathParam(value = "endpointmac") final String endpointMac) {
        if (endpointMapping.getP1().isEndpoint(endpointMac)) {
            return Response.ok(toEndpoint("P1", endpointMapping.getP1())).build();
        } else  if (endpointMapping.getP2().isEndpoint(endpointMac)) {
            return Response.ok(toEndpoint("P2", endpointMapping.getP2())).build();
        } else  if (endpointMapping.getP3().isEndpoint(endpointMac)) {
            return Response.ok(toEndpoint("P3", endpointMapping.getP3())).build();
        }

        return Response.noContent().build();
    }

    @GET
    @Path("/endpoints/")
    public List<XMLEndpoint> processGetRoot() {
        final List<XMLEndpoint> endpoints = new ArrayList<>();

        endpoints.add(toEndpoint("P1", endpointMapping.getP1())); 
        endpoints.add(toEndpoint("P2", endpointMapping.getP2())); 
        endpoints.add(toEndpoint("P3", endpointMapping.getP3()));

        return endpoints;
    }

    @PUT
    @Path("/endpoints/")
    public Response processPost(final List<XMLEndpoint> endpoints) {

        for (XMLEndpoint endpoint : endpoints) {
            if ("P1".equals(endpoint.getName())) {
                endpointMapping.setP1(new EndpointDevice(endpoint.getMac(), endpoint.getX(), endpoint.getY()));
            }
            if ("P2".equals(endpoint.getName())) {
                endpointMapping.setP2(new EndpointDevice(endpoint.getMac(), endpoint.getX(), endpoint.getY()));
            }
            if ("P3".equals(endpoint.getName())) {
                endpointMapping.setP3(new EndpointDevice(endpoint.getMac(), endpoint.getX(), endpoint.getY()));
            }
        }
        return Response.ok().build();
    }
    
    
    private XMLEndpoint toEndpoint(final String name, final EndpointDevice ep) {
        final XMLEndpoint xmlep = new XMLEndpoint();
        xmlep.setName(name);
        xmlep.setMac(ep.getMac());
        xmlep.setX(ep.getX());
        xmlep.setY(ep.getY());
        
        return xmlep;
    }

}
