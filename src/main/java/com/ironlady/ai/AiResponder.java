package com.ironlady.ai;

import java.util.*;

public class AiResponder {

    private static final Map<String, String> KEY_RESPONSES = new LinkedHashMap<>();
    private static final List<String> PROGRAMS = Arrays.asList("Software Engineering", "Data Science", "Web Development", "Career Transition Bootcamp");

    static {
        KEY_RESPONSES.put("eligibility", "Eligibility depends on the program. Typically you should have a basic education (10+2). For career switchers, we accept learners with demonstrated motivation or relevant learning history. Want to tell me which program you're interested in?");
        KEY_RESPONSES.put("fees", "Fees vary by program. We offer monthly payment plans and some scholarships for eligible learners. If you tell me the program (e.g., Data Science), I can show a typical fee range.");
        KEY_RESPONSES.put("placement", "We help with interview prep, mentorship and connect learners with hiring partners. Placement support includes mock interviews and resume reviews.");
        KEY_RESPONSES.put("enroll", "Enrollment is simple: 1) Choose a program, 2) Fill the form on our site, 3) Complete payment or request a scholarship. Would you like the enrollment link?");
        KEY_RESPONSES.put("courses", "We offer short upskilling courses and comprehensive bootcamps. Programs: " + String.join(", ", PROGRAMS));
        KEY_RESPONSES.put("mentor", "We provide industry mentors for career guidance. You can request mentor time during the program.");
        KEY_RESPONSES.put("scholarship", "We allocate partial scholarships for candidates who show financial need and strong motivation. Apply during enrollment for consideration.");
        KEY_RESPONSES.put("duration", "Program durations vary: short courses (4-8 weeks), bootcamps (12-24 weeks). Which one interests you?");
    }

    public static String getResponse(String message) {
        // Backwards-compatible wrapper
        AiResult r = getResult(message);
        return r == null ? "" : r.getText();
    }

    public static AiResult getResult(String message) {
        if (message == null || message.trim().isEmpty()) {
            String t = "Hi! I'm the Iron Lady Smart Assistant. How can I help you today? Ask about eligibility, fees, placement, courses, or enrollment.";
            return new AiResult(t, Arrays.asList("Eligibility", "Fees", "Placement", "Courses"), null);
        }

        String m = message.toLowerCase();

        // Quick greetings
        if (m.matches(".*\\b(hi|hello|hey|good morning|good evening)\\b.*")) {
            String t = "Hello! I'm the Iron Lady Smart Assistant 👋. I can help with programs, eligibility, fees, enrollment, placements, and recommendations. What would you like to know?";
            return new AiResult(t, Arrays.asList("Courses", "Eligibility", "Fees"), null);
        }

        // Keyword matching (first hit wins to keep predictable)
        for (Map.Entry<String, String> e : KEY_RESPONSES.entrySet()) {
            if (m.contains(e.getKey())) {
                String text = e.getValue();
                List<String> sug = Arrays.asList("More details", "Apply", "Scholarships");
                // Add small program card when asking about courses or fees
                String html = null;
                if (e.getKey().equals("courses")) {
                    html = "<div class=\"program-card\"><strong>Programs</strong><ul>" +
                            "<li>Data Science — Projects, Mentorship</li>" +
                            "<li>Web Development — Build real websites</li>" +
                            "<li>Career Transition Bootcamp — Portfolio + Interviews</li>" +
                            "</ul></div>";
                }
                return new AiResult(text, sug, html);
            }
        }

        // Rule-based personalization: recommend programs based on keywords
        if (m.matches(".*\\b(data|machine learning|ml|ai)\\b.*")) {
            String t = "It sounds like you're interested in Data Science / ML. Recommended program: Data Science bootcamp (projects + mentorship). Would you like curriculum, fees, or scholarship info?";
            String html = "<div class=\"program-card\"><strong>Data Science</strong><p>Duration: 12-24 wks · Projects · Mentorship</p><p>Quick actions: <em>Curriculum</em> | <em>Fees</em></p></div>";
            return new AiResult(t, Arrays.asList("Curriculum", "Fees", "Apply"), html);
        }
        if (m.matches(".*\\b(web|frontend|backend|full[- ]?stack)\\b.*")) {
            String t = "For web development, our Web Development track is recommended: projects, mentor reviews, interview prep. Would you like curriculum or schedule?";
            String html = "<div class=\"program-card\"><strong>Web Development</strong><p>Duration: 12 wks · Hands-on projects</p></div>";
            return new AiResult(t, Arrays.asList("Curriculum", "Schedule", "Apply"), html);
        }
        if (m.matches(".*\\b(career switch|career change|switching)\\b.*")) {
            String t = "We have a Career Transition Bootcamp focused on building a portfolio and interview skills. I can show eligibility and scholarships too. Interested?";
            return new AiResult(t, Arrays.asList("Eligibility", "Scholarships"), null);
        }

        // Fallback: try external AI for a friendly/humorous reply, otherwise use local humor templates
        String external = ExternalAiClient.fetchReply(message);
        if (external != null && !external.trim().isEmpty()) {
            List<String> sug = Arrays.asList("Tell a joke", "Courses", "Fees");
            return new AiResult(external, sug, null);
        }

        // Local humor fallback
        List<String> jokes = Arrays.asList(
                "I didn't catch that — but here's a smile: Why did the developer go broke? Because he used up all his cache! 😄",
                "Hmm, not sure — but here's a fun thought: Learning is like coding; sometimes you need to read the docs... and sometimes you need coffee. ☕",
                "I'm all ears — but in the meantime: Did you hear about the computer that took a nap? It had a hard drive! 😴"
        );
        String t = jokes.get(new java.util.Random().nextInt(jokes.size())) + "\nCan you tell me which program or topic (e.g., 'Data Science', 'Fees', 'Enrollment')?";
        return new AiResult(t, Arrays.asList("Tell a joke", "Eligibility", "Courses"), null);
    }

    // For demo / diagnostics
    public static String explainFlow() {
        return "AI Flow: 1) Front-end sends user message → 2) Server uses keyword matching + rule-based logic in AiResponder to pick an answer → 3) Answer + user message stored in MySQL via JDBC → 4) UI displays messages. This improves on static FAQ by giving context-aware, conversational replies and quick follow-ups.";
    }
}
