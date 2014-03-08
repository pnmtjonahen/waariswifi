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

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class Device {
    
    private double distanceToP1;
    private double distanceToP2;
    private double distanceToP3;
    
    private EndpointDevice P1;
    private EndpointDevice P2;
    private EndpointDevice P3;

    private double x = Double.NaN;
    private double y = Double.NaN;
    
    private boolean valid;
    
    public static Device create(final EndpointDevice P1, final EndpointDevice P2, final EndpointDevice P3) {
        final Device device = new Device();
        device.P1 = P1;
        device.P2 = P2;
        device.P3 = P3;
        return device;
    }

    public void update(final String endpointmac, final double distance) {
        if (P1.isEndpoint(endpointmac)) {
            distanceToP1 = distance;
        } else if (P2.isEndpoint(endpointmac)) {
            distanceToP2 = distance;
        } else if (P2.isEndpoint(endpointmac)) {
            distanceToP3 = distance;
        }
        recalculate();
    }

    private void recalculate() {
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
//        double a = P2.getX();
//        double b = distanceToP2;
//        double c = distanceToP1;
//        
//        double A = Math.acos((Math.pow(a, 2) - Math.pow(b, 2) - Math.pow(c, 2))/-(2*b*c)) * 180/Math.PI;
//        double B = Math.acos((Math.pow(b, 2) - Math.pow(a, 2) - Math.pow(c, 2))/-(2*a*c)) * 180/Math.PI;
//        double C = 180.0 - A - B;
//        
//        y = c * Math.sin(Math.toRadians( B ));
//        x = Math.sqrt(Math.pow(c, 2) - Math.pow(y, 2));
        
//        valid = calculateThreeCircleIntersection(distanceToP1, distanceToP2, distanceToP3);
        circleCircleIntersection();
        valid = isXValid() && isYValid();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
            
    public boolean isValid() {
        return valid;
    }

    private boolean isYValid() {
        return !Double.isNaN(y);
    }

    private boolean isXValid() {
        return !Double.isNaN(x);
    }
    
    
    private static final double EPSILON = 0.000001;
    
 /**
  *  http://stackoverflow.com/questions/19723641/find-intersecting-point-of-three-circles-programmatically
  */
    private boolean calculateThreeCircleIntersection(final double r0, final double r1, final double r2) {
        double a, dx, dy, d, h, rx, ry;
        double point2_x, point2_y;
        
        final double x0 = P1.getX();
        final double y0 = P1.getY(); 
        final double x1 = P2.getX();
        final double y1 = P2.getY();
        final double x2 = P3.getX();
        final double y2 = P3.getY(); 
        
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
            x = Math.abs(intersectionPoint1_x);
            y = Math.abs(intersectionPoint1_y);

        /* Lets determine if circle 3 intersects at either of the above intersection points. */
        dx = intersectionPoint1_x - x2;
        dy = intersectionPoint1_y - y2;
        double d1 = Math.sqrt((dy * dy) + (dx * dx));

        dx = intersectionPoint2_x - x2;
        dy = intersectionPoint2_y - y2;
        double d2 = Math.sqrt((dy * dy) + (dx * dx));

        if (Math.abs(d1 - r2) < EPSILON) {
            System.out.println("INTERSECTION Circle1 AND Circle2 AND Circle3: (" + intersectionPoint1_x + "," + intersectionPoint1_y + ")");
            x = Math.abs(intersectionPoint1_x);
            y = Math.abs(intersectionPoint1_y);
        } else if (Math.abs(d2 - r2) < EPSILON) {
            System.out.println("INTERSECTION Circle1 AND Circle2 AND Circle3: (" + intersectionPoint2_x + "," + intersectionPoint2_y + ")"); //here was an error
            x = Math.abs(intersectionPoint2_x);
            y = Math.abs(intersectionPoint2_y);
        } else {
            System.out.println("INTERSECTION Circle1 AND Circle2 AND Circle3: NONE");
//            return false;
        }
        
        return true;
    }
/**
 * http://mathworld.wolfram.com/Circle-CircleIntersection.html
 */
    private void circleCircleIntersection() {
        double r = distanceToP2;
        double R = distanceToP1;
        double d = P2.getX();
        
        x = Math.pow(d, 2) - Math.pow(r, 2) + Math.pow(R, 2) / (2 * d);
        
        double k = Math.pow(d, 2) - Math.pow(r,2)  + Math.pow(R,2);
        y = (4 * Math.pow(d, 2) * Math.pow(R, 2) - Math.pow(k, 2)) / (4 * Math.pow(d, 2));
        
        
    }
        
    
}
