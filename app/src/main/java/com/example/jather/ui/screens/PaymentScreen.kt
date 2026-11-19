package com.example.jather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(navController: NavController) {
    var selectedMethod by remember { mutableStateOf("mada") }
    var cardHolderName by remember { mutableStateOf("أحمد محمد العتيبي") }
    var cardNumber by remember { mutableStateOf("4532 •••• •••• 8892") }
    var expiryDate by remember { mutableStateOf("08/27") }
    var cvv by remember { mutableStateOf("321") }

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
                            text = "الدفع والتأكيد",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkPurple
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "مراجعة تفاصيل الفاتورة وإتمام عملية الدفع بأمان",
                            fontSize = 12.sp,
                            color = SecondaryText,
                            lineHeight = 16.sp
                        )
                    }
                }

                // Summary Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = LightLavender.copy(alpha = 0.5f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "ملخص الفاتورة",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryPurple
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("الخدمة:", fontSize = 14.sp, color = SecondaryText)
                                Text("استشارة تغذية علاجية", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MainText)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("الأخصائي:", fontSize = 14.sp, color = SecondaryText)
                                Text("أ. سارة الشمري", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MainText)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("رسوم الاستشارة:", fontSize = 14.sp, color = SecondaryText)
                                Text("150.00 ريال", fontSize = 14.sp, color = MainText)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("ضريبة القيمة المضافة (15%):", fontSize = 14.sp, color = SecondaryText)
                                Text("22.50 ريال", fontSize = 14.sp, color = MainText)
                            }

                            HorizontalDivider(color = BorderColor, modifier = Modifier.padding(vertical = 4.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("الإجمالي المستحق:", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MainText)
                                Text("172.50 ريال سعودي", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkPurple)
                            }
                        }
                    }
                }

                // Payment Method Selector
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "اختر طريقة الدفع",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkPurple
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            PaymentMethodChip(
                                title = "مدى mada",
                                isSelected = selectedMethod == "mada",
                                onClick = { selectedMethod = "mada" },
                                modifier = Modifier.weight(1f)
                            )
                            PaymentMethodChip(
                                title = "بطاقة ائتمان",
                                isSelected = selectedMethod == "card",
                                onClick = { selectedMethod = "card" },
                                modifier = Modifier.weight(1f)
                            )
                            PaymentMethodChip(
                                title = "Apple Pay",
                                isSelected = selectedMethod == "apple",
                                onClick = { selectedMethod = "apple" },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Card Details Form
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "بيانات بطاقة الدفع",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryPurple
                            )

                            OutlinedTextField(
                                value = cardHolderName,
                                onValueChange = { cardHolderName = it },
                                label = { Text("اسم صاحب البطاقة", fontSize = 13.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp)
                            )

                            OutlinedTextField(
                                value = cardNumber,
                                onValueChange = { cardNumber = it },
                                label = { Text("رقم البطاقة", fontSize = 13.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedTextField(
                                    value = expiryDate,
                                    onValueChange = { expiryDate = it },
                                    label = { Text("تاريخ الانتهاء", fontSize = 13.sp) },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(14.dp)
                                )

                                OutlinedTextField(
                                    value = cvv,
                                    onValueChange = { cvv = it },
                                    label = { Text("رمز الأمان CVV", fontSize = 13.sp) },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(14.dp)
                                )
                            }
                        }
                    }
                }

                // Security Note Box
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SuccessBg, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "🔒 جميع بيانات الدفع مشفرة ومحمية وفق أعلى معايير الأمان المالي.",
                            fontSize = 12.sp,
                            color = SuccessGreen
                        )
                    }
                }

                // Action Buttons
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(
                            onClick = { navController.navigate(Screen.BookingConfirmation.route) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text(
                                text = "تأكيد وإتمام الدفع (172.50 ريال)",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        OutlinedButton(
                            onClick = { navController.navigate(Screen.PaymentFailed.route) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFC62828))
                        ) {
                            Text(
                                text = "محاكاة فشل عملية الدفع ⚠️",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFC62828)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PaymentMethodChip(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = if (isSelected) 2.5.dp else 1.dp,
                color = if (isSelected) PrimaryPurple else BorderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = if (isSelected) LightLavender else Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) PrimaryPurple else MainText
        )
    }
}
