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

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import nl.tjonahen.wificollector.devicenameing.MacNameResolver;
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

    /**
     * 
     * @return - 
     */
    public List<EndpointEntity> getAll() {
        return entityManager.createNamedQuery("EndpointEntity.selectAll", EndpointEntity.class).getResultList();
    }
    
    /**
     * 
     * @param name -
     * @return - 
     */
    public EndpointEntity get(final String name) {
        return entityManager.find(EndpointEntity.class, name);
    }
    
    /**
     * 
     * @param epe - 
     */
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
    /**
     * 
     * @param mac -
     * @return -
     */
    public MacNameResolverEntity getMacNameResolverEntity(final String mac) {
        return entityManager.find(MacNameResolverEntity.class, mac);
    }
    
    /**
     * 
     * @param epe - 
     */
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
    
    /**
     * 
     * @return - 
     */
    public EndpointMapping getEndpointMapping() {
        final EndpointMapping em = new EndpointMapping();
        for (EndpointEntity ee : getAll()) {
            em.set(ee);
        }
        return em;
    }

    /**
     * 
     * @return -
     */
    public List<MacNameResolverEntity> getAllMacNameResolvers() {
        return entityManager.createNamedQuery("MacNameResolverEntity.selectAll", 
                                            MacNameResolverEntity.class).getResultList();
    }

    /**
     * 
     * @param entity - 
     */
    @Transactional
    public void delete(final MacNameResolverEntity entity) {
        entityManager.remove(entity);
    }

    /**
     * 
     * @return - 
     */
    public MacNameResolver getMacNameResolver() {
        return new MacNameResolver(getAllMacNameResolvers());
    }
    
    
}
