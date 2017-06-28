package it.unifi.ing.swam.model;

import java.util.UUID;

public class ModelFactory {

	private ModelFactory(){
	}

	public static User generateUser() {
		return new User(UUID.randomUUID().toString());
	}


}
