package com.example.jather.data

import androidx.compose.runtime.mutableStateListOf
import com.example.jather.data.model.Client
import com.example.jather.data.model.DietPlan
import com.example.jather.data.model.Meal
import com.example.jather.data.model.PlanStatus
import com.example.jather.data.model.WeekDay

object PlanRepository {

    const val CURRENT_CLIENT_ID = DemoData.CURRENT_CLIENT_ID

    // Same clients as the rest of the app (see DemoData)
    val clients = DemoData.clients.map { Client(it.id, it.name) }

    private var lastMealId = 0

    val plans = mutableStateListOf<DietPlan>()

    init {
        plans.addAll(samplePlans())
    }

    fun newMealId(): Int {
        lastMealId++
        return lastMealId
    }

    fun getClient(clientId: Int): Client? {
        return clients.find { it.id == clientId }
    }

    fun latestPlanFor(clientId: Int): DietPlan? {
        return plans.filter { it.clientId == clientId }.maxByOrNull { it.week }
    }

    fun latestPublishedPlanFor(clientId: Int): DietPlan? {
        return plans
            .filter { it.clientId == clientId && it.status == PlanStatus.PUBLISHED }
            .maxByOrNull { it.week }
    }

    fun planForEditor(clientId: Int): DietPlan {
        val latest = latestPlanFor(clientId)
        if (latest != null && latest.status == PlanStatus.DRAFT) {
            return latest
        }
        return DietPlan(
            id = nextPlanId(),
            clientId = clientId,
            clientName = getClient(clientId)?.name ?: "",
            week = (latest?.week ?: 0) + 1,
            status = PlanStatus.DRAFT,
            meals = emptyMap(),
            notes = ""
        )
    }

    private fun nextPlanId(): Int {
        val maxId = plans.maxOfOrNull { it.id } ?: 0
        return maxId + 1
    }

    fun savePlan(plan: DietPlan) {
        val index = plans.indexOfFirst { it.id == plan.id }
        if (index == -1) {
            plans.add(plan)
        } else {
            plans[index] = plan
        }
    }

    fun publishPlan(planId: Int) {
        val plan = plans.find { it.id == planId } ?: return
        savePlan(plan.copy(status = PlanStatus.PUBLISHED))
    }

    private fun sampleDay(): List<Meal> {
        return listOf(
            Meal(newMealId(), "الفطور", "8:00 ص", 420, "شوفان + حليب + موز", "عصيدة الشعير + زبادي بالفواكه"),
            Meal(newMealId(), "الغداء", "1:30 م", 650, "أرز + دجاج + سلطة", "برغل + سمك مشوي"),
            Meal(newMealId(), "العشاء", "7:30 م", 380, "زبادي + توت", "جبن قريش + خيار")
        )
    }

    private fun publishedPlan(id: Int, clientId: Int, week: Int, days: List<WeekDay>, notes: String): DietPlan {
        return DietPlan(
            id = id,
            clientId = clientId,
            clientName = getClient(clientId)?.name ?: "",
            week = week,
            status = PlanStatus.PUBLISHED,
            meals = days.associateWith { sampleDay() },
            notes = notes
        )
    }

    private fun emptyDraft(id: Int, clientId: Int, week: Int): DietPlan {
        return DietPlan(
            id = id,
            clientId = clientId,
            clientName = getClient(clientId)?.name ?: "",
            week = week,
            status = PlanStatus.DRAFT,
            meals = emptyMap(),
            notes = ""
        )
    }

    private fun samplePlans(): List<DietPlan> {
        return listOf(
            publishedPlan(
                id = 1,
                clientId = 2,
                week = 2,
                days = listOf(WeekDay.SUN, WeekDay.MON, WeekDay.TUE, WeekDay.WED),
                notes = "اشربي 8 أكواب ماء يومياً."
            ),
            publishedPlan(
                id = 2,
                clientId = 3,
                week = 4,
                days = listOf(WeekDay.SUN, WeekDay.TUE, WeekDay.THU),
                notes = "قللي السكريات وزيدي الخضار في العشاء."
            ),
            emptyDraft(id = 3, clientId = 1, week = 1)
        )
    }
}
