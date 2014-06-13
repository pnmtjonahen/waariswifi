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

package nl.tjonahen.wificollector.devicenameing;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import nl.tjonahen.wificollector.model.MacNameResolverEntity;

/**
 * mac to name resolver. Used to resolve known macadresses into a userfriendly name.
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class MacNameResolver {

    private final Map<String, String> resolve;

    /**
     * 
     * @param names list of names 
     */
    public MacNameResolver(final List<MacNameResolverEntity> names) {
        this.resolve = new TreeMap<>();
        for (MacNameResolverEntity entity : names) {
            resolve.put(entity.getMac(), entity.getName());
        }
    }
    
    /**
     * 
     * @param deviceMac -
     * @return -
     */
    public String resolve(final String deviceMac) {
        final String key = deviceMac.toLowerCase();
        return resolve.getOrDefault(key, deviceMac);
    }
    
}
