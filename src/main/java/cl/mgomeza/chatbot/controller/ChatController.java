package cl.mgomeza.chatbot.controller;

import cl.mgomeza.chatbot.dto.request.ChatRequest;
import cl.mgomeza.chatbot.dto.response.ChatResponse;
import cl.mgomeza.chatbot.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
                "status", "UP",
                "message", "SecureBot API is running"
        );
    }

    @PostMapping
    public ChatResponse sendMessage(@RequestBody ChatRequest request) {
        String reply = chatService.sendMessage(request.message());
        return new ChatResponse(reply);
    }
}
