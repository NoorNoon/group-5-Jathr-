
package com.example.jather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
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
import com.example.jather.ui.components.JathrScaffold
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.draw.clip
import com.example.jather.ui.theme.*
import com.example.jather.navigation.Screen

private val Purple = Color(0xFF6B4E8C)
private val DarkPurple = Color(0xFF3B2A50)
private val Lavender = Color(0xFFEDE5F4)
private val Background = Color(0xFFF7F3FA)
private val Secondary = Color(0xFF7A7085)

@Composable
fun WeeklyFollowUpScreen(navController: NavController) {
    ClientFollowUpScreen(
        onBack = { navController.popBackStack() },
        onSubmitFollowUp = { navController.popBackStack() }
    )
}

@Composable
fun ClientFollowUpScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onSubmitFollowUp: () -> Unit = {}
) {
    var step by remember { mutableIntStateOf(0) }
    var weight by remember { mutableStateOf("") }
    var adherence by remember { mutableStateOf("جيد") }
    var water by remember { mutableStateOf("٤–٦") }
    var energy by remember { mutableStateOf("طبيعية") }
    var difficulty by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var requestPlanChange by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var showReview by remember { mutableStateOf(false) }

    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Background)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Lavender)
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "رجوع",
                        tint = Purple
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "جذر",
                        color = Purple,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Text(
                        "المتابعة الأسبوعية",
                        color = DarkPurple,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Text("٢ / ٦", color = Secondary, fontSize = 12.sp)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "الأسبوع الثاني · ٥–١١ أكتوبر",
                    color = Secondary,
                    fontSize = 13.sp
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("القياسات", "الالتزام", "الملاحظات")
                        .forEachIndexed { index, title ->
                            Surface(
                                modifier = Modifier.weight(1f),
                                color = if (step == index) Purple else Lavender,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    title,
                                    modifier = Modifier.padding(12.dp),
                                    color = if (step == index)
                                        Color.White else DarkPurple,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                }

                when (step) {
                    0 -> {
                        SectionCard("ملخص التقدم") {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                MetricCard("الوزن السابق", "70.8 كجم", Modifier.weight(1f))
                                MetricCard("الوزن الحالي", "70.2 كجم", Modifier.weight(1f))
                            }
                            Text(
                                "القيم المعروضة مثال تجريبي إلى حين ربط بيانات العميل.",
                                color = Secondary,
                                fontSize = 11.sp
                            )
                        }

                        SectionCard("تحديث القياسات") {
                            OutlinedTextField(
                                value = weight,
                                onValueChange = {
                                    weight = it
                                    error = null
                                },
                                label = { Text("وزنك الحالي (كجم)") },
                                placeholder = { Text("مثال: 70.2") },
                                singleLine = true,
                                isError = error != null,
                                modifier = Modifier.fillMaxWidth()
                            )
                            if (error != null) {
                                Text(
                                    error!!,
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    1 -> {
                        SectionCard("مدى الالتزام بالخطة") {
                            ChoiceGroup(
                                listOf("ممتاز", "جيد", "متوسط", "ضعيف"),
                                adherence
                            ) { adherence = it }
                        }

                        SectionCard("شرب الماء يوميًا") {
                            ChoiceGroup(
                                listOf("أقل من ٤", "٤–٦", "٧–٩", "أكثر من ٩"),
                                water
                            ) { water = it }
                        }

                        SectionCard("كيف كان مستوى طاقتك؟") {
                            ChoiceGroup(
                                listOf("مرتفعة", "طبيعية", "منخفضة"),
                                energy
                            ) { energy = it }
                        }
                    }

                    2 -> {
                        SectionCard("الصعوبات التي واجهتك") {
                            OutlinedTextField(
                                value = difficulty,
                                onValueChange = { difficulty = it },
                                placeholder = {
                                    Text("مثل ضيق الوقت أو صعوبة تحضير الوجبات")
                                },
                                minLines = 3,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        SectionCard("رسالة إلى الأخصائي") {
                            OutlinedTextField(
                                value = notes,
                                onValueChange = { notes = it },
                                placeholder = {
                                    Text("اكتب أسئلتك أو ملاحظاتك...")
                                },
                                minLines = 3,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = requestPlanChange,
                                    onCheckedChange = {
                                        requestPlanChange = it
                                    }
                                )
                                Text("أحتاج مراجعة أو تعديل خطتي الغذائية")
                            }
                        }

                        SectionCard("حالة المتابعة") {
                            Text(
                                "جاهزة للمراجعة",
                                color = Purple,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "بعد ربط قاعدة البيانات ستظهر حالة الإرسال ومراجعة الأخصائي.",
                                color = Secondary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        if (step > 0) step-- else onBack()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("السابق")
                }

                Button(
                    onClick = {
                        if (step == 0) {
                            val value = weight.toDoubleOrNull()
                            if (value == null || value <= 0.0 || value > 500.0) {
                                error = "أدخل وزنًا صحيحًا بالكيلوجرام"
                                return@Button
                            }
                        }

                        if (step < 2) {
                            step++
                        } else {
                            showReview = true
                        }
                    },
                    modifier = Modifier.weight(2f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple
                    )
                ) {
                    Text(if (step == 2) "مراجعة المتابعة" else "التالي")
                }
            }
        }

        if (showReview) {
            AlertDialog(
                onDismissRequest = { showReview = false },
                title = { Text("مراجعة بيانات المتابعة") },
                text = {
                    Text(
                        "الوزن: $weight كجم\n" +
                                "الالتزام: $adherence\n" +
                                "الماء: $water\n" +
                                "الطاقة: $energy\n" +
                                "طلب تعديل الخطة: " +
                                if (requestPlanChange) "نعم" else "لا"
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        showReview = false
                        onSubmitFollowUp()
                    }) {
                        Text("تأكيد")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showReview = false }) {
                        Text("تعديل")
                    }
                }
            )
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content = {
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    color = DarkPurple,
                    fontSize = 16.sp
                )
                content()
            }
        )
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Lavender,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, color = Secondary, fontSize = 12.sp)
            Spacer(Modifier.height(6.dp))
            Text(
                value,
                color = DarkPurple,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ChoiceGroup(
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    options.chunked(2).forEach { rowOptions ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            rowOptions.forEach { option ->
                val active = option == selected
                OutlinedButton(
                    onClick = { onSelect(option) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (active) Lavender else Color.White,
                        contentColor = DarkPurple
                    )
                ) {
                    Text(
                        option,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
fun ProgressScreen(navController: NavController) {
    JathrScaffold(
        title = "",
        navController = navController,
        showTopBar = false,
        showBottomBar = true
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9F7FC))
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Modern Header matching SharedHomeScreen style
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "تقدّمي",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = JathrTextMain
                    )
                    Text(
                        text = "متابعة رحلتك الصحية",
                        fontSize = 14.sp,
                        color = JathrTextSecondary
                    )
                }
                Surface(
                    color = JathrLavender,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "جذر",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        color = JathrPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }

            // Section A — Progress Overview Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, JathrBorder)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "الوزن الحالي",
                                fontSize = 12.sp,
                                color = JathrTextSecondary
                            )
                            Text(
                                text = "٧٠٫٢ كجم",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = JathrPrimary
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "الهدف المطلوب",
                                fontSize = 12.sp,
                                color = JathrTextSecondary
                            )
                            Text(
                                text = "٦٦٫٠ كجم",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = JathrTextMain
                            )
                        }
                    }

                    // Progress bar
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("البداية: ٧٤ كجم", fontSize = 11.sp, color = JathrTextSecondary)
                            Text("خسرتِ ٣٫٨ من ٨ كجم", fontSize = 11.sp, color = JathrPrimary, fontWeight = FontWeight.SemiBold)
                        }
                        LinearProgressIndicator(
                            progress = { 0.48f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = JathrPrimary,
                            trackColor = JathrLavender
                        )
                    }
                }
            }

            // Section B — Weight Progress Chart
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, JathrBorder)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "تطور الوزن (آخر ٦ أسابيع)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = JathrTextMain
                        )
                        Surface(
                            color = JathrLavender,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "بيانات تجريبية",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 10.sp,
                                color = JathrPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    val weights = listOf(72.5f, 72.0f, 71.5f, 70.9f, 70.5f, 70.2f)
                    val weeks = listOf("أسبوع ١", "أسبوع ٢", "أسبوع ٣", "أسبوع ٤", "أسبوع ٥", "أسبوع ٦")

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(top = 8.dp, bottom = 4.dp)
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val width = size.width
                            val height = size.height
                            val minWeight = weights.minOrNull() ?: 60f
                            val maxWeight = weights.maxOrNull() ?: 80f
                            val range = (maxWeight - minWeight).coerceAtLeast(1f)
                            val paddingVertical = 24.dp.toPx()
                            val usableHeight = height - paddingVertical * 2

                            val points = weights.mapIndexed { index, weight ->
                                val x = index * (width / (weights.size - 1))
                                val y = paddingVertical + usableHeight * (1f - (weight - minWeight) / range)
                                Offset(x, y)
                            }

                            // Draw subtle horizontal grid lines
                            for (i in 0..3) {
                                val gy = paddingVertical + (usableHeight / 3) * i
                                drawLine(
                                    color = JathrBorder.copy(alpha = 0.5f),
                                    start = Offset(0f, gy),
                                    end = Offset(width, gy),
                                    strokeWidth = 1f
                                )
                            }

                            // Draw connecting line
                            for (i in 0 until points.size - 1) {
                                drawLine(
                                    color = JathrPrimary,
                                    start = points[i],
                                    end = points[i + 1],
                                    strokeWidth = 3.dp.toPx()
                                )
                            }

                            // Draw points
                            points.forEach { point ->
                                drawCircle(
                                    color = Color.White,
                                    radius = 6.dp.toPx(),
                                    center = point
                                )
                                drawCircle(
                                    color = JathrPrimary,
                                    radius = 4.dp.toPx(),
                                    center = point
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        weeks.forEach { week ->
                            Text(
                                text = week,
                                fontSize = 10.sp,
                                color = JathrTextSecondary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Section C — Weekly Statistics (2 Compact Cards)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, JathrBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("التغيير الأسبوعي", fontSize = 12.sp, color = JathrTextSecondary)
                        Text("-٠٫٧ كجم", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                        Text("ممتاز مقارنة بالهدف", fontSize = 10.sp, color = JathrTextSecondary)
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, JathrBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("الالتزام بالخطة", fontSize = 12.sp, color = JathrTextSecondary)
                        Text("٩٠٪", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = JathrPrimary)
                        Text("التزام عالي هذا الأسبوع", fontSize = 10.sp, color = JathrTextSecondary)
                    }
                }
            }

            // Section D — Weekly Follow-Up Button
            Button(
                onClick = { navController.navigate(Screen.WeeklyFollowUp.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)
            ) {
                Text(
                    text = "تسجيل المتابعة الأسبوعية",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
