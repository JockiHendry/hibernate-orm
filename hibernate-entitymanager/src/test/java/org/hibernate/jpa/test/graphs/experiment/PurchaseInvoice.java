package org.hibernate.jpa.test.graphs.experiment;

import javax.persistence.*;

@Entity
public class PurchaseInvoice extends Invoice {

    public enum InvoiceStatus { OPEN, RECEIVED, CLOSE }

    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    AccountPayable accountPayable;

    @Enumerated
    InvoiceStatus status = InvoiceStatus.OPEN;

    @ManyToOne
    Supplier supplier;

}
