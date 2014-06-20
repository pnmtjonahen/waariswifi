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
import nl.tjonahen.wificollector.devicenameing.MacNameResolver;
import nl.tjonahen.wificollector.endpointdevice.EndpointMapping;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@ApplicationScoped
public class TriangulationFactory {
    
    /**
     * 
     * @param endpointMapping -
     * @param macNameResolver -
     * @return -
     */
    public Triangulation create(final EndpointMapping endpointMapping, 
                                final MacNameResolver macNameResolver) 
    {
        return Triangulation.getInstance(endpointMapping, macNameResolver);
    }
}
