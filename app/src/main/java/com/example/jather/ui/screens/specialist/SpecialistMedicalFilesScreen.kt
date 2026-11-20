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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jather.navigation.navigateToSpecialistAnalysisReview
import com.example.jather.ui.components.specialist.*

data class SpecialistFile(
    val id: Int,
    val name: String,
    val type: String,
    val date: String,
    val size: String,
    val status: String
)

val mockFiles = listOf(
    SpecialistFile(1, "تحليل الدم الشامل.pdf", "تحليل", "٢٨ سبتمبر", "١٫٢ ميجابايت", "بانتظار المراجعة"),
    SpecialistFile(2, "تقرير فيتامين د.pdf", "تقرير", "١٥ سبتمبر", "٨٥٠ كيلوبايت", "مراجَع"),
    SpecialistFile(3, "وصفة طبية.jpg", "وصفة", "١٠ سبتمبر", "٢٫١ ميجابايت", "مراجَع")
)

@Composable
fun SpecialistMedicalFilesScreen(navController: NavController, clientId: Int = 1) {
    var selectedFilter by remember { mutableStateOf("الكل") }

    val filteredFiles = mockFiles.filter { file ->
        when (selectedFilter) {
            "نتائج التحليل" -> file.type == "تحليل"
            "التقارير" -> file.type == "تقرير"
            "الوصفات" -> file.type == "وصفة"
            else -> true
        }
    }

    JathrScrollScreen(
        navController = navController,
        title = "الملفات الطبية",
        subtitle = "ملفات العميلة المرفوعة للمراجعة",
        onBack = { navController.popBackStack() },
        selectedBottomTab = 1
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("الكل", "نتائج التحليل", "التقارير", "الوصفات").forEach { filter ->
                    val isSelected = selectedFilter == filter
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) SpecialistPrimary else Color.White)
                            .border(1.dp, if (isSelected) SpecialistPrimary else SpecialistBorder, RoundedCornerShape(12.dp))
                            .clickable { selectedFilter = filter }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) Color.White else SpecialistTextMain,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (filteredFiles.isEmpty()) {
                Text(
                    text = "لا توجد ملفات في هذا التصنيف",
                    color = SpecialistTextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                )
            } else {
                filteredFiles.forEach { file ->
                    SpecialistFileCard(
                        file = file,
                        onViewClick = { navController.navigateToSpecialistAnalysisReview(file.id) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "يطلع على ملفاتك أخصائيك المعتمد فقط",
                color = SpecialistTextSecondary,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun SpecialistFileCard(file: SpecialistFile, onViewClick: () -> Unit) {
    val (badgeBg, badgeText) = if (file.status == "مراجَع") {
        SpecialistGreenBg to SpecialistGreen
    } else {
        SpecialistAmberBg to SpecialistAmber
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, SpecialistBorder, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(SpecialistLavender, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "📄", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = file.name,
                    color = SpecialistDarkPurple,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${file.date} · ${file.size}",
                    color = SpecialistTextSecondary,
                    fontSize = 12.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(badgeBg)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = file.status,
                    color = badgeText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(SpecialistScreenBg)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = file.type,
                    color = SpecialistPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            TextButton(onClick = onViewClick) {
                Text(
                    text = "عرض الملف ←",
                    color = SpecialistPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpecialistMedicalFilesScreenPreview() {
    SpecialistMedicalFilesScreen(rememberNavController(), 1)
}
