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

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@ApplicationScoped
public class MacNameResolver {

    private final Properties resolve;

    public MacNameResolver() {
        
        this.resolve = new Properties();
        try {
            this.resolve.load(this.getClass().getResourceAsStream("/macnameresolver.properties"));
        } catch (IOException ex) {
            Logger.getLogger(MacNameResolver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String resolve(final String deviceMac) {
        if (resolve.containsKey(deviceMac.toLowerCase())) {
            return resolve.getProperty(deviceMac.toLowerCase());
        }
        return deviceMac;
    }
    
}
