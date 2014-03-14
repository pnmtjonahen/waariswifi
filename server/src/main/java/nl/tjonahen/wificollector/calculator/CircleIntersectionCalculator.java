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
public class CircleIntersectionCalculator  implements Calculator  {
    
    /**
     * http://mathworld.wolfram.com/Circle-CircleIntersection.html
     */
    public Point recalculate(final double x0,
        final double y0, 
        final double r0,
        final double x1,
        final double y1,
        final double r1,
        final double x2,
        final double y2,
        final double r2) {
        
        double r = r1;
        double R = r0;
        double d = x1;
        
        double x = Math.pow(d, 2) - Math.pow(r, 2) + Math.pow(R, 2) / (2 * d);
        
        double k = Math.pow(d, 2) - Math.pow(r,2)  + Math.pow(R,2);
        double y = (4 * Math.pow(d, 2) * Math.pow(R, 2) - Math.pow(k, 2)) / (4 * Math.pow(d, 2));
        
        return new Point(x, y);
        
    }

}
