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

package nl.tjonahen.wificollector.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * MacNameResolverEntity represents the mac adres to name resolving record. For each known macadres a logical name
 * is registered.
 * 
 * MacNameResolving is used to show a more human readable name than simply the mac adress.
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Entity
@NamedQueries({@NamedQuery(name = "MacNameResolverEntity.selectAll", query = "SELECT e FROM MacNameResolverEntity e")})
@XmlRootElement
public class MacNameResolverEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String mac;
    
    @Basic
    private String name;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return this.mac.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MacNameResolverEntity other = (MacNameResolverEntity) obj;
        return Objects.equals(this.mac, other.mac);
    }
    
    
    
    
}
