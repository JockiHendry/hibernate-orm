package org.hibernate.jpa.test.graphs.experiment;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Discount {

    BigDecimal percent;

}
