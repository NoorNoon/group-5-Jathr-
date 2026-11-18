package com.example.jather.ui.screens.plans

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.data.PlanRepository
import com.example.jather.data.model.Client
import com.example.jather.data.model.DietPlan
import com.example.jather.data.model.PlanStatus
import com.example.jather.navigation.navigateToPlanEditor
import com.example.jather.navigation.navigateToPlanPreview
import com.example.jather.ui.components.JathrScaffold
import com.example.jather.ui.components.plans.PlanBackground
import com.example.jather.ui.components.plans.PlanChip
import com.example.jather.ui.components.plans.PlanPrimaryButton
import com.example.jather.ui.components.plans.PlanScrollBar
import com.example.jather.ui.components.plans.PlanStatusChip
import com.example.jather.ui.components.plans.RtlScreen
import com.example.jather.ui.theme.JathrBorder
import com.example.jather.ui.theme.JathrLavender
import com.example.jather.ui.theme.JathrTextMain
import com.example.jather.ui.theme.JathrTextSecondary

@Composable
fun PlansListScreen(navController: NavController) {
    var filter by remember { mutableStateOf<PlanStatus?>(null) }
    var selectedClient by remember { mutableStateOf<Client?>(null) }
    var menuOpen by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val client = selectedClient
    val clientPlans = PlanRepository.plans.filter { client == null || it.clientId == client.id }
    val draftsCount = clientPlans.count { it.status == PlanStatus.DRAFT }
    val shownPlans = clientPlans
        .filter { filter == null || it.status == filter }
        .sortedBy { it.status }

    RtlScreen {
        JathrScaffold(
            title = "الخطط الغذائية",
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
                    Row(
                        modifier = Modifier.padding(horizontal = 18.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PlanChip(text = "الكل", selected = filter == null, onClick = { filter = null })
                        PlanChip(
                            text = "مسودة ($draftsCount)",
                            selected = filter == PlanStatus.DRAFT,
                            onClick = { filter = PlanStatus.DRAFT }
                        )
                        PlanChip(
                            text = "منشورة",
                            selected = filter == PlanStatus.PUBLISHED,
                            onClick = { filter = PlanStatus.PUBLISHED }
                        )
                    }

                    Box(modifier = Modifier.padding(horizontal = 18.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color.White)
                                .border(1.dp, JathrBorder, RoundedCornerShape(14.dp))
                                .clickable { menuOpen = true }
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "اختيار اسم العميل", color = JathrTextSecondary, fontSize = 11.sp)
                                Text(text = client?.name ?: "كل العملاء", color = JathrTextMain, fontSize = 14.sp)
                            }
                            Text(text = "▾", color = JathrTextSecondary, fontSize = 18.sp)
                        }

                        DropdownMenu(expanded = menuOpen, onDismissRequest = { menuOpen = false }) {
                            DropdownMenuItem(
                                text = { Text("كل العملاء") },
                                onClick = {
                                    selectedClient = null
                                    menuOpen = false
                                }
                            )
                            for (item in PlanRepository.clients) {
                                DropdownMenuItem(
                                    text = { Text(item.name) },
                                    onClick = {
                                        selectedClient = item
                                        menuOpen = false
                                    }
                                )
                            }
                        }
                    }

                    if (shownPlans.isEmpty()) {
                        Text(
                            text = "لا توجد خطط هنا حالياً",
                            color = JathrTextSecondary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 40.dp)
                        )
                    } else {
                        for (plan in shownPlans) {
                            PlanRow(
                                plan = plan,
                                onClick = {
                                    if (plan.status == PlanStatus.DRAFT) {
                                        navController.navigateToPlanEditor(plan.clientId)
                                    } else {
                                        navController.navigateToPlanPreview(plan.clientId)
                                    }
                                }
                            )
                        }
                    }

                    Text(
                        text = "يرى العميل النسخة المنشورة فقط، أما المسودات فتبقى لديك.",
                        color = JathrTextSecondary,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp)
                    )

                    if (client == null) {
                        Text(
                            text = "اختاري العميل أولاً لإنشاء خطة جديدة",
                            color = JathrTextSecondary,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    PlanPrimaryButton(
                        text = "إنشاء خطة جديدة",
                        enabled = client != null,
                        onClick = { if (client != null) navController.navigateToPlanEditor(client.id) },
                        modifier = Modifier.padding(horizontal = 18.dp)
                    )
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

@Composable
fun PlanRow(plan: DietPlan, onClick: () -> Unit) {
    val details = if (plan.days().isEmpty()) {
        "الأسبوع ${plan.week} · لم تُعبّأ بعد"
    } else {
        "الأسبوع ${plan.week} · ${plan.averageCalories()} سعرة"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, JathrBorder, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(JathrLavender, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "📋", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = plan.clientName, color = JathrTextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text(text = details, color = JathrTextSecondary, fontSize = 12.sp)
        }

        PlanStatusChip(plan.status)
    }
}
