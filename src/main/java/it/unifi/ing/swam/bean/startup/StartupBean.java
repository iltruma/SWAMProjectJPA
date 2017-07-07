package it.unifi.ing.swam.bean.startup;


import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.transaction.Transactional;

import it.unifi.ing.swam.dao.AgencyDao;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Address;
import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.Receiver;
import it.unifi.ing.swam.model.Tracking;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

@Singleton
@Startup
public class StartupBean {

	@Inject
	private UserDao userDao;
	
	@Inject
	private AgencyDao agencyDao;
	
	@Inject
	private AgencyDao missionDao;
	
	@Inject
	private WaybillDao waybillDao;
		
	@PostConstruct
	@Transactional
	public void init() {
		Agency a = ModelFactory.generateAgency();
		
		User user1 = ModelFactory.generateUser("operator1","operator1");
		User user2 = ModelFactory.generateUser("driver1","driver1");
		User user3 = ModelFactory.generateUser("all","all");

		user1.addRole(ModelFactory.generateOperator());
		user2.addRole(ModelFactory.generateDriver());
		
		user3.addRole(ModelFactory.generateOperator());
		user3.addRole(ModelFactory.generateDriver());
		user3.addRole(ModelFactory.generateCustomer());
		

		
		user1.setAgency(a);
		user2.setAgency(a);
		user3.setAgency(a);
		
		user3.getCustomerRole().setOperator(user1);

		

		
		Waybill waybill = ModelFactory.generateWaybill();

        User operator = user1;
        User driver = user2;
        

        User sender = user3;
        sender.setName("mario");

        Mission mission = ModelFactory.generateMission();
        driver.getDriverRole().addMission(mission);
        mission.setDate(Calendar.getInstance());
        mission.addWaybill(waybill);

        Receiver receiver = new Receiver();
        Agency destinationAgency = ModelFactory.generateAgency();
        receiver.setDestinationAgency(destinationAgency);
        receiver.setName("giorgio");
        Address address = new Address();
        address.setStreet("via giorgini");
        address.setCity("arezzo");
        address.setZip("52100");
        address.setState("Italia");
        receiver.setAddress(address);

        Calendar date = Calendar.getInstance();
        date.setTime(new Date(53264723543L));
        waybill.setAcceptDate(date);
        waybill.setDeliveryDate(date);
        //waybill.setOperator(operator);
        waybill.setSender(sender);
        waybill.setReceiver(receiver);
        waybill.setTracking(Tracking.IDLE);
        waybill.setCost(10F);
  
		agencyDao.save(a);
		
		userDao.save(user1);
		userDao.save(user2);
		userDao.save(user3);
		

        agencyDao.save(destinationAgency);
        waybillDao.save(waybill);
        missionDao.save(mission);
		
	}

}
