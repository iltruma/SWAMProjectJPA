package it.unifi.ing.swam.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "roles")
public abstract class Role extends BaseEntity {

	@Transient
	protected RoleType type;
	
	@ManyToOne
	protected User owner;

	protected Role() {

    }

    public Role(String uuid) {
        super(uuid);
    }
    
    public RoleType getType() {
		return type;
	}

	public void setType(RoleType type) {
		this.type = type;
	}
	
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

}
