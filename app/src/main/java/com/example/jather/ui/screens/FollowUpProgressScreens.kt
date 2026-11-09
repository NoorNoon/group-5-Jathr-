package com.example.jather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.ui.components.JathrScaffold
import com.example.jather.ui.theme.JathrPrimary
import com.example.jather.ui.theme.JathrTextMain
import com.example.jather.ui.theme.JathrTextSecondary

@Composable
fun WeeklyFollowUpScreen(navController: NavController) {
    var currentWeight by remember { mutableStateOf("74.5") }
    var notes by remember { mutableStateOf("التزمت بالخطة الرياضية والغذائية بشكل جيد.") }

    JathrScaffold(
        title = "المتابعة الأسبوعية",
        navController = navController,
        showBackButton = true,
        showBottomBar = true
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("تسجيل قياسات الأسبوع", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = JathrTextMain)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = currentWeight, onValueChange = { currentWeight = it }, label = { Text("الوزن الحالي (كجم)") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("ملاحظاتك لأخصائي التغذية") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)
            ) {
                Text("إرسال التتقرير", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun ProgressScreen(navController: NavController) {
    JathrScaffold(
        title = "تقدمي ووزني",
        navController = navController,
        showBackButton = true,
        showBottomBar = true
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp)
        ) {
            Text("سجل تغير الوزن", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = JathrTextMain)
            Spacer(modifier = Modifier.height(16.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("الوزن البدئي: 78.0 كجم", fontSize = 14.sp, color = JathrTextSecondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("الوزن الحالي: 74.5 كجم", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = JathrPrimary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("إجمالي الفاقد: 3.5 كجم", fontSize = 14.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
