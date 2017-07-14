package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.Item;

public class ItemDao extends BaseDao<Item> {

    private static final long serialVersionUID = 20L;

    public ItemDao() {
        super(Item.class);
    }

    public List<Item> findByWaybillId(Long waybillId) {
        return entityManager.createQuery("FROM Item WHERE waybill_id = :waybill_id", Item.class)
                .setParameter("waybill_id", waybillId).getResultList();
    }

}
