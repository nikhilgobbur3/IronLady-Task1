Demo Script (2-3 minutes)

1. Opening (20s) ✅
   - "Iron Lady empowers women with tech career programs. Problem: learners have quick questions about eligibility, fees, placements — static FAQ pages are slow for conversational follow-up."

2. Solution & Architecture (30s) 🔧
   - "We built the Iron Lady Smart Assistant: a lightweight servlet/JSP app. Frontend sends messages via Fetch to `/chat`. Server runs `AiResponder` (keyword + rule-based logic) to produce replies. All messages persist to MySQL via JDBC."
   - Show `web.xml`, `ChatServlet`, `AiResponder` briefly.

3. Live demo flow (60-90s) ▶️
   - Open app homepage. Point out quick-action buttons (Eligibility, Fees, Placement).
   - Click 'Eligibility' → show the assistant reply.
   - Type: "I want to switch to data science" → show personalized recommendation (Data Science bootcamp) and follow-up question.
   - Click "Show AI Flow (Demo)" → show explanation returned by `AiResponder.explainFlow()`.

4. Why AI vs static FAQ (20s) 💡
   - Quick contextual answers, follow-up questions, personalized recommendations (rule-based), and retained chat history for continuity.

5. Wrap-up & Next steps (10s)
   - "This is a demo-first minimal solution; possible extensions: more sophisticated NLP, external LLMs for richer replies, analytics on chat logs, OAuth user accounts."