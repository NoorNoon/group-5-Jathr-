package com.example.jather.ui.screens.specialist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jather.navigation.Screen
import com.example.jather.navigation.navigateToPlanEditor
import com.example.jather.navigation.navigateToSpecialistAnalysisReview
import com.example.jather.navigation.navigateToSpecialistAssessment
import com.example.jather.navigation.navigateToSpecialistFiles
import com.example.jather.ui.components.specialist.*

@Composable
fun ClientProfileScreen(navController: NavController, clientId: Int = 1) {
    val client = mockClients.find { it.id == clientId } ?: mockClients[0]
    var selectedTab by remember { mutableStateOf("الملخص") }
    var noteText by remember { mutableStateOf("") }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    val tabs = listOf("الملخص", "الصحة", "الملفات", "المتابعات")

    JathrScrollScreen(
        navController = navController,
        title = client.name,
        subtitle = "إنقاص الوزن · ٢٨ سنة",
        onBack = { navController.popBackStack() },
        selectedBottomTab = 1
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .border(1.dp, SpecialistBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(SpecialistLavender, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = client.name.take(1),
                        color = SpecialistPrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = client.name,
                        color = SpecialistDarkPurple,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${client.goal} · ٢٨ سنة",
                        color = SpecialistTextSecondary,
                        fontSize = 13.sp
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                tabs.forEach { tab ->
                    val isSelected = selectedTab == tab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) SpecialistPrimary else Color.White)
                            .border(1.dp, if (isSelected) SpecialistPrimary else SpecialistBorder, RoundedCornerShape(12.dp))
                            .clickable { selectedTab = tab }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tab,
                            color = if (isSelected) Color.White else SpecialistTextMain,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1
                        )
                    }
                }
            }

            when (selectedTab) {
                "الملخص" -> {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            StatCard(title = "الوزن الحالي", value = "٧٠٫٢ كجم", modifier = Modifier.weight(1f))
                            StatCard(title = "الفرق منذ البداية", value = "−٣٫٨", modifier = Modifier.weight(1f))
                            StatCard(title = "الالتزام", value = "٩٠٪", modifier = Modifier.weight(1f))
                        }
                    }
                }
                "الصحة" -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .border(1.dp, SpecialistBorder, RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "الملخص الصحي والحساسيات",
                            color = SpecialistDarkPurple,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "تعاني العميلة من ضغط الدم المعتدل وحساسية اللاكتوز. لا توجد موانع طبية قوية للحمية المقترحة.",
                            color = SpecialistTextSecondary,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        SpecialistSecondaryButton(
                            text = "عرض التقييم الغذائي",
                            onClick = { navController.navigateToSpecialistAssessment(client.id) }
                        )
                    }
                }
                "الملفات" -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .border(1.dp, SpecialistBorder, RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "الملفات الطبية المرفوعة",
                            color = SpecialistDarkPurple,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        FileRow(fileName = "تحليل الدم الشامل.pdf", type = "تحليل", onViewClick = { navController.navigateToSpecialistAnalysisReview(1) })
                        FileRow(fileName = "تقرير فيتامين د.pdf", type = "تقرير", onViewClick = { navController.navigateToSpecialistAnalysisReview(2) })
                        FileRow(fileName = "وصفة طبية.jpg", type = "وصفة", onViewClick = { navController.navigateToSpecialistAnalysisReview(3) })

                        TextButton(
                            onClick = { navController.navigateToSpecialistFiles(client.id) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "عرض كل الملفات", color = SpecialistPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
                "المتابعات" -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .border(1.dp, SpecialistBorder, RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "ملاحظات وتوصيات الأخصائي",
                            color = SpecialistDarkPurple,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        OutlinedTextField(
                            value = noteText,
                            onValueChange = { noteText = it },
                            placeholder = { Text("اكتب ملاحظة أو توصية للعميلة", color = SpecialistTextSecondary, fontSize = 13.sp) },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = SpecialistPrimary,
                                unfocusedBorderColor = SpecialistBorder
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                        )
                        SpecialistSecondaryButton(
                            text = "إرسال الملاحظة",
                            onClick = {
                                snackbarMessage = "تم إرسال الملاحظة بنجاح"
                                noteText = ""
                            }
                        )
                        if (snackbarMessage != null) {
                            Text(
                                text = snackbarMessage!!,
                                color = SpecialistGreen,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .border(1.dp, SpecialistBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "الخطة الحالية",
                        color = SpecialistTextSecondary,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "الأسبوع ٣",
                        color = SpecialistDarkPurple,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                SpecialistPrimaryButton(
                    text = "تعديل الخطة الغذائية",
                    onClick = { navController.navigateToPlanEditor(client.id) }
                )
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .border(1.dp, SpecialistBorder, RoundedCornerShape(14.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, color = SpecialistTextSecondary, fontSize = 11.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = value, color = SpecialistDarkPurple, fontSize = 15.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    }
}

@Composable
fun FileRow(fileName: String, type: String, onViewClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SpecialistScreenBg)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "📄", fontSize = 18.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = fileName, color = SpecialistTextMain, fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(SpecialistLavender)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(text = type, color = SpecialistPrimary, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
            }
        }
        TextButton(onClick = onViewClick) {
            Text(text = "عرض", color = SpecialistPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClientProfileScreenPreview() {
    ClientProfileScreen(rememberNavController(), 1)
}
