# 🏢 TechNova Employee Management System

A modern and user-friendly **Employee Management System** built using **Java and Swing**, designed to efficiently manage employee records with powerful features and visual analytics.

This application demonstrates core programming principles along with an interactive graphical user interface for real-world usability.

---

## 🚀 Overview

The system manages different types of employees such as:
- 👨‍💼 Manager
- 👩‍💻 Engineer
- 🎓 Intern

Each employee shares common attributes like **Name, ID, Salary, and Domain**, while specific roles include additional properties (e.g., team size for managers, duration for interns).

---

## ✨ Key Features

### 🔹 Employee Operations
- ➕ Add Employee (Manager / Engineer / Intern)
- 📋 Display Employees (All / Role-based)
- 🔍 Search Employee by ID
- 🧑‍💼 Search Employee by Name
- ✏️ Update Employee Details
- ❌ Delete Employee

### 🔹 Data Processing
- 💰 Sort Employees by Salary
- 📊 Filter Employees by Salary Range
- 🔢 Count Employees (All / Role-wise)
- 📈 Calculate Average Salary

### 🔹 Visual Analytics
- 📊 Employee Distribution (Pie Chart)
- 📉 Salary Comparison (Bar Chart)

---

## 🧠 Concepts Implemented

- Object-Oriented Programming (OOP)
  - Inheritance
  - Method Overriding
  - Encapsulation
- Java Swing (GUI Development)
- Event Handling
- Data Validation
- Collections (`ArrayList`)
- Modular Code Structure

---

## 🏗️ Project Structure
Project/
├── src/
│ └── ppl_minipro/
│ ├── Employee.java
│ ├── Manager.java
│ ├── Engineer.java
│ ├── Intern.java
│ ├── EmployeeStore.java
│ ├── MainFrame.java
│ ├── HomePage.java
│ ├── AddEmployeePage.java
│ ├── DisplayPage.java
│ ├── ManagePage.java
│ ├── AnalyticsPage.java
│ └── UITheme.java
├── lib/
│ └── jfreechart-1.5.6.jar
└── README.md

---

## 🎨 UI Highlights

- 🌙 Modern dark-themed interface
- 🎯 Clean and structured layouts
- 🔄 Multi-page navigation using `CardLayout`
- 📋 Tabular employee display
- ⚡ Dynamic forms with conditional fields
- 📊 Integrated charts for insights

---

## 📊 Analytics Dashboard

The system includes a dedicated analytics section:
- 📌 Role-wise employee distribution (Pie Chart)
- 📌 Salary insights (Bar Chart)
- 📌 Real-time workforce statistics

Powered by **JFreeChart**

---

## 🧪 Sample Data

The application includes **preloaded dummy employee records** to:
- Avoid empty UI states
- Demonstrate functionality immediately
- Provide better user experience

---

## 🛠️ Tech Stack

| Technology     | Usage |
|----------------|------|
| Java           | Core programming |
| Java Swing     | GUI development |
| JFreeChart     | Data visualization |

---

## ⚙️ Setup & Installation

### 🔧 Prerequisites
- Java JDK (17 or above recommended)
- Eclipse / IntelliJ IDEA

---

### ▶️ Steps to Run

1. **Clone the repository**
```bash
git clone https://github.com/your-username/your-repository-name.git
```

2. **Open the project in your IDE**
  
3. **Add JFreeChart Library**
Go to:
Build Path → Configure Build Path → Libraries
Click:
Add External JARs
Select:
jfreechart-1.5.6.jar

4. **Ensure Project Configuration**

All files use the same package:
package ppl_minipro;
Remove module-info.java if present
Run the Application
Run → MainFrame.java

---

## 📌 Usage

Navigate using the home screen
Add and manage employee records
Use filters and sorting for better data handling
View analytics for insights

---

## 🔮 Future Enhancements

💾 Database integration (MySQL / MongoDB)
🌐 Web-based version (Spring Boot + React)
🔐 User authentication system
📤 Export reports (PDF/Excel)
☁️ Cloud deployment

---

📬 Contact

For queries or feedback, feel free to connect.

---

⭐ Acknowledgment

This project is built to demonstrate practical implementation of:
Java Programming concepts
GUI development
Real-world application design
