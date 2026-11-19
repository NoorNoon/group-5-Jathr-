package com.example.jather.ui.screens.specialist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jather.ui.components.specialist.*

@Composable
fun NutritionAssessmentScreen(navController: NavController, clientId: Int = 1) {
    val client = mockClients.find { it.id == clientId } ?: mockClients[0]
    var step by remember { mutableStateOf(1) }
    var age by remember { mutableStateOf("٢٨") }
    var height by remember { mutableStateOf("١٦٢") }
    var weight by remember { mutableStateOf("٧١") }
    var gender by remember { mutableStateOf("أنثى") }
    var goal by remember { mutableStateOf("إنقاص الوزن") }
    var conditions by remember { mutableStateOf(setOf("ضغط الدم", "حساسية اللاكتوز")) }

    JathrScrollScreen(
        navController = navController,
        title = "التقييم الغذائي",
        subtitle = "المعلومات الصحية مرجع لتصميم الخطة",
        onBack = { navController.popBackStack() },
        selectedBottomTab = 1
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SpecialistLavender)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("👤", fontSize = 18.sp)
                Spacer(Modifier.width(10.dp))
                Text(
                    "تقييم العميل: ${client.name}",
                    color = SpecialistDarkPurple,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // مؤشر الخطوات (قابل للضغط)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(1 to "1. البيانات", 2 to "2. الهدف", 3 to "3. الصحة").forEach { (n, label) ->
                    val selected = step == n
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (selected) SpecialistPrimary else Color.White)
                            .border(
                                1.dp,
                                if (selected) SpecialistPrimary else SpecialistBorder,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { step = n }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            label,
                            color = if (selected) Color.White else SpecialistTextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .border(1.dp, SpecialistBorder, RoundedCornerShape(16.dp))
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when (step) {
                    1 -> {
                        SectionTitle("البيانات الشخصية والقياسات")
                        AssessmentField("العمر", age) { age = it }
                        AssessmentField("الطول (سم)", height) { height = it }
                        AssessmentField("الوزن (كجم)", weight) { weight = it }
                        Text("الجنس", color = SpecialistTextSecondary, fontSize = 12.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("أنثى", "ذكر").forEach { g ->
                                AssessmentChip(g, gender == g, Modifier.weight(1f)) { gender = g }
                            }
                        }
                    }
                    2 -> {
                        SectionTitle("الهدف الغذائي")
                        listOf("إنقاص الوزن", "زيادة الوزن", "تثبيت الوزن", "نمط حياة صحي").forEach { g ->
                            AssessmentChip(g, goal == g, Modifier.fillMaxWidth()) { goal = g }
                        }
                    }
                    3 -> {
                        SectionTitle("الحالات الصحية والحساسية")
                        listOf("ضغط الدم", "حساسية اللاكتوز", "السكري", "حساسية الغلوتين", "لا يوجد").forEach { c ->
                            AssessmentChip(c, c in conditions, Modifier.fillMaxWidth()) {
                                conditions = when {
                                    c == "لا يوجد" -> if (c in conditions) emptySet() else setOf(c)
                                    c in conditions -> conditions - c
                                    else -> (conditions - "لا يوجد") + c
                                }
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (step > 1) {
                    Box(Modifier.weight(1f)) {
                        SpecialistSecondaryButton(text = "السابق", onClick = { step-- })
                    }
                }
                Box(Modifier.weight(1f)) {
                    SpecialistPrimaryButton(
                        text = if (step < 3) "التالي" else "إنهاء",
                        onClick = { if (step < 3) step++ else navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, color = SpecialistDarkPurple, fontSize = 16.sp, fontWeight = FontWeight.Bold)
}

@Composable
private fun AssessmentField(label: String, value: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label, fontSize = 12.sp) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SpecialistPrimary,
            unfocusedBorderColor = SpecialistBorder
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun AssessmentChip(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) SpecialistLavender else SpecialistScreenBg)
            .border(
                1.dp,
                if (selected) SpecialistPrimary else SpecialistBorder,
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text,
            color = if (selected) SpecialistDarkPurple else SpecialistTextMain,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
        if (selected) Text("✓", color = SpecialistPrimary, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun NutritionAssessmentScreenPreview() {
    NutritionAssessmentScreen(rememberNavController(), 1)
}