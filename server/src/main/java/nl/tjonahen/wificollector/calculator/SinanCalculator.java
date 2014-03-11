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

import nl.tjonahen.wificollector.Device;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class SinanCalculator  implements Calculator  {
    
    public void recalculate(final Device device) {
 
             double x1 = device.getP1().getX();
             double y1 = device.getP1().getY();
             double dist1 = device.getDistanceToP1();
 
             double x2 = device.getP2().getX();
             double y2 = device.getP2().getY();
             double dist2 = device.getDistanceToP2();
 
             double x3 = device.getP3().getX();
             double y3 = device.getP3().getY();
             double dist3 = device.getDistanceToP3();
 
             double y32 = y3 - y2;
             double y13 = y1 - y3;
             double y21 = y2 - y1;
             double x32 = x3 - x2;
             double x13 = x1 - x3;
             double x21 = x2 - x1;
             double A = Math.pow(x1, 2) + Math.pow(y1, 2) - Math.pow(dist1, 2);
             double B = Math.pow(x2, 2) + Math.pow(y2, 2) - Math.pow(dist2, 2);
             double C = Math.pow(x3, 2) + Math.pow(y3, 2) - Math.pow(dist3, 2);
 
             double x = (A * y32 + B * y13 + C * y21)
                           / (2 * (x1 * y32 + x2 * y13 + x3 * y21));
             double y = (A * x32 + B * x13 + C * x21)
                           / (2 * (y1 * x32 + y2 * x13 + y3 * x21));
             
             device.setX(x);
             device.setY(y);
 
       }
}
