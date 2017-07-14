package it.unifi.ing.swam.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.model.Item;
import it.unifi.ing.swam.model.Load;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.Waybill;

public class ItemDaoJpaTest extends JpaTest {

    ItemDao itemDao;

    Item item;
    Waybill waybill;

    @Override
    protected void init() throws InitializationError {

        item = ModelFactory.generateItem();
        item.setVolume(Float.valueOf(1F));
        item.setWeigth(Float.valueOf(1F));
        Load load = new Load();
        load.addItem(item);
        waybill = ModelFactory.generateWaybill();
        waybill.setLoad(load);

        entityManager.persist(item);
        entityManager.persist(waybill);

        itemDao = new ItemDao();
        JpaTest.inject(itemDao, entityManager);
    }

    @Test
    public void testSave() {
        Item itemSave = ModelFactory.generateItem();

        itemDao.save(itemSave); // Persist

        itemDao.save(itemSave); // Merge

        assertEquals(itemSave, entityManager.createQuery("FROM Item i WHERE i = :item", Item.class)
                .setParameter("item", itemSave).getSingleResult());
    }

    @Test
    public void testFindByWaybillId() {
        List<Item> result = itemDao.findByWaybillId(waybill.getId());
        assertEquals(1, result.size());
        assertEquals(item, result.get(0));
    }

}
