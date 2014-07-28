package org.hibernate.jpa.test.graphs.experiment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AccountPayable {

    @Id @GeneratedValue
    Long number;

    BigDecimal amount;

    @ElementCollection @OrderColumn
    List<Payment> payments = new ArrayList<Payment>();

}
