package com.example.jather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.navigation.Screen
import com.example.jather.ui.components.JathrScaffold
import com.example.jather.ui.theme.JathrPrimary
import com.example.jather.ui.theme.JathrTextMain
import com.example.jather.ui.theme.JathrTextSecondary

@Composable
fun PaymentFailedScreen(navController: NavController) {
    JathrScaffold(
        title = "فشل الدفع",
        navController = navController,
        showBackButton = true,
        showBottomBar = true
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("عذراً، فشلت عملية الدفع!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFFC62828))
            Spacer(modifier = Modifier.height(12.dp))
            Text("يرجى التحقق من بيانات البطاقة والمحاولة مرة أخرى.", fontSize = 14.sp, color = JathrTextSecondary, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)
            ) {
                Text("إعادة المحاولة", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}
