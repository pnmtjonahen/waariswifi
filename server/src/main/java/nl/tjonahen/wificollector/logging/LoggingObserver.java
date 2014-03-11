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

package nl.tjonahen.wificollector.logging;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import nl.tjonahen.wificollector.WifiDevicePayload;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@ApplicationScoped
public class LoggingObserver {
        public void onMessage(@Observes WifiDevicePayload msg) {
//            System.out.println(String.format("{\"device\":\"%s\", \"x\":\"%f\", \"y\":\"%f\", \"endpoint\":\"%s\", \"distance\":\"%f\", \"triangulated\":%s, \"expired\":%s}", 
//                                        msg.getDeviceMac(), 
//                                        msg.getX(), 
//                                        msg.getY(), 
//                                        msg.getEndpointMac(),
//                                        msg.getDistance(),
//                                        msg.isTriangulated(),
//                                        msg.isExpired()));
        }
}
