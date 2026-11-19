package com.example.jather.ui.screens.specialist

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jather.navigation.Screen
import com.example.jather.ui.components.bottomNavItems
import com.example.jather.ui.components.specialist.*

private data class MyFile(val id: Int, val name: String, val type: String, val meta: String, val status: String)

@Composable
fun ClientMedicalFilesScreen(navController: NavController) {
    val files = remember {
        mutableStateListOf(
            MyFile(1, "وصفة طبية.jpg", "الوصفات", "JPG · ١٫٤ ميجابايت · اليوم", "جار الرفع"),
            MyFile(2, "تحليل الدم الشامل.pdf", "نتائج التحليل", "PDF · ٢٫١ ميجابايت · ٢٨ سبتمبر", "مشاركة"),
            MyFile(3, "تقرير فيتامين د.pdf", "نتائج التحليل", "PDF · ٠٫٨ ميجابايت · ٢٨ سبتمبر", "مشاركة"),
            MyFile(4, "تقرير تكوين الجسم.pdf", "التقارير", "PDF · ١٫٢ ميجابايت · ١٥ سبتمبر", "مشاركة")
        )
    }
    var nextId by remember { mutableStateOf(5) }
    var filter by remember { mutableStateOf("الكل") }
    val scrollState: ScrollState = rememberScrollState()

    fun addMock(label: String) {
        files.add(0, MyFile(nextId++, "ملف جديد ($label)", "التقارير", "٠٫٥ ميجابايت · اليوم", "جار الرفع"))
    }

    val shown = files.filter { filter == "الكل" || it.type == filter }

    SpecialistRtlScreen {
        Scaffold(
            containerColor = SpecialistScreenBg,
            bottomBar = {
                NavigationBar(containerColor = SpecialistLavender) {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = false,
                            onClick = { navController.navigate(item.route) },
                            label = { Text(item.label, fontSize = 11.sp, color = SpecialistTextSecondary) },
                            icon = { Text(item.iconEmoji, fontSize = 18.sp) }
                        )
                    }
                }
            }
        ) { pad ->
            Box(Modifier.fillMaxSize().padding(pad)) {
                Column(
                    modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(bottom = 24.dp)
                ) {
                    JathrHeader(
                        title = "ملفاتي الطبية",
                        subtitle = "ارفع تحاليلك وتقاريرك ليطلع عليها أخصائيك",
                        onBack = { navController.popBackStack() }
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // صندوق الرفع المنقط
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .drawBehind {
                                    drawRoundRect(
                                        color = SpecialistPrimary,
                                        style = Stroke(
                                            width = 2.dp.toPx(),
                                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f))
                                        ),
                                        cornerRadius = CornerRadius(16.dp.toPx())
                                    )
                                }
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text("ارفع ملفًا جديدًا", color = SpecialistDarkPurple, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("صورة أو PDF · حتى ١٠ ميجابايت", color = SpecialistTextSecondary, fontSize = 11.sp)
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf("📷" to "الكاميرا", "🖼" to "معرض الصور", "📄" to "ملف (PDF)").forEach { (icon, label) ->
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color.White)
                                            .border(1.dp, SpecialistBorder, RoundedCornerShape(12.dp))
                                            .clickable { addMock(label) }
                                            .padding(vertical = 12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(icon, fontSize = 18.sp)
                                        Spacer(Modifier.height(4.dp))
                                        Text(label, fontSize = 11.sp, color = SpecialistTextMain)
                                    }
                                }
                            }
                        }

                        // الفلاتر
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("الكل", "نتائج التحليل", "التقارير", "الوصفات").forEach { f ->
                                val sel = filter == f
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (sel) SpecialistPrimary else Color.White)
                                        .border(1.dp, if (sel) SpecialistPrimary else SpecialistBorder, RoundedCornerShape(12.dp))
                                        .clickable { filter = f }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(f, fontSize = 11.sp, maxLines = 1, fontWeight = FontWeight.SemiBold,
                                        color = if (sel) Color.White else SpecialistTextMain)
                                }
                            }
                        }

                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("الملفات المرفوعة (${files.size})", color = SpecialistDarkPurple, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("الأحدث أولًا", color = SpecialistTextSecondary, fontSize = 11.sp)
                        }

                        if (shown.isEmpty()) {
                            Text("لا توجد ملفات هنا", color = SpecialistTextSecondary, textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp))
                        }

                        shown.forEach { f ->
                            val uploading = f.status == "جار الرفع"
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White)
                                    .border(1.dp, SpecialistBorder, RoundedCornerShape(16.dp))
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    Modifier.size(44.dp).background(SpecialistLavender, RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) { Text(if (f.name.endsWith(".jpg")) "🖼" else "📄", fontSize = 20.sp) }
                                Spacer(Modifier.width(12.dp))
                                Column(Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(f.name, color = SpecialistDarkPurple, fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold, maxLines = 1, modifier = Modifier.weight(1f, false))
                                        Spacer(Modifier.width(8.dp))
                                        Text(
                                            f.status,
                                            color = if (uploading) SpecialistAmber else SpecialistGreen,
                                            fontSize = 10.sp,
                                            modifier = Modifier
                                                .background(if (uploading) SpecialistAmberBg else SpecialistGreenBg, RoundedCornerShape(10.dp))
                                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(Modifier.height(4.dp))
                                    Text("${f.type} · ${f.meta}", color = SpecialistTextSecondary, fontSize = 11.sp)
                                }
                                Text(
                                    "🗑",
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { files.removeAll { it.id == f.id } }
                                        .padding(8.dp)
                                )
                            }
                        }

                        Text(
                            "يطلع على ملفاتك أخصائيك المعتمد فقط",
                            color = SpecialistTextSecondary, fontSize = 12.sp, textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        SpecialistPrimaryButton(
                            text = "احجز موعدًا لمراجعة ملفاتك",
                            onClick = { navController.navigate(Screen.BookAppointment.route) }
                        )
                    }
                }
                SpecialistScrollBar(
                    scrollState = scrollState,
                    modifier = Modifier.align(Alignment.TopEnd).fillMaxHeight()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClientMedicalFilesScreenPreview() {
    ClientMedicalFilesScreen(rememberNavController())
}