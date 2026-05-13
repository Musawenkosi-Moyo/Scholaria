package com.research.assistant;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.ObjectMapper;

@Service
public class ResearchService {

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient;

    private final ObjectMapper objectMapper;

    public ResearchService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public String processContent(ResearchRequest request) {
        try {
            // Prompt Building Process
            String prompt = buildPrompt(request);

            // Querying the AI Model
            Map<String, Object> requestBody = Map.of(
                    "contents", new Object[] {
                            Map.of("parts", new Object[] {
                                    Map.of("text", prompt)
                            })
                    });

            String response = webClient.post()
                    .uri(apiUrl + "?key=" + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return extractTextFromResponse(response);
        } catch (Exception e) {
            return "Error calling AI API: " + e.getMessage();
        }
    }

    private String extractTextFromResponse(String response) {

        // Getting the output from the API
        try {
            AIResponse aiResponse = objectMapper.readValue(response, AIResponse.class);
            if (aiResponse.getCandidates() != null && !aiResponse.getCandidates().isEmpty()) {
                AIResponse.Candidate firstCandidate = aiResponse.getCandidates().get(0);

                if (firstCandidate.getContent() != null &&
                        firstCandidate.getContent().getParts() != null &&
                        !firstCandidate.getContent().getParts().isEmpty()) {
                    return firstCandidate.getContent().getParts().get(0).getText();
                }
            }
            return "no content found in response";
        } catch (Exception e) {
            return "Error Parsing: " + e.getMessage();
        }

    }

    // Crafting the prompt based on the operation
    private String buildPrompt(ResearchRequest request) {
        StringBuilder prompt = new StringBuilder();
        switch (request.getOperation()) {
            case "summarize":
                prompt.append("Summarize the following text. Please provide at least three distinct versions (e.g., Professional, Short, and Key Points) and separate each version clearly with a divider (---) and bold headers:\n\n");
                break;
            case "suggest":
                prompt.append("Suggest topics based on the following content. Please list them clearly with bullet points and separate logical groups with dividers (---):\n\n");
                break;
            case "simplify":
                prompt.append("Simplify the following text. Provide the simplified version followed by a divider (---) and then a list of the key changes made:\n\n");
                break;
            case "questions":
                prompt.append("Generate Questions based on the following content. Group the questions by difficulty and separate groups with dividers (---):\n\n");
                break;
            case "extract":
                prompt.append("Extract Keywords from the following text. List them in categories and separate categories with dividers (---):\n\n");
                break;
            case "citation":
                prompt.append("Provide citations for the following content. Format them clearly and separate different citation styles (e.g., APA, MLA) with dividers (---):\n\n");
                break;
            default:
                throw new IllegalArgumentException("Unknown operation: " + request.getOperation());
        }
        prompt.append(request.getContent());
        return prompt.toString();
    }
}
