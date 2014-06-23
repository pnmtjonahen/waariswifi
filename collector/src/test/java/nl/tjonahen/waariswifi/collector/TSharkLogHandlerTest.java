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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class TSharkLogHandlerTest {
    

    @Mock
    private WifiCollectorClient client;
    
    @Mock
    private BufferedReader bufferedReader;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * Test of run method, of class TSharkLogHandler.
     */
    @Test
    public void testRun() {
        InputStream input = getClass().getResourceAsStream("/testinput.txt");
        TSharkLogHandler instance = new TSharkLogHandler(client);
        instance.run(new BufferedReader(new InputStreamReader(input)));
    }
    
    @Test
    public void testTimeOut() throws IOException {
        TSharkLogHandler instance = new TSharkLogHandler(client);
        when(bufferedReader.readLine()).thenReturn("1403116369.961803000/t84:51:81:a7:44:47/t-56/t2417", null, "end");
        
        instance.run(bufferedReader);
    }
    
    @Test
    public void testIOException() throws IOException {
        TSharkLogHandler instance = new TSharkLogHandler(client);

        when(bufferedReader.readLine()).thenThrow(IOException.class).thenReturn("end");
        instance.run(bufferedReader);
    }
    @Test
    public void testInterruptedException() throws IOException {
        TSharkLogHandler instance = new TSharkLogHandler(client);

        when(bufferedReader.readLine()).thenThrow(InterruptedException.class).thenReturn("end");
        instance.run(bufferedReader);
    }
    
}
