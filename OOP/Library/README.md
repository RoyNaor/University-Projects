
# Library Management System

## 📖 Project Description
The **Library Management System** is a Python-based application designed to efficiently manage a library's inventory, users, and loan operations. The system includes features such as adding books, user registration, lending and returning books, maintaining waiting lists, generating reports on popular books, and managing user notifications.

---

## 🚀 Instructions to Run the Project

### ✅ Prerequisites
**Python Installation**:
    - Ensure Python 3.6 or higher is installed on your system.


### ▶️ Steps to Run
1. Open an ide (like Pycharm) and navigate to the project’s start directory.
2. Run the Landing.py script:
   ```bash
   python landing.py
   ```

---

## ✨ Features

### 📚 Book Management
- Add new books to the library’s inventory.
- Update and remove books from the system.
- Search books by title, author, or genre.

### 👤 User Management
- Register new users and allow them to log in.
- Manage user notifications for events like book availability.

### 🔄 Lending System
- Lend books to registered users and track their status.
- Maintain a waiting list for books currently on loan.
- Notify users when books become available.

### 📥 Returning System
- Handle book returns and update inventory automatically.
- Notify users in the waiting list when a book becomes available.

### 📊 Reports and Analytics
- Display popular books based on loan and request counts.

---

## 🛠️ Design Patterns Implemented

### 1️⃣ **Singleton Pattern**
- **Usage**: Implemented in the `NotificationSystem` and `Logger` classes to ensure only one instance of the notification and logger managers exists throughout the application.

### 2️⃣ **Factory Pattern**
- **Usage**: Used in the `BookFactory` class to create `Book` objects in a consistent and efficient way.

### 3️⃣ **Observer Pattern**
- **Usage**: Utilized in the `NotificationSystem` to manage user notifications and updates efficiently.
  - **Subject**: The `NotificationSystem` class acts as the **Subject**, maintaining a list of observers (users) and notifying them when relevant events occur (e.g., book availability updates).
  - **Observers**: The `User` class instances act as **Observers**, subscribing to the `NotificationSystem` to receive updates about events like book availability or other library notifications.

### 4️⃣ **Decorator Pattern**
- **Usage**: Utilized in the `Logger` class to wrap methods with logging functionality. Decorators like `log_decorator`, `log_search`, and `log_with_param` are used to:
  - Log success and failure messages for method execution.
  - Automatically handle exceptions and log errors when they occur.
  - Log additional context, such as parameters and actions performed.
  - Example: Wrapping a function with `log_decorator("success", "fail")` ensures any success or failure is logged automatically, reducing redundant code and centralizing logging logic.

---

## 📂 Project Structure

### Main Directories and Files:
- **`start/`**: Entry point of the application. It contains essential files to initialize and configure the library management system, like `main.py` The central script that sets up the system and initializes data
- **`helpers/`**: Contains utility functions, file handling logic, and helpers like `FileHandler`, `Paths`, and `utils`.
- **`design/`**: Includes implementations of design patterns such as `Observer`, `Subject`, and `NotificationSystem`.
- **`buttons/`**: Contains logic for user actions, such as adding, searching, removing, lending, and returning books.
- **`files/`**: Directory for storing application data in CSV files:
  - `books.csv`: Main inventory of the library.
  - `available_books.csv`: Books currently available for lending.
  - `loaned_books.csv`: Books currently loaned out.
  - `popular_books.csv`: Most borrowed/requested books.
  - `users.csv`: User data, including login credentials and messages.
  - `logs.txt`: Application logs for debugging and tracking events.

---

## 📝 Example Use Case
1. Register as a new user.
2. login in after registration
3. Add books to the library’s inventory.
4. Search for books by title, author, or genre.
5. Lend books to users and maintain a waiting list for unavailable books.
6. Return books and automatically notify waiting users.
7. View popular books and analytics on library usage.

---
## 🛠️ Error Handling:
In the project we used Try and Except.
This following Error does not effect the project and happens when closing an active window at the same time that an alert_lable is on the screen

```bash
invalid command name "xxxxdestroy"
    while executing
"xxxxdestroy"
    ("after" script)
```

Enjoy managing your library with ease! 📚✨
