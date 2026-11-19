package com.example.jather.data.model

enum class WeekDay(val label: String) {
    SUN("الأحد"), MON("الاثنين"), TUE("الثلاثاء"), WED("الأربعاء"),
    THU("الخميس"), FRI("الجمعة"), SAT("السبت")
}

enum class PlanStatus(val label: String) {
    DRAFT("مسودة"),
    PUBLISHED("منشورة")
}

data class Client(
    val id: Int,
    val name: String
)

data class Meal(
    val id: Int,
    val name: String,
    val time: String,
    val calories: Int,
    val items: String,
    val alternatives: String
)

data class DietPlan(
    val id: Int,
    val clientId: Int,
    val clientName: String,
    val week: Int,
    val status: PlanStatus,
    val meals: Map<WeekDay, List<Meal>>,
    val notes: String
) {
    fun days(): List<WeekDay> {
        return WeekDay.entries.filter { meals.containsKey(it) }
    }

    fun mealsFor(day: WeekDay): List<Meal> {
        return meals[day] ?: emptyList()
    }

    fun caloriesFor(day: WeekDay): Int {
        return mealsFor(day).sumOf { it.calories }
    }

    fun averageCalories(): Int {
        val days = days()
        if (days.isEmpty()) return 0
        return days.sumOf { caloriesFor(it) } / days.size
    }
}

val mealNames = listOf("الفطور", "وجبة خفيفة", "الغداء", "العشاء")
