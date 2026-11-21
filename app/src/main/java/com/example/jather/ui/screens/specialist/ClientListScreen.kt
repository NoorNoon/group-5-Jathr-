package com.example.jather.ui.screens.specialist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jather.navigation.Screen
import com.example.jather.navigation.navigateToSpecialistAnalysisReview
import com.example.jather.navigation.navigateToSpecialistAssessment
import com.example.jather.data.DemoData
import com.example.jather.navigation.navigateToSpecialistClient
import com.example.jather.navigation.navigateToSpecialistFiles
import com.example.jather.ui.components.specialist.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person

data class SpecialistClient(
    val id: Int,
    val name: String,
    val goal: String,
    val week: Int,
    val status: String
)

// Same clients and ids as the plans screens (see DemoData)
val mockClients = DemoData.clients.map {
    SpecialistClient(it.id, it.name, it.goal, it.week, it.status)
}

@Composable
fun ClientListScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("الكل") }

    val filteredClients = mockClients.filter { client ->
        val matchesSearch = client.name.contains(searchQuery) || client.goal.contains(searchQuery)
        val matchesFilter = when (selectedFilter) {
            "نشط" -> client.status == "نشط"
            "يحتاج متابعة" -> client.status == "يحتاج متابعة"
            else -> true
        }
        matchesSearch && matchesFilter
    }

    JathrScrollScreen(
        navController = navController,
        title = "العملاء",
        subtitle = "١٢ عميل تتابع حالاتهم",
        onBack = { navController.popBackStack() },
        selectedBottomTab = 1
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("ابحث باسم العميل", color = SpecialistTextSecondary, fontSize = 13.sp) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = SpecialistPrimary,
                    unfocusedBorderColor = SpecialistBorder
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("الكل", "نشط", "يحتاج متابعة").forEach { filter ->
                    val isSelected = selectedFilter == filter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) SpecialistPrimary else Color.White)
                            .border(1.dp, if (isSelected) SpecialistPrimary else SpecialistBorder, RoundedCornerShape(12.dp))
                            .clickable { selectedFilter = filter }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) Color.White else SpecialistTextMain,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (filteredClients.isEmpty()) {
                Text(
                    text = "لا يوجد عملاء مطابقين للبحث",
                    color = SpecialistTextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                )
            } else {
                filteredClients.forEach { client ->
                    ClientCard(client = client, onClick = { navController.navigateToSpecialistClient(client.id) })
                }
            }
        }
    }
}

@Composable
fun ClientCard(client: SpecialistClient, onClick: () -> Unit) {
    val (badgeBg, badgeText) = when (client.status) {
        "نشط" -> SpecialistGreenBg to SpecialistGreen
        "يحتاج متابعة" -> SpecialistAmberBg to SpecialistAmber
        else -> SpecialistLavender to SpecialistTextSecondary
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, SpecialistBorder, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(SpecialistLavender, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = client.name,
                tint = SpecialistPrimary,
                modifier = Modifier.size(27.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = client.name,
                color = SpecialistDarkPurple,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${client.goal} · الأسبوع ${client.week}",
                color = SpecialistTextSecondary,
                fontSize = 12.sp
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(badgeBg)
                .padding(horizontal = 10.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = client.status,
                color = badgeText,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClientListScreenPreview() {
    ClientListScreen(rememberNavController())
}
