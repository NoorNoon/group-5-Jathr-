# Section 1 – Entities at a Glance

| Entity | What it stores | Related to |
|---|---|---|
| USER_ACCOUNT | Identity, contact information and role | CLIENT_PROFILE, SPECIALIST_PROFILE, NOTIFICATION, NOTIFICATION_SETTING |
| CLIENT_PROFILE | Client health data, allergies and goals | USER_ACCOUNT, MEDICAL_FILE, CARE_ASSIGNMENT |
| SPECIALIST_PROFILE | Credentials, specialty and fee | USER_ACCOUNT, AVAILABILITY, CARE_ASSIGNMENT |
| CARE_ASSIGNMENT | Client-specialist assignment and selected package | CLIENT_PROFILE, SPECIALIST_PROFILE, PACKAGE, APPOINTMENT, WEEKLY_FOLLOWUP, NUTRITION_PLAN, SPECIALIST_NOTE |
| MEDICAL_FILE | Uploaded medical-file metadata and review status | CLIENT_PROFILE |
| AVAILABILITY | Weekly specialist availability windows | SPECIALIST_PROFILE |
| APPOINTMENT | Consultation booking and meeting link | CARE_ASSIGNMENT, PAYMENT, RATING |
| PAYMENT | Payment attempts and status | APPOINTMENT |
| NUTRITION_PLAN | Weekly plan and publishing status | CARE_ASSIGNMENT, MEAL |
| MEAL | Scheduled meals and calories | NUTRITION_PLAN |
| WEEKLY_FOLLOWUP | Weekly progress and adherence | CARE_ASSIGNMENT |
| SPECIALIST_NOTE | Specialist notes for a client | CARE_ASSIGNMENT |
| NOTIFICATION | Notification contents and read status | USER_ACCOUNT |
| NOTIFICATION_SETTING | Notification preferences | USER_ACCOUNT |
| PACKAGE | Package name, session count and price | CARE_ASSIGNMENT |
| RATING | Session stars and optional comments | APPOINTMENT |
