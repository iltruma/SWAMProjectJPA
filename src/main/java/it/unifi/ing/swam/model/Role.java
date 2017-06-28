package it.unifi.ing.swam.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Role extends BaseEntity {

    protected Role() {

    }

    public Role(String uuid) {
        super(uuid);
    }

}
