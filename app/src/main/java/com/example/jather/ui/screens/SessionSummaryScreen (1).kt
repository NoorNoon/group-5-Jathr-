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
 val item=HayaAppointmentStore.get(id)?:return
 val initial=HayaAppointmentStore.summaries[id]
 var notes by remember(id){mutableStateOf(initial?.first?:"")}
 var status by remember(id){mutableStateOf(initial?.second?:"مكتملة")}
 var saved by remember(id){mutableStateOf(initial!=null)}
 HayaAppointmentPage(navController,"ملخص الجلسة للأخصائي"){
 HayaAppointmentCard{
 Text("بيانات الجلسة",color=JathrPrimary)
 HayaAppointmentInfo("العميل",item.person)
 HayaAppointmentInfo("رقم الجلسة",item.id)
 HayaAppointmentInfo("التاريخ",hayaAppointmentDate(item.date))
 HayaAppointmentInfo("الوقت",item.time)
 }
 HayaAppointmentCard{
 Text("ملاحظات الجلسة",color=JathrPrimary)
 OutlinedTextField(value=notes,onValueChange={notes=it;saved=false},label={Text("ملاحظات الأخصائي")},modifier=Modifier.fillMaxWidth().height(150.dp))
 }
 HayaAppointmentCard{
 Text("حالة الجلسة",color=JathrPrimary)
 listOf("مكتملة","لم يحضر العميل","مؤجلة").forEach{option->
 Row{RadioButton(selected=status==option,onClick={status=option;saved=false});Text(option,modifier=Modifier.padding(top=12.dp))}
 }
 }
 if(saved)Text("تم حفظ الملخص بنجاح",color=hayaSuccess)
 HayaAppointmentButton("حفظ الملخص"){HayaAppointmentStore.summaries[id]=notes to status;saved=true}
 OutlinedButton(onClick={navController.navigate(Screen.Home.route){launchSingleTop=true}},modifier=Modifier.fillMaxWidth()){Text("العودة للصفحة الرئيسية")}
 }
}
