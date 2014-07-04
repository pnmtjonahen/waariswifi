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
 * A single coordinate point.
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class Point {
    private double x;
    private double y;

    /**
     * Default constructor
     */
    public Point() {
        this.x = Double.NaN;
        this.y = Double.NaN;
    }
    /**
     * 
     * @param x -
     * @param y -
     */
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
    
    public boolean isValid() {
        return isXValid() && isYValid();
    }

    private boolean isYValid() {
        return !Double.isNaN(y);
    }

    private boolean isXValid() {
        return !Double.isNaN(x);
    }

    public void add(final Point recalculate) {
        if (!recalculate.isValid()) {
            return;
        }
        if (isXValid()) {
            this.x = (this.x + recalculate.x) / 2.0;
        } else {
            this.x = recalculate.x;
        }
        if (isYValid()) {
            this.y = (this.y + recalculate.y) / 2.0;
        } else {
            this.y = recalculate.y;
            
        }
    }
    
}
