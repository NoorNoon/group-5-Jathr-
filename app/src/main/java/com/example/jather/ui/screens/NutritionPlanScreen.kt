package com.example.jather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.data.PlanRepository
import com.example.jather.data.model.WeekDay
import com.example.jather.navigation.Screen
import com.example.jather.ui.components.JathrScaffold
import com.example.jather.ui.components.plans.PlanBackground
import com.example.jather.ui.components.plans.PlanDayTabs
import com.example.jather.ui.components.plans.PlanMealCard
import com.example.jather.ui.components.plans.PlanNotesBox
import com.example.jather.ui.components.plans.PlanPrimaryButton
import com.example.jather.ui.components.plans.PlanScrollBar
import com.example.jather.ui.components.plans.PlanSecondaryButton
import com.example.jather.ui.components.plans.PlanTotalCalories
import com.example.jather.ui.components.plans.RtlScreen
import com.example.jather.ui.theme.JathrTextSecondary

@Composable
fun NutritionPlanScreen(navController: NavController) {
    var selectedDay by remember { mutableStateOf<WeekDay?>(null) }
    val scrollState = rememberScrollState()
    val plan = PlanRepository.latestPublishedPlanFor(PlanRepository.CURRENT_CLIENT_ID)

    RtlScreen {
        JathrScaffold(
            title = "خطتي الغذائية",
            navController = navController,
            showBackButton = true,
            showBottomBar = true
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PlanBackground)
                    .padding(innerPadding)
            ) {
                if (plan == null) {
                    Text(
                        text = "لا توجد خطة منشورة بعد.\nستظهر خطتك هنا بمجرد أن ينشرها الأخصائي.",
                        color = JathrTextSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(32.dp)
                    )
                } else {
                    val days = plan.days()
                    val chosenDay = selectedDay
                    val currentDay = if (chosenDay != null && chosenDay in days) chosenDay else days.firstOrNull()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(vertical = 14.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "اختاري اليوم لعرض الوجبات",
                            color = JathrTextSecondary,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 18.dp)
                        )

                        PlanDayTabs(
                            days = days,
                            selectedDay = currentDay,
                            onDayClick = { day -> selectedDay = day }
                        )

                        if (currentDay != null) {
                            PlanTotalCalories(calories = plan.caloriesFor(currentDay))
                            for (meal in plan.mealsFor(currentDay)) {
                                PlanMealCard(meal = meal)
                            }
                        }

                        PlanNotesBox(notes = plan.notes)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            PlanPrimaryButton(
                                text = "المتابعة الأسبوعية",
                                onClick = { navController.navigate(Screen.WeeklyFollowUp.route) },
                                modifier = Modifier.weight(1f)
                            )
                            PlanSecondaryButton(
                                text = "تقدمي",
                                onClick = { navController.navigate(Screen.Progress.route) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    PlanScrollBar(
                        scrollState = scrollState,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .fillMaxHeight()
                    )
                }
            }
        }
    }
}
