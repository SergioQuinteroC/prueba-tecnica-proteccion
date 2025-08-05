-- Insertar usuarios de prueba
-- Las contraseñas están encriptadas con BCrypt
-- admin123 -> $2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.
-- password -> $2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa

-- Insertar usuarios solo si no existen
INSERT INTO users (name, email, password, role) 
SELECT 'Admin User', 'admin@example.com', '$2a$10$NsUT8EUBHYZLUUYTmj5z/evLA6b.0joz6laoIrKfyNBVlkXkmqjW2', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@example.com');

INSERT INTO users (name, email, password, role) 
SELECT 'Regular User', 'user@example.com', '$2a$10$NsUT8EUBHYZLUUYTmj5z/evLA6b.0joz6laoIrKfyNBVlkXkmqjW2', 'USER'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'user@example.com'); 