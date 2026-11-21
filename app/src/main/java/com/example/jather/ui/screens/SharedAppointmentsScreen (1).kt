package com.example.jather.ui.screens

import com.example.jather.data.DemoData
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.navigation.Screen
import com.example.jather.ui.components.JathrScaffold
import com.example.jather.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

internal data class SharedAppointment(val id:String,val person:String,val date:LocalDate,val time:String,val completed:Boolean=false,val cancelled:Boolean=false)
internal object SharedAppointmentStore {
 val items = mutableStateListOf(
 SharedAppointment("2041",DemoData.clientName(2),LocalDate.of(2026,11,22),"4:00 م"),
 SharedAppointment("2097",DemoData.clientName(4),LocalDate.of(2026,11,24),"6:20 م"),
 SharedAppointment("2100",DemoData.clientName(2),LocalDate.of(2026,11,26),"5:00 م"),
 SharedAppointment("1981",DemoData.clientName(2),LocalDate.of(2026,11,18),"5:00 م",true),
 SharedAppointment("3012",DemoData.clientName(6),LocalDate.of(2026,11,22),"10:00 ص"),
 SharedAppointment("3013",DemoData.clientName(3),LocalDate.of(2026,11,23),"2:00 م"),
 SharedAppointment("3014",DemoData.clientName(5),LocalDate.of(2026,11,25),"3:00 م"),
 SharedAppointment("3005",DemoData.clientName(5),LocalDate.of(2026,11,17),"3:00 م",true))
 val summaries=mutableStateMapOf<String,Pair<String,String>>()
 val ratings=mutableStateMapOf<String,Pair<Int,String>>()
 var role by mutableStateOf("العميل")
 var tab by mutableStateOf("القادمة")
 fun get(id:String)=items.firstOrNull{it.id==id}
 fun update(item:SharedAppointment){val i=items.indexOfFirst{it.id==item.id};if(i>=0)items[i]=item}
}
internal fun sharedAppointmentDate(date:LocalDate)=date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy",Locale.US))
internal val sharedDanger=Color(0xFFB3261E)
internal val sharedSuccess=Color(0xFF28854D)

@Composable internal fun SharedAppointmentCard(content:@Composable ColumnScope.()->Unit){
 Surface(modifier=Modifier.fillMaxWidth(),color=Color.White,shape=RoundedCornerShape(14.dp),border=BorderStroke(1.dp,JathrBorder)){
 Column(Modifier.padding(16.dp),verticalArrangement=Arrangement.spacedBy(12.dp),content=content)
 }
}
@Composable internal fun SharedAppointmentInfo(label:String,value:String){
 Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){
 Text(label,color=JathrTextSecondary,fontSize=13.sp)
 Text(value,color=JathrTextMain,fontSize=13.sp,fontWeight=FontWeight.Medium)
 }
}
@Composable internal fun SharedAppointmentButton(label:String,enabled:Boolean=true,onClick:()->Unit){
 Button(onClick=onClick,enabled=enabled,modifier=Modifier.fillMaxWidth().height(50.dp),colors=ButtonDefaults.buttonColors(containerColor=JathrPrimary),shape=RoundedCornerShape(12.dp)){Text(label)}
}
@Composable internal fun SharedAppointmentPage(navController:NavController,title:String,content:@Composable ColumnScope.()->Unit){
 CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl){
 JathrScaffold(title=title,navController=navController,showBottomBar=true){ padding->
 Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),verticalArrangement=Arrangement.spacedBy(14.dp)){
 Text("جذر",color=JathrPrimary,fontWeight=FontWeight.Bold,fontSize=20.sp,modifier=Modifier.align(Alignment.CenterHorizontally))
 Text(title,color=JathrTextMain,fontWeight=FontWeight.Bold,fontSize=19.sp)
 content()
 }
 }
 }
}
@Composable fun SharedAppointmentsScreen(navController:NavController){
 val role=SharedAppointmentStore.role;val tab=SharedAppointmentStore.tab
 val visible=SharedAppointmentStore.items.filter { item->
 (if(role=="العميل") !item.id.startsWith("3") else item.id.startsWith("3")) &&
 (if(tab=="القادمة") !item.completed&&!item.cancelled else item.completed||item.cancelled)
 }
 SharedAppointmentPage(navController,"مواعيدي"){
 Row(horizontalArrangement=Arrangement.spacedBy(10.dp)){
 listOf("العميل","الأخصائي").forEach{option->
 val selected=role==option
 Surface(Modifier.weight(1f).height(48.dp).clickable{SharedAppointmentStore.role=option;SharedAppointmentStore.tab="القادمة"},color=if(selected)JathrPrimary else Color.White,shape=RoundedCornerShape(12.dp),border=BorderStroke(1.dp,JathrBorder)){
 Box(contentAlignment=Alignment.Center){Text(option,color=if(selected)Color.White else JathrPrimary,fontWeight=FontWeight.Bold)}
 }
 }
 }
 Text(if(role=="العميل")"مواعيدي مع الأخصائي" else "جدول مواعيد الأخصائي",fontWeight=FontWeight.Bold,color=JathrTextMain)
 Row(horizontalArrangement=Arrangement.spacedBy(10.dp)){
 listOf("القادمة","السابقة").forEach{option->
 FilterChip(selected=tab==option,onClick={SharedAppointmentStore.tab=option},label={Text(option)},modifier=Modifier.weight(1f))
 }
 }
 if(visible.isEmpty())Text("لا توجد مواعيد",color=JathrTextSecondary)
 visible.forEach{item->
 SharedAppointmentCard{
 Row(verticalAlignment=Alignment.CenterVertically){
 Text(item.person,fontWeight=FontWeight.Bold,color=JathrTextMain,modifier=Modifier.weight(1f))
 Text(if(item.cancelled)"ملغي" else if(item.completed)"مكتمل" else "مؤكد",color=if(item.cancelled)sharedDanger else sharedSuccess)
 }
 SharedAppointmentInfo("رقم الحجز",item.id)
 SharedAppointmentInfo("التاريخ",sharedAppointmentDate(item.date))
 SharedAppointmentInfo("الوقت",item.time)
 OutlinedButton(onClick={navController.navigate("shared_appointment_details/${item.id}")},modifier=Modifier.fillMaxWidth()){Text("تفاصيل الموعد",color=JathrPrimary)}
 if(item.completed&&!item.cancelled){
 SharedAppointmentButton(if(role=="الأخصائي")"ملخص الجلسة" else "تقييم الجلسة"){
 navController.navigate(if(role=="الأخصائي")"shared_session_summary/${item.id}" else "shared_session_rating/${item.id}")
 }
 }
 }
 }
 }
}
