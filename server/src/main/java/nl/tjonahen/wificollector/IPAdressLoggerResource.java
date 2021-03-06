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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Path("ipadresslogger")
@RequestScoped
public class IPAdressLoggerResource {

    /**
     * Retrieves representation of an instance of nl.tjonahen.wificollector.IPAdressLoggerResource
     * @param  ip -
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{ip}")
    @Consumes(MediaType.TEXT_PLAIN)
    public String get(@PathParam("ip") final String ip) {
        System.out.println(ip);
        return "OK";
    }

}
