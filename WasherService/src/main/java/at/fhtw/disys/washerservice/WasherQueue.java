package at.fhtw.disys.washerservice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WasherQueue implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(WasherQueue.class);

    private Channel washerChannel;

    private WasherPanRepository panRepository;

    private BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public WasherQueue(Channel washerChannel, WasherPanRepository panRepository) throws IOException {
        this.washerChannel = washerChannel;
        this.panRepository = panRepository;

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String pan = new String(delivery.getBody(), StandardCharsets.UTF_8);
            logger.info("received from " + WasherApplication.WASHER_CHANNEL + " message " + pan);
            queue.add(pan);
            logger.debug(queue.toString());
        };
        washerChannel.basicConsume(WasherApplication.WASHER_CHANNEL, true, deliverCallback, consumerTag -> {});
    }

    public void doWashing(String panId) {
        logger.info("start washing pan " + panId);

        // simulate washing
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // ignore
        }

        panRepository.returnPan(panId);

        logger.info("finished washing pan " + panId);
    }

    @Override
    public void run() {
        String panId = null;
        while ( true ) {
            do {
                try {
                    panId = queue.take();
                    doWashing(panId);
                } catch (InterruptedException e) {
                    logger.warn( e.toString() );
                    // ignored, just retry later
                }
            } while( panId!=null );
        }
    }
}
