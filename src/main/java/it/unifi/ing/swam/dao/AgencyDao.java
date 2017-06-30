package it.unifi.ing.swam.dao;

import it.unifi.ing.swam.model.Address;
import it.unifi.ing.swam.model.Agency;

public class AgencyDao extends BaseDao {

    public Agency findById(Long id) {
        return entityManager.find(Agency.class, id);
    }

    public Agency findByName(String name) {
        return entityManager.createQuery("FROM Agency WHERE name = :name", Agency.class).setParameter("name", name)
                .getSingleResult();
    }

    public Agency findByAddress(Address address) {
        return entityManager.createQuery("SELECT a FROM Agency a WHERE a.address = :address ", Agency.class)
                .setParameter("address", address).getSingleResult();
    }

}
