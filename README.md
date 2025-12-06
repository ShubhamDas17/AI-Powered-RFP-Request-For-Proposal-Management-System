Here is a perfect README.md for your project — clean, professional, and complete.
Just copy–paste into your GitHub or project folder.


---

🚀 AI-Powered RFP Management System

Spring Boot + React + MySQL + Gemini AI

This project is an end-to-end AI-driven RFP (Request for Proposal) Management System that helps organizations evaluate vendor proposals automatically using Google Gemini AI.

It includes a Spring Boot backend, React frontend, and Google Gemini AI integration for intelligent proposal scoring.


---

📌 Features

✅ 1. Create RFP

User enters raw requirement text.

System stores it and can also extract structured JSON using AI.



---

✅ 2. Vendor Management

Add vendor

List vendors

Delete vendor

Clean UI for easy management



---

✅ 3. Submit Proposal

Vendors submit proposals linked to RFPs

Stored securely in MySQL

AI automatically evaluates the proposal



---

✅ 4. AI-Based Proposal Evaluation (Core Feature)

For each RFP + vendor proposal, Gemini AI returns:

{
  "score": 0–100,
  "analysis": "AI explanation"
}

This helps teams pick the best vendor fast and accurately.


---

✅ 5. Compare Proposals

Shows all vendor proposals for an RFP

Displays AI Score + Analysis

Helps identify the best vendor in seconds



---

🏗 Tech Stack

Backend (Spring Boot)

Spring Boot 3

Spring Data JPA

MySQL

REST APIs

Google Gemini AI (Java SDK)

Lombok

WebClient / Java SDK


Frontend (React)

React + Vite / CRA

Axios

Material UI

Reusable components



---

🗄 Database Structure

RFP Table

Column	Type

id	Long
originalDescription	TEXT
structuredData	TEXT
createdAt	DATETIME


Vendor Table

Column	Type

id	Long
name	VARCHAR
email	VARCHAR


Proposal Table

Column	Type

id	Long
vendor_id	FK
rfp_id	FK
proposalText	TEXT
structuredResponse	TEXT
score	INT



---

🔧 Setup Instructions

### Backend Setup

1. Install:

Java 17

Maven

MySQL



2. Create database:



CREATE DATABASE rfpdb;

3. Configure application.properties:



spring.datasource.url=jdbc:mysql://localhost:3306/rfpdb
spring.datasource.username=root
spring.datasource.password=1234

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

gemini.api.key=YOUR_KEY_HERE

4. Run backend:



mvn spring-boot:run

Backend runs at:

http://localhost:8080


---

Frontend Setup

1. Install Node.js


2. Inside the frontend folder:



npm install
npm start

Frontend runs at:

http://localhost:3000


---

🔗 API Endpoints

RFP

Method	Endpoint	Description

POST	/api/rfp/create	Create new RFP


Vendor

Method	Endpoint	Description

GET	/api/vendor/list	Get all vendors
POST	/api/vendor/add	Add vendor
DELETE	/api/vendor/delete/{id}	Delete vendor


Proposal

Method	Endpoint	Description

POST	/api/proposal/submit	Submit proposal
GET	/api/proposal/rfp/{id}/compare	Compare all proposals for RFP


AI Test

Method	Endpoint	Description

GET	/api/ai/test	Test Gemini API



---

🤖 How AI Scoring Works

The backend sends a prompt to Gemini:

Compare vendor proposal with RFP and return:
{
  "score": <0-100>,
  "analysis": "<short explanation>"
}

AI responds with JSON, which gets saved into the database and shown in the UI.


---

📽 Demo Video (Suggested Flow)

In your demo (5 minutes), show:

1. Add Vendor


2. Create RFP


3. Submit Proposal


4. Compare Proposals


5. Explain AI Score & Analysis




---

🌟 Why This Project Matters

Reduces manual review work

Removes human bias

Faster vendor selection

Practical use-case of AI in enterprise procurement

Full-stack + AI integration experience



---

📬 Contact

If you want help setting up or improving this project, feel free to reach out!
