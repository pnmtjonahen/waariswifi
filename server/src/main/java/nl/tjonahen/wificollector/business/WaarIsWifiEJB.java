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

package nl.tjonahen.wificollector.business;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import nl.tjonahen.wificollector.endpointdevice.EndpointDevice;
import nl.tjonahen.wificollector.endpointdevice.EndpointMapping;
import nl.tjonahen.wificollector.model.EndpointEntity;
import nl.tjonahen.wificollector.model.MacNameResolverEntity;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Stateless
public class WaarIsWifiEJB {
    @PersistenceContext(name = "WaarisWifiPu")
    private EntityManager entityManager;

    
    public List<EndpointEntity> getAll() {
        return entityManager.createNamedQuery("EndpointEntity.selectAll", EndpointEntity.class).getResultList();
    }
    
    public EndpointEntity get(final String name) {
        return entityManager.find(EndpointEntity.class, name);
    }
    
    @Transactional
    public void update(final EndpointEntity epe) {
        final EndpointEntity current = get(epe.getName());
        if (current == null) {
            entityManager.persist(epe);
        } else {
            current.setMac(epe.getMac());
            current.setX(epe.getX());
            current.setY(epe.getY());
            
            entityManager.merge(current);    
        }
    }
    public MacNameResolverEntity getMacNameResolverEntity(final String mac) {
        return entityManager.find(MacNameResolverEntity.class, mac);
    }
    
    @Transactional
    public void update(final MacNameResolverEntity epe) {
        final MacNameResolverEntity current = getMacNameResolverEntity(epe.getMac());
        if (current == null) {
            entityManager.persist(epe);
        } else {
            current.setName(epe.getName());
            entityManager.merge(current);    
        }
    }
    
    
    public EndpointMapping getEndpointMapping() {
        final EndpointMapping em = new EndpointMapping();
        EndpointEntity ep = get("P1");
        if (ep == null) {
            em.setP1(new EndpointDevice("P1.mac", 0, 0));
        } else {
            em.setP1(new EndpointDevice(ep.getMac(), ep.getX(), ep.getY()));
        }
        ep = get("P2");
        if (ep == null) {
            em.setP2(new EndpointDevice("P2.mac", 0, 0));
        } else {
            em.setP2(new EndpointDevice(ep.getMac(), ep.getX(), ep.getY()));
        }
        ep = get("P3");
        if (ep == null) {
            em.setP3(new EndpointDevice("P3.mac", 0, 0));
        } else {
            em.setP3(new EndpointDevice(ep.getMac(), ep.getX(), ep.getY()));
        }
        return em;
    }

    public List<MacNameResolverEntity> getAllMacNameResolvers() {
        return entityManager.createNamedQuery("MacNameResolverEntity.selectAll", MacNameResolverEntity.class).getResultList();
    }
    
    
}
