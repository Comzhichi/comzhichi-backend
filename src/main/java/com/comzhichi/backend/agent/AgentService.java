package com.comzhichi.backend.agent;

import com.comzhichi.dto.response.ChatResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final ChatClient chatClient;

    // conversationId = email del usuario (del JWT): cada usuario tiene su propia memoria
    public ChatResponse chat(String conversationId, String message) {
        return chatClient.prompt()
                .user(message)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .entity(ChatResponse.class);
    }

    @PostConstruct
    void logKey() {
        System.out.println(">>> AI_API_KEY starts with: " +
                (apiKey != null && apiKey.length() > 10 ? apiKey.substring(0, 10) : "EMPTY/NULL"));
    }

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;
}