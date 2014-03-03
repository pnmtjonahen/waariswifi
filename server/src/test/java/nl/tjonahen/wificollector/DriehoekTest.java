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

import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class DriehoekTest {
    
    /*
                    A
                    /\
                 c /  \ b
                  /    \
                 B------C
                     a    
    */

    @Test
    public void testCosinusRegel() {
        int a = 8;
        int b = 8;
        int c = 8;
        
        double A = Math.acos((Math.pow(a, 2) - Math.pow(b, 2) - Math.pow(c, 2))/-(2*b*c)) * 180/Math.PI;
        double B = Math.acos((Math.pow(b, 2) - Math.pow(a, 2) - Math.pow(c, 2))/-(2*a*c)) * 180/Math.PI;
        double C = 180.0 - A - B;
        
        System.out.println(String.format("Test 1 %.2f %.2f %.2f", A, B, C));
    }
    @Test
    public void testCosinusRegel2() {
        int a = 80;
        int b = 41;
        int c = 40;
        
        double A = Math.acos((Math.pow(a, 2) - Math.pow(b, 2) - Math.pow(c, 2))/-(2*b*c)) * 180/Math.PI;
        double B = Math.acos((Math.pow(b, 2) - Math.pow(a, 2) - Math.pow(c, 2))/-(2*a*c)) * 180/Math.PI;
        double C = 180.0 - A - B;
        
        System.out.println(String.format("Test 2 %.2f %.2f %.2f", A, B, C));
    }
    @Test
    public void testCosinusRegel3() {
        double a = 10;
        double b = 6;
        double c = 6.5;
        
//        double A = Math.acos((Math.pow(a, 2) - Math.pow(b, 2) - Math.pow(c, 2))/-(2*b*c)) * 180/Math.PI;
        double B = Math.acos((Math.pow(b, 2) - Math.pow(a, 2) - Math.pow(c, 2))/-(2*a*c)) * 180/Math.PI;
        double A = 90.0 - B;
        
        System.out.println(String.format("Test 3 %.2f %.2f %.2f", A, B, 90.0));
    }
}
