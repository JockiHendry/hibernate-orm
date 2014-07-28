package org.hibernate.jpa.test.graphs.experiment;

import org.hibernate.Hibernate;
import org.hibernate.jpa.test.BaseEntityManagerFunctionalTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.persistence.*;
import java.math.BigDecimal;

public class ExperimentTest extends BaseEntityManagerFunctionalTestCase {

    @Override
    protected Class<?>[] getAnnotatedClasses() {
        return new Class[] { Supplier.class, Invoice.class, LineItem.class, PurchaseInvoice.class,
            AccountPayable.class, Payment.class };
    }

    @Test
    public void testImplicitJoinInWhereConditionShouldFollowGraphDefinition() {
        EntityManager em = getOrCreateEntityManager();
        em.getTransaction().begin();

        Supplier supplier = new Supplier();
        em.persist(supplier);
        PurchaseInvoice purchaseInvoice = new PurchaseInvoice();
        purchaseInvoice.supplier = supplier;
        em.persist(purchaseInvoice);
        AccountPayable accountPayable = new AccountPayable();
        accountPayable.amount = new BigDecimal("102544.45");
        accountPayable.payments.add(new Payment());
        accountPayable.payments.add(new Payment());
        purchaseInvoice.accountPayable = accountPayable;
        em.persist(accountPayable);
        em.getTransaction().commit();
        em.clear();

        em.getTransaction().begin();
        EntityGraph<PurchaseInvoice> entityGraph = em.createEntityGraph(PurchaseInvoice.class);
        Subgraph<AccountPayable> subgraph = entityGraph.addSubgraph("accountPayable", AccountPayable.class);
        subgraph.addAttributeNodes("payments");

        Query q = em.createQuery("FROM PurchaseInvoice p WHERE p.accountPayable.amount > 100000");
        q.setHint("javax.persistence.fetchgraph", entityGraph);
        PurchaseInvoice result = (PurchaseInvoice) q.getSingleResult();

        assertTrue(Hibernate.isInitialized(result));
        assertFalse(Hibernate.isInitialized(result.lineItems));
        assertTrue(Hibernate.isInitialized(result.accountPayable));
        assertTrue(Hibernate.isInitialized(result.accountPayable.payments));
        assertEquals(2, result.accountPayable.payments.size());

        em.getTransaction().commit();
        em.close();
    }
}
