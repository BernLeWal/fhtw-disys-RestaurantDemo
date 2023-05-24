package at.fhtw.disys.washerservice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

@Slf4j
public class WasherApplication {
    private static Logger logger = LoggerFactory.getLogger(WasherApplication.class);

    public static final String WASHER_CHANNEL = "WasherChanel";

    public static void main(String[] args) throws IOException, TimeoutException {
        WasherPanRepository panRepository = new WasherPanRepository();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel washerChannel = connection.createChannel();
        washerChannel.queueDeclare(WASHER_CHANNEL, false, false, false, null);

        ExecutorService service = Executors.newSingleThreadExecutor();
        WasherQueue washerQueue = new WasherQueue(washerChannel, panRepository);
        service.submit(washerQueue);
    }

}
