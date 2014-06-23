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
import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class AppTest {
    
    @Mock
    private TSharkLogHandler tSharkLogHandler;
    
    @Mock
    private BufferedReader bufferedReader;    

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    /**
     * Test of main method, of class App.
     */
    @Test
    public void testMain() throws Exception {
        String[] args = {};
        App.main(args);
    }

    @Test 
    public void testApp() throws FileNotFoundException {
        App app = new App(tSharkLogHandler, bufferedReader);
        app.run();
        
        
    }
}
