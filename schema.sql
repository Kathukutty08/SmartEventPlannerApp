CREATE DATABASE event_planner;
USE event_planner;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50),
    role VARCHAR(20)
);

CREATE TABLE events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    organizer_id INT,
    event_name VARCHAR(100),
    budget DECIMAL(10,2),
    venue VARCHAR(100),
    audience_size INT,
    event_date DATE
);

CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    rating INT,
    comment TEXT
);

CREATE TABLE vendors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    service_type VARCHAR(50)
);

CREATE TABLE event_vendors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    vendor_id INT
);

CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    amount DECIMAL(10,2),
    description VARCHAR(255)
);

CREATE TABLE expenses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    amount DECIMAL(10,2),
    description VARCHAR(255)
);

CREATE TABLE revenues (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    amount DECIMAL(10,2),
    source VARCHAR(100)
);
