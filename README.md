# 📥 File Download Manager

A JavaFX desktop application that simulates downloading multiple files simultaneously using **multithreading** and a **thread pool**.

Each file runs on its own thread, and its progress is displayed live through animated progress bars.

---

# 📌 Project Description

This project demonstrates how multiple downloads can run in parallel using Java threads.

The application visually simulates downloading files by updating progress bars in real time, making it an excellent educational example for learning:

- Threads
- Runnable
- Thread pools
- JavaFX concurrency
- Safe UI updates

---

# 🎯 Purpose

This project was created as a teaching example for students learning **Java Multithreading**.

Instead of only explaining threads theoretically, students can visually observe:

- Multiple threads running simultaneously
- Queued tasks waiting for available threads
- Progress bars updating independently
- Safe communication between worker threads and the JavaFX UI thread

---

# 🧩 Concepts Demonstrated

| Concept | Usage in Project |
|---|---|
| `Runnable` | `DownloadTask implements Runnable` |
| Thread Pool | `Executors.newFixedThreadPool(3)` |
| `Thread.sleep()` | Simulates download speed |
| `synchronized` | Protects shared UI updates |
| `Platform.runLater()` | Updates JavaFX UI safely |
| JavaFX + FXML | User interface built with Scene Builder |

---

# 🖥️ How It Works

## Workflow

1. Application launches
2. Five files appear with:
   - `0%`
   - `⏸ Queued`
3. User clicks **▶ Start Downloads**
4. Thread pool starts with maximum **3 concurrent threads**
5. The first 3 files begin downloading immediately
6. Remaining files wait in queue
7. As a file finishes:
   - The next queued file starts automatically
8. When all downloads finish:
   - Button changes to:
     - `✅ Start Again`

---

# 🗂️ Project Structure

```text
FileDownloadManager/
│
├── Main.java
│   └── Launches the JavaFX application
│
├── DownloadController.java
│   └── Handles UI events and thread pool
│
├── DownloadTask.java
│   └── Runnable task that simulates downloading
│
└── download.fxml
    └── JavaFX UI layout created using Scene Builder
```
