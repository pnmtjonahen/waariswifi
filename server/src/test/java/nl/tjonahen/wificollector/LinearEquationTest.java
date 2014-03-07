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
public class LinearEquationTest {

    private static class Point {

        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    @Test
    public void test() {

//  AP1: (x – AP1.x)^2  + (y – AP1.y)^2 = d1^2
//  AP2: (x – AP2.x)^2  + (y – AP2.y)^2 = d2^2
//  AP3: (x – AP3.x)^2  + (y – AP3.y)^2 = d3^2 
        final Point AP1 = new Point(0, 0);
        final Point AP2 = new Point(26, 0);
        final Point AP3 = new Point(16, 26);

        final double d1 = 10;
        final double d2 = 19;
        final double d3 = 0;

        calculateThreeCircleIntersection(AP1.x, AP1.y, d1, AP2.x, AP2.y, d2, AP3.x, AP3.y, d3);

    }
    private static final double EPSILON = 0.000001;

    private boolean calculateThreeCircleIntersection(double x0, double y0, double r0,
            double x1, double y1, double r1,
            double x2, double y2, double r2) {
        double a, dx, dy, d, h, rx, ry;
        double point2_x, point2_y;

        /* dx and dy are the vertical and horizontal distances between
         * the first to circle centers.
         */
        dx = x1 - x0;
        dy = y1 - y0;

        /* Determine the straight-line distance between the centers. */
        d = Math.sqrt((dy * dy) + (dx * dx));

        /* Check for solvability. */
        if (d > (r0 + r1)) {
            System.out.println("INTERSECTION Circle1 AND Circle2 : Do not intersect");
            /* no solution. circles do not intersect. */
            return false;
        }
        if (d < Math.abs(r0 - r1)) {
            System.out.println("INTERSECTION Circle1 AND Circle2 : Contain within");
            /* no solution. one circle is contained in the other */
            return false;
        }

        /* 'point 2' is the point where the line through the circle
         * intersection points crosses the line between the circle
         * centers.
         */

        /* Determine the distance from point 0 to point 2. */
        a = ((r0 * r0) - (r1 * r1) + (d * d)) / (2.0 * d);

        /* Determine the coordinates of point 2. */
        point2_x = x0 + (dx * a / d);
        point2_y = y0 + (dy * a / d);

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
        double intersectionPoint1_x = point2_x + rx;
        double intersectionPoint2_x = point2_x - rx;
        double intersectionPoint1_y = point2_y + ry;
        double intersectionPoint2_y = point2_y - ry;

        System.out.println("INTERSECTION Circle1 AND Circle2:  (" + intersectionPoint1_x + "," + intersectionPoint1_y + ")" + " AND (" + intersectionPoint2_x + "," + intersectionPoint2_y + ")");

        /* Lets determine if circle 3 intersects at either of the above intersection points. */
        dx = intersectionPoint1_x - x2;
        dy = intersectionPoint1_y - y2;
        double d1 = Math.sqrt((dy * dy) + (dx * dx));

        dx = intersectionPoint2_x - x2;
        dy = intersectionPoint2_y - y2;
        double d2 = Math.sqrt((dy * dy) + (dx * dx));

        if (Math.abs(d1 - r2) < EPSILON) {
            System.out.println("INTERSECTION Circle1 AND Circle2 AND Circle3: (" + intersectionPoint1_x + "," + intersectionPoint1_y + ")");
        } else if (Math.abs(d2 - r2) < EPSILON) {
            System.out.println("INTERSECTION Circle1 AND Circle2 AND Circle3: (" + intersectionPoint2_x + "," + intersectionPoint2_y + ")"); //here was an error
        } else {
            System.out.println("INTERSECTION Circle1 AND Circle2 AND Circle3: NONE");
        }
        return true;
    }
}
