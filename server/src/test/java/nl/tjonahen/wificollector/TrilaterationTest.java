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
public class TrilaterationTest {
    
    static class Point {
        final double x;
        final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
        
    }
    
    @Test
    public void calc() {
        Point P1 = new Point(0,0);
        Point P2 = new Point(10, 0);
        Point P3 = new Point(8.5,6);
        
        double r1 = 6.5;
        double r2 = 6;
        double r3 = 9;
        
        double x = (Math.pow(r1, 2) - Math.pow(r2, 2) + Math.pow(P2.getX(), 2)) / (2*P2.getX());
        System.out.println(x);
//        double y = ((Math.pow(r1, 2) - Math.pow(r3, 2) + Math.pow(P3.getX(), 2) + Math.pow(P3.getY(), 2)) / (2 * P3.getY())) - (x * (P3.getX()/P3.getY()));
        
        // a^2 = b^2 + c^2
        
        double y = Math.sqrt(Math.pow(r1, 2) - Math.pow(x, 2));
        System.out.println(y);
    
    }
    
    
}
