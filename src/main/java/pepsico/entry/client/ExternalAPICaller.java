package pepsico.entry.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalAPICaller {
    private final RestTemplate restTemplate;

    @Autowired
    public ExternalAPICaller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
