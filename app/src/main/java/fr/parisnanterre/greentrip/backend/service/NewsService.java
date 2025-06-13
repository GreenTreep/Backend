package fr.parisnanterre.greentrip.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NewsService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_KEY = "pub_3388b5bcd2cc4e9ea999762df279e09c";
    private static final String BASE_URL = "https://newsdata.io/api/1/news";

    public String fetchNewsByTopic(String topic) {
        String url = BASE_URL + "?apikey=" + API_KEY + "&language=fr&q=" + topic;
        return restTemplate.getForObject(url, String.class);
    }
}
