package cl.mgomeza.chatbot.service.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

import java.util.Map;

@Component
@ConditionalOnProperty(
        name = "securebot.ai.provider",
        havingValue = "gemini"
)
public class GeminiProvider implements AIProvider {

    private final RestClient restClient;
    private final String model;

    public GeminiProvider(
            @Value("${securebot.ai.gemini.api-key}") String apiKey,
            @Value("${securebot.ai.gemini.model}") String model) {

        this.restClient = RestClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com")
                .defaultHeader("x-goog-api-key", apiKey)
                .build();
        this.model = model;
    }

    @Override
    public String generate(String prompt) {

        Map<String, Object> request = Map.of(
                "model", model,
                "input", prompt
        );

        JsonNode response = restClient.post()
                .uri("/v1/interactions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(JsonNode.class);

        if (response == null) {
            throw new IllegalStateException("Empty response from Gemini");
        }

        StringBuilder result = new StringBuilder();

        for (JsonNode step : response.path("steps")) {
            if (!"model_output".equals(step.path("type").asText())) {
                continue;
            }
            for (JsonNode content : step.path("content")) {
                if ("text".equals(content.path("type").asText())) {
                    result.append(content.path("text").asText());
                }
            }
        }

        if (result.isEmpty()) {
            throw new IllegalStateException("Gemini returned no text response");
        }
        return result.toString();
    }
}
