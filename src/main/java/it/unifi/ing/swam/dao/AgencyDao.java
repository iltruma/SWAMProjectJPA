package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.Address;
import it.unifi.ing.swam.model.Agency;

public class AgencyDao extends BaseDao {

    public Agency findById(Long id) {
        return entityManager.find(Agency.class, id);
    }

    public Agency findByName(String name) {
        List<Agency> result = entityManager.createQuery("FROM Agency WHERE name = :name", Agency.class)
                .setParameter("name", name).getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

    public Agency findByAddress(Address address) {
        List<Agency> result = entityManager
                .createQuery("SELECT a FROM Agency a WHERE a.address = :address ", Agency.class)
                .setParameter("address", address).getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

}
