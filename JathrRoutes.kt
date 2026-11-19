package com.example.jathrproject.ui.navigation

object JathrRoutes {
    const val FollowUpsList = "followups_list"
    const val ClientFollowUp = "client_followup"
    const val SpecialistReview = "specialist_review"
    const val ClientProgress = "client_progress"

    // Reserved placeholder route names for team integration
    const val Home = "home"
    const val Appointments = "appointments"
    const val MealPlan = "meal_plan"
    const val Profile = "profile"
}

enum class UserRole {
    CLIENT,
    SPECIALIST
}

/**
 * Development flag to toggle the temporary top testing navigation bar.
 * Set to false before final integration with the team's project codebase.
 */
const val SHOW_DEV_TEST_BAR = false
