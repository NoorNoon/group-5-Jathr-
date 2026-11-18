package com.example.jather.ui.screens.plans

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.data.PlanRepository
import com.example.jather.data.model.DietPlan
import com.example.jather.data.model.PlanStatus
import com.example.jather.data.model.WeekDay
import com.example.jather.navigation.navigateBackToPlans
import com.example.jather.ui.components.JathrScaffold
import com.example.jather.ui.components.plans.PlanBackground
import com.example.jather.ui.components.plans.PlanConfirmDialog
import com.example.jather.ui.components.plans.PlanDayTabs
import com.example.jather.ui.components.plans.PlanGreen
import com.example.jather.ui.components.plans.PlanLightGreen
import com.example.jather.ui.components.plans.PlanMealCard
import com.example.jather.ui.components.plans.PlanNotesBox
import com.example.jather.ui.components.plans.PlanPrimaryButton
import com.example.jather.ui.components.plans.PlanScrollBar
import com.example.jather.ui.components.plans.PlanSecondaryButton
import com.example.jather.ui.components.plans.PlanTotalCalories
import com.example.jather.ui.components.plans.RtlScreen
import com.example.jather.ui.theme.JathrBorder
import com.example.jather.ui.theme.JathrLavender
import com.example.jather.ui.theme.JathrPrimary
import com.example.jather.ui.theme.JathrTextMain
import com.example.jather.ui.theme.JathrTextSecondary

@Composable
fun PlanPreviewScreen(navController: NavController, clientId: Int) {
    var selectedDay by remember { mutableStateOf<WeekDay?>(null) }
    var showPublishDialog by remember { mutableStateOf(false) }
    var justPublished by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val plan = PlanRepository.latestPlanFor(clientId)
    if (plan == null) {
        Text(text = "لم يتم العثور على الخطة", modifier = Modifier.padding(32.dp))
        return
    }

    if (justPublished) {
        PublishedSuccess(navController = navController, plan = plan)
        return
    }

    val isPublished = plan.status == PlanStatus.PUBLISHED
    val days = plan.days()
    val chosenDay = selectedDay
    val currentDay = if (chosenDay != null && chosenDay in days) chosenDay else days.firstOrNull()

    RtlScreen {
        JathrScaffold(
            title = if (isPublished) "الخطة المنشورة" else "معاينة قبل النشر",
            navController = navController,
            showBackButton = true,
            showBottomBar = false
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PlanBackground)
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = if (isPublished) {
                            "ℹ️  هذه الخطة منشورة ويراها ${plan.clientName} الآن"
                        } else {
                            "👁  هكذا ستظهر الخطة للعميل بعد النشر"
                        },
                        color = JathrPrimary,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp)
                            .background(JathrLavender, RoundedCornerShape(14.dp))
                            .padding(14.dp)
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

                    if (isPublished) {
                        PlanSecondaryButton(
                            text = "العودة إلى الخطط",
                            onClick = { navController.navigateBackToPlans() },
                            modifier = Modifier.padding(horizontal = 18.dp)
                        )
                    } else {
                        PlanPrimaryButton(
                            text = "نشر الخطة للعميل",
                            onClick = { showPublishDialog = true },
                            modifier = Modifier.padding(horizontal = 18.dp)
                        )
                        PlanSecondaryButton(
                            text = "رجوع للتعديل",
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.padding(horizontal = 18.dp)
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

        if (showPublishDialog) {
            PlanConfirmDialog(
                title = "نشر الخطة للعميل؟",
                message = "ستظهر النسخة المنشورة لـ${plan.clientName} فوراً ويصلها إشعار بذلك.",
                confirmText = "نشر الآن",
                dismissText = "رجوع للتعديل",
                confirmColor = JathrPrimary,
                onConfirm = {
                    PlanRepository.publishPlan(plan.id)
                    showPublishDialog = false
                    justPublished = true
                },
                onDismiss = { showPublishDialog = false }
            )
        }
    }
}

@Composable
fun PublishedSuccess(navController: NavController, plan: DietPlan) {
    RtlScreen {
        JathrScaffold(
            title = "تم النشر",
            navController = navController,
            showBackButton = true,
            showBottomBar = false,
            onBackClick = { navController.navigateBackToPlans() }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PlanBackground)
                    .padding(innerPadding)
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(PlanLightGreen, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "✓", color = PlanGreen, fontSize = 34.sp, fontWeight = FontWeight.Bold)
                }

                Text(
                    text = "تم نشر الخطة بنجاح",
                    color = JathrTextMain,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "أرسلنا إشعاراً إلى ${plan.clientName}، وأصبحت الخطة ظاهرة لها في صفحة خطتي.",
                    color = JathrTextSecondary,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 6.dp, bottom = 16.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .border(1.dp, JathrBorder, RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SummaryRow(label = "العميل", value = plan.clientName)
                    SummaryRow(label = "الخطة", value = "الأسبوع ${plan.week} · ${plan.averageCalories()} سعرة")
                }

                PlanPrimaryButton(
                    text = "العودة إلى الخطط",
                    onClick = { navController.navigateBackToPlans() },
                    modifier = Modifier.padding(top = 18.dp)
                )
            }
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, color = JathrTextSecondary, fontSize = 13.sp, modifier = Modifier.weight(1f))
        Text(text = value, color = JathrTextMain, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}
