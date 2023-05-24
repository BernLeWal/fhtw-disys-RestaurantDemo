package at.fhtw.disys.cookservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.NoSuchElementException;

public class CookPanRepository {
    private static Logger logger = LoggerFactory.getLogger(CookPanRepository.class);

    public String getPan() {
        String panId;
        try {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/v1/pans/clean"))
                    .GET().build();
            var response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            panId = response.body();
            logger.info("fetched from REST OrderAPI panId " + panId);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new NoSuchElementException(e);
        }
        return panId;
    }
}
