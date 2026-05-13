package com.research.assistant;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ResearchService {

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public ResearchService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String processContent(ResearchRequest request) {
        // Build the prompt
        String prompt = buildPrompt(request);

        // Query the AI Model
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[] {
                                Map.of("text", prompt)
                        })
                });

        return webClient.post()
                .uri(apiUrl + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String buildPrompt(ResearchRequest request) {
        StringBuilder prompt = new StringBuilder();
        switch (request.getOperation()) {
            case "summarize":
                prompt.append("Summarize the following text:\n\n");
                break;
            case "suggest":
                prompt.append("Suggest topics based on the following content:\n\n");
                break;
            case "simplify":
                prompt.append("Simplify the following text:\n\n");
                break;
            case "questions":
                prompt.append("Generate Questions based on the following content:\n\n");
                break;
            case "extract":
                prompt.append("Extract Keywords from the following text:\n\n");
                break;
            case "citation":
                prompt.append("Provide citations for the following content:\n\n");
                break;
            default:
                throw new IllegalArgumentException("Unknown operation: " + request.getOperation());
        }
        prompt.append(request.getContent());
        return prompt.toString();
    }
}
