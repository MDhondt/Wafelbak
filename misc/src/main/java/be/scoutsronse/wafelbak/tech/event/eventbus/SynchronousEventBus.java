package be.scoutsronse.wafelbak.tech.event.eventbus;

class SynchronousEventBus extends EventBusImpl {

    @Override
    public void post(Object event) {
        subscribersForEvent(event).forEach(subscriber -> {
            try {
                subscriber.handle(event);
            } catch (Throwable t) {
            }
        });
    }
}