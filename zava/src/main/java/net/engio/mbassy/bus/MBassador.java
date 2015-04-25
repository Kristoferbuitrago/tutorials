package net.engio.mbassy.bus;

import net.engio.mbassy.bus.common.IMessageBus;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.PublicationError;
import net.engio.mbassy.bus.publication.SyncAsyncPostCommand;

import java.util.concurrent.TimeUnit;


public class MBassador<T> extends AbstractSyncAsyncMessageBus<T, SyncAsyncPostCommand<T>> implements IMessageBus<T, SyncAsyncPostCommand<T>> {


    public MBassador(IBusConfiguration configuration) {
        super(configuration);
    }

    public MBassador(){
        this(new BusConfiguration()
                .addFeature(Feature.SyncPubSub.Default())
                .addFeature(Feature.AsynchronousHandlerInvocation.Default())
                .addFeature(Feature.AsynchronousMessageDispatch.Default()));
    }


    public IMessagePublication publishAsync(T message) {
        return addAsynchronousPublication(createMessagePublication(message));
    }

    public IMessagePublication publishAsync(T message, long timeout, TimeUnit unit) {
        return addAsynchronousPublication(createMessagePublication(message), timeout, unit);
    }


    /**
     * Synchronously publish a message to all registered listeners (this includes listeners defined for super types)
     * The call blocks until every messageHandler has processed the message.
     *
     * @param message
     */
    public void publish(T message) {
        try {
            IMessagePublication publication = createMessagePublication(message);
            publication.execute();
        } catch (Throwable e) {
            handlePublicationError(new PublicationError()
                    .setMessage("Error during publication of message")
                    .setCause(e)
                    .setPublishedMessage(message));
        }

    }


    @Override
    public SyncAsyncPostCommand<T> post(T message) {
        return new SyncAsyncPostCommand<T>(this, message);
    }

}
