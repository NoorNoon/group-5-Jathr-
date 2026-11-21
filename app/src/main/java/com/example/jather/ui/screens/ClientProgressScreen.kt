package com.example.jathrproject.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jathrproject.ui.components.JathrHeader
import com.example.jathrproject.ui.components.JathrScrollableContainer
import com.example.jathrproject.ui.theme.BorderColor
import com.example.jathrproject.ui.theme.JathrProjectTheme
import com.example.jathrproject.ui.theme.LightLavender
import com.example.jathrproject.ui.theme.MainText
import com.example.jathrproject.ui.theme.PrimaryPurple
import com.example.jathrproject.ui.theme.ScreenBackground
import com.example.jathrproject.ui.theme.SecondaryText

@Composable
fun ClientFollowUpScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onSubmitFollowUp: () -> Unit = {}
) {
    var weightText by remember { mutableStateOf("70.2") }
    var selectedAdherence by remember { mutableStateOf("جيد") }
    var selectedWaterIntake by remember { mutableStateOf("٤–٦") }
    var notesText by remember { mutableStateOf("صعب عليّ الالتزام بوجبة العشاء في أيام الدوام.") }

    val adherenceOptions = listOf("ممتاز", "جيد", "متوسط", "ضعيف")
    val waterOptions = listOf("أقل من ٤", "٤–٦", "٧–٩", "أكثر من ٩")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
    ) {
        JathrScrollableContainer {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Header (Normal very light background #F7F3FA)
                JathrHeader(
                    title = "المتابعة الأسبوعية",
                    subtitle = "الأسبوع ٢ · من ٥ إلى ١١ أكتوبر",
                    backgroundColor = ScreenBackground,
                    showBackArrow = true,
                    onBackClick = onBack
                )

                // Main Form inside a light lavender-tinted section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(LightLavender.copy(alpha = 0.5f))
                            .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Current Weight Field
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = "وزنك الحالي (كجم)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = SecondaryText
                                )
                                OutlinedTextField(
                                    value = weightText,
                                    onValueChange = { weightText = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedBorderColor = PrimaryPurple,
                                        unfocusedBorderColor = BorderColor,
                                        focusedTextColor = MainText,
                                        unfocusedTextColor = MainText
                                    ),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    singleLine = true
                                )
                            }

                            // Plan Adherence Options
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = "مدى التزامك بالخطة",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = SecondaryText
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    adherenceOptions.forEach { option ->
                                        val isSelected = option == selectedAdherence
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(44.dp)
                                                .clip(RoundedCornerShape(22.dp))
                                                .background(if (isSelected) PrimaryPurple else Color.White)
                                                .border(
                                                    width = 1.dp,
                                                    color = if (isSelected) PrimaryPurple else BorderColor,
                                                    shape = RoundedCornerShape(22.dp)
                                                )
                                                .clickable { selectedAdherence = option },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = option,
                                                fontSize = 13.sp,
                                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                                color = if (isSelected) Color.White else SecondaryText,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }

                            // Water Intake Options
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = "كم كوب ماء شربت يومياً؟",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = SecondaryText
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    waterOptions.forEach { option ->
                                        val isSelected = option == selectedWaterIntake
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(44.dp)
                                                .clip(RoundedCornerShape(22.dp))
                                                .background(if (isSelected) PrimaryPurple else Color.White)
                                                .border(
                                                    width = 1.dp,
                                                    color = if (isSelected) PrimaryPurple else BorderColor,
                                                    shape = RoundedCornerShape(22.dp)
                                                )
                                                .clickable { selectedWaterIntake = option },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = option,
                                                fontSize = 12.sp,
                                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                                color = if (isSelected) Color.White else SecondaryText,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }

                            // Notes Field
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = "ملاحظات أو صعوبات واجهتك",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = SecondaryText
                                )
                                OutlinedTextField(
                                    value = notesText,
                                    onValueChange = { notesText = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(110.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedBorderColor = PrimaryPurple,
                                        unfocusedBorderColor = BorderColor,
                                        focusedTextColor = MainText,
                                        unfocusedTextColor = MainText
                                    ),
                                    minLines = 3,
                                    maxLines = 5
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Submit Button with explicit white text styling
                    Button(
                        onClick = onSubmitFollowUp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryPurple,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "إرسال المتابعة إلى نوره",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClientFollowUpScreenPreview() {
    JathrProjectTheme {
        ClientFollowUpScreen()
    }
}
