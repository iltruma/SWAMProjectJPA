package it.unifi.ing.swam.bean.startup;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.transaction.Transactional;

import it.unifi.ing.swam.dao.AgencyDao;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;

@Singleton
@Startup
public class StartupBean {

	@Inject
	private UserDao userDao;
	
	@Inject
	private AgencyDao agencyDao;
	
	@Inject
	private WaybillDao waybillDao;
		
	@PostConstruct
	@Transactional
	public void init() {
		Agency a = ModelFactory.generateAgency();
		
		User user1 = ModelFactory.generateUser("operator1","operator1");
		User user2 = ModelFactory.generateUser("driver1","driver1");
		user1.addRole(ModelFactory.generateOperator());
		user2.addRole(ModelFactory.generateDriver());
		user1.setAgency(a);
		user2.setAgency(a);
		agencyDao.save(a);
		
		userDao.save(user1);
		userDao.save(user2);
		
		
	}

}
