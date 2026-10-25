# Jathr — System Architecture

**CSC 402 · Milestone 2 · Part B2 · Android (Kotlin, Jetpack Compose)**

## 1. Introduction

Jathr is an Arabic-first (RTL) Android application that brings nutrition specialists and their clients into one place: booking and paying for consultations, uploading medical files, following a personalized nutrition plan, filling in weekly follow-ups, and tracking progress.

We organize the app with **MVVM on top of a Clean Architecture layering** (UI → Domain → Data). We chose this approach for four reasons:

1. **Separation of concerns.** Screens only render state; business rules live in use cases; networking and storage live in repositories. Each part can be changed without breaking the others.
2. **Fits Jetpack Compose.** Compose is declarative: the UI is a function of state. A `ViewModel` exposing `StateFlow` maps directly onto this model.
3. **Testability.** The Domain layer depends only on repository *interfaces*, so use cases and ViewModels can be tested with fake repositories, with no Android framework or network needed.
4. **Team parallelism.** Because layers communicate through contracts (use cases and repository interfaces), team members can build the UI, the domain logic and the data sources at the same time.

The architecture follows the **Unidirectional Data Flow (UDF)** principle: *events flow up, state flows down*.

![Jathr Proposed System Architecture](architecture.png)

*Figure 1 — Proposed system architecture (MVVM): UI → Domain → Data → Backend → External system.*

---

## 2. Architecture Overview

The system has five tiers. The first three live inside the Android app; the last two are outside it.

| Tier | Where | Role |
|---|---|---|
| UI Layer | Android app | Compose screens, ViewModels, navigation |
| Domain Layer | Android app | Use cases, repository interfaces, domain models |
| Data Layer | Android app | Repository implementations, REST API clients, optional Room cache |
| Backend | Server | Backend REST API (single entry point) and Server Database |
| External system | Third party | Moyasar payment gateway (mada, Visa, Mastercard) |

The app is organized by **feature columns**, and each feature has its own screens, ViewModel, use cases, repository and API client:

**Account & Profile · Booking · Payment · Nutrition & Progress · Medical Files · Specialist · Notifications**

---

## 3. Layer Responsibilities and Golden Rules

### 3.1 UI Layer

**Technology:** Jetpack Compose, ViewModel, StateFlow, Navigation Compose.

**Responsibilities**
- Render screens (Compose) grouped by feature.
- Capture user input and send it to the ViewModel as **events**.
- Observe the ViewModel's `StateFlow` and render the current state (Loading / Success / Error).
- Handle navigation between screens through the Navigation Compose `NavHost`.

**Golden rule:** *The UI layer contains no business logic and never accesses the database or the API directly.*

### 3.2 Domain Layer

**Technology:** Pure Kotlin (use cases, repository interfaces, domain models).

**Responsibilities**
- Hold the application's **business rules** as use cases (e.g., booking rules, payment processing).
- Define **repository interfaces** (contracts) that describe what data the app needs, without saying how it is obtained.
- Define domain models used across the app.

**Golden rule:** *The Domain layer is independent of the UI and of any concrete data source.* It does not know about Compose, Retrofit or Room.

### 3.3 Data Layer

**Technology:** Retrofit + OkHttp (REST clients), Room (optional local cache), repository implementations.

**Responsibilities**
- Implement the repository interfaces from the Domain layer.
- Call the **Backend REST API** through typed API clients (e.g., `AppointmentApi`, `PaymentApi`).
- Map API/database data into domain models.
- Optionally cache selected data locally in **Room** for offline support.

**Golden rule:** *The Data layer contains no UI logic and exposes data only through repository interfaces.* The app talks only to the Backend REST API and never directly to the Server Database or to Moyasar.

---

## 4. Layer Components

| Layer | Responsibility | Key classes |
|---|---|---|
| **UI** | Displays screens, collects input, observes state, manages navigation | `LoginScreen`, `SignUpScreen`, `ProfileScreen`, `HealthInformationScreen`, `BookAppointmentScreen`, `MyAppointmentsScreen`, `PaymentScreen`, `BookingConfirmationScreen`, `NutritionPlanScreen`, `WeeklyFollowUpScreen`, `MyProgressScreen`, `MedicalFilesScreen`, `ClientsScreen`, `ScheduleScreen`, `CreatePlanScreen`, `NotificationsScreen`, `NavHost` |
| **UI (state holders)** | Hold `StateFlow<UiState>` per feature, receive events, call use cases | `AccountViewModel`, `BookingViewModel`, `PaymentViewModel`, `NutritionPlanViewModel`, `MedicalFilesViewModel`, `SpecialistViewModel`, `NotificationsViewModel` |
| **Domain (use cases)** | Business rules, one action per use case | `LoginUseCase`, `SignUpUseCase`, `UpdateProfileUseCase`, `UpdateHealthInfoUseCase`, `BookAppointmentUseCase`, `GetMyAppointmentsUseCase`, `ProcessPaymentUseCase`, `GetNutritionPlanUseCase`, `SubmitFollowUpUseCase`, `GetProgressUseCase`, `UploadMedicalFileUseCase`, `GetMedicalFilesUseCase`, `GetClientsUseCase`, `GetScheduleUseCase`, `CreatePlanUseCase`, `GetNotificationsUseCase` |
| **Domain (contracts)** | Repository interfaces | `AccountRepository`, `AppointmentRepository`, `PaymentRepository`, `NutritionPlanRepository`, `MedicalFileRepository`, `ClientRepository`, `NotificationRepository` |
| **Data (repositories)** | Implement the contracts, map data, coordinate API and cache | `AccountRepositoryImpl`, `AppointmentRepositoryImpl`, `PaymentRepositoryImpl`, `NutritionPlanRepositoryImpl`, `MedicalFileRepositoryImpl`, `ClientRepositoryImpl`, `NotificationRepositoryImpl` |
| **Data (sources)** | REST clients and local cache | `AccountApi`, `AppointmentApi`, `PaymentApi`, `NutritionPlanApi`, `MedicalFileApi`, `ClientApi`, `NotificationApi`, `RoomDatabase` |
| **Backend** | Single entry point for the app; stores shared data; talks to the payment gateway | Backend REST API, Server Database |
| **External** | Card payments (mada, Visa, Mastercard) | Moyasar Payment Gateway |

---

## 5. Unidirectional Data Flow (UDF)

In Jathr, data moves in one direction around a loop:

- **Events travel up.** User actions (taps, text input, selections) are sent from the Compose screen to the ViewModel, then to use cases, repositories, the API clients and finally the backend.
- **Results travel back up the same chain** as data returned from the backend to the repository, use case and ViewModel.
- **State flows down.** The ViewModel writes the result into its `StateFlow`. The UI only *observes* this state and re-renders.

Key principles:

1. The UI never changes data directly; it only sends events.
2. The **ViewModel is the single source of truth** for each screen's state.
3. State is exposed as an immutable `UiState` (`Loading`, `Success`, `Error`).
4. Navigation Compose moves between screens in response to state (e.g., after a successful booking, navigate from `BookAppointmentScreen` to `PaymentScreen`).
5. The app talks only to the Backend REST API, never directly to the Server Database or Moyasar.

### Example: Book Appointment screen

**UiState**

```kotlin
sealed interface BookingUiState {
    data object Loading : BookingUiState
    data class Success(val slots: List<Slot>, val bookingId: String? = null) : BookingUiState
    data class Error(val message: String) : BookingUiState
}
```

**Events (UI → ViewModel)**

```kotlin
sealed interface BookingEvent {
    data object LoadAvailableAppointments : BookingEvent
    data class SelectAppointmentSlot(val slot: Slot) : BookingEvent
    data object ConfirmBooking : BookingEvent
    data object RetryLoading : BookingEvent
}
```

**ViewModel (excerpt)**

```kotlin
@HiltViewModel
class BookingViewModel @Inject constructor(
    private val bookAppointment: BookAppointmentUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<BookingUiState>(BookingUiState.Loading)
    val state: StateFlow<BookingUiState> = _state.asStateFlow()

    fun onEvent(event: BookingEvent) {
        when (event) {
            is BookingEvent.ConfirmBooking -> confirm()
            // other events ...
            else -> Unit
        }
    }

    private fun confirm() = viewModelScope.launch {
        _state.value = BookingUiState.Loading
        bookAppointment(selectedSlot)
            .onSuccess { _state.value = BookingUiState.Success(slots, it.id) }
            .onFailure { _state.value = BookingUiState.Error(it.message ?: "Booking failed") }
    }
}
```

*(Excerpt is illustrative; the final code may differ.)*

---

## 6. Walk-through: Booking an Appointment

The numbers below match the circled numbers (①–⑦) in the architecture diagram.

| Step | Layer | What happens |
|---|---|---|
| **①** | UI | The client selects an available time slot and taps **Confirm** in `BookAppointmentScreen`. The screen sends a `ConfirmBooking` event to `BookingViewModel`. |
| **②** | UI | `BookingViewModel` sets its `StateFlow` to `Loading` and invokes `BookAppointmentUseCase`. |
| **③** | Domain | `BookAppointmentUseCase` applies the booking rules (e.g., a valid slot and package are selected) and calls the `AppointmentRepository` interface. |
| **④** | Data | `AppointmentRepositoryImpl` sends the request through `AppointmentApi` (Retrofit) to the Backend REST API. |
| **⑤** | Backend | The backend validates the request, **saves the booking in the Server Database**, and returns a success or error response. |
| **⑥** | Data → Domain → UI | The response is mapped to a domain model and returned through `AppointmentRepositoryImpl` and the use case to `BookingViewModel`. |
| **⑦** | UI | `BookingViewModel` updates its state to `Success` or `Error`. On success, Navigation Compose navigates to `PaymentScreen`; on error, the screen shows a message with a retry option. |

---

## 7. Walk-through: Paying with Moyasar (Visa)

The labels below match **P1–P9** in the diagram.

| Step | Layer | What happens |
|---|---|---|
| **P1** | UI | `PaymentScreen` sends a *pay* event to `PaymentViewModel` (state: `Loading`). |
| **P2** | UI → Domain | `PaymentViewModel` invokes `ProcessPaymentUseCase`. |
| **P3** | Domain | The use case calls the `PaymentRepository` interface. |
| **P4** | Data | `PaymentRepositoryImpl` sends the request through `PaymentApi` to the Backend REST API. |
| **P5** | Backend | The backend sends a payment request to **Moyasar** (mada, Visa and Mastercard are handled by the gateway, not by the app). |
| **P6** | External | Moyasar returns the payment result to the backend. |
| **P7** | Backend | The backend updates the booking and payment status in the Server Database. |
| **P8** | Data → Domain → UI | The result returns through `PaymentApi`, the repository and the use case to `PaymentViewModel`. |
| **P9** | UI | On **success**, `PaymentScreen` navigates to `BookingConfirmationScreen`. On **failure**, `PaymentScreen` shows the payment-failed error state with a retry option. |

---

## 8. Design Decisions Summary

- **Single backend entry point.** The app calls only the Backend REST API. This keeps credentials (database, payment gateway) off the device and gives one place to enforce security and business validation.
- **Gateway handled server-side.** Card processing is delegated to Moyasar through the backend, so the app never handles raw payment confirmation logic.
- **Optional offline support.** Room caches selected data. Repositories decide whether to read from cache or network, so the UI and Domain layers are unaffected.
- **One ViewModel per feature, one use case per action.** This keeps classes small, focused and easy to test.
- **Navigation is state-driven.** Screen transitions (e.g., booking → payment → confirmation) respond to ViewModel state rather than being triggered from data-layer code.
