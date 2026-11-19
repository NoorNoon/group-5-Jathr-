package com.example.jather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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

// Local Color Palette (Matching PackagesScreen.kt)
private val DarkPurple = Color(0xFF3B2A50)
private val PrimaryPurple = Color(0xFF6B4E8C)
private val LightLavender = Color(0xFFEDE5F4)
private val ScreenBackground = Color(0xFFF7F3FA)
private val BorderColor = Color(0xFFD9CCE6)
private val MainText = Color(0xFF2B2235)
private val SecondaryText = Color(0xFF7A7085)

data class Specialist(
    val id: String,
    val name: String,
    val title: String,
    val rating: String,
    val avatarEmoji: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointmentScreen(navController: NavController) {
    val specialists = remember {
        listOf(
            Specialist("1", "أ. سارة الشمري", "أخصائية تغذية علاجية", "⭐ 4.9", "👩‍⚕️"),
            Specialist("2", "د. خالد العمري", "مستشار سمنة وزيادة وزن", "⭐ 4.8", "👨‍⚕️"),
            Specialist("3", "أ. نورة الفالح", "أخصائية تغذية رياضية", "⭐ 4.9", "🥗")
        )
    }

    val dates = remember {
        listOf("اليوم (14 يونيو)", "غداً (15 يونيو)", "الأحد (16 يونيو)", "الإثنين (17 يونيو)", "الثلاثاء (18 يونيو)")
    }

    val timeSlots = remember {
        listOf("04:00 مساءً", "05:00 مساءً", "06:30 مساءً", "08:00 مساءً", "09:00 مساءً")
    }

    val consultationTypes = remember {
        listOf("استشارة فيديو مباشرة", "محادثة صوتية", "محادثة نصية (شات)")
    }

    var selectedSpecialistId by remember { mutableStateOf("1") }
    var selectedDate by remember { mutableStateOf(dates[1]) }
    var selectedTimeSlot by remember { mutableStateOf(timeSlots[1]) }
    var selectedType by remember { mutableStateOf(consultationTypes[0]) }
    var notes by remember { mutableStateOf("") }

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
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Single Clean Header
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
                            text = "حجز موعد استشارة",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkPurple
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "اختر الأخصائي، نوع الجلسة، والوقت المناسب لتقديم الرعاية المخصصة لك",
                            fontSize = 12.sp,
                            color = SecondaryText,
                            lineHeight = 16.sp
                        )
                    }
                }

                // 1. Specialist Selection
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "1. اختر أخصائي التغذية",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkPurple
                        )

                        specialists.forEach { specialist ->
                            val isSelected = selectedSpecialistId == specialist.id
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        if (isSelected) 2.5.dp else 1.dp,
                                        if (isSelected) PrimaryPurple else BorderColor,
                                        RoundedCornerShape(16.dp)
                                    )
                                    .clickable { selectedSpecialistId = specialist.id },
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) LightLavender else Color.White
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(CircleShape)
                                            .background(LightLavender),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(specialist.avatarEmoji, fontSize = 24.sp)
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = specialist.name,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) DarkPurple else MainText
                                        )
                                        Text(
                                            text = specialist.title,
                                            fontSize = 13.sp,
                                            color = SecondaryText
                                        )
                                    }

                                    Text(
                                        text = specialist.rating,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryPurple
                                    )
                                }
                            }
                        }
                    }
                }

                // 2. Consultation Type
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "2. نوع الاستشارة",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkPurple
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(consultationTypes) { type ->
                                val isSelected = selectedType == type
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { selectedType = type },
                                    label = { Text(type, fontSize = 13.sp) },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = PrimaryPurple,
                                        selectedLabelColor = Color.White,
                                        containerColor = LightLavender,
                                        labelColor = MainText
                                    )
                                )
                            }
                        }
                    }
                }

                // 3. Date Selection
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "3. اختر التاريخ",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkPurple
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(dates) { dateText ->
                                val isSelected = selectedDate == dateText
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (isSelected) PrimaryPurple else LightLavender)
                                        .clickable { selectedDate = dateText }
                                        .padding(horizontal = 14.dp, vertical = 10.dp)
                                ) {
                                    Text(
                                        text = dateText,
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) Color.White else MainText
                                    )
                                }
                            }
                        }
                    }
                }

                // 4. Time Selection
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "4. اختر الوقت المتاح",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkPurple
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            timeSlots.take(3).forEach { slot ->
                                val isSelected = selectedTimeSlot == slot
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .border(
                                            if (isSelected) 2.dp else 1.dp,
                                            if (isSelected) PrimaryPurple else BorderColor,
                                            RoundedCornerShape(12.dp)
                                        )
                                        .background(if (isSelected) PrimaryPurple else Color.White)
                                        .clickable { selectedTimeSlot = slot }
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = slot,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else MainText
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            timeSlots.drop(3).forEach { slot ->
                                val isSelected = selectedTimeSlot == slot
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .border(
                                            if (isSelected) 2.dp else 1.dp,
                                            if (isSelected) PrimaryPurple else BorderColor,
                                            RoundedCornerShape(12.dp)
                                        )
                                        .background(if (isSelected) PrimaryPurple else Color.White)
                                        .clickable { selectedTimeSlot = slot }
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = slot,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else MainText
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                // 5. Notes / Reason
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "5. ملاحظات أو سبب الاستشارة (اختياري)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MainText
                        )
                        OutlinedTextField(
                            value = notes,
                            onValueChange = { notes = it },
                            placeholder = { Text("مثال: متابعة نظام تخسيس، حساسية من بعض الأطعمة...", fontSize = 13.sp, color = SecondaryText) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp)
                        )
                    }
                }

                // Summary & Navigation Button
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = LightLavender.copy(alpha = 0.5f))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text("ملخص الموعد المحدد:", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryPurple)
                                val specName = specialists.find { it.id == selectedSpecialistId }?.name ?: ""
                                Text("• $specName - $selectedType", fontSize = 13.sp, color = MainText)
                                Text("• الموعد: $selectedDate - $selectedTimeSlot", fontSize = 13.sp, color = MainText)
                                Text("• القيمة: 150 ريال سعودي", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryPurple)
                            }
                        }

                        Button(
                            onClick = { navController.navigate(Screen.Payment.route) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text(
                                text = "الانتقال إلى تفاصيل الدفع",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
