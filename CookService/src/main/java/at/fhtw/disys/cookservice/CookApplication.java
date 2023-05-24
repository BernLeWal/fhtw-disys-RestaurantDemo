package at.fhtw.disys.cookservice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

@Slf4j
public class CookApplication {
    private static Logger logger = LoggerFactory.getLogger(CookApplication.class);

    public static final String KITCHEN_CHANNEL = "KitchenChannel";
    public static final String WASHER_CHANNEL = "WasherChanel";

    public static void main(String[] args) throws IOException, TimeoutException {
        CookPanRepository panRepository = new CookPanRepository();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel kitchenChannel = connection.createChannel();
        kitchenChannel.queueDeclare(KITCHEN_CHANNEL, false, false, false, null);
        Channel washerChannel = connection.createChannel();
        washerChannel.queueDeclare(WASHER_CHANNEL, false, false, false, null);

        ExecutorService service = Executors.newSingleThreadExecutor();
        CookQueue cookQueue = new CookQueue(kitchenChannel, washerChannel, panRepository);
        service.submit( cookQueue );
    }

}
