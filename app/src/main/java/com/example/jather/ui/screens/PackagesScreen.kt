package com.example.jather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

// Local Color Palette (As specified in project prompt #3)
private val DarkPurple = Color(0xFF3B2A50)
private val PrimaryPurple = Color(0xFF6B4E8C)
private val LightLavender = Color(0xFFEDE5F4)
private val ScreenBackground = Color(0xFFF7F3FA)
private val BorderColor = Color(0xFFD9CCE6)
private val MainText = Color(0xFF2B2235)
private val SecondaryText = Color(0xFF7A7085)
private val SuccessGreen = Color(0xFF2E7D4F)

data class SubscriptionPackage(
    val id: String,
    val title: String,
    val price: String,
    val duration: String,
    val badge: String?,
    val isRecommended: Boolean,
    val description: String,
    val features: List<String>
)

@Composable
fun PackagesScreen(navController: NavController) {
    val packages = remember {
        listOf(
            SubscriptionPackage(
                id = "basic",
                title = "الباقة الأساسية",
                price = "150",
                duration = "شهرياً",
                badge = "الأنسب للبداية",
                isRecommended = false,
                description = "مثالية للراغبين في البدء بتحسين نمط حياتهم الغذائي.",
                features = listOf(
                    "خطة غذائية أسبوعية مخصصة",
                    "استشارة نصية واحدة شهرياً",
                    "متابعة الوزن والقياسات عبر التطبيق"
                )
            ),
            SubscriptionPackage(
                id = "standard",
                title = "الباقة المتقدمة",
                price = "300",
                duration = "شهرياً",
                badge = "⭐ الأكثر طلباً",
                isRecommended = true,
                description = "الخيار الأفضل للحصول على متابعة مستمرة ونتائج أسرع.",
                features = listOf(
                    "خطة غذائية أسبوعية محدثة باستمرار",
                    "استشارتان عبر الاتصال الصوتي أو الفيديو",
                    "دعم وتواصل مباشر مع أخصائي التغذية",
                    "تقارير تحليل التقدم الأسبوعي"
                )
            ),
            SubscriptionPackage(
                id = "premium",
                title = "الباقة الشاملة",
                price = "500",
                duration = "شهرياً",
                badge = "👑 متابعة مكثفة",
                isRecommended = false,
                description = "تغطية كاملة ومتابعة يومية مكثفة مع كبار الأخصائيين.",
                features = listOf(
                    "خطط غذائية مخصصة متكاملة مع الوصفات",
                    "استشارات أسبوعية مباشرة غير محدودة",
                    "تواصل مباشر على مدار الساعة",
                    "تصميم برامج رياضية مرافقة للخطة",
                    "مراجعة دقيقة للتحاليل والملفات الطبية"
                )
            )
        )
    }

    var selectedPackageId by remember { mutableStateOf("standard") }

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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Single Clean Header (App Name centered, Title below, Back Arrow on right)
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
                                    .align(Alignment.CenterStart) // In RTL, CenterStart aligns to the RIGHT
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
                            text = "الباقات المتاحة",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkPurple
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "احصل على متابعة مخصصة مع أخصائيي جذر لتحقيق أهدافك الصحية",
                            fontSize = 12.sp,
                            color = SecondaryText,
                            lineHeight = 16.sp
                        )
                    }
                }

                items(packages) { pkg ->
                    PackageCard(
                        pkg = pkg,
                        isSelected = selectedPackageId == pkg.id,
                        onSelect = { selectedPackageId = pkg.id }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = { navController.navigate(Screen.Payment.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = "الاشتراك في الباقة المختارة",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = {
                            navController.navigate(Screen.BookAppointment.route)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryPurple)
                    ) {
                        Text(
                            text = "حجز استشارة فردية بدون اشتراك",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryPurple
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PackageCard(
    pkg: SubscriptionPackage,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val cardBorderColor = if (isSelected) PrimaryPurple else BorderColor
    val borderWidth = if (isSelected) 2.5.dp else 1.dp
    val cardBg = if (isSelected) LightLavender else Color.White

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(borderWidth, cardBorderColor, RoundedCornerShape(16.dp))
            .clickable { onSelect() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = isSelected,
                        onClick = onSelect,
                        colors = RadioButtonDefaults.colors(selectedColor = PrimaryPurple)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = pkg.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) DarkPurple else MainText
                    )
                }

                if (pkg.badge != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (pkg.isRecommended) PrimaryPurple else LightLavender.copy(alpha = 0.8f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = pkg.badge,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (pkg.isRecommended) Color.White else PrimaryPurple
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = pkg.price,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurple
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "ريال / ${pkg.duration}",
                    fontSize = 13.sp,
                    color = SecondaryText,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = pkg.description,
                fontSize = 13.sp,
                color = SecondaryText,
                lineHeight = 18.sp
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = BorderColor
            )

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                pkg.features.forEach { feature ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "✓",
                            color = SuccessGreen,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = feature,
                            fontSize = 13.sp,
                            color = MainText
                        )
                    }
                }
            }
        }
    }
}
