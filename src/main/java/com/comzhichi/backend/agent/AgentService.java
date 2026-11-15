package com.comzhichi.backend.agent;

import com.comzhichi.dto.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
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
}