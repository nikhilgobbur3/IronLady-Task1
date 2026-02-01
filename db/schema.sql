-- MySQL schema for Iron Lady Smart Assistant
CREATE DATABASE IF NOT EXISTS ironlady CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ironlady;

CREATE TABLE IF NOT EXISTS chat_history (
  id INT AUTO_INCREMENT PRIMARY KEY,
  sender VARCHAR(20) NOT NULL,
  message TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Sample data
INSERT INTO chat_history (sender, message) VALUES ('assistant', 'Hello! Welcome to Iron Lady Smart Assistant. Ask about eligibility, fees, placement or courses.');
