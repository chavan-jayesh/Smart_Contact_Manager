# 📇 Smart Contact Manager

A **full-stack Spring Boot web application** for managing and organizing personal contacts, featuring **secure authentication, cloud storage, and AWS deployment**.

Deployed live on **AWS Elastic Beanstalk** with a custom domain, SMTP integration via **Resend**, and a responsive UI built with **Tailwind CSS**.

🌐 **Live Demo**: [Click Here](https://smartcontacts.online)

---

## 💼 Highlights

- **Real-world Project**: Designed as a production-ready application for managing contacts securely.  
- **Cloud Deployment**: Fully hosted on **AWS Elastic Beanstalk** with environment-based configurations.  
- **Secure Email Integration**: SMTP (Resend API) for email verification.
- **Modern UI**: Responsive, mobile-friendly interface using **Tailwind CSS** + Thymeleaf templates.  
- **Database-Driven**: MySQL backend with ORM for persistence.  

---

## 🚀 Core Features

- **Authentication & Authorization**  
  - JWT-based login/registration  
  - BCrypt password encryption  

- **Contact Management**  
  - Add, update, delete, and search contacts  
  - Upload and store profile images (Cloudinary)  

- **Email Services**  
  - Account verification  
  - SMTP configuration using Resend

- **Responsive UI**  
  - Optimized for desktop and mobile users  
  - Clean, modern design with Tailwind CSS

---

## 🛠 Tech Stack

| Layer         | Technology |
|---------------|------------|
| Backend       | Java 21, Spring Boot, Spring Data JPA, Hibernate |
| Frontend      | Thymeleaf, Tailwind CSS |
| Database      | MySQL |
| Cloud         | AWS Elastic Beanstalk, Cloudinary |
| Email Service | Resend SMTP API |
| Build Tool    | Maven |
| Version Control | Git, GitHub |

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the repository
```bash
git clone https://github.com/chavan-jayesh/Smart_Contact_Manager.git
cd Smart_Contact_Manager
```

### 2️⃣ Set Environment Variables
Create a `.env` file **or** set variables in your deployment environment:  

```bash
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_DB= your mysql database name
PORT=8080
MYSQL_USER= your mysql username
MYSQL_PASSWORD= your mysql username
DDL_AUTO=update
SHOW_SQL=true
ACTIVE_PROFILE=dev
GOOGLE_CLIENT_ID=28171768874-e2rolq8sse68lfpd10mbr3slvr7p4tio.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=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
GITHUB_CLIENT_ID=Iv23liqWR1NHuHlnIscg
GITHUB_CLIENT_SECRET=6a56e573d28cb50cbab46fec7ed7dfc815ac54d8
CLOUDINARY_NAME=doovfpwzv
CLOUDINARY_API_KEY=545519266866162
CLOUDINARY_API_SECRET=qh8gvrtcIpVBPWy76veTlt5sOcQ
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_USERNAME=resend
EMAIL_PASSWORD=re_MQCPckBi_E3EbHNCvTgMufyh26gfN8mPw
EMAIL_DOMAIN=https://smartcontacts.online
EMAIL_PROTOCOL=smtp
MAX_FILE_SIZE=10MB
MAX_REQUEST_SIZE=10MB
```
---
### 3️⃣ Run the application

```bash
mvn spring-boot:run
```
---
### 📸 Screenshots
<br>
<img width="1366" height="722" alt="Screenshot (191)" src="https://github.com/user-attachments/assets/eca55c73-0f99-4d2f-99e4-f066c292879b" style="border: 5px solid black;"/>
<br><br>
<img width="1366" height="727" alt="Screenshot (192)" src="https://github.com/user-attachments/assets/1b469d26-f2ae-4cbf-8bbc-afd24ea54b55" style="border: 5px solid black;"/>
<br><br>
<img width="1366" height="725" alt="Screenshot (193)" src="https://github.com/user-attachments/assets/d3011de8-f2ce-4036-b5db-e4f5abe3207c" style="border: 5px solid black;" />
<br><br>
<img width="1366" height="723" alt="Screenshot (194)" src="https://github.com/user-attachments/assets/5caa82e7-ea60-4511-b560-7f852b477158" style="border: 5px solid black;" />
<br><br>
<img width="1366" height="727" alt="Screenshot 2025-08-13 181550" src="https://github.com/user-attachments/assets/184bde9d-f863-4524-aadc-b7320fd5a024" style="border: 5px solid black;" />
<br>

