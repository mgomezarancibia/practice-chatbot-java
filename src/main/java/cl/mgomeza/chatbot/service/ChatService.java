package cl.mgomeza.chatbot.service;

import cl.mgomeza.chatbot.service.ai.AIProvider;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final AIProvider aiProvider;

    public ChatService(AIProvider aiProvider) {
        this.aiProvider = aiProvider;
    }

    public String sendMessage(String message) {
        return aiProvider.generate(message);
    }
}
