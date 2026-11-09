package com.example.jather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.ui.components.JathrScaffold
import com.example.jather.ui.theme.JathrPrimary
import com.example.jather.ui.theme.JathrTextMain
import com.example.jather.ui.theme.JathrTextSecondary

@Composable
fun NutritionPlanScreen(navController: NavController) {
    JathrScaffold(
        title = "الخطة الغذائية",
        navController = navController,
        showBackButton = true,
        showBottomBar = true
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("وجبات اليوم - البرنامج المخصص", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = JathrTextMain)
            Spacer(modifier = Modifier.height(16.dp))

            MealCard(title = "وجبة الإفطار", details = "بيض مسلوق + خبز أسمر + شاي أخضر بدون سكر")
            Spacer(modifier = Modifier.height(12.dp))
            MealCard(title = "وجبة الغداء", details = "صدر دجاج مشوي + أرز بسمتي (1 كوب) + سلطة خضراء")
            Spacer(modifier = Modifier.height(12.dp))
            MealCard(title = "وجبة العشاء", details = "زبادي يوناني + تفاحة + حفنة مكسرات غير منكهة")
        }
    }
}

@Composable
fun MealCard(title: String, details: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = JathrPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(details, fontSize = 14.sp, color = JathrTextSecondary)
        }
    }
}
