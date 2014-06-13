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

package nl.tjonahen.wificollector.device;

import nl.tjonahen.wificollector.calculator.SinanCalculator;
import nl.tjonahen.wificollector.endpointdevice.EndpointMapping;

/**
 * Device factory. Determines the calculator to use
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public final class DeviceFactory {
    private DeviceFactory() {
        
    }
    
    /**
     * 
     * @param name -
     * @param endpointMapping -
     * @return -
     */
    public static Device create(final String name, final EndpointMapping endpointMapping) {
        return new Device(name, endpointMapping, new SinanCalculator());
    }
}
