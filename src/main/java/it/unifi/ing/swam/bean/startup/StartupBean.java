package it.unifi.ing.swam.bean.startup;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.transaction.Transactional;

import it.unifi.ing.swam.dao.AgencyDao;
import it.unifi.ing.swam.dao.FareDao;
import it.unifi.ing.swam.dao.ItemDao;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Address;
import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.Driver;
import it.unifi.ing.swam.model.Fare;
import it.unifi.ing.swam.model.Item;
import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.Receiver;
import it.unifi.ing.swam.model.State;
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
	private MissionDao missionDao;

	@Inject
	private WaybillDao waybillDao;

	@Inject
	private ItemDao itemDao;

	@Inject
	private FareDao fareDao;

	private Item[] item = new Item[200];

	@PostConstruct
	@Transactional
	public void init() {
		Agency agency1 = ModelFactory.generateAgency();
		Agency agency2 = ModelFactory.generateAgency();
		agency1.setName("Filiale di Firenze");
		agency1.setAddress(new Address("Via Roma","Firenze","Italia","50100"));
		agency2.setName("Filiale di Arezzo");
		agency2.setAddress(new Address("Via Torino","Arezzo","Italia","50200"));

		User operator1 = ModelFactory.generateUser("operator1", "operator1");
		operator1.setName("Primo Operatore");
		User operator2 = ModelFactory.generateUser("operator2", "operator2");
		operator2.setName("Secondo Operatore");
		User customer1 = ModelFactory.generateUser("customer1", "customer1");
		customer1.setName("Primo Cliente");
		User customer2 = ModelFactory.generateUser("customer2", "customer2");
		customer2.setName("Secondo Cliente");
		User driver1 = ModelFactory.generateUser("driver1", "driver1");
		driver1.setName("Primo Guidatore");
		User driver2 = ModelFactory.generateUser("driver2", "driver2");
		driver2.setName("Secondo Guidatore");
		User all = ModelFactory.generateUser("all", "all");
		all.setName("Tutto Tutti");

		operator1.addRole(ModelFactory.generateOperator());
		operator2.addRole(ModelFactory.generateOperator());

		customer1.addRole(ModelFactory.generateCustomer());
		customer1.getCustomerRole().setOperator(operator1);
		Address a = customer1.getCustomerRole().getAddress();
		a.setCity("Arezzo");
		a.setZip("52100");
		a.setStreet("Via menchetti 24");
		Fare f = ModelFactory.generateFare();
		f.setFunctionCost(0.2F);
		f.setZone("italia");
		f.setStartDate(Calendar.getInstance());
		customer1.getCustomerRole().addFare(f);
		fareDao.save(f);

		all.addRole(ModelFactory.generateOperator());
		all.addRole(ModelFactory.generateCustomer());
		all.addRole(ModelFactory.generateDriver());
		f = ModelFactory.generateFare();
		f.setFunctionCost(0.2F);
		f.setZone("italia");
		f.setStartDate(Calendar.getInstance());
		all.getCustomerRole().addFare(f);
		fareDao.save(f);

		customer2.addRole(ModelFactory.generateCustomer());
		customer2.getCustomerRole().setOperator(all);
		f = ModelFactory.generateFare();
		f.setFunctionCost(0.2F);
		f.setZone("italia");
		f.setStartDate(Calendar.getInstance());
		customer2.getCustomerRole().addFare(f);
		fareDao.save(f);

		driver1.addRole(ModelFactory.generateDriver());
		driver2.addRole(ModelFactory.generateDriver());

		all.getCustomerRole().setOperator(operator2);

		// Controllo NotNull di User
		operator1.setAgency(agency1);
		customer1.setAgency(agency1);
		driver1.setAgency(agency1);
		all.setAgency(agency1);
		operator2.setAgency(agency2);
		customer2.setAgency(agency2);
		driver2.setAgency(agency2);

		// Controllo NotNull di Customer
		customer1.getCustomerRole().setOperator(operator1);
		customer2.getCustomerRole().setOperator(operator2);
		all.getCustomerRole().setOperator(operator1);
		customer1.getCustomerRole().setState(State.ACTIVE);
		customer2.getCustomerRole().setState(State.ACTIVE);
		all.getCustomerRole().setState(State.ACTIVE);

		// Controllo NotNull di Driver
		driver1.getDriverRole().setAvailType(Driver.AvailType.AVAILABLE);
		driver2.getDriverRole().setAvailType(Driver.AvailType.AVAILABLE);
		all.getDriverRole().setAvailType(Driver.AvailType.AVAILABLE);

		Waybill waybill1 = ModelFactory.generateWaybill();
		Waybill waybill2 = ModelFactory.generateWaybill();
		Waybill waybill3 = ModelFactory.generateWaybill();
		Waybill waybill4 = ModelFactory.generateWaybill();
		Waybill waybill5 = ModelFactory.generateWaybill();
		Waybill waybill6 = ModelFactory.generateWaybill();

		// Controllo NotNull di Waybill
		Receiver receiver1 = new Receiver();
		Agency destinationAgency1 = ModelFactory.generateAgency();
		destinationAgency1.setName("Filiale di Destinazione");
		destinationAgency1.setAddress(new Address("Via Milano","Trieste","Italia","20890"));
		receiver1.setDestinationAgency(destinationAgency1);
		receiver1.setName("Primo Ricevitore");
		Address address1 = new Address();
		address1.setStreet("Via Giorgini");
		address1.setCity("Arezzo");
		address1.setZip("52100");
		address1.setState("Italia");
		receiver1.setAddress(address1);
		Calendar date1 = Calendar.getInstance();
		waybill1.setAcceptDate(date1);
		waybill1.setSender(customer1);
		waybill1.setReceiver(receiver1);

		Receiver receiver2 = new Receiver();
		Agency destinationAgency2 = ModelFactory.generateAgency();
		receiver2.setDestinationAgency(destinationAgency2);
		receiver2.setName("Secondo Ricevitore");
		Address address2 = new Address();
		address2.setStreet("Via Santa Marta");
		address2.setCity("Firenze");
		address2.setZip("50100");
		address2.setState("Italia");
		receiver2.setAddress(address2);
		Calendar date2 = Calendar.getInstance();
		waybill2.setAcceptDate(date2);
		waybill2.setSender(all);
		waybill2.setReceiver(receiver2);

		Receiver receiver3 = new Receiver();
		Agency destinationAgency3 = ModelFactory.generateAgency();
		receiver3.setDestinationAgency(destinationAgency3);
		receiver3.setName("Terzo Ricevitore");
		Address address3 = new Address();
		address3.setStreet("Via Venezia");
		address3.setCity("Firenze");
		address3.setZip("50100");
		address3.setState("Italia");
		receiver3.setAddress(address3);
		Calendar date3 = Calendar.getInstance();
		waybill3.setAcceptDate(date3);
		waybill3.setSender(customer2);
		waybill3.setOperator(operator1);
		waybill3.setReceiver(receiver3);
		waybill3.setTracking(Tracking.DELIVERING);

		Receiver receiver4 = new Receiver();
		Agency destinationAgency4 = ModelFactory.generateAgency();
		receiver4.setDestinationAgency(destinationAgency4);
		receiver4.setName("Quarto Ricevitore");
		Address address4 = new Address();
		address4.setStreet("Via Trento");
		address4.setCity("Firenze");
		address4.setZip("50100");
		address4.setState("Italia");
		receiver4.setAddress(address4);
		Calendar date4 = Calendar.getInstance();
		waybill4.setAcceptDate(date4);
		waybill4.setSender(customer2);
		waybill4.setOperator(operator2);
		waybill4.setReceiver(receiver4);
		waybill4.setTracking(Tracking.DELIVERING);

		Receiver receiver5 = new Receiver();
		Agency destinationAgency5 = ModelFactory.generateAgency();
		receiver5.setDestinationAgency(destinationAgency5);
		receiver5.setName("Quinto Ricevitore");
		Address address5 = new Address();
		address5.setStreet("Via Trieste");
		address5.setCity("Firenze");
		address5.setZip("50100");
		address5.setState("Italia");
		receiver5.setAddress(address5);
		Calendar date5 = Calendar.getInstance();
		waybill5.setAcceptDate(date5);
		waybill5.setDeliveryDate(date5);
		waybill5.setSender(customer1);
		waybill5.setOperator(operator2);
		waybill5.setReceiver(receiver5);
		waybill5.setTracking(Tracking.DELIVERED);
		waybill5.setSign(true);

		Receiver receiver6 = new Receiver();
		Agency destinationAgency6 = ModelFactory.generateAgency();
		receiver6.setDestinationAgency(destinationAgency6);
		receiver6.setName("Quarto Ricevitore");
		Address address6 = new Address();
		address6.setStreet("Via Genova");
		address6.setCity("Firenze");
		address6.setZip("50100");
		address6.setState("Italia");
		receiver6.setAddress(address6);
		Calendar date6 = Calendar.getInstance();
		waybill6.setAcceptDate(date6);
		waybill6.setDeliveryDate(date6);
		waybill6.setSender(all);
		waybill6.setOperator(operator1);
		waybill6.setReceiver(receiver4);
		waybill6.setTracking(Tracking.DELIVERED);
		waybill6.setSign(true);


		Mission mission1 = ModelFactory.generateMission();
		Mission mission2 = ModelFactory.generateMission();
		Mission mission3 = ModelFactory.generateMission();

		mission1.setDate(Calendar.getInstance());
		mission1.addWaybill(waybill3);
		mission1.setDriver(driver1.getDriverRole());

		mission2.setDate(Calendar.getInstance());
		mission2.addWaybill(waybill6);
		mission2.setDriver(driver2.getDriverRole());

		mission3.setDate(Calendar.getInstance());
		mission3.addWaybill(waybill4);
		mission3.addWaybill(waybill5);
		mission3.setDriver(all.getDriverRole());

		generateAndPersistItems();
		waybill1.getLoad().addItem(item[0]);
		waybill1.getLoad().addItem(item[1]);
		waybill1.getLoad().addItem(item[2]);
		waybill1.retreiveCost();

		waybill2.getLoad().addItem(item[3]);
		waybill2.getLoad().addItem(item[4]);
		waybill2.retreiveCost();

		waybill3.getLoad().addItem(item[5]);
		waybill3.getLoad().addItem(item[6]);
		waybill3.getLoad().addItem(item[7]);
		waybill3.retreiveCost();

		waybill4.getLoad().addItem(item[8]);
		waybill4.getLoad().addItem(item[9]);
		waybill4.getLoad().addItem(item[10]);
		waybill4.getLoad().addItem(item[11]);
		waybill4.retreiveCost();

		waybill5.getLoad().addItem(item[12]);
		waybill5.retreiveCost();

		waybill6.getLoad().addItem(item[13]);
		waybill6.retreiveCost();

		agencyDao.save(agency1);
		agencyDao.save(agency2);




		userDao.save(operator1);
		userDao.save(operator2);
		userDao.save(customer1);
		userDao.save(customer2);
		userDao.save(driver1);
		userDao.save(driver2);
		userDao.save(all);

		agencyDao.save(destinationAgency1);
		agencyDao.save(destinationAgency2);
		agencyDao.save(destinationAgency3);
		agencyDao.save(destinationAgency4);
		agencyDao.save(destinationAgency5);
		agencyDao.save(destinationAgency6);

		waybillDao.save(waybill1);
		waybillDao.save(waybill2);
		waybillDao.save(waybill3);
		waybillDao.save(waybill4);
		waybillDao.save(waybill5);
		waybillDao.save(waybill6);

		missionDao.save(mission1);
		missionDao.save(mission2);
		missionDao.save(mission3);



	}

	private void generateAndPersistItems() {
		for (int i = 0; i < 100; i++) {
			item[i] = ModelFactory.generateItem();
			item[i].setVolume((float) (i + 1));
			item[i].setWeigth((float) (i + 2));

			itemDao.save(item[i]);
		}
	}

}
