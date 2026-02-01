package com.ironlady.servlet;

import com.ironlady.ai.AiResponder;
import com.ironlady.db.DbUtil;
import com.ironlady.model.ChatMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // action=getHistory
        String action = req.getParameter("action");
        resp.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            if ("getHistory".equals(action)) {
                List<ChatMessage> history = DbUtil.getHistory(200);
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (int i = 0; i < history.size(); i++) {
                    ChatMessage m = history.get(i);
                    sb.append("{")
                      .append("\"id\":").append(m.getId()).append(',')
                      .append("\"sender\":\"").append(escape(m.getSender())).append("\",")
                      .append("\"message\":\"").append(escape(m.getMessage())).append("\",")
                      .append("\"createdAt\":\"").append(m.getCreatedAt()).append("\"")
                      .append("}");
                    if (i < history.size() - 1) sb.append(',');
                }
                sb.append(" ]");
                out.print(sb.toString());
            } else if ("explain".equals(action)) {
                out.print("{\"explain\":\"" + escape(AiResponder.explainFlow()) + "\"}");
            } else {
                resp.setStatus(400);
                out.print("{\"error\":\"Missing or invalid action\"}");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = req.getParameter("message");
        resp.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            if (message == null || message.trim().isEmpty()) {
                resp.setStatus(400);
                out.print("{\"error\":\"Message required\"}");
                return;
            }
            // save user message
            DbUtil.saveMessage("user", message);

            // generate AI response (structured)
            com.ironlady.ai.AiResult result = AiResponder.getResult(message);
            String aiText = result == null ? "" : result.getText();

            // save AI message (plain text for history)
            DbUtil.saveMessage("assistant", aiText);

            // build JSON response with optional suggestions and html
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("\"reply\":\"").append(escape(aiText)).append("\"");
            if (result != null && result.getSuggestions() != null) {
                sb.append(",\"suggestions\":[");
                List<String> s = result.getSuggestions();
                for (int i = 0; i < s.size(); i++) {
                    sb.append("\"").append(escape(s.get(i))).append("\"");
                    if (i < s.size() - 1) sb.append(',');
                }
                sb.append("]");
            }
            if (result != null && result.getHtml() != null) {
                sb.append(",\"html\":\"").append(escape(result.getHtml())).append("\"");
            }
            sb.append("}");

            out.print(sb.toString());
        }
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }
}
