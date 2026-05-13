# Scholaria: AI Research Assistant 🚀

Scholaria is a powerful, AI-driven research assistant built with **Spring Boot** and the **Google Gemini API**. It helps researchers, students, and professionals process text efficiently by summarizing content, suggesting topics, simplifying complex language, and more.

## ✨ Features

- **Summarize**: Generate multiple versions of summaries (Professional, Short, and Key Points).
- **Suggest**: Get topic suggestions based on your research content.
- **Simplify**: Translate complex technical jargon into easy-to-understand language.
- **Questions**: Automatically generate study questions from your text.
- **Extract**: Pull out key entities and keywords.
- **Citations**: Generate citations in various formats (APA, MLA, etc.).

## 🛠️ Technology Stack

- **Java 25**
- **Spring Boot 4.0.6**
- **Spring WebFlux** (WebClient for non-blocking API calls)
- **Google Gemini 3 Flash Preview** (Generative AI)
- **Lombok** (For cleaner code)
- **Dotenv** (For secure environment variable management)

## 🚀 Getting Started

### Prerequisites

- **Java 25** or higher installed.
- **Maven** for dependency management.
- A **Google Gemini API Key** (Get one from [Google AI Studio](https://aistudio.google.com/)).

### Installation

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/Musawenkosi-Moyo/Scholaria.git
    cd assistant
    ```

2.  **Configure Environment Variables**:
    Create a `.env` file in the root directory and add your API key:
    ```env
    GEMINI_API_KEY=your_api_key_here
    ```

3.  **Build the project**:
    ```bash
    mvn clean install
    ```

4.  **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

## 📖 API Usage

### Process Content
**Endpoint**: `POST /api/research/process`

**Request Body**:
```json
{
    "content": "Paste your research text here...",
    "operation": "summarize"
}
```

**Valid Operations**:
- `summarize`
- `suggest`
- `simplify`
- `questions`
- `extract`
- `citation`

## 🛡️ Security

This project uses a `.env` file to manage sensitive API keys. Ensure that the `.env` file is included in your `.gitignore` and never committed to version control.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request or open an issue for any bugs or feature requests.

## 📄 License

This project is licensed under the MIT License.
