package it.unifi.ing.swam.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "roles")
public abstract class Role extends BaseEntity {

    protected Role() {

    }

    public Role(String uuid) {
        super(uuid);
    }

}
