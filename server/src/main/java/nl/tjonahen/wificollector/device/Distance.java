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

package nl.tjonahen.wificollector.device;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class Distance {
    private final int maxSize;
    
    private final FixedSizeList<Double> fixedSizeList;
    private final double defaultDistance;
    public Distance(final int max) {
        this.maxSize = max;
        this.fixedSizeList = new FixedSizeList<Double>(maxSize);
        this.defaultDistance = Double.NaN;
    }

    public Distance(final int max, final double def) {
        this.maxSize = max;
        this.fixedSizeList = new FixedSizeList<Double>(maxSize);
        this.defaultDistance = def;
    }
    
    
    public void add(final double distance) {
        fixedSizeList.add(distance);
    }
    
    public double getAverage() {
        if (fixedSizeList.size() >= maxSize) {
            double sum = 0;
            for (Double value: fixedSizeList) {
                sum += value;
            }
            
            return sum / maxSize;
        }
        return defaultDistance;
    }
    
}
