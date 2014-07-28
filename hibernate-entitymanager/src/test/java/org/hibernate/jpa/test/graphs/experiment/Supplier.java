package org.hibernate.jpa.test.graphs.experiment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Supplier {

    @Id @GeneratedValue
    Long number;

}
