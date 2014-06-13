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

package nl.tjonahen.wificollector.calculator;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class PhilippeCalculator implements Calculator {
    public Point recalculate(final double x0,
        final double y0, 
        final double r0,
        final double x1,
        final double y1,
        final double r1,
        final double x2,
        final double y2,
        final double r2) {
    /*
                    A
                    /\
                 c /  \ b
                  /    \
                 B------C
                     a    
    
    P1 = B
    P2 = C
    P3 = A
    
    */        
        double a = x1;
        double b = r1;
        double c = r0;
        
        double A = Math.acos((Math.pow(a, 2) - Math.pow(b, 2) - Math.pow(c, 2))/-(2*b*c)) * 180/Math.PI;
        double B = Math.acos((Math.pow(b, 2) - Math.pow(a, 2) - Math.pow(c, 2))/-(2*a*c)) * 180/Math.PI;
//        double C = 180.0 - A - B;
        
        double y = c * Math.sin(Math.toRadians( B ));
        double x = Math.sqrt(Math.pow(c, 2) - Math.pow(y, 2));
        
        return new Point(x, y);
    }

}
