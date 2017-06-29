package it.unifi.ing.swam.dao;

import it.unifi.ing.swam.model.Address;
import it.unifi.ing.swam.model.Agency;

public class AgencyDao extends BaseDao {

    public Agency findById(Long id) {
        return entityManager.find(Agency.class, id);
    }

    public Agency findByName(String name) {
        return entityManager.createQuery("SELECT a FROM Agency a WHERE a.name = :name", Agency.class)
                .setParameter("name", name).getSingleResult();
    }

    public Agency findByAddress(Address a) {
        return entityManager
                .createQuery(
                        "SELECT a FROM Agency a WHERE a.street = :street "
                                + "AND a.city = :city AND a.address_state = :address_state AND a.zip = :zip",
                        Agency.class)
                .setParameter("street", a.getStreet()).setParameter("city", a.getCity())
                .setParameter("adress_state", a.getState()).setParameter("zip", a.getZip()).getSingleResult();
    }

}
