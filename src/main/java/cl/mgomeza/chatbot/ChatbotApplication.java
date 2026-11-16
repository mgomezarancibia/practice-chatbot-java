package cl.mgomeza.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatbotApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ChatbotApplication.class, args);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }

}
