package org.hibernate.jpa.test.graphs.experiment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Invoice {

    @Id @GeneratedValue
    Long number;

    @ElementCollection @OrderColumn
    List<LineItem> lineItems = new ArrayList<LineItem>();

    @Embedded
    Discount discount;

}
