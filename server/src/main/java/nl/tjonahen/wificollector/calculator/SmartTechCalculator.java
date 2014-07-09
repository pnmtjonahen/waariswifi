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
public class SmartTechCalculator implements Calculator {

    @Override
    public Point recalculate(double x1, double y1, double r1, 
                             double x2, double y2, double r2, 
                             double x3, double y3, double r3) 
    {
        double r1sq = Math.pow(r1, 2);
        double r2sq = Math.pow(r2, 2);
        double r3sq = Math.pow(r3, 2);

        // int x = (r1^2 - r2^2 + d^2) / (2 d)
        int d = (int) (x2 - x1);
        double dsq = Math.pow(d, 2);
        int x = (int) (r1sq - r2sq + dsq) / (2 * d);

        // int y = ((r1^2 - r3^2 + i^2 + j^2) / (2 j)) - (1 / j) x
        double isq = Math.pow(x3, 2);
        double jsq = Math.pow(y3, 2);
        int y = (int) (((r1sq - r3sq + isq + jsq) / (2 * y3)) - (1 / y3) * x);

        // int z = + / - sqrt(r1^2 - x^2 - y^2)
        int xsq = (int) Math.pow(x, 2);
        int ysq = (int) Math.pow(y, 2);
        int z = (int) Math.sqrt(r1sq - xsq - ysq);

        return new Point(x, y);

    }

}
