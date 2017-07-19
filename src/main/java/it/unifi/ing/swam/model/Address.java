package it.unifi.ing.swam.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Address {

	@NotNull
	private String street;

	@NotNull
	private String city;

	@NotNull
	@Column(name = "address_state")
	private String state;

	@NotNull
	private String zip;

	public Address() {
		street = "";
		city = "";
		state = "";
		zip = "";
	}

	public Address(String street, String city, String state, String zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != Address.class){
			return false;
		}
		Address a = (Address)obj;
		return a.street.equals(this.street) && a.city.equals(this.city) && a.state.equals(this.state) && a.zip.equals(this.zip);
	}


	public String format() {
		String address = street + " " + city;

		if ((street != "" || city != "") && (state != "" || zip != ""))
			address += ",";

		address += " " + state + " " + zip;
		return address;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
