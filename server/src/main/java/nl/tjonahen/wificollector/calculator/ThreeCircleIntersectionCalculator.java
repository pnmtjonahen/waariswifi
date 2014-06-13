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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class ThreeCircleIntersectionCalculator implements Calculator {
    private static final Logger LOGGER = Logger.getLogger(ThreeCircleIntersectionCalculator.class.getName());

    private static final double EPSILON = 0.000001;

    //CHECKSTYLE:OFF
    /**
     * http://stackoverflow.com/questions/19723641/find-intersecting-point-of-three-circles-programmatically
     *
     * @param x0 -
     * @param y0 -
     * @param r0 -
     * @param x1 -
     * @param y1 -
     * @param r1 -
     * @param x2 -
     * @param y2 -
     * @param r2 -
     * @return -
     */
    @Override
    public Point recalculate(
            final double x0,
            final double y0,
            final double r0,
            final double x1,
            final double y1,
            final double r1,
            final double x2,
            final double y2,
            final double r2) 
    {
//CHECKSTYLE:ON
        double a, dx, dy, d, h, rx, ry, x, y;
        double point2X, point2Y;

        /* dx and dy are the vertical and horizontal distances between
         * the first to circle centers.
         */
        dx = x1 - x0;
        dy = y1 - y0;

        /* Determine the straight-line distance between the centers. */
        d = Math.sqrt((dy * dy) + (dx * dx));

        /* Check for solvability. */
        if (d > (r0 + r1)) {
            LOGGER.finest("INTERSECTION Circle1 AND Circle2 : Do not intersect");
            /* no solution. circles do not intersect. */
            return new Point(Double.NaN, Double.NaN);
        }
        if (d < Math.abs(r0 - r1)) {
            LOGGER.finest("INTERSECTION Circle1 AND Circle2 : Contain within");
            /* no solution. one circle is contained in the other */
            return new Point(Double.NaN, Double.NaN);
        }

        /* 'point 2' is the point where the line through the circle
         * intersection points crosses the line between the circle
         * centers.
         */

        /* Determine the distance from point 0 to point 2. */
        a = ((r0 * r0) - (r1 * r1) + (d * d)) / (2.0 * d);

        /* Determine the coordinates of point 2. */
        point2X = x0 + (dx * a / d);
        point2Y = y0 + (dy * a / d);

        /* Determine the distance from point 2 to either of the
         * intersection points.
         */
        h = Math.sqrt((r0 * r0) - (a * a));

        /* Now determine the offsets of the intersection points from
         * point 2.
         */
        rx = -dy * (h / d);
        ry = dx * (h / d);

        /* Determine the absolute intersection points. */
        double intersectionPoint1X = point2X + rx;
        double intersectionPoint2X = point2X - rx;
        double intersectionPoint1Y = point2Y + ry;
        double intersectionPoint2Y = point2Y - ry;

        LOGGER.log(Level.FINEST, "INTERSECTION Circle1 AND Circle2:  ({0},{1}" + ")" + " AND ({2},{3})", 
                new Object[]{intersectionPoint1X, intersectionPoint1Y, intersectionPoint2X, intersectionPoint2Y});
        x = Math.abs(intersectionPoint1X);
        y = Math.abs(intersectionPoint1Y);

        /* Lets determine if circle 3 intersects at either of the above intersection points. */
        dx = intersectionPoint1X - x2;
        dy = intersectionPoint1Y - y2;
        double d1 = Math.sqrt((dy * dy) + (dx * dx));

        dx = intersectionPoint2X - x2;
        dy = intersectionPoint2Y - y2;
        double d2 = Math.sqrt((dy * dy) + (dx * dx));

        if (Math.abs(d1 - r2) < EPSILON) {
            LOGGER.log(Level.FINEST, "INTERSECTION Circle1 AND Circle2 AND Circle3: ({0},{1})", 
                                            new Object[]{intersectionPoint1X, intersectionPoint1Y});
            x = Math.abs(intersectionPoint1X);
            y = Math.abs(intersectionPoint1Y);
        } else if (Math.abs(d2 - r2) < EPSILON) {
            LOGGER.log(Level.FINEST, "INTERSECTION Circle1 AND Circle2 AND Circle3: ({0},{1})", 
                                    new Object[]{intersectionPoint2X, intersectionPoint2Y}); //here was an error
            x = Math.abs(intersectionPoint2X);
            y = Math.abs(intersectionPoint2Y);
        } else {
            LOGGER.finest("INTERSECTION Circle1 AND Circle2 AND Circle3: NONE");
        }

        return new Point(x, y);
    }

}
