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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import javax.ws.rs.client.ClientBuilder;

/**
 *
 * args[0] local macadres args[1] baseurl args[3] optional logfile to monitor if not given use stdin
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class App {

    /**
     *
     * @param args -
     * @throws FileNotFoundException -
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 0) {
            System.out.println("Usage: localMacadress serverBaseUrl [input]");
            System.out.println("If no input file is given app will use stdin");
        } else {
            final TSharkLogHandler tSharkLogHandler = new TSharkLogHandler(
                    new WifiCollectorClient(ClientBuilder.newClient(), args[0], args[1]));

            final App app = new App(tSharkLogHandler, 
                                args.length >= MIN_ARGS 
                                            ? new BufferedReader(new InputStreamReader(new FileInputStream(args[2]))) 
                                            : new BufferedReader(new InputStreamReader(System.in)));
            app.run();
        }
    }

    private final TSharkLogHandler tSharkLogHandler;
    private final BufferedReader bufferedReader;

    /**
     * Constructor.
     * 
     * @param tSharkLogHandler - tshark log handler
     * @param bufferedReader - reader to read log statements from
     */
    public App(final TSharkLogHandler tSharkLogHandler, final BufferedReader bufferedReader) {
        this.tSharkLogHandler = tSharkLogHandler;
        this.bufferedReader = bufferedReader;
    }

    /**
     * Starts the loghandler
     * @throws FileNotFoundException - 
     */
    public void run() throws FileNotFoundException {
        tSharkLogHandler.run(bufferedReader);
    }
    private static final int MIN_ARGS = 3;
}
