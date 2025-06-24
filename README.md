
 🎉 SmartEventPlannerApp

A Java-based console application designed to simplify event management with features like user authentication, event creation, vendor assignments, feedback collection, payments, and budgeting.

> 🔧 Built using Java, JDBC, and MySQL.

---

 📌 Features

- 👥 User Management
  - Register and login as `organizer` or `attendee`
  - Role-based access control
- 📅 Event Management
  - Organizers can create and view events
- 🛠️ Vendor Management
  - Add vendors and assign them to specific events
- 💸 Financial Tracking
  - Add expenses and revenue for each event
  - Record payments and generate budget reports
- ⭐ Feedback System
  - Attendees can submit ratings and comments for events

---

 🧱 Tech Stack

| Technology | Purpose                   |
|------------|----------------------------|
| Java       | Application development    |
| JDBC       | Database connectivity      |
| MySQL      | Data storage and queries   |

---

 ⚙️ How to Run the Project Locally

 1️⃣ Clone the Repository

```bash
git clone https://github.com/Kathukutty08/SmartEventPlannerApp.git
cd SmartEventPlannerApp
```

 2️⃣ Setup the MySQL Database

- Create a MySQL database named `event_planner`
- Create the necessary tables using the schema below *(or from `schema.sql` if available)*:

```sql
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
    budget DOUBLE,
    venue VARCHAR(100),
    audience_size INT
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
    service_type VARCHAR(100)
);

CREATE TABLE event_vendors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    vendor_id INT
);

CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    amount DOUBLE,
    description VARCHAR(255)
);

CREATE TABLE expenses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    item VARCHAR(100),
    cost DOUBLE
);

CREATE TABLE revenues (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    source VARCHAR(100),
    amount DOUBLE
);
```

---

 3️⃣ Configure Database Connection

In `DBHelper.java`, update your MySQL credentials:

```java
static final String URL = "jdbc:mysql://localhost:3306/event_planner";
static final String USER = "your_username";
static final String PASS = "your_password";
```

---

### 4️⃣ Compile and Run

```bash
javac *.java
java SmartEventPlannerApp
```

---

 📌 Sample Workflow

1. Register/Login as an organizer
2. Create an event
3. Add vendors and assign to event
4. Add expenses, revenue, and record payments
5. View budget reports
6. Collect feedback from attendees

---

📈 Future Improvements

- 🌐 Frontend with React.js + Tailwind CSS
- 🐳 Docker support
- 📱 Android app version
- ✉️ Email/SMS notifications

---

 🙌 Author

Theja Sri 
GitHub: [@Kathukutty08](https://github.com/Kathukutty08)

---

 📃 License

This project is open-source and available under the [MIT License](LICENSE).
