package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.Item;

public class ItemDao extends BaseDao {

    public Item findById(Long id) {
        return entityManager.find(Item.class, id);
    }

    public List<Item> findByWaybillId(Long waybillId) {
        return entityManager.createQuery("FROM Item i WHERE" + "i.waybill_id = :waybill_id", Item.class)
                .setParameter("waybill_id", waybillId).getResultList();
    }

}
