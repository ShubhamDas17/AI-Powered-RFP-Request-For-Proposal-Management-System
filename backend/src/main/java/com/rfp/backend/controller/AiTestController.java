package com.rfp.backend.controller;

import com.rfp.backend.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AiTestController {

    private final AiService aiService;

    @GetMapping("/ping")
    public String ping() {
        return "AI Controller OK";
    }

    @GetMapping("/test")
    public String testAI() {
        return aiService.testAI();
    }

    @PostMapping("/extract")
    public String extract(@RequestBody String text) {
        return aiService.extractStructuredDataFromText(text);
    }
}
