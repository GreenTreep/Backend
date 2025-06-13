package fr.parisnanterre.greentrip.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class ChatbotService {

    private final WebClient webClient;

    // Clé à déplacer en variable d'environnement en prod
    private final String mistralApiKey = "1vbdiKEC1qrhSTXuanGgaw3KGD4KdzBx";
    

    public ChatbotService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
            .baseUrl("https://api.mistral.ai/v1")
            .defaultHeader("Authorization", "Bearer " + mistralApiKey)
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Accept", "application/json")
            .build();
    }

    public Mono<String> getChatbotResponse(String model, Object messagesPayload) {
        return webClient.post()
                .uri("/chat/completions") // ✅ chemin relatif car baseUrl déjà défini
                .bodyValue(Map.of(
                    "model", model,
                    "messages", messagesPayload
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .map(responseMap -> {
                    Object choicesObj = responseMap.get("choices");
                    if (choicesObj instanceof java.util.List<?> choicesList &&
                        !choicesList.isEmpty() &&
                        choicesList.get(0) instanceof Map<?, ?> firstChoiceMap &&
                        ((Map<?, ?>) firstChoiceMap).get("message") instanceof Map<?, ?> messageMap
                    ) {
                        Map<?, ?> message = (Map<?, ?>) ((Map<?, ?>) firstChoiceMap).get("message");
                        Object content = message.get("content");
                        return content instanceof String ? (String) content : "Pas de réponse";
                    }
                    return "Pas de réponse";
                });
    }
}
