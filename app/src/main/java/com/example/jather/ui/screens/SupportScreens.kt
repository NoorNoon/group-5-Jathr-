package com.example.jather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.ui.components.JathrScaffold
import com.example.jather.ui.theme.JathrPrimary
import com.example.jather.ui.theme.JathrTextSecondary

@Composable
fun NotificationsScreen(navController: NavController) {
    JathrScaffold(
        title = "الإشعارات",
        navController = navController,
        showBackButton = true,
        showBottomBar = true
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("موعد استشارة جديد", fontWeight = FontWeight.Bold, color = JathrPrimary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("تم تأكيد موعدك القادم مع أ. سارة الشمري غداً الساعة 4 عصراً.", fontSize = 14.sp, color = JathrTextSecondary)
                }
            }
        }
    }
}

@Composable
fun MedicalFilesScreen(navController: NavController) {
    JathrScaffold(
        title = "الملفات الطبية",
        navController = navController,
        showBackButton = true,
        showBottomBar = true
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("تقرير تحاليل الدم الشامل", fontWeight = FontWeight.Bold, color = JathrPrimary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("تاريخ الرفع: 2025/05/01", fontSize = 14.sp, color = JathrTextSecondary)
                }
            }
        }
    }
}

@Composable
fun EditProfileScreen(navController: NavController) {
    JathrScaffold(
        title = "تعديل الملف الشخصي",
        navController = navController,
        showBackButton = true,
        showBottomBar = true
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp), verticalArrangement = Arrangement.Center) {
            OutlinedTextField(value = "أحمد محمد", onValueChange = {}, label = { Text("الاسم") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)) {
                Text("حفظ التغييرات", color = Color.White)
            }
        }
    }
}

@Composable
fun RateSessionScreen(navController: NavController) {
    JathrScaffold(
        title = "تقييم الجلسة",
        navController = navController,
        showBackButton = true,
        showBottomBar = true
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text("كيف كانت تجربتك مع أخصائي التغذية؟", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = JathrPrimary)
            Spacer(modifier = Modifier.height(16.dp))
            Text("★★★★★", fontSize = 32.sp, color = Color(0xFFFFB300))
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)) {
                Text("إرسال التقييم", color = Color.White)
            }
        }
    }
}
