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
fun BookAppointmentScreen(navController: NavController) {
    var date by remember { mutableStateOf("2025/06/15") }
    var time by remember { mutableStateOf("05:00 مساءً") }

    JathrScaffold(
        title = "حجز موعد",
        navController = navController,
        showBackButton = true,
        showBottomBar = true
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("اختر موعد الاستشارة", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = JathrTextMain)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("التاريخ") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = time, onValueChange = { time = it }, label = { Text("الوقت") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.navigate(Screen.Payment.route) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)
            ) {
                Text("الانتقال إلى الدفع", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun PaymentScreen(navController: NavController) {
    var cardNumber by remember { mutableStateOf("4532 •••• •••• 8892") }

    JathrScaffold(
        title = "الدفع",
        navController = navController,
        showBackButton = true,
        showBottomBar = true
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("إتمام عملية الدفع (محاكاة)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = JathrTextMain)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = cardNumber, onValueChange = { cardNumber = it }, label = { Text("رقم البطاقة الائتمانية") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            Text("المبلغ الإجمالي: 150 ريال سعودي", fontSize = 14.sp, color = JathrTextSecondary)

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.navigate(Screen.BookingConfirmation.route) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)
            ) {
                Text("إتمام الدفع والحجز (نجاح)", fontSize = 16.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = { navController.navigate(Screen.PaymentFailed.route) },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("محاكاة فشل الدفع", color = Color(0xFFC62828), fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun BookingConfirmationScreen(navController: NavController) {
    JathrScaffold(
        title = "تأكيد الحجز",
        navController = navController,
        showBackButton = false,
        showBottomBar = true
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("تم الحجز بنجاح!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
            Spacer(modifier = Modifier.height(12.dp))
            Text("تم تأكيد موعدك وإرسال التفاصيل إلى بريدك الإلكتروني.", fontSize = 14.sp, color = JathrTextSecondary, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)
            ) {
                Text("العودة إلى الرئيسية", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

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
