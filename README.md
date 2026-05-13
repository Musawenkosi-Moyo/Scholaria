# Scholaria: AI Research Assistant

Scholaria is a high-performance, AI-driven research automation tool built with **Java 25** and **Spring Boot 4.0.6**. It leverages the cutting-edge capabilities of **Google Gemini 3 Flash Preview** to provide researchers with a suite of tools for content analysis, synthesis, and transformation.

---

## Architecture Overview

The application follows a clean, layered architecture designed for scalability and maintainability:

- **Controller Layer**: Exposes REST endpoints and handles incoming JSON requests.
- **Service Layer**: Manages prompt engineering and processes the raw response from the Gemini API.
- **Client Layer**: Uses Spring WebFlux's non-blocking `WebClient` for high-performance API communication.

---

## Features

### Intelligent Summarization

Scholaria doesn't just shorten text; it understands the context and intent. The summarization engine generates three distinct perspectives to suit different needs. The **Professional** summary provides a high-level academic overview, the **Concise** version is optimized for quick social media or bio usage, and the **Key Points** section distills the content into actionable bullet points. Each section is clearly separated by visual dividers to ensure readability.

### Topic & Research Suggestions

This feature acts as a brainstorming partner. By analyzing the core themes of your text, Scholaria suggests related topics and future research directions. It categorizes these suggestions into logical groups, helping researchers branch out their studies and find connections they might have missed.

### Content Simplification & Deconstruction

Complex academic jargon can be a barrier to understanding. The simplification tool translates dense technical language into clear, accessible prose without losing the original meaning. After providing the simplified version, it includes a deconstruction section that explains the specific complex concepts and terms that were simplified.

### Question Generation & Comprehension

Perfect for students and educators, this feature transforms any text into a learning resource. It automatically generates comprehension and critical thinking questions, grouping them by difficulty level (Easy, Medium, and Hard). This allows users to test their own understanding or create quiz material for others.

### Keyword Extraction & Categorization

Scholaria identifies the most significant entities and themes within a document. Rather than just a flat list, it categorizes keywords into relevant groups (such as Technologies, Concepts, or People), making it easier to tag documents or build a research index.

---

## Getting Started

### Prerequisites

- **Java 25** or higher.
- **Maven** for building.
- A **Google Gemini API Key** from [Google AI Studio](https://aistudio.google.com/).

### Installation & Setup

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/Musawenkosi-Moyo/Scholaria.git
    cd assistant
    ```
2.  **Configure `.env`**:
    Create a `.env` file in the root directory:
    ```env
    GEMINI_API_KEY=your_actual_api_key
    ```
3.  **Run**:
    ```bash
    mvn spring-boot:run
    ```

---

## Troubleshooting

- **429 Too Many Requests**: You have hit your Google AI Studio quota. Wait 60 seconds for the reset.
- **404 Not Found**: Ensure you are using the correct model name (`gemini-3-flash-preview`) and endpoint version (`v1beta`).
- **415 Unsupported Media Type**: Ensure your request has the `Content-Type: application/json` header.

---

## 📄 License

This project is licensed under the MIT License.
