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

import nl.tjonahen.wificollector.WifiData;

/**
 * Calculates the average distance. Hold up to max number of distances and calculate the average.
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class Distance {
    private final int maxSize;
    
    private final FixedSizeList<WifiData> fixedSizeList;
    private final double defaultDistance;
    
    /**
     * 
     * @param max size.
     */
    public Distance(final int max) {
        this.maxSize = max;
        this.fixedSizeList = new FixedSizeList<WifiData>(maxSize);
        this.defaultDistance = Double.NaN;
    }

    /**
     * 
     * @param max size 
     * @param def default distance
     */
    public Distance(final int max, final double def) {
        this.maxSize = max;
        this.fixedSizeList = new FixedSizeList<WifiData>(maxSize);
        this.defaultDistance = def;
    }
    
    /**
     * 
     * @param wifiData - 
     */
    public void add(final WifiData wifiData) {
        fixedSizeList.add(wifiData);
    }
    
    /**
     * 
     * @return average distance 
     */
    public double getAverage() {
        final WifiData min = determineMinimumDb();
        if (min == null) {
            return Double.NaN;
        }
            
        return calculateDistance(Math.abs(Double.valueOf(min.getDb())), Double.valueOf(min.getFreq()));

    }
    
    //CHECKSTYLE:OFF
    double calculateDistance(double levelInDb, double freqInMHz)    {
       final double exp = (27.55 - (20 * Math.log10(freqInMHz)) + levelInDb) / 20.0;
       return Math.pow(10.0, exp);
    } 
    //CHECKSTYLE:ON

    private WifiData determineMinimumDb() {
        WifiData min = null;
        for (final WifiData wifiData : fixedSizeList) {
            if (min == null) {
                min = wifiData;
            } else {
                if (Math.abs(Double.valueOf(wifiData.getDb())) < Math.abs(Double.valueOf(min.getDb())) ) {
                    min = wifiData;
                }
            }
        }
        return min;
    }

    
}
