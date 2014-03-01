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

import java.util.Map;
import java.util.TreeMap;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@ApplicationScoped
public class MacNameResolver {

    private final Map<String, String> resolve;

    public MacNameResolver() {
        this.resolve = new TreeMap<>();
        resolve.put("78:92:9c:22:cd:6a","PC(Kay)");
        resolve.put("94:db:c9:b7:f9:a2","PC(Amber)");
        resolve.put("00:16:0a:26:a7:06","PC(Huiskamer)");
        resolve.put("f0:08:f1:8e:3e:95","Telefoon1(Mijn)");
        resolve.put("50:46:5d:16:8a:69","Tablet1(Mijn)");
        resolve.put("00:26:ab:0b:cd:91","Printer1");
        resolve.put("84:25:db:e4:a3:A0","Telefoon2");
        resolve.put("18:3d:a2:57:e3:50","PC4");
        resolve.put("d0:51:62:29:f3:84","Telefoon3");
        resolve.put("2c:cc:15:22:25:54","Telefoon4");
        resolve.put("00:0d:f0:58:93:2c","PC5");
        resolve.put("00:24:d6:72:e1:74","PC6");
        resolve.put("00:0c:f6:be:cb:c3", "Pi 1");
        
    }
    
    public String resolve(final String deviceMac) {
        if (resolve.containsKey(deviceMac.toLowerCase())) {
            return resolve.get(deviceMac.toLowerCase());
        }
        return deviceMac;
    }
    
}
