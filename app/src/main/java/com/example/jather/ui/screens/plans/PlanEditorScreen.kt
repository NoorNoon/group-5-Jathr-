package com.example.jather.ui.screens.plans

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.data.PlanRepository
import com.example.jather.data.model.Meal
import com.example.jather.data.model.PlanStatus
import com.example.jather.data.model.WeekDay
import com.example.jather.data.model.mealNames
import com.example.jather.navigation.navigateToPlanPreview
import com.example.jather.ui.components.JathrScaffold
import com.example.jather.ui.components.plans.PlanBackground
import com.example.jather.ui.components.plans.PlanConfirmDialog
import com.example.jather.ui.components.plans.PlanDaySelector
import com.example.jather.ui.components.plans.PlanDayTabs
import com.example.jather.ui.components.plans.PlanPrimaryButton
import com.example.jather.ui.components.plans.PlanRed
import com.example.jather.ui.components.plans.PlanScrollBar
import com.example.jather.ui.components.plans.PlanSecondaryButton
import com.example.jather.ui.components.plans.PlanStatusChip
import com.example.jather.ui.components.plans.RtlScreen
import com.example.jather.ui.theme.JathrBorder
import com.example.jather.ui.theme.JathrLavender
import com.example.jather.ui.theme.JathrPrimary
import com.example.jather.ui.theme.JathrTextMain
import com.example.jather.ui.theme.JathrTextSecondary
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon

@Composable
fun PlanEditorScreen(navController: NavController, clientId: Int) {
    val context = LocalContext.current
    val startPlan = remember { PlanRepository.planForEditor(clientId) }
    var plan by remember { mutableStateOf(startPlan) }
    var savedPlan by remember { mutableStateOf(startPlan) }
    var selectedDay by remember { mutableStateOf<WeekDay?>(null) }
    var editingMealId by remember { mutableIntStateOf(-1) }
    var dayToRemove by remember { mutableStateOf<WeekDay?>(null) }
    var showUnsavedDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val days = plan.days()
    val chosenDay = selectedDay
    val currentDay = if (chosenDay != null && chosenDay in days) chosenDay else days.firstOrNull()
    val dayMeals = if (currentDay == null) emptyList() else plan.mealsFor(currentDay)
    val hasChanges = plan != savedPlan
    val canPreview = plan.meals.values.flatten().any { it.items.isNotBlank() }

    fun changeMeals(newMeals: List<Meal>) {
        if (currentDay != null) {
            plan = plan.copy(meals = plan.meals + (currentDay to newMeals))
        }
    }

    fun onDayClick(day: WeekDay) {
        if (day in days) {
            if (plan.mealsFor(day).isEmpty()) {
                plan = plan.copy(meals = plan.meals - day)
            } else {
                dayToRemove = day
            }
        } else {
            plan = plan.copy(meals = plan.meals + (day to emptyList()))
            selectedDay = day
        }
    }

    fun saveDraft() {
        val cleanMeals = plan.meals.mapValues { entry ->
            entry.value.filter { it.items.isNotBlank() }
        }
        val draft = plan.copy(meals = cleanMeals, status = PlanStatus.DRAFT)
        PlanRepository.savePlan(draft)
        plan = draft
        savedPlan = draft
        editingMealId = -1
    }

    fun goBack() {
        if (hasChanges) {
            showUnsavedDialog = true
        } else {
            navController.popBackStack()
        }
    }

    BackHandler { goBack() }

    RtlScreen {
        JathrScaffold(
            title = "محرر الخطة — ${plan.clientName}",
            navController = navController,
            showBackButton = true,
            showBottomBar = false,
            onBackClick = { goBack() }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PlanBackground)
                    .padding(innerPadding)
                    .imePadding()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 18.dp)) {
                        Text(text = "أيام الخطة", color = JathrPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "حددي الأيام التي تشملها خطة الأسبوع ${plan.week}",
                            color = JathrTextSecondary,
                            fontSize = 12.sp
                        )
                    }

                    PlanDaySelector(selectedDays = days, onDayClick = { day -> onDayClick(day) })

                    if (currentDay == null) {
                        Text(
                            text = "لم تُحدد أي أيام بعد.\nاختاري يوماً واحداً على الأقل لبدء جدول الوجبات.",
                            color = JathrTextSecondary,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 30.dp, horizontal = 18.dp)
                        )
                    } else {
                        PlanDayTabs(
                            days = days,
                            selectedDay = currentDay,
                            onDayClick = { day -> selectedDay = day }
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "إجمالي السعرات اليومية",
                                color = JathrTextSecondary,
                                fontSize = 12.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "${plan.caloriesFor(currentDay)}",
                                color = JathrTextMain,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            PlanStatusChip(PlanStatus.DRAFT)
                        }

                        for (i in dayMeals.indices) {
                            val meal = dayMeals[i]
                            if (meal.id == editingMealId) {
                                MealEditCard(
                                    meal = meal,
                                    onChange = { newMeal ->
                                        val list = dayMeals.toMutableList()
                                        list[i] = newMeal
                                        changeMeals(list)
                                    },
                                    onDone = { editingMealId = -1 },
                                    onDelete = {
                                        changeMeals(dayMeals - meal)
                                        editingMealId = -1
                                    }
                                )
                            } else {
                                MealViewCard(meal = meal, onEditClick = { editingMealId = meal.id })
                            }
                        }

                        OutlinedButton(
                            onClick = {
                                val name = if (dayMeals.size < mealNames.size) mealNames[dayMeals.size] else "وجبة إضافية"
                                val newMeal = Meal(PlanRepository.newMealId(), name, "", 0, "", "")
                                changeMeals(dayMeals + newMeal)
                                editingMealId = newMeal.id
                            },
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, JathrBorder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp)
                        ) {
                            Text(text = "+ إضافة وجبة", color = JathrPrimary)
                        }
                    }

                    OutlinedTextField(
                        value = plan.notes,
                        onValueChange = { text -> plan = plan.copy(notes = text) },
                        label = { Text("ملاحظات الأخصائي للعميل") },
                        shape = RoundedCornerShape(14.dp),
                        colors = planFieldColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp)
                            .padding(horizontal = 18.dp)
                    )

                    Row(
                        modifier = Modifier.padding(horizontal = 18.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        PlanSecondaryButton(
                            text = "معاينة",
                            enabled = canPreview,
                            onClick = {
                                saveDraft()
                                navController.navigateToPlanPreview(clientId)
                            },
                            modifier = Modifier.weight(1f)
                        )
                        PlanPrimaryButton(
                            text = "حفظ مسودة",
                            enabled = days.isNotEmpty(),
                            onClick = {
                                saveDraft()
                                Toast.makeText(context, "تم حفظ المسودة", Toast.LENGTH_SHORT).show()
                            },
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

        val removeDay = dayToRemove
        if (removeDay != null) {
            PlanConfirmDialog(
                title = "إزالة يوم ${removeDay.label}",
                message = "سيتم حذف وجبات يوم ${removeDay.label} من الخطة. هل تريدين المتابعة؟",
                confirmText = "إزالة",
                dismissText = "إلغاء",
                onConfirm = {
                    plan = plan.copy(meals = plan.meals - removeDay)
                    dayToRemove = null
                },
                onDismiss = { dayToRemove = null }
            )
        }

        if (showUnsavedDialog) {
            PlanConfirmDialog(
                title = "تعديلات غير محفوظة",
                message = "لديك تغييرات لم تُحفظ في الخطة. هل تريدين مغادرة الصفحة دون حفظ؟",
                confirmText = "مغادرة دون حفظ",
                dismissText = "متابعة التعديل",
                onConfirm = {
                    showUnsavedDialog = false
                    navController.popBackStack()
                },
                onDismiss = { showUnsavedDialog = false }
            )
        }
    }
}

@Composable
fun MealViewCard(meal: Meal, onEditClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.dp, JathrBorder, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = meal.name, color = JathrTextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "${meal.time.ifEmpty { "--:--" }} · ${meal.calories} سعرة",
                color = JathrTextSecondary,
                fontSize = 12.sp
            )
            Text(
                text = meal.items.ifEmpty { "لم تُكتب الوجبة بعد" },
                color = JathrTextMain,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp)
            )
            if (meal.alternatives.isNotEmpty()) {
                Text(text = "البدائل: ${meal.alternatives}", color = JathrTextSecondary, fontSize = 12.sp)
            }
        }

        Box(
            modifier = Modifier
                .size(36.dp)
                .background(JathrLavender, CircleShape)
                .clickable { onEditClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = "تعديل",
                tint = JathrPrimary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun MealEditCard(meal: Meal, onChange: (Meal) -> Unit, onDone: () -> Unit, onDelete: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.5.dp, JathrPrimary, RoundedCornerShape(16.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = meal.name, color = JathrPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = meal.time,
                onValueChange = { text -> onChange(meal.copy(time = text)) },
                label = { Text("الوقت") },
                placeholder = { Text("8:00 ص") },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = planFieldColors(),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = if (meal.calories == 0) "" else meal.calories.toString(),
                onValueChange = { text ->
                    val number = text.filter { it.isDigit() }.take(4)
                    onChange(meal.copy(calories = number.toIntOrNull() ?: 0))
                },
                label = { Text("السعرات") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(14.dp),
                colors = planFieldColors(),
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = meal.items,
            onValueChange = { text -> onChange(meal.copy(items = text)) },
            label = { Text("مكونات الوجبة") },
            shape = RoundedCornerShape(14.dp),
            colors = planFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = meal.alternatives,
            onValueChange = { text -> onChange(meal.copy(alternatives = text)) },
            label = { Text("البدائل") },
            shape = RoundedCornerShape(14.dp),
            colors = planFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = onDone,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)
            ) {
                Text("تم")
            }
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = onDelete) {
                Text(text = "حذف الوجبة", color = PlanRed)
            }
        }
    }
}

@Composable
fun planFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = JathrPrimary,
    unfocusedBorderColor = JathrBorder,
    focusedLabelColor = JathrPrimary,
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White
)
