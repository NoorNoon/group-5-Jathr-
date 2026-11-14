# 6. Firebase Data Plan

| Entity class | Planned Firestore query and purpose | Used by (ViewModel / Use Case) |
|---|---|---|
| UserAccount | getAccountByAuthUid(uid): load the signed-in account and its role. | LoadAccountUseCase |
| ClientProfile | getClientProfile(userId): load client health data and goals. | LoadClientProfileUseCase |
| SpecialistProfile | getSpecialistProfile(userId): load specialist details and fee. | LoadSpecialistProfileUseCase |
| Notification | listNotifications(userId): list the account notifications by created_at. | NotificationsViewModel |
| NotificationSetting | getNotificationSettings(userId): load delivery preferences. | NotificationsViewModel |
| MedicalFile | listMedicalFiles(clientId): list client files, excluding DELETED. | MedicalFilesViewModel |
| Availability | listAvailability(specialistId, day): find enabled time windows. | BookingViewModel |
| CareAssignment | listActiveAssignments(specialistId): find active client assignments. | SpecialistClientsViewModel |
| Package | listPackages(): display the catalog. getPackage(id): load the selected package. | BrowsePackagesUseCase<br>LoadSubscriptionUseCase |
| Appointment | listUpcomingAppointments(careId): filter by status and order by starts_at. | AppointmentsViewModel |
| Payment | listPaymentAttempts(appointmentId): list attempts by attempted_at. | PaymentViewModel |
| WeeklyFollowup | listFollowups(careId): list weekly entries by week_start_date. | ProgressViewModel |
| NutritionPlan | getActivePlan(careId): load the published nutrition plan. | NutritionPlanViewModel |
| Meal | listMeals(planId): load meals by day_of_week and display_order. | NutritionPlanViewModel |
| SpecialistNote | listSpecialistNotes(careId): list care notes by created_at. | ClientProfileViewModel |
| Rating | getRating(appointmentId): read the RATING document whose ID equals appointmentId, to check for an existing rating.<br>listSpecialistRatings(specialistId): Firestore has no JOIN, so load in steps: (1) query CARE_ASSIGNMENT where specialist_id = specialistId to get the care_ids; (2) query APPOINTMENT where care_id is in those ids (in batches of 30) and status = COMPLETED to get the appointment_ids; (3) read the RATING documents by those appointment_ids. Read the client name from USER_ACCOUNT when displayed. | RateSessionViewModel<br>SpecialistRatingsViewModel |

Query and consumer names are proposed for the Firebase implementation.

Firestore document IDs implement logical primary keys. The Kotlin classes above map to the 16 entities in Section 3. Foreign IDs store the corresponding document IDs.

Remote source: Cloud Firestore for app data and Firebase Authentication for sign-in. Use Security Rules for permitted writes, field validation and parent checks. Use backend transactions or unique document IDs for uniqueness and coupled updates. Apply the delete policies in Section 4 through backend operations.

## Types and Enums

| Type or enum | Representation |
|---|---|
| Document IDs and foreign keys | Kotlin String values pointing to Firestore documents. |
| Date and time | Timestamp for dated events. HH:mm strings for time of day. |
| Money | Integer halalas for package prices, payment amounts and appointment totals. Specialist session_fee is stored in SAR per Section 3. |
| Enums | Store role, appointment/payment/plan statuses, adherence, review and meal type as the strings defined in Section 3. |
| Medical file status | ACTIVE, REVIEWED, DELETED. |
| Weekday | Int from 0 (Sunday) to 6 (Saturday). |

## Sample Data

Planned source: synthetic records created by the team in a test Firebase project. Include a client and specialist, one package, an assignment, an appointment, a payment attempt, a plan with meals, a follow-up, a notification and a rating. Populate the test data before a demo if needed.
