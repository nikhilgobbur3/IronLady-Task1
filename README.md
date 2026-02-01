<<<<<<< HEAD
# Iron Lady – Task 1
AI-based Customer Interaction Solution

This project is an AI-powered Smart Assistant that helps users understand
Iron Lady’s courses, eligibility, fees, enrollment process, and placement support.

## Demo Video


## Tech Stack
- Java
- JSP & Servlets
- JDBC
- MySQL
- HTML, CSS, JavaScript
- Apache Tomcat 9
=======
# Iron Lady Smart Assistant (Maven Java Web App)

## Overview
Iron Lady Smart Assistant is a demo AI-assisted chatbot built as a Maven-based Java web application (Servlets + JSP) to help learners understand programs, check eligibility, learn enrollment steps, answer FAQs, and receive simple personalized recommendations.

## Tech Stack
- Java 8 (or 11)
- Servlet API, JSP
- MySQL
- JDBC (no ORM)
- Apache Tomcat 9
- Frontend: JSP + HTML + CSS + Vanilla JavaScript (Fetch API)

## Project structure
- `src/main/java` - Java sources (`db`, `ai`, `servlet`, `model`)
- `src/main/webapp` - `index.jsp`, `css/`, `js/`, `WEB-INF/web.xml`
- `pom.xml`

## Setup & Run
1. Install Java 8 or 11, Maven, MySQL, Tomcat 9.
2. Create DB and table using `db/schema.sql` or run the SQL manually.
   - `mysql -u root -p < db/schema.sql`
3. Update DB credentials in `DbUtil.java` (DB_URL, DB_USER, DB_PASS).
4. Build WAR:
   - `mvn clean package`
   - The WAR file is `target/ironlady-ai-assistant.war`.
5. Deploy to Tomcat:
   - Copy the WAR to `TOMCAT_HOME/webapps/` and start Tomcat.
   - Open `http://localhost:8080/ironlady-ai-assistant/`.

## Demo script
See `docs/demo_script.md` (2–3 minute talk track plus actions).

## Notes
- AI is implemented with keyword matching and rule-based logic in `AiResponder`. It now returns contextual suggestions and simple program cards for richer demo replies and includes humorous local fallbacks.
- Optional external AI: You can enable an external AI API by updating `src/main/resources/config.properties`:

  external.ai.enabled=true
  external.ai.url=https://your.api/endpoint
  external.ai.key=YOUR_API_KEY

  The app will attempt to call the API for richer or humorous replies but always falls back to deterministic local responses if the external call fails.

- Chat history persists in MySQL using JDBC (see `DbUtil`). The app can auto-create the `ironlady` database and `chat_history` table if needed (update DB credentials in `DbUtil`).
>>>>>>> e118a66 (Initial commit for task 1)
