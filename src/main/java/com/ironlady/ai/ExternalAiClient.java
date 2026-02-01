package com.ironlady.ai;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExternalAiClient {
    // Simple, optional HTTP client for an external AI endpoint.
    // Expects a POST JSON {"prompt":"..."} and returns response body as plain text or JSON containing reply.

    public static String fetchReply(String prompt) {
        if (!Config.isExternalEnabled()) return null;
        String url = Config.getExternalUrl();
        String key = Config.getExternalKey();
        if (url == null || url.isEmpty()) return null;
        HttpURLConnection conn = null;
        try {
            URL u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            if (key != null && !key.isEmpty()) conn.setRequestProperty("Authorization", "Bearer " + key);
            conn.setDoOutput(true);
            String body = "{" + "\"prompt\":\"" + escapeJson(prompt) + "\"}";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes("UTF-8"));
            }
            int code = conn.getResponseCode();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    code >= 400 ? conn.getErrorStream() : conn.getInputStream(), "UTF-8"))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line).append('\n');
                String resp = sb.toString().trim();
                // If API returns raw text, return it. If JSON, try to extract a "reply" field simply.
                int idx = resp.indexOf("\"reply\"");
                if (idx >= 0) {
                    // naive extraction
                    int c = resp.indexOf(':', idx);
                    if (c > 0) {
                        int start = resp.indexOf('"', c);
                        int end = resp.indexOf('"', start + 1);
                        if (start > 0 && end > start) {
                            return resp.substring(start + 1, end);
                        }
                    }
                }
                return resp;
            }
        } catch (Exception e) {
            // If external fails, don't break the app - return null so fallback is used.
            return null;
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
