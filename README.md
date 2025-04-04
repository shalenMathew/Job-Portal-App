# 📱 Job Application Tracker App

An Android app to **track job applications** with a clean and user-friendly interface. Users can create, view, edit, and delete job entries efficiently.

---

## 🔧 Tech Stack

### 🖥️ Frontend (Android)
- **Kotlin**
- **Jetpack Compose**
- **MVVM Architecture**
- **Hilt (Dependency Injection)**
- **Retrofit** – for network communication
- **Room DB** – for local storage
- **Navigation Component**
- **Coroutines + Flow** – for async operations

### 🌐 Backend (Server)
- **Node.js** – JavaScript runtime environment
- **Express.js** – for building RESTful APIs
- **MongoDB** – NoSQL database
- **Mongoose** – MongoDB ODM (Object Data Modeling)

---

## ✅ Features

- 🔹 Add new job applications with title, description, and location
- 🔹 Edit and update existing jobs
- 🔹 Delete job entries
- 🔹 View all jobs from server
- 🔹 Full **CRUD support** (Create, Read, Update, Delete)
- 🔹 Seamless integration of local and remote data

---

## 📦 Backend API Endpoints

| Method | Endpoint       | Description          |
|--------|----------------|----------------------|
| GET    | `/jobs`       | Get all jobs         |
| POST   | `/jobs`        | Create a new job     |
| PUT    | `/jobs/{id}`   | Update job by ID     |
| DELETE | `/jobs/{id}`   | Delete job by ID     |

---

## 🚀 How to Run

### Android App
1. Clone the repo
2. Open in **Android Studio**
3. Run the app on emulator or device

