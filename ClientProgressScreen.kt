package com.example.jathrproject.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
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
import com.example.jathrproject.ui.theme.MainText
import com.example.jathrproject.ui.theme.PrimaryPurple
import com.example.jathrproject.ui.theme.ScreenBackground
import com.example.jathrproject.ui.theme.SecondaryText
import com.example.jathrproject.ui.theme.SuccessBackground
import com.example.jathrproject.ui.theme.SuccessGreen

@Composable
fun ClientProgressScreen(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onAppointmentsClick: () -> Unit = {},
    onPlanClick: () -> Unit = {},
    onProgressClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onNavItemClick: (Int) -> Unit = {},
) {
    var selectedNavItem by remember { mutableIntStateOf(3) } // 3 is "تقدّمي"

    val navItems = listOf(
        Pair("الرئيسية", Icons.Default.Home),
        Pair("مواعيدي", Icons.Default.DateRange),
        Pair("خطتي", Icons.AutoMirrored.Filled.List),
        Pair("تقدّمي", Icons.AutoMirrored.Filled.TrendingUp),
        Pair("حسابي", Icons.Default.Person)
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                navItems.forEachIndexed { index, (label, icon) ->
                    val isSelected = selectedNavItem == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            selectedNavItem = index
                            onNavItemClick(index)
                            when (index) {
                                0 -> onHomeClick()
                                1 -> onAppointmentsClick()
                                2 -> onPlanClick()
                                3 -> onProgressClick()
                                4 -> onProfileClick()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = label
                            )
                        },
                        label = {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryPurple,
                            selectedTextColor = PrimaryPurple,
                            indicatorColor = LightLavender,
                            unselectedIconColor = SecondaryText,
                            unselectedTextColor = SecondaryText
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(ScreenBackground)
        ) {
            JathrScrollableContainer {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    // Header
                    JathrHeader(
                        title = "تقدّمي",
                        subtitle = "نظرة على رحلتك خلال ٦ أسابيع",
                        backgroundColor = ScreenBackground,
                        showBackArrow = false,
                        showNotification = true,
                        onNotificationClick = onNotificationClick
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Section 1 — Goal Progress Card
                        GoalProgressCard()

                        // Section 2 — Statistics Cards
                        StatisticsGrid()

                        // Section 3 — Weight Progress Chart Card
                        WeightChartCard()

                        // Section 4 — Weekly Comparison Table Card
                        WeeklyComparisonCard()
                    }
                }
            }
        }
    }
}

@Composable
private fun GoalProgressCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Right side (Start in RTL): Circular progress indicator
            Box(
                modifier = Modifier.size(76.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 8.dp.toPx()
                    val diameter = size.minDimension - strokeWidth
                    val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
                    val arcSize = Size(diameter, diameter)

                    // Background arc
                    drawArc(
                        color = LightLavender,
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(width = strokeWidth)
                    )

                    // Progress arc (48%)
                    drawArc(
                        color = PrimaryPurple,
                        startAngle = -90f,
                        sweepAngle = 0.48f * 360f,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(width = strokeWidth)
                    )
                }

                Text(
                    text = "٤٨٪",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkPurple
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Details on the left (End in RTL)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "تحقيق الهدف",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkPurple
                    )

                    // Status Chip
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SuccessBackground)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "في المسار",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SuccessGreen
                        )
                    }
                }

                Text(
                    text = "٣٫٨ من ٨ كجم",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MainText
                )

                Text(
                    text = "متبقي ٤٫٢ كجم للوصول إلى ٦٦ كجم",
                    fontSize = 12.sp,
                    color = SecondaryText
                )
            }
        }
    }
}

@Composable
private fun StatisticsGrid() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Row 1: 3 equal cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(
                label = "الوزن الحالي (كجم)",
                value = "٧٠٫٢",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "الفرق منذ البداية",
                value = "−٣٫٨",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "الوزن المستهدف",
                value = "٦٦",
                modifier = Modifier.weight(1f)
            )
        }

        // Row 2: 2 equal cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(
                label = "متوسط الخسارة أسبوعياً (كجم)",
                value = "−٠٫٧٦",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "متوسط الالتزام",
                value = "٨٢٪",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .border(1.dp, BorderColor, RoundedCornerShape(14.dp))
            .padding(vertical = 12.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = SecondaryText,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
private fun WeightChartCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "تغيّر الوزن (كجم)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkPurple
                )
                Text(
                    text = "الأسبوع ١–٦",
                    fontSize = 12.sp,
                    color = SecondaryText
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Canvas Chart
            val dataPoints = listOf(74.0f, 73.3f, 72.8f, 71.9f, 70.9f, 70.2f)
            val weekLabels = listOf("أسبوع ١", "أسبوع ٢", "أسبوع ٣", "أسبوع ٤", "أسبوع ٥", "أسبوع ٦")

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                val width = size.width
                val height = size.height - 30.dp.toPx()
                val minY = 65.0f
                val maxY = 75.0f
                val targetY = 66.0f

                // Grid lines
                val gridStep = height / 3
                for (i in 0..3) {
                    val y = i * gridStep
                    drawLine(
                        color = BorderColor.copy(alpha = 0.5f),
                        start = Offset(0f, y),
                        end = Offset(width, y),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                // Dashed target weight reference line (66 kg)
                val targetYPx = height * (1f - ((targetY - minY) / (maxY - minY)))
                drawLine(
                    color = SecondaryText.copy(alpha = 0.6f),
                    start = Offset(0f, targetYPx),
                    end = Offset(width, targetYPx),
                    strokeWidth = 1.5.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )

                // Calculate point positions
                val stepX = width / (dataPoints.size - 1)
                val points = dataPoints.mapIndexed { index, weight ->
                    val x = index * stepX
                    val y = height * (1f - ((weight - minY) / (maxY - minY)))
                    Offset(x, y)
                }

                // Draw gradient fill under line
                val fillPath = Path().apply {
                    moveTo(points.first().x, points.first().y)
                    for (i in 1 until points.size) {
                        lineTo(points[i].x, points[i].y)
                    }
                    lineTo(points.last().x, height)
                    lineTo(points.first().x, height)
                    close()
                }

                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(LightLavender, Color.Transparent),
                        startY = 0f,
                        endY = height
                    )
                )

                // Draw connected purple line
                val linePath = Path().apply {
                    moveTo(points.first().x, points.first().y)
                    for (i in 1 until points.size) {
                        lineTo(points[i].x, points[i].y)
                    }
                }

                drawPath(
                    path = linePath,
                    color = PrimaryPurple,
                    style = Stroke(width = 3.dp.toPx())
                )

                // Draw point markers
                points.forEachIndexed { index, point ->
                    val isLast = index == points.lastIndex
                    val dotRadius = if (isLast) 5.dp.toPx() else 4.dp.toPx()

                    // Dot outer fill
                    drawCircle(
                        color = if (isLast) DarkPurple else PrimaryPurple,
                        radius = dotRadius,
                        center = point
                    )

                    // Inner white core for non-last dots
                    if (!isLast) {
                        drawCircle(
                            color = Color.White,
                            radius = 2.dp.toPx(),
                            center = point
                        )
                    }
                }
            }

            // Week Labels along bottom
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                weekLabels.forEach { label ->
                    Text(
                        text = label,
                        fontSize = 10.sp,
                        color = SecondaryText,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun WeeklyComparisonCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "مقارنة أسبوعية",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkPurple
            )

            // Table Headers
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "المعيار",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = SecondaryText,
                    modifier = Modifier.weight(1.2f)
                )
                Text(
                    text = "هذا الأسبوع",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = SecondaryText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "الماضي",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = SecondaryText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "الفرق",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = SecondaryText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }

            HorizontalDivider(color = BorderColor.copy(alpha = 0.6f), thickness = 1.dp)

            // Row 1: Weight
            ComparisonRow(
                metric = "الوزن",
                current = "٧٠٫٢",
                previous = "٧٠٫٩",
                diff = "−٠٫٧ ↓",
                diffBg = SuccessBackground,
                diffColor = SuccessGreen
            )

            HorizontalDivider(color = BorderColor.copy(alpha = 0.4f), thickness = 1.dp)

            // Row 2: Weekly Change
            ComparisonRow(
                metric = "التغيّر الأسبوعي",
                current = "−٠٫٧",
                previous = "−٠٫٧",
                diff = "٠ =",
                diffBg = LightLavender,
                diffColor = PrimaryPurple
            )

            HorizontalDivider(color = BorderColor.copy(alpha = 0.4f), thickness = 1.dp)

            // Row 3: Adherence
            ComparisonRow(
                metric = "الالتزام",
                current = "٩٠٪",
                previous = "٨٥٪",
                diff = "+٥٪ ↑",
                diffBg = SuccessBackground,
                diffColor = SuccessGreen
            )
        }
    }
}

@Composable
private fun ComparisonRow(
    metric: String,
    current: String,
    previous: String,
    diff: String,
    diffBg: Color,
    diffColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = metric,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkPurple,
            modifier = Modifier.weight(1.2f)
        )
        Text(
            text = current,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = MainText,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = previous,
            fontSize = 13.sp,
            color = SecondaryText,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(diffBg)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = diff,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = diffColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClientProgressScreenPreview() {
    JathrProjectTheme {
        ClientProgressScreen()
    }
}
