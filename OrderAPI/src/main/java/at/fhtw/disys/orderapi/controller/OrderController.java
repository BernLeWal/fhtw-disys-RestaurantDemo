package at.fhtw.disys.orderapi.controller;

import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
public class OrderController {
    public static final String KITCHEN_CHANNEL = "KitchenChannel";
    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping
    public void startOrder(@RequestBody String meal) {
        logger.info("startOrder '" + meal + "' received.");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (
                var connection = factory.newConnection();
                var channel = connection.createChannel()
                ) {
            channel.queueDeclare(KITCHEN_CHANNEL, false, false, false, null);
            channel.basicPublish("", KITCHEN_CHANNEL, null, meal.getBytes(StandardCharsets.UTF_8));
            logger.info("published to queue " + KITCHEN_CHANNEL + " message " + meal);
        } catch (IOException e) {
            logger.warn(e.toString());
        } catch (TimeoutException e) {
            logger.warn(e.toString());
        }
    }
}
