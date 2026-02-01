package com.ironlady.ai;

import java.util.List;

public class AiResult {
    private String text;
    private List<String> suggestions;
    private String html;

    public AiResult(String text, List<String> suggestions, String html) {
        this.text = text;
        this.suggestions = suggestions;
        this.html = html;
    }

    public String getText() { return text; }
    public List<String> getSuggestions() { return suggestions; }
    public String getHtml() { return html; }
}
