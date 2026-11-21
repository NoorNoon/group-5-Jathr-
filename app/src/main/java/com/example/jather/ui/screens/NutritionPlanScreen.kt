
package com.example.jather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.data.PlanRepository
import com.example.jather.data.model.WeekDay
import com.example.jather.navigation.Screen
import com.example.jather.ui.components.JathrBottomBar
import com.example.jather.ui.components.plans.PlanDayTabs
import com.example.jather.ui.components.plans.PlanMealCard
import com.example.jather.ui.components.plans.PlanNotesBox
import com.example.jather.ui.components.plans.PlanTotalCalories
import com.example.jather.ui.theme.JathrLavender
import com.example.jather.ui.theme.JathrPrimary

private val CardBg = Color(0xFFFAF8FD)
private val CardBorder = Color(0xFFDCD2EA)
private val MainText = Color(0xFF34263E)
private val MutedText = Color(0xFF8A8095)

@Composable
fun NutritionPlanScreen(navController: NavController) {
    var selectedDay by remember { mutableStateOf<WeekDay?>(null) }
    val scrollState = rememberScrollState()

    val plan = PlanRepository.latestPublishedPlanFor(
        PlanRepository.CURRENT_CLIENT_ID
    )

    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        Scaffold(
            containerColor = JathrLavender,
            bottomBar = { JathrBottomBar(navController) },
            topBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(JathrLavender)
                        .padding(
                            horizontal = 18.dp,
                            vertical = 12.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "جذر",
                        color = JathrPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "خطتي الغذائية",
                        color = MainText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = "وجباتك اليومية وتوصيات الأخصائي",
                        color = MutedText,
                        fontSize = 12.sp
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (plan == null) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            "لا توجد خطة غذائية منشورة",
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp,
                            color = MainText
                        )
                        Text(
                            "ستظهر خطتك هنا بعد أن ينشرها الأخصائي.",
                            color = MutedText,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    val days = plan.days()
                    val currentDay =
                        selectedDay?.takeIf { it in days }
                            ?: days.firstOrNull()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(horizontal = 16.dp)
                            .padding(top = 8.dp, bottom = 24.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(14.dp)
                    ) {
                        if (currentDay != null) {
                            JathrPlanCard {
                                Text(
                                    "السعرات اليومية",
                                    fontWeight = FontWeight.SemiBold,
                                    color = MainText
                                )
                                Spacer(Modifier.height(8.dp))
                                PlanTotalCalories(
                                    calories = plan.caloriesFor(currentDay)
                                )
                            }
                        }

                        Text(
                            "اختر اليوم",
                            fontWeight = FontWeight.Bold,
                            color = MainText,
                            fontSize = 16.sp
                        )

                        JathrPlanCard {
                            PlanDayTabs(
                                days = days,
                                selectedDay = currentDay,
                                onDayClick = { selectedDay = it }
                            )
                        }

                        Text(
                            "وجبات اليوم",
                            fontWeight = FontWeight.Bold,
                            color = MainText,
                            fontSize = 16.sp
                        )

                        if (currentDay != null) {
                            val meals = plan.mealsFor(currentDay)

                            if (meals.isEmpty()) {
                                JathrPlanCard {
                                    Text(
                                        "لا توجد وجبات لهذا اليوم.",
                                        color = MutedText
                                    )
                                }
                            } else {
                                meals.forEach { meal ->
                                    PlanMealCard(meal = meal)
                                }
                            }
                        }

                        JathrPlanCard {
                            Text(
                                "ملاحظات الأخصائي",
                                fontWeight = FontWeight.Bold,
                                color = MainText
                            )
                            Spacer(Modifier.height(8.dp))
                            PlanNotesBox(notes = plan.notes)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate(
                                        Screen.WeeklyFollowUp.route
                                    ) {
                                        launchSingleTop = true
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = JathrPrimary
                                )
                            ) {
                                Text(
                                    "المتابعة الأسبوعية",
                                    fontSize = 12.sp,
                                    maxLines = 1
                                )
                            }

                            OutlinedButton(
                                onClick = {
                                    navController.navigate(
                                        Screen.Progress.route
                                    ) {
                                        launchSingleTop = true
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    "تقدمي",
                                    color = JathrPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun JathrPlanCard(
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            CardBorder
        ),
        colors = CardDefaults.cardColors(
            containerColor = CardBg
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            content = content
        )
    }
}
