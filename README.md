# IoT-Based Smart Door Lock Using Java & Spring Boot

## 📌 Overview
This project is an **IoT-based Smart Door Lock System** built using **Java**, **Spring Boot**, and **Arduino**.  
It uses **serial communication** to send unlock commands to an Arduino-based door lock mechanism and authenticate users via a backend API.

The project supports two modes:
1. **Normal Mode** – Connects to Arduino via serial port and checks password/OTP from backend.
2. **Simulation Mode** – Runs without Arduino hardware, simulating the unlock process for testing.

---

## ⚙️ Features
- **Password & OTP Authentication** (via Spring Boot API)
- **Serial Communication** with Arduino (`jSerialComm` library)
- **Simulation Mode** for testing without hardware
- **Mock Signals** and **Mock Responses** for quick validation
- **Cross-Platform** – Works on Windows, Mac, and Linux

---

## 🛠️ Tech Stack
- **Java 18**
- **Spring Boot** (for backend API)
- **Arduino UNO** (or compatible board)
- **jSerialComm** (for serial communication)
- **HTTP API Calls** for authentication

---

## 📂 Project Structure
