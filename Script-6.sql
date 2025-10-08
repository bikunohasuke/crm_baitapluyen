CREATE DATABASE crm_app;

USE crm_app;

CREATE TABLE IF NOT EXISTS roles (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    fullname VARCHAR(100) NOT NULL,
    avatar VARCHAR(100),
    role_id INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS status (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS jobs (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    start_date DATE,
    end_date DATE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tasks (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    start_date DATE,
    end_date DATE,
    user_id INT NOT NULL,
    job_id INT NOT NULL,
    status_id INT NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles (id)  ON DELETE CASCADE;
ALTER TABLE tasks ADD FOREIGN KEY (user_id) REFERENCES users (id)  ON DELETE CASCADE;
ALTER TABLE tasks ADD FOREIGN KEY (job_id) REFERENCES jobs (id)  ON DELETE CASCADE;
ALTER TABLE tasks ADD FOREIGN KEY (status_id) REFERENCES status (id)  ON DELETE CASCADE;

INSERT INTO roles( name, description ) VALUES ("ROLE_ADMIN", "Quản trị hệ thống");
INSERT INTO roles( name, description ) VALUES ("ROLE_MANAGER", "Quản lý");
INSERT INTO roles( name, description ) VALUES ("ROLE_USER", "Nhân viên");

INSERT INTO status( name ) VALUES ("Chưa thực hiện");
INSERT INTO status( name ) VALUES ("Đang thực hiện");
INSERT INTO status( name ) VALUES ("Đang thực hiện");

INSERT INTO users(email, password, fullname, role_id) VALUE('tranvanb@gmail.com', '1234', 'Tran Van B', 2);

INSERT INTO users(email, password, fullname, avatar, role_id) VALUE('nguyenthic@gmail.com', '12345678', 'Tran Thi C', '', 3);

INSERT INTO jobs(name, start_date, end_date) VALUE('Triển khai dự án', '2025-06-05', '2025-06-25');


UPDATE status SET name = 'Đã hoàn thành' WHERE id = 3;

SELECT *
FROM users u
WHERE u.email = 'buiminhd@gmail.com' AND u.password = '12345';

SELECT *
FROM users u;

SELECT * FROM users u JOIN roles r ON u.role_id = r.id;


INSERT INTO roles(name, description) VALUE('Test', 'Test Desc');

DELETE FROM users u WHERE u.id = '';

SELECT * FROM roles r;

SELECT * FROM tasks t;

SELECT * FROM jobs j;

SELECT * FROM status s;



SELECT t.id, t.name, t.start_date, t.end_date, u.fullname AS user, j.name AS job, s.name AS status
FROM tasks t
JOIN users u ON t.user_id = u.id
JOIN jobs j ON t.job_id = j.id
JOIN status s ON t.status_id = s.id;

SELECT t.id, t.name, t.start_date, t.end_date, j.name AS job, s.name AS status, s.id AS status_id
FROM tasks t
JOIN jobs j ON t.job_id = j.id
JOIN status s ON t.status_id = s.id
WHERE t.user_id = 4;

DELETE FROM jobs j WHERE j.id = '';

DELETE FROM roles r WHERE r.id = '';

DELETE FROM tasks t WHERE t.id = '';

DELETE FROM users u WHERE u.id = '';

SELECT * FROM jobs j WHERE j.id = 1;

SELECT * FROM roles r WHERE r.id = 1;

SELECT * FROM users u WHERE u.id = 1;

SELECT * FROM tasks t WHERE t.id = 3;

SELECT * FROM tasks t WHERE t.user_id = 4 AND t.status_id = 1;

SELECT t.id, t.name, t.start_date, t.end_date, t.user_id, t.job_id, j.name AS job, t.status_id, s.name AS status 
FROM tasks t 
JOIN jobs j ON t.job_id = j.id
JOIN status s ON t.status_id = s.id
WHERE t.id = 3;

UPDATE jobs j SET j.name = 'E-commerce', j.start_date = '2025-05-30', j.end_date = '2025-07-30'
WHERE j.id = 4;

UPDATE roles r SET r.name = '', r.description = '' WHERE r.id = '';

UPDATE users u SET u.email = '', u.password = '', u.fullname = '', u.avatar = '', u.role_id = ''
WHERE u.id = 2;

UPDATE tasks t SET t.name = '', t.start_date = '', t.end_date = '', t.user_id = 1, t.job_id = 1, t.status_id = 1
WHERE t.id = 1;

UPDATE tasks t SET t.status_id = ? WHERE t.id = ?

SELECT * 
FROM tasks t
WHERE t.job_id = 4;

SELECT * 
FROM tasks t
WHERE t.user_id = 4;

SELECT t.id, t.name, t.start_date, t.end_date, t.job_id, j.name AS job, u.avatar AS user_avatar, u.fullname AS user, t.status_id, s.name AS status
FROM tasks t 
JOIN jobs j ON t.job_id = j.id
JOIN users u ON t.user_id = u.id
JOIN status s ON t.status_id = s.id
WHERE t.job_id = 4
ORDER BY u.fullname, s.id;
