# FailWatch

> A lightweight failure detection system for distributed environments, built with Java and TCP sockets.

---

## 📌 Overview

**FailWatch** is a simple and effective system for **detecting node failures** in a distributed network. It simulates periodic communication between nodes using heartbeats, and uses a timeout-based detector to identify and manage suspected node failures.

This project is designed for academic and experimental purposes, especially in the context of **Distributed Systems** and **Fault Tolerance** studies.

---

## 🎯 Features

- **Heartbeat-based Failure Detection**
  - Nodes send periodic heartbeat messages.
  - A central monitor verifies the presence of each node.

- **Timeout Mechanism**
  - If a node fails to send a heartbeat within a configurable timeout, it is marked as *suspected*.

- **Recovery Detection**
  - If a suspected node starts responding again, it is marked as *recovered*.

- **Manual Failure Simulation**
  - Nodes can simulate failures by stopping heartbeat responses (via terminal commands).

- **Real-time Logs**
  - All critical events (failures, recoveries) are printed to the terminal.

- **Configurable Parameters**
  - Heartbeat interval and timeout can be set at runtime.

---

## 🧱 Architecture
```
+----------------------+ +----------------------+
| NodeSimulator 1 | <--> | NodeMonitor |
+----------------------+ +----------------------+
| Heartbeat Sender | | Listener + Detector |
| Simulated Failure | | Logs + Status |
+----------------------+ +----------------------+
```

---

## ⚙️ Technologies

- **Language:** Java 17+
- **Communication:** TCP sockets
- **Architecture:** Modular (monitor and simulator separated)
- **Interface:** Command-line based (CLI)

---

## 🚀 How to Run

### ✅ Compile

```bash
javac detector/monitor/*.java detector/simulador/*.java
```

### 🖥 Start Node Simulator (in a separate terminal)
```bash
java -cp . detector.simulador.NodeSimulator 5001
```

### Use commands:
- **falhar** — simulate failure
- **voltar** — simulate recovery

### 📡 Start Node Monitor
```bash
java -cp . detector.monitor.NodeMonitor 127.0.0.1:5001
```

### 📝 Example Output
- [Mon Jun 10 15:10:01 2025] Monitor listening on port 5000
- [Mon Jun 10 15:10:12 2025] [SUSPECTED] Failure detected on node: 127.0.0.1:5001
- [Mon Jun 10 15:10:24 2025] [RECOVERY] Node back online: 127.0.0.1:5001

## 📁 Project Structure
```
src/
├── detector/
│   ├── monitor/
│   │   ├── NodeMonitor.java
│   │   ├── HeartbeatSender.java
│   │   ├── HeartbeatListener.java
│   │   └── FailureDetector.java
│   └── simulador/
│       └── NodeSimulator.java
```

## 📚 Educational Use
This project was created for learning and demonstration purposes in Distributed Systems classes. It provides a foundational implementation of failure detection using heartbeats, timeout policies, and CLI-based simulation.
