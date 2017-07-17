package it.unifi.ing.swam.controller.strategy;

import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.Item;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.Tracking;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

public class CustomerStrategy extends RoleStrategy {

    protected CustomerStrategy(String wid, User u) {
        super(wid, u);

    }

    @Override
    public Waybill initWaybill() {
        if (waybillId == null) {
            waybill = ModelFactory.generateWaybill();
            waybill.setSender(user);
        } else
            try {
                Long id = Long.valueOf(waybillId);
                waybill = waybillDao.findById(id);

                if (waybill == null)
                    throw new IllegalStateException("waybill does not exist in database");

                if (!waybill.getSender().equals(user))
                    throw new IllegalStateException("you can't access this waybill");

            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("id not a number");
            }
        return waybill;
    }

    @Override
    public void checkEdit() {
        if (waybill.getOperator() != null || !user.getCustomerRole().isActive()
                || !waybill.getTracking().equals(Tracking.IDLE))
            throw new IllegalStateException("you can't edit this waybill");
    }

    @Override
    public void setAgency(Long id) {
        if (id == null)
            return;

        Agency a = agencyDao.findById(id);
        if (a == null)
            throw new IllegalArgumentException("id not found");
        waybill.getReceiver().setDestinationAgency(a);
    }

    @Override
    public void setNewItem(Long id) {
        if (id == null)
            return;

        Item i = itemDao.findById(id);
        if (i == null)
            throw new IllegalArgumentException("id not found");
        waybill.getLoad().addItem(i);
    }

    @Override
    public void delete() {
        waybillDao.delete(waybill);
    }

}
