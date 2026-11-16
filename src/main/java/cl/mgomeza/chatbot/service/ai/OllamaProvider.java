package cl.mgomeza.chatbot.service.ai;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        name = "securebot.ai.provider",
        havingValue = "ollama"
)
public class OllamaProvider implements AIProvider {
    @Override
    public String generate(String prompt) {
        return "Prueba Ollama.";
    }
}
