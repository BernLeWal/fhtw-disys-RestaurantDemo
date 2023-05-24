package at.fhtw.disys.washerservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.NoSuchElementException;

public class WasherPanRepository {
    private static Logger logger = LoggerFactory.getLogger(WasherPanRepository.class);

    public void returnPan(String pan) {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/v1/pans/clean"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(pan)).build();
            var response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("posted to REST OrderAPI pan " + pan);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new NoSuchElementException(e);
        }
    }
}
