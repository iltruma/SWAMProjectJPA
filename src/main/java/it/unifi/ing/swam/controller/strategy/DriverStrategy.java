package it.unifi.ing.swam.controller.strategy;

import java.util.Calendar;

import it.unifi.ing.swam.model.Tracking;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

public class DriverStrategy extends RoleStrategy {

    protected DriverStrategy(String wid, User u) {
        super(wid, u);
    }

    @Override
    public Waybill initWaybill() {
        if (waybillId == null)
            throw new IllegalStateException("Driver can't create a waybill");
        else {
            try {
                Long id = Long.valueOf(waybillId);
                waybill = waybillDao.findById(id);

                if (waybill == null)
                    throw new IllegalStateException("waybill does not exist in database");

                if (!missionDao.findByDriverAndDate(user, Calendar.getInstance()).getWaybills().contains(waybill))
                    throw new IllegalStateException("you can't access this waybill");

            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("id not a number");
            }
        }

        return waybill;
    }

    @Override
    public void checkEdit() {
        if (!waybill.getTracking().equals(Tracking.SHIPPING) && !waybill.getTracking().equals(Tracking.DELIVERING))
            throw new IllegalStateException("you can't edit this waybill");
    }

    @Override
    public void setSignAndTracking() {
        waybill.setSign(true);
        waybill.setTracking(Tracking.DELIVERED);
        waybill.setDeliveryDate(Calendar.getInstance());
        super.save();
    }

}
