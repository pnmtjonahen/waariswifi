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
public class SinanCalculator  implements Calculator  {
    
    //CHECKSTYLE:OFF
    /**
     * 
     * @param x1 -
     * @param y1 -
     * @param dist1 -
     * @param x2 -
     * @param y2 -
     * @param dist2 -
     * @param x3 -
     * @param y3 -
     * @param dist3 -
     * @return -
     */
    @Override
    public Point recalculate(final double x1,
        final double y1, 
        final double dist1,
        final double x2,
        final double y2,
        final double dist2,
        final double x3,
        final double y3,
        final double dist3) 
    {
 
 
             double y32 = y3 - y2;
             double y13 = y1 - y3;
             double y21 = y2 - y1;
             double x32 = x3 - x2;
             double x13 = x1 - x3;
             double x21 = x2 - x1;
             double pA = Math.pow(x1, 2) + Math.pow(y1, 2) - Math.pow(dist1, 2);
             double pB = Math.pow(x2, 2) + Math.pow(y2, 2) - Math.pow(dist2, 2);
             double pC = Math.pow(x3, 2) + Math.pow(y3, 2) - Math.pow(dist3, 2);
 
             double x = (pA * y32 + pB * y13 + pC * y21)
                           / (2 * (x1 * y32 + x2 * y13 + x3 * y21));
             double y = (pA * x32 + pB * x13 + pC * x21)
                           / (2 * (y1 * x32 + y2 * x13 + y3 * x21));
             
             return new Point(x, y);
       }
    //CHECKSTYLE:ON
}
