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
import com.example.jather.ui.components.specialist.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Person

@Composable
fun MedicalAnalysisReviewScreen(navController: NavController, fileId: Int = 1) {
    val file = mockFiles.find { it.id == fileId } ?: mockFiles[0]
    val client = mockClients[0]

    var reviewStatus by remember { mutableStateOf("بانتظار المراجعة") }
    var reviewNote by remember { mutableStateOf("") }
    var showSuccessMessage by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    JathrScrollScreen(
        navController = navController,
        title = "مراجعة التحليل الطبي",
        subtitle = "راجع نتيجة التحليل وسجّل ملاحظتك",
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
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(SpecialistLavender, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = client.name,
                        tint = SpecialistPrimary,
                        modifier = Modifier.size(27.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = client.name,
                        color = SpecialistDarkPurple,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${client.goal} · ٢٨ سنة",
                        color = SpecialistTextSecondary,
                        fontSize = 12.sp
                    )
                }
            }

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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(SpecialistLavender, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.Description, contentDescription = null, tint = SpecialistPrimary, modifier = Modifier.size(22.dp))
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

                    SpecialistSecondaryButton(
                        text = "عرض الملف",
                        onClick = { },
                        modifier = Modifier.width(130.dp)
                    )
                }
            }

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
                    text = "حالة المراجعة",
                    color = SpecialistDarkPurple,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val statuses = listOf(
                        Triple("بانتظار المراجعة", SpecialistAmberBg, SpecialistAmber),
                        Triple("تمت المراجعة", SpecialistGreenBg, SpecialistGreen),
                        Triple("يحتاج متابعة", Color(0xFFFFECE0), Color(0xFFD35400))
                    )

                    statuses.forEach { (statusText, bgCol, textCol) ->
                        val isSelected = reviewStatus == statusText
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) bgCol else SpecialistScreenBg)
                                .border(1.dp, if (isSelected) textCol else SpecialistBorder, RoundedCornerShape(12.dp))
                                .clickable { reviewStatus = statusText }
                                .padding(vertical = 10.dp, horizontal = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = statusText,
                                color = if (isSelected) textCol else SpecialistTextSecondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                        }
                    }
                }
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
                Text(
                    text = "ملاحظة الأخصائي للعميلة",
                    color = SpecialistDarkPurple,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = reviewNote,
                    onValueChange = { reviewNote = it },
                    placeholder = { Text("اكتبي ملاحظتك للعميلة", color = SpecialistTextSecondary, fontSize = 13.sp) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SpecialistPrimary,
                        unfocusedBorderColor = SpecialistBorder
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
            }

            Text(
                text = "يطلع على ملفاتك أخصائيك المعتمد فقط",
                color = SpecialistTextSecondary,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            SpecialistPrimaryButton(
                text = "تسجيل المراجعة",
                onClick = {
                    showSuccessMessage = true
                    scope.launch {
                        delay(1200)
                        navController.popBackStack()
                    }
                }
            )

            if (showSuccessMessage) {
                Text(
                    text = "تم تسجيل المراجعة",
                    color = SpecialistGreen,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MedicalAnalysisReviewScreenPreview() {
    MedicalAnalysisReviewScreen(rememberNavController(), 1)
}
