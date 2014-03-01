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

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class Node {
    private final String name;
    private final Set<Vector> vectors;

    public Node(String name) {
        this.name = name;
        this.vectors = new TreeSet<>(new Comparator<Vector>() {

            @Override
            public int compare(Vector o1, Vector o2) {
                return o1.getEndnode().compareTo(o2.getEndnode());
            }
        });
    }

    public String getName() {
        return name;
    }

    public Set<Vector> getVectors() {
        return vectors;
    }
    
    
    
}
