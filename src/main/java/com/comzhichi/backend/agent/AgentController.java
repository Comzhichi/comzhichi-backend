package com.comzhichi.backend.agent;

import com.comzhichi.backend.agent.dto.ChatRequest;
import com.comzhichi.backend.agent.dto.ChatResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @PostMapping("/chat")
    public ChatResponse chat(@Valid @RequestBody ChatRequest request,
                             Authentication auth) {
        return agentService.chat(auth.getName(), request.message());
    }
}