<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Iron Lady Smart Assistant - Your intelligent companion for course information, enrollment, and support">
    <meta name="theme-color" content="#667eea">
    <title>Iron Lady Smart Assistant</title>
    
    <!-- Preconnect to Google Fonts for faster loading -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    
    <!-- Stylesheet -->
    <link rel="stylesheet" href="css/style.css">
    
    <!-- Favicon (optional - you can add your own) -->
    <!-- <link rel="icon" type="image/png" href="images/favicon.png"> -->
</head>
<body>
    <!-- Animated Background Particles (decorative) -->
    <div class="bg-decoration">
        <div class="particle particle-1"></div>
        <div class="particle particle-2"></div>
        <div class="particle particle-3"></div>
    </div>

    <div class="container">
        <!-- Header Section -->
        <header class="header">
            <h1>
                <span class="icon-wrapper">💎</span>
                Iron Lady Smart Assistant
            </h1>
            <p class="subtitle">Your intelligent companion for admissions, courses, and support</p>
        </header>

        <!-- Chat Window -->
        <div class="chat-window" id="chatWindow">
            <div id="messages"></div>
            <div id="typingIndicator" class="typing" style="display:none">Assistant is typing</div>
        </div>

        <!-- Quick Actions -->
        <div class="quick-actions">
            <button data-msg="eligibility" title="Check eligibility requirements">
                <span class="btn-icon">📋</span>
                Eligibility
            </button>
            <button data-msg="fees" title="View fee structure">
                <span class="btn-icon">💰</span>
                Fees
            </button>
            <button data-msg="placement" title="Placement information">
                <span class="btn-icon">🎯</span>
                Placement
            </button>
            <button data-msg="enroll" title="Enrollment process">
                <span class="btn-icon">✅</span>
                Enroll
            </button>
            <button data-msg="courses" title="Browse available courses">
                <span class="btn-icon">📚</span>
                Courses
            </button>
            <button data-msg="tell me a joke" title="Lighten the mood">
                <span class="btn-icon">😄</span>
                Tell a joke
            </button>
        </div>

        <!-- Suggestions Area -->
        <div id="suggestions" class="suggestions"></div>

        <!-- Chat Input Form -->
        <form id="chatForm" onsubmit="return false;">
            <div class="input-wrapper">
                <input 
                    type="text" 
                    id="messageInput" 
                    placeholder="Ask me anything about courses, fees, placement..." 
                    autocomplete="off"
                    aria-label="Type your message"
                />
            </div>
            <button id="sendBtn" type="submit" aria-label="Send message">
                <span class="send-icon">📤</span>
                <span class="send-text">Send</span>
            </button>
        </form>

        <!-- Explain Section -->
        <div class="explain">
            <button id="explainBtn" aria-label="Show AI flow demonstration">
                <span class="explain-icon">🔍</span>
                Show AI Flow (Demo)
            </button>
            <div id="explainArea"></div>
        </div>

        <!-- Footer Info -->
        <footer class="footer">
            <p class="footer-text">💡 Powered by AI • Available 24/7 • Instant Responses</p>
        </footer>
    </div>

    <!-- Context Path for JavaScript -->
    <script>
        const contextPath = "<%=request.getContextPath()%>";
    </script>
    
    <!-- Main Chat Script -->
    <script src="js/chat.js"></script>
</body>
</html>