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

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@ApplicationScoped
@ServerEndpoint("/wifiendpoint")
public class WifiEndpoint {
    private static final Logger LOGGER = Logger.getLogger(WifiEndpoint.class.getName());
    

    private static final Set<Session> sessions
            = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(final Session session) {
        LOGGER.info("Open...");
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(final String message, final Session client) {
        LOGGER.log(Level.FINEST, "onMessage({0})", message);
    }

    @OnError
    public void onError(Throwable t) {
        LOGGER.log(Level.FINEST, "onError({0})", t.getMessage());
    }

    @OnClose
    public void onClose(final Session session) {
        LOGGER.info("Close...");
        sessions.remove(session);
    }

    public void onJMSMessage(@Observes WifiDevicePayload msg) {
        for (Session s : sessions) {
            if (s.isOpen()) {
                try {
                    s.getBasicRemote().sendText(
                                String.format("{\"device\":\"%s\", \"x\":\"%f\", \"y\":\"%f\", \"endpoint\":\"%s\", \"distance\":\"%f\", \"triangulated\":%s, \"expired\":%s}", 
                                        msg.getDeviceMac(), 
                                        msg.getX(), 
                                        msg.getY(), 
                                        msg.getEndpointMac(),
                                        msg.getDistance(),
                                        msg.isTriangulated(),
                                        msg.isExpired()));
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, "Sending message {0}", ex.getMessage());
                }
            }
        }
    }

}
