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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import nl.tjonahen.wificollector.business.WaarIsWifiEJB;
import nl.tjonahen.wificollector.endpointdevice.EndpointDevice;
import nl.tjonahen.wificollector.endpointdevice.EndpointMapping;
import nl.tjonahen.wificollector.model.EndpointEntity;
import nl.tjonahen.wificollector.model.MacNameResolverEntity;

/**
 * Admin services to administer the application.
 * 
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Path("/admin")
@Stateless
public class AdminService {

   
    @EJB
    private WaarIsWifiEJB waarIsWifiEJB;
    
    /**
     *  
     * @param endpointMac -
     * @return -
     */
    @GET
    @Path("/{endpointmac}/")
    public Response processGet(@PathParam(value = "endpointmac") final String endpointMac) {
        final EndpointMapping endpointMapping = waarIsWifiEJB.getEndpointMapping();
        
        if (endpointMapping.getP1().isEndpoint(endpointMac)) {
            return Response.ok(toEndpoint("P1", endpointMapping.getP1())).build();
        } else  if (endpointMapping.getP2().isEndpoint(endpointMac)) {
            return Response.ok(toEndpoint("P2", endpointMapping.getP2())).build();
        } else  if (endpointMapping.getP3().isEndpoint(endpointMac)) {
            return Response.ok(toEndpoint("P3", endpointMapping.getP3())).build();
        }

        return Response.noContent().build();
    }

    /**
     * 
     * @return -
     */
    @GET
    @Path("/endpoints/")
    public List<EndpointEntity> processGetRoot() {
        final EndpointMapping endpointMapping = waarIsWifiEJB.getEndpointMapping();
        final List<EndpointEntity> endpoints = new ArrayList<>();

        endpoints.add(toEndpoint("P1", endpointMapping.getP1())); 
        endpoints.add(toEndpoint("P2", endpointMapping.getP2())); 
        endpoints.add(toEndpoint("P3", endpointMapping.getP3()));

        return endpoints;
    }

    /**
     * 
     * @param endpoints -
     * @return -
     */
    @PUT
    @Path("/endpoints/")
    public Response processPost(final List<EndpointEntity> endpoints) {

        for (EndpointEntity endpoint : endpoints) {
            waarIsWifiEJB.update(endpoint);
        }
        return Response.ok().build();
    }
    
    
    private EndpointEntity toEndpoint(final String name, final EndpointDevice ep) {
        final EndpointEntity xmlep = new EndpointEntity();
        xmlep.setName(name);
        xmlep.setMac(ep.getMac());
        xmlep.setX(ep.getX());
        xmlep.setY(ep.getY());
        
        return xmlep;
    }
    
    /**
     * 
     * @return -
     */
    @GET
    @Path("/macnameresolver/")
    public List<MacNameResolverEntity> getMacNameResolvers() {
        return waarIsWifiEJB.getAllMacNameResolvers();
    }
    
    /**
     * 
     * @param list -
     * @return -
     */
    @PUT
    @Path("/macnameresolver/")
    public Response putMacNameResolvers(final List<MacNameResolverEntity> list) {
        for (MacNameResolverEntity entity : list) {
            waarIsWifiEJB.update(entity);
        }
        final List<MacNameResolverEntity> current = waarIsWifiEJB.getAllMacNameResolvers();
        for (MacNameResolverEntity entity: current) {
            if (!list.contains(entity)) {
                waarIsWifiEJB.delete(entity);
            }
        }
        return Response.ok().build();
    }

}
