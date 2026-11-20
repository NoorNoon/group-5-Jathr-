package com.example.jathrproject.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jathrproject.ui.components.JathrHeader
import com.example.jathrproject.ui.components.JathrScrollableContainer
import com.example.jathrproject.ui.theme.BorderColor
import com.example.jathrproject.ui.theme.DarkPurple
import com.example.jathrproject.ui.theme.JathrProjectTheme
import com.example.jathrproject.ui.theme.LightLavender
import com.example.jathrproject.ui.theme.PrimaryPurple
import com.example.jathrproject.ui.theme.ScreenBackground

@Composable
fun FollowUpsListScreen(
    modifier: Modifier = Modifier,
    onOpenFollowUp: () -> Unit = {},
    onOpenSpecialistReview: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    var selectedCardIndex by remember { mutableIntStateOf(1) }

    val cardTitles = listOf(
        "تصفية حسب الأسبوع",
        "متابعة الأسبوع الحالي",
        "المتابعات السابقة"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
    ) {
        JathrScrollableContainer {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Header (Light Lavender background)
                JathrHeader(
                    title = "سجل المتابعات المشترك",
                    subtitle = "عرض المتابعات الأسبوعية",
                    backgroundColor = LightLavender,
                    showBackArrow = false,
                    onBackClick = onBackClick
                )

                // Main content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                ) {
                    cardTitles.forEachIndexed { index, title ->
                        val isSelected = selectedCardIndex == index
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isSelected) LightLavender.copy(alpha = 0.4f) else Color.White)
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) PrimaryPurple else BorderColor,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .clickable {
                                    selectedCardIndex = index
                                    if (index == 1) {
                                        onOpenFollowUp()
                                    } else if (index == 2) {
                                        onOpenSpecialistReview()
                                    }
                                }
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = DarkPurple,
                                textAlign = TextAlign.Center
                            )
                        }

                        if (index < cardTitles.lastIndex) {
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Primary purple button with explicit white text styling
                    Button(
                        onClick = {
                            if (selectedCardIndex == 2) {
                                onOpenSpecialistReview()
                            } else {
                                onOpenFollowUp()
                            }
                        },
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
                            text = "فتح متابعة",
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
fun FollowUpsListScreenPreview() {
    JathrProjectTheme {
        FollowUpsListScreen()
    }
}
