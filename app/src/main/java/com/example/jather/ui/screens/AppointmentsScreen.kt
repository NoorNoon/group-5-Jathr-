
package com.example.jather.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.navigation.Screen
import com.example.jather.ui.components.JathrScaffold
import com.example.jather.ui.theme.JathrPrimary
import com.example.jather.data.DemoData

private val AppointmentsBackground = Color(0xFFF7F3FA)
private val AppointmentsDark = Color(0xFF3B2A50)
private val AppointmentsMuted = Color(0xFF7A7085)
private val AppointmentsBorder = Color(0xFFD9CCE6)
private val AppointmentsLavender = Color(0xFFEDE5F4)
private val AppointmentsGreen = Color(0xFF2E7D4F)

@Composable
fun AppointmentsScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf("القادمة") }

    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        JathrScaffold(
            title = "مواعيدي",
            navController = navController,
            showBackButton = false,
            showBottomBar = true,
            showTopBar = false
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppointmentsBackground)
                    .padding(innerPadding)
            ) {
                // Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppointmentsLavender)
                        .padding(
                            horizontal = 20.dp,
                            vertical = 18.dp
                        )
                ) {
                    Text(
                        text = "جذر",
                        modifier = Modifier.align(
                            Alignment.CenterHorizontally
                        ),
                        color = JathrPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(Modifier.height(14.dp))

                    Text(
                        text = "مواعيدي",
                        color = AppointmentsDark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    Text(
                        text = "تابع مواعيدك وجلساتك مع أخصائي التغذية",
                        color = AppointmentsMuted,
                        fontSize = 13.sp
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Summary
                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {
                        AppointmentSummaryCard(
                            title = "المواعيد القادمة",
                            count = "1",
                            modifier = Modifier.weight(1f)
                        )

                        AppointmentSummaryCard(
                            title = "المواعيد السابقة",
                            count = "0",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Tabs
                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(10.dp)
                    ) {
                        listOf("القادمة", "السابقة").forEach { tab ->
                            val isSelected = selectedTab == tab

                            Button(
                                onClick = { selectedTab = tab },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                        if (isSelected)
                                            JathrPrimary
                                        else
                                            Color.White,
                                    contentColor =
                                        if (isSelected)
                                            Color.White
                                        else
                                            JathrPrimary
                                )
                            ) {
                                Text(
                                    text = tab,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    Text(
                        text = if (selectedTab == "القادمة")
                            "المواعيد القادمة"
                        else
                            "المواعيد السابقة",
                        color = AppointmentsDark,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (selectedTab == "القادمة") {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            border = BorderStroke(
                                1.dp,
                                AppointmentsBorder
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement =
                                    Arrangement.spacedBy(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement =
                                        Arrangement.SpaceBetween,
                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "استشارة مع ${DemoData.SPECIALIST_NAME}",
                                        color = AppointmentsDark,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Surface(
                                        color = Color(0xFFE3F2E8),
                                        shape = RoundedCornerShape(50)
                                    ) {
                                        Text(
                                            text = "مؤكد",
                                            color = AppointmentsGreen,
                                            fontSize = 12.sp,
                                            modifier = Modifier.padding(
                                                horizontal = 12.dp,
                                                vertical = 6.dp
                                            )
                                        )
                                    }
                                }

                                HorizontalDivider(
                                    color = AppointmentsBorder
                                )

                                AppointmentDetailRow(
                                    "التاريخ",
                                    "22 نوفمبر 2026"
                                )

                                AppointmentDetailRow(
                                    "الوقت",
                                    "04:00 مساءً"
                                )

                                AppointmentDetailRow(
                                    "نوع الجلسة",
                                    "استشارة تغذية"
                                )
                            }
                        }
                    } else {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Text(
                                text = "لا توجد مواعيد سابقة حتى الآن",
                                color = AppointmentsMuted,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp)
                            )
                        }
                    }
                }

                // Booking button
                Button(
                    onClick = {
                        navController.navigate(
                            Screen.BookAppointment.route
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        )
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = JathrPrimary
                    )
                ) {
                    Text(
                        text = "حجز موعد جديد",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun AppointmentSummaryCard(
    title: String,
    count: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
            1.dp,
            AppointmentsBorder
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = AppointmentsMuted,
                fontSize = 12.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = count,
                color = JathrPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
        }
    }
}

@Composable
private fun AppointmentDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = AppointmentsMuted,
            fontSize = 13.sp
        )

        Text(
            text = value,
            color = AppointmentsDark,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp
        )
    }
}
