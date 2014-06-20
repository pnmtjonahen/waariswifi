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
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * EndpointEntity represents an endpoint as stored in the database. Enpoints are the devices that perform the 
 * network monitoring.
 * 
 * Enpoints have a name, a mac adres and a fixed location (x,y).
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Entity
@NamedQueries({@NamedQuery(name = "EndpointEntity.selectAll", query = "SELECT e FROM EndpointEntity e")})
@XmlRootElement
public class EndpointEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String name;
    @Basic
    private String mac;
    @Basic
    private double x;
    @Basic
    private double y;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


}
