document.addEventListener('DOMContentLoaded', function () {
    const messagesEl = document.getElementById('messages');
    const input = document.getElementById('messageInput');
    const sendBtn = document.getElementById('sendBtn');
    const form = document.getElementById('chatForm');
    const typingEl = document.getElementById('typingIndicator');
    const suggestionsEl = document.getElementById('suggestions');

    function appendMessage(sender, text, createdAt, html) {
        const div = document.createElement('div');
        div.className = 'msg ' + sender;
        let time = '';
        if (createdAt) {
            try {
                time = '<div class="ts">' + new Date(createdAt).toLocaleString() + '</div>';
            } catch (e) {
                time = '<div class="ts">' + createdAt + '</div>';
            }
        }
        if (sender === 'user') {
            div.innerHTML = '<div class="bubble user-bubble"><strong>You:</strong> ' + escapeHtml(text) + '</div>' + time;
        } else {
            // assistant may include html block
            const content = html ? html + '<div class="assistant-text">' + escapeHtml(text) + '</div>' : '<div class="assistant-text">' + escapeHtml(text) + '</div>';
            div.innerHTML = '<div class="bubble assistant-bubble"><strong>Assistant:</strong> ' + content + '</div>' + time;
        }
        messagesEl.appendChild(div);
        messagesEl.scrollTop = messagesEl.scrollHeight;
    }

    function escapeHtml(s) {
        if (!s) return '';
        return s.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    }

    function showTyping(show) {
        typingEl.style.display = show ? 'block' : 'none';
    }

    function renderSuggestions(list) {
        suggestionsEl.innerHTML = '';
        if (!list || !list.length) return;
        list.forEach(item => {
            const btn = document.createElement('button');
            btn.className = 'chip';
            btn.textContent = item;
            btn.addEventListener('click', function () {
                appendMessage('user', item);
                sendMessage(item);
            });
            suggestionsEl.appendChild(btn);
        });
    }

    async function loadHistory() {
        try {
            const res = await fetch(contextPath + '/chat?action=getHistory');
            const data = await res.json();
            messagesEl.innerHTML = '';
            data.forEach(m => appendMessage(m.sender === 'user' ? 'user' : 'assistant', m.message, m.createdAt, null));
        } catch (e) {
            console.error(e);
        }
    }

    async function sendMessage(msg) {
        try {
            showTyping(true);
            const formData = new URLSearchParams();
            formData.append('message', msg);
            const res = await fetch(contextPath + '/chat', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: formData.toString()
            });
            const data = await res.json();
            showTyping(false);
            if (data.reply) {
                appendMessage('assistant', data.reply, new Date().toISOString(), data.html ? (new DOMParser().parseFromString(data.html, 'text/html').body.innerHTML) : null);
            }
            if (data.suggestions) renderSuggestions(data.suggestions);
        } catch (e) {
            showTyping(false);
            console.error(e);
        }
    }

    // UI events
    sendBtn.addEventListener('click', function () {
        const text = input.value.trim();
        if (!text) return;
        appendMessage('user', text);
        input.value = '';
        sendMessage(text);
    });

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        sendBtn.click();
    });

    document.querySelectorAll('.quick-actions button').forEach(b => {
        b.addEventListener('click', function () {
            const text = b.getAttribute('data-msg');
            appendMessage('user', text);
            sendMessage(text);
        });
    });

    document.getElementById('explainBtn').addEventListener('click', async function () {
        try {
            const res = await fetch(contextPath + '/chat?action=explain');
            const data = await res.json();
            document.getElementById('explainArea').textContent = data.explain;
        } catch (e) {
            console.error(e);
        }
    });

    loadHistory();
});