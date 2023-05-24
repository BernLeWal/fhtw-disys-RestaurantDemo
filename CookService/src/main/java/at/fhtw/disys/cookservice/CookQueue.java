package at.fhtw.disys.cookservice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CookQueue implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(CookQueue.class);

    private Channel kitchenChannel;
    private Channel washerChannel;

    private CookPanRepository panRepository;

    private BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public CookQueue(Channel kitchenChannel, Channel washerChannel, CookPanRepository panRepository) throws IOException {
        this.kitchenChannel = kitchenChannel;
        this.washerChannel = washerChannel;
        this.panRepository = panRepository;

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String meal = new String(delivery.getBody(), StandardCharsets.UTF_8);
            logger.info("received from " + CookApplication.KITCHEN_CHANNEL + " message " + meal);
            queue.add(meal);
            logger.debug(queue.toString());
        };
        kitchenChannel.basicConsume(CookApplication.KITCHEN_CHANNEL, true, deliverCallback, consumerTag -> {});
    }

    public String doCooking(String meal) {
        logger.info("start cooking meal " + meal);
        // fetch a pan
        String panId = panRepository.getPan();

        // simulate cooking
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // ignore
        }

        logger.info("finished cooking meal " + meal + ". Used pan is " + panId);
        return panId;
    }

    @Override
    public void run() {
        while ( true ) {
            String meal = null;
            do {
                try {
                    meal = queue.take();
                    var pan = doCooking(meal);
                    washerChannel.basicPublish("", CookApplication.WASHER_CHANNEL, null, pan.getBytes(StandardCharsets.UTF_8));
                } catch( IOException e ) {
                    logger.error( e.toString() );
                } catch (InterruptedException e) {
                    logger.warn( e.toString() );
                    // ignored, just retry later
                }
            } while( meal!=null );
        }
    }
}
