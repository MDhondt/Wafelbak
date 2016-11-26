package be.scoutsronse.wafelbak.repository;

import be.scoutsronse.wafelbak.repository.db.TransactionRollbacked;
import be.scoutsronse.wafelbak.tech.event.ApplicationStarted;
import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

import static be.scoutsronse.wafelbak.tech.thread.Executors.newSingleThreadExecutor;

public abstract class Index<Entity> {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private final ExecutorService indexLoader = newSingleThreadExecutor(this.getClass().getSimpleName());

    private volatile boolean initialized;

    public synchronized void save(Entity entity) {
        if (!isInitialized()) {
            initialize();
        } else {
            doSave(entity);
        }
    }

    public synchronized void update(Entity entity) {
        delete(entity);
        save(entity);
    }

    public synchronized void delete(Entity entity) {
        Entity existing = findExisting(entity);
        if (contains(entity)) {
            doDelete(findExisting(entity));
        }
    }

    public synchronized void clear() {
        doClear();
        initialized = false;
    }

    public synchronized void initialize() {
        if (!isInitialized()) {
            logger.info("initializing...");
            doInitialize();
            initialized = true;
            logger.info("initialization successful");
        }
    }

    protected synchronized boolean isInitialized() {
        return initialized;
    }

    public void initializeAsynchronously() {
        indexLoader.execute(this::initialize);
    }

    @Subscribe
    public void initializeCacheWhen(ApplicationStarted event) {
        initializeAsynchronously();
    }

    @Subscribe
    public void initializeCacheWhen(TransactionRollbacked event) {
        if (!event.isReadOnly()) {
            clear();
            initializeAsynchronously();
        }
    }

    public boolean contains(Entity entity) {
        return findExisting(entity) != null;
    }

    protected abstract void doClear();

    protected abstract void doInitialize();

    protected abstract void doSave(Entity entity);

    protected abstract void doDelete(Entity entity);

    protected abstract Entity findExisting(Entity entity);

    public abstract Collection<Entity> findAll();
}