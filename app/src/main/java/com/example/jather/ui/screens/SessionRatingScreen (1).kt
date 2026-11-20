package com.example.jather.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.navigation.Screen
import com.example.jather.ui.theme.*

@Composable fun SessionRatingScreen(navController:NavController,id:String){
 val item=SharedAppointmentStore.get(id)?:return
 val initial=SharedAppointmentStore.ratings[id]
 var stars by remember(id){mutableIntStateOf(initial?.first?:0)}
 var comment by remember(id){mutableStateOf(initial?.second?:"")}
 var sent by remember(id){mutableStateOf(initial!=null)}
 SharedAppointmentPage(navController,"تقييم الجلسة للعميل"){
 SharedAppointmentCard{
 Text("بيانات الجلسة المكتملة",color=JathrPrimary)
 SharedAppointmentInfo("الأخصائي",item.person)
 SharedAppointmentInfo("رقم الجلسة",item.id)
 SharedAppointmentInfo("التاريخ",sharedAppointmentDate(item.date))
 SharedAppointmentInfo("الوقت",item.time)
 }
 SharedAppointmentCard{
 Text("تقييم من خمس نجوم",color=JathrPrimary)
 Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.Center){
 (1..5).forEach{n->Text(if(n<=stars)"★" else "☆",fontSize=38.sp,color=Color(0xFFE0A04A),modifier=Modifier.clickable(enabled=!sent){stars=n})}
 }
 Text(if(stars==0)"اختاري تقييمك" else "تقييمك: $stars من 5",modifier=Modifier.fillMaxWidth(),textAlign=TextAlign.Center)
 }
 SharedAppointmentCard{
 Text("تعليق اختياري",color=JathrPrimary)
 OutlinedTextField(value=comment,onValueChange={comment=it},enabled=!sent,label={Text("اكتبي تعليقك")},modifier=Modifier.fillMaxWidth().height(130.dp))
 }
 if(sent)Text("تم إرسال التقييم بنجاح، شكرًا لك!",color=sharedSuccess)
 SharedAppointmentButton("إرسال التقييم",enabled=stars>0&&!sent){SharedAppointmentStore.ratings[id]=stars to comment;sent=true}
 OutlinedButton(onClick={navController.navigate(Screen.Home.route){launchSingleTop=true}},modifier=Modifier.fillMaxWidth()){Text("العودة للصفحة الرئيسية")}
 }
}
