package com.example.jather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jather.navigation.Screen
import com.example.jather.ui.components.bottomNavItems

// Local Color Palette (Matching PackagesScreen.kt)
private val DarkPurple = Color(0xFF3B2A50)
private val PrimaryPurple = Color(0xFF6B4E8C)
private val LightLavender = Color(0xFFEDE5F4)
private val ScreenBackground = Color(0xFFF7F3FA)
private val BorderColor = Color(0xFFD9CCE6)
private val MainText = Color(0xFF2B2235)
private val SecondaryText = Color(0xFF7A7085)
private val SuccessGreen = Color(0xFF2E7D4F)
private val SuccessBg = Color(0xFFE3F2E8)
private val DraftAmber = Color(0xFF9A6B00)
private val DraftBg = Color(0xFFFFF1D6)

data class SubscriptionRecord(
    val id: String,
    val packageName: String,
    val startDate: String,
    val endDate: String,
    val price: String,
    val status: String,
    val isActive: Boolean,
    val invoiceNumber: String
)

@Composable
fun SubscriptionHistoryScreen(navController: NavController) {
    val subscriptions = remember {
        listOf(
            SubscriptionRecord(
                id = "sub_1",
                packageName = "الباقة المتقدمة - 3 شهور",
                startDate = "2025/05/01",
                endDate = "2025/08/01",
                price = "300 ريال / شهرياً",
                status = "نشط 🟢",
                isActive = true,
                invoiceNumber = "INV-2025-0501"
            ),
            SubscriptionRecord(
                id = "sub_2",
                packageName = "الباقة الأساسية",
                startDate = "2025/04/01",
                endDate = "2025/05/01",
                price = "150 ريال / شهرياً",
                status = "منتهي 🔴",
                isActive = false,
                invoiceNumber = "INV-2025-0401"
            ),
            SubscriptionRecord(
                id = "sub_3",
                packageName = "الباقة التجريبية الأسبوعية",
                startDate = "2025/03/20",
                endDate = "2025/03/27",
                price = "50 ريال",
                status = "منتهي 🔴",
                isActive = false,
                invoiceNumber = "INV-2025-0320"
            )
        )
    }

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
                contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 14.dp, bottom = 28.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header Section
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "جذر",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryPurple
                            )

                            IconButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier
                                    .size(48.dp)
                                    .align(Alignment.CenterStart)
                            ) {
                                Text(
                                    text = "→",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryPurple
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "سجل الاشتراكات",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkPurple
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "متابعة تفاصيل باقاتك الحالية والسابقة وسجل الفواتير",
                            fontSize = 12.sp,
                            color = SecondaryText,
                            lineHeight = 16.sp
                        )
                    }
                }

                items(subscriptions) { sub ->
                    SubscriptionCard(
                        subscription = sub,
                        onRenewOrUpgrade = {
                            navController.navigate(Screen.Packages.route)
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DraftBg)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "💡 هل تحتاج مساعدة في اشتراكك؟",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DraftAmber
                            )
                            Text(
                                text = "يمكنك دائماً التواصل مع فريق خدمة العملاء أو الأخصائي لتعديل باقتك أو إيقاف التجديد التلقائي.",
                                fontSize = 12.sp,
                                color = MainText,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SubscriptionCard(
    subscription: SubscriptionRecord,
    onRenewOrUpgrade: () -> Unit
) {
    val cardBorderColor = if (subscription.isActive) PrimaryPurple else BorderColor
    val cardBorderWidth = if (subscription.isActive) 2.5.dp else 1.dp
    val cardBackground = if (subscription.isActive) LightLavender else Color.White

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(cardBorderWidth, cardBorderColor, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = if (subscription.isActive) 4.dp else 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = subscription.packageName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (subscription.isActive) DarkPurple else MainText
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (subscription.isActive) SuccessBg else LightLavender.copy(alpha = 0.8f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = subscription.status,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (subscription.isActive) SuccessGreen else SecondaryText
                    )
                }
            }

            HorizontalDivider(color = BorderColor)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("تاريخ التفعيل:", fontSize = 13.sp, color = SecondaryText)
                Text(subscription.startDate, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MainText)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("تاريخ الانتهاء:", fontSize = 13.sp, color = SecondaryText)
                Text(subscription.endDate, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MainText)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("رقم الفاتورة:", fontSize = 13.sp, color = SecondaryText)
                Text(subscription.invoiceNumber, fontSize = 13.sp, color = PrimaryPurple, fontWeight = FontWeight.SemiBold)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("المبلغ:", fontSize = 13.sp, color = SecondaryText)
                Text(subscription.price, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryPurple)
            }

            if (subscription.isActive) {
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = onRenewOrUpgrade,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("ترقية أو تجديد الباقة", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}
