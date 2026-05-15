document.addEventListener('DOMContentLoaded', () => {
  chrome.storage.local.get(['researchNotes'], function(result){
  if(result.researchNotes) {
    document.getElementById('notes').value = result.researchNotes;
  }
  });
document.getElementById('summarizeBtn').addEventListener('click', () => processContent('summarize'));
document.getElementById('suggestBtn').addEventListener('click', () => processContent('suggest'));
document.getElementById('simplifyBtn').addEventListener('click', () => processContent('simplify'));
document.getElementById('questionsBtn').addEventListener('click', () => processContent('questions'));
document.getElementById('extractBtn').addEventListener('click', () => processContent('extract'));
document.getElementById('citationBtn').addEventListener('click', () => processContent('citation'));
document.getElementById('saveNotesBtn').addEventListener('click', saveNotes);
});

async function processContent(operation) {
    try {
        const[tab]  = await chrome.tabs.query({active: true, currentWindow: true});
        const[{result}] = await chrome.scripting.executeScript({
            target: {tabId: tab.id},
            function: () => {
                return window.getSelection().toString();
            }
        });

        if (!result) {
            showResult('Please select some text to process.');
            return;
        }
        const response = await fetch('http://localhost:8080/api/research/process',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({content: result, operation: operation})   
        });
        if (!response.ok) {
            throw new Error(`API Error: ${response.status}`);
        }

        const data = await response.json();
        showResult(data.result || 'No result returned.');

    } catch (error) {
        showResult('Error: ' + error.message);
    }

}

async function saveNotes() {

    const notes = document.getElementById('notes').value;
    chrome.storage.local.set({researchNotes: notes}, function() {
        alert('Notes saved successfully!');
    });
}

function parseMarkdown(text) {
    text = text.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    text = text.replace(/^---$/gm, '<hr class="divider">');
    text = text.replace(/^### (.+)$/gm, '<h3 class="result-heading">$1</h3>');
    text = text.replace(/^## (.+)$/gm, '<h2 class="result-heading">$1</h2>');
    text = text.replace(/^# (.+)$/gm, '<h1 class="result-heading">$1</h1>');
    text = text.replace(/\*\*(.+?)\*\*/g, '<strong>$1</strong>');
    text = text.replace(/^[\*\-] (.+)$/gm, '<li>$1</li>');

    text = text.replace(/(<li>.*<\/li>\n?)+/g, (match) => `<ul class="result-list">${match}</ul>`);

    text = text.replace(/(?<!>)\n/g, '<br>');

    return text;
}

function showResult(content){
    const resultsDiv = document.getElementById('results');
    if (resultsDiv) {
        const formatted = parseMarkdown(content);
        resultsDiv.innerHTML = `<div class="result-item"><div class="result-content">${formatted}</div></div>`;
        resultsDiv.scrollTop = 0;
    } else {
        console.error('Results element not found');
    }
}