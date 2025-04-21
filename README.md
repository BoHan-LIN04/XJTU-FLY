# ✈️  XJTU‑FLY – Metasearch & Smart Recommendation for Flights

XJTU‑FLY is an end‑to‑end flight‐search platform that **crawls multiple OTAs / airline APIs, stores & cleans large‑scale datasets with Spark, and serves personalised recommendations via REST / LLM agents**. It consists of three sub‑projects:

| Module | Tech Highlights | Path |
| ------ | --------------- | ---- |
| **FlightHelper_UI** | React 18 + Remix + Tailwind; Ant Design & MapGL visualisations | `FlightHelper_UI/` :contentReference[oaicite:1]{index=1} |
| **FlightHelper_Backend** | Spring Boot 3, MyBatis‑Plus, MySQL; JWT security & LLM chatbot gateway | `FlightHelper_Backend/` :contentReference[oaicite:2]{index=2} |
| **FlightHelper_BigData** | Spark 2.4 (Scala 2.11), Hive, Spark MLlib pipelines for fare prediction & user clustering | `FlightHelper_BigData/` :contentReference[oaicite:3]{index=3} |

---

## 🌐 Architecture

1. **Data Ingestion** – scheduled crawlers pull fare / schedule / seat‑map data to HDFS → Spark jobs cleanse & join heterogeneous sources.  
2. **Offline Analytics** – Spark MLlib computes price trends, demand hotspots and user segments; results pushed to MySQL / Redis.  
3. **REST + LLM Gateway** – Spring Boot exposes search / recommend APIs; an optional LLM endpoint (e.g. GPT Proxy) turns natural‑language queries into structured filters.  
4. **Interactive UI** – Remix SPAs consume the APIs, render heat‑maps, price curves and smart suggestions; supports PWA offline mode.

---

## 🚀 Quick Start

```bash
# 1️⃣ Big‑Data layer (Spark local‑mode)
cd FlightHelper_BigData
mvn clean package -DskipTests
spark-submit --class org.flight_helper.Job main.jar

# 2️⃣ Backend API
cd ../FlightHelper_Backend
mvn spring-boot:run  # default port 8080

# 3️⃣ Frontend
cd ../FlightHelper_UI
npm install
npm run dev          # http://localhost:3000
