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
import javax.ws.rs.core.Response;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class WifiCollectorClient {

    private static final int HTTP_RESPONSE_OK = 200;

    private static final int FREQUENCY_IDX = 3;
    private static final int DB_IDX = 2;
    private static final int DEVICEMAC_IDX = 1;
    private static final int TIMESTAMP_IDX = 0;
    private static final int NUMBER_OF_FIELDS = 4;


    private final Client client;
    private final String baseUrl;
    private final String endpointmac;

    /**
     *
     * @param client -
     * @param endpointmac -
     * @param baseUrl -
     */
    public WifiCollectorClient(final Client client, final String endpointmac, final String baseUrl) {
        this.client = client;
        this.baseUrl = baseUrl;
        this.endpointmac = endpointmac;
    }

    /**
     *
     * @param line -
     */
    public void process(final String line) {

        final String[] fields = line.split("\t");
        if (fields.length != NUMBER_OF_FIELDS) {
            System.out.println("TShark reports " + line);
            return;
        }
        final String timestamp = fields[TIMESTAMP_IDX];
        final String devicemac = fields[DEVICEMAC_IDX];
        final String db = fields[DB_IDX];
        final String frequency = fields[FREQUENCY_IDX];

        final Response response;

        response = sendJSON(devicemac, timestamp, db, frequency);
        if (response.getStatus() != HTTP_RESPONSE_OK) {
            System.out.println("Recieved HTTP:" + response.getStatus());
        }
    }

    private Response sendJSON(final String devicemac, final String timestamp, final String db, final String frequency) {
        WifiData data = new WifiData();
        data.setEndpointmac(endpointmac);
        data.setDevicemac(devicemac);
        data.setDb(db);
        data.setFreq(frequency);
        data.setTimestamp(timestamp);

        final Response response = client
                .target(baseUrl + "/" )
                .request()
                .post(Entity.xml(data));
        return response;
    }

}
