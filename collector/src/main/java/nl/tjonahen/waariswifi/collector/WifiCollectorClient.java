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
package nl.tjonahen.waariswifi.collector;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class WifiCollectorClient {

    private final Client client;
    private final String baseUrl;
    private final String endpointmac;

    public WifiCollectorClient(final Client client, final String endpointmac, final String baseUrl) {
        this.client = client;
        this.baseUrl = baseUrl;
        this.endpointmac = endpointmac;
    }

    public void process(final String line) {

        final String[] fields = line.split("\t");
        if (fields.length != 4) {
            System.out.println("TShark reports " + line);
            return;
        }
        final String timestamp = fields[0];
        final String devicemac = fields[1];
        final String db = fields[2];
        final String frequency = fields[3];
        
        final Response response = client
                .target(baseUrl + "/" + endpointmac + "/" + devicemac)
                .request()
                .post(Entity.entity(timestamp + ":" + db + ":" + frequency, MediaType.TEXT_PLAIN));

        if (response.getStatus() != 200) {
            System.out.println("Recieved HTTP:" + response.getStatus());
        }
    }
}
