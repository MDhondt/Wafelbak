package be.scoutsronse.wafelbak.repository.db;

import be.scoutsronse.wafelbak.tech.event.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

import javax.persistence.EntityManagerFactory;

public class EventPostingJpaTransactionManager extends JpaTransactionManager implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LogManager.getLogger(EventPostingJpaTransactionManager.class);

    private EventBus eventBus;

    public EventPostingJpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) {
        try {
            super.doRollback(status);
        } catch (Exception e) {
            LOGGER.error("Problem occured while rollbacking.", e);
        } finally {
            eventBus.post(TransactionRollbacked.transactionRollbacked(status.isReadOnly()));
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        eventBus = event.getApplicationContext().getBean(EventBus.class);
    }
}