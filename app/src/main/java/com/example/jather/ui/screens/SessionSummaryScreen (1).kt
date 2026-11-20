package com.example.jather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jather.navigation.Screen
import com.example.jather.ui.theme.*

@Composable fun SessionSummaryScreen(navController:NavController,id:String){
 val item=SharedAppointmentStore.get(id)?:return
 val initial=SharedAppointmentStore.summaries[id]
 var notes by remember(id){mutableStateOf(initial?.first?:"")}
 var status by remember(id){mutableStateOf(initial?.second?:"مكتملة")}
 var saved by remember(id){mutableStateOf(initial!=null)}
 SharedAppointmentPage(navController,"ملخص الجلسة للأخصائي"){
 SharedAppointmentCard{
 Text("بيانات الجلسة",color=JathrPrimary)
 SharedAppointmentInfo("العميل",item.person)
 SharedAppointmentInfo("رقم الجلسة",item.id)
 SharedAppointmentInfo("التاريخ",sharedAppointmentDate(item.date))
 SharedAppointmentInfo("الوقت",item.time)
 }
 SharedAppointmentCard{
 Text("ملاحظات الجلسة",color=JathrPrimary)
 OutlinedTextField(value=notes,onValueChange={notes=it;saved=false},label={Text("ملاحظات الأخصائي")},modifier=Modifier.fillMaxWidth().height(150.dp))
 }
 SharedAppointmentCard{
 Text("حالة الجلسة",color=JathrPrimary)
 listOf("مكتملة","لم يحضر العميل","مؤجلة").forEach{option->
 Row{RadioButton(selected=status==option,onClick={status=option;saved=false});Text(option,modifier=Modifier.padding(top=12.dp))}
 }
 }
 if(saved)Text("تم حفظ الملخص بنجاح",color=sharedSuccess)
 SharedAppointmentButton("حفظ الملخص"){SharedAppointmentStore.summaries[id]=notes to status;saved=true}
 OutlinedButton(onClick={navController.navigate(Screen.Home.route){launchSingleTop=true}},modifier=Modifier.fillMaxWidth()){Text("العودة للصفحة الرئيسية")}
 }
}
