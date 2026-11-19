package com.example.jather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jather.navigation.Screen
import com.example.jather.ui.components.bottomNavItems

// Local Color Palette (Matching PackagesScreen.kt)
private val PrimaryPurple = Color(0xFF6B4E8C)
private val LightLavender = Color(0xFFEDE5F4)
private val ScreenBackground = Color(0xFFF7F3FA)
private val BorderColor = Color(0xFFD9CCE6)
private val MainText = Color(0xFF2B2235)
private val SecondaryText = Color(0xFF7A7085)
private val SuccessGreen = Color(0xFF2E7D4F)
private val SuccessBg = Color(0xFFE3F2E8)

@Composable
fun BookingConfirmationScreen(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = ScreenBackground,
            bottomBar = {
                NavigationBar(
                    containerColor = LightLavender,
                    contentColor = PrimaryPurple
                ) {
                    bottomNavItems.forEach { item ->
                        val isSelected = currentRoute == item.route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(Screen.Home.route)
                                        launchSingleTop = true
                                    }
                                }
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) PrimaryPurple else Color.DarkGray
                                )
                            },
                            icon = {
                                Text(
                                    text = item.iconEmoji,
                                    fontSize = 18.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.White
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ScreenBackground)
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Top App Name Header
                item {
                    Text(
                        text = "جذر",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryPurple,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Success Badge Icon & Message
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(SuccessBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✓",
                                fontSize = 44.sp,
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen
                            )
                        }

                        Text(
                            text = "تم الحجز والدفع بنجاح!",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = SuccessGreen
                        )

                        Text(
                            text = "تم تأكيد موعدك بنجاح وتم إرسال تفاصيل الفاتورة وتأكيد الحجز إلى بريدك الإلكتروني.",
                            fontSize = 14.sp,
                            color = SecondaryText,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }

                // Ticket / Appointment Summary Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("رقم الموعد المرجعي:", fontSize = 13.sp, color = SecondaryText)
                                Text("#JATHR-88492", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryPurple)
                            }

                            HorizontalDivider(color = BorderColor)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("الأخصائي:", fontSize = 13.sp, color = SecondaryText)
                                Text("أ. سارة الشمري", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MainText)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("نوع الجلسة:", fontSize = 13.sp, color = SecondaryText)
                                Text("استشارة فيديو مباشرة", fontSize = 14.sp, color = MainText)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("تاريخ الموعد:", fontSize = 13.sp, color = SecondaryText)
                                Text("الأحد، 15 يونيو 2025", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MainText)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("الوقت:", fontSize = 13.sp, color = SecondaryText)
                                Text("05:00 مساءً", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MainText)
                            }

                            HorizontalDivider(color = BorderColor)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("حالة الدفع:", fontSize = 13.sp, color = SecondaryText)
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SuccessBg)
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "مدفوع (172.5 ريال)",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SuccessGreen
                                    )
                                }
                            }
                        }
                    }
                }

                // Actions
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                navController.navigate(Screen.Appointments.route) {
                                    popUpTo(Screen.Home.route)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text(
                                text = "عرض كافة مواعيدي",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Home.route)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryPurple)
                        ) {
                            Text(
                                text = "العودة إلى الصفحة الرئيسية",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryPurple
                            )
                        }
                    }
                }
            }
        }
    }
}
