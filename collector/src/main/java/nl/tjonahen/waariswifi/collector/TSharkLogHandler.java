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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class TSharkLogHandler {

    private final WifiCollectorClient wifiCollectorClient;

    /**
     * 
     * @param wifiCollectorClient -
     */
    public TSharkLogHandler(final WifiCollectorClient wifiCollectorClient) {
        this.wifiCollectorClient = wifiCollectorClient;
    }

    /**
     * 
     * @param input - 
     */
    public void run(final InputStream input) {
        final BufferedReader bf = new BufferedReader(new InputStreamReader(input));
        while (true) {
            try {
                String line = bf.readLine();
                if (line != null) {
                    wifiCollectorClient.process(line);
                } else {
                    Thread.sleep(TIME_OUT);
                }
            } catch (IOException | InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

    }
    private static final int TIME_OUT = 10;

}
