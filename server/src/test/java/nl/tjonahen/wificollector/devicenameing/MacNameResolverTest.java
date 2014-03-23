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

package nl.tjonahen.wificollector.devicenameing;

import java.util.ArrayList;
import java.util.List;
import nl.tjonahen.wificollector.model.MacNameResolverEntity;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class MacNameResolverTest {
    
    @Test
    public void testResolver() {
        List<MacNameResolverEntity> entitys = new ArrayList<>();
        MacNameResolverEntity entity = new MacNameResolverEntity();
        entity.setMac("00:26:ab:0b:cd:91");
        entity.setName("Printer1");
        entitys.add(entity);
        final MacNameResolver macNameResolver = new MacNameResolver(entitys);
        
        Assert.assertEquals("Printer1", macNameResolver.resolve("00:26:AB:0B:CD:91"));
                
                
    }
}
