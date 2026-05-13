package com.research.assistant;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/research")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ResearchController {
    private final ResearchService researchService;

    @PostMapping(value = "/process", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, String>> processContent(@RequestBody ResearchRequest request) {
        String result = researchService.processContent(request);
        return ResponseEntity.ok(Map.of("result", result));
    }
}