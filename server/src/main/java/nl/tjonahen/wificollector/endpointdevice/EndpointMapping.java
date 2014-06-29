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
package nl.tjonahen.wificollector.endpointdevice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import nl.tjonahen.wificollector.model.EndpointEntity;

/**
 *
 * Fixed locations and mac adresses are loaded from a property file
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class EndpointMapping {
    // fixed locations
    private final Map<String, EndpointEntity> macEndpointMap = new HashMap<>();


    /**
     * default.
     */
    public EndpointMapping() {
    }

    /**
     * 
     * @param macadres -
     * @return - 
     */
    public EndpointEntity get(final String macadres) {
        return macEndpointMap.get(macadres);
    }

    /**
     * 
     * @param epP - 
     */
    public void set(final EndpointEntity epP) {
        macEndpointMap.put(epP.getMac(), epP);
    }

    public Collection<EndpointEntity> getEndpoints() {
        return macEndpointMap.values();
    }
}
