package com.example.jather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jather.navigation.Screen
import com.example.jather.ui.theme.*
import java.time.*
import java.util.Locale

private val times=(8*60..22*60 step 20).map { minutes->
 val hour=minutes/60;val m=minutes%60;val h=if(hour%12==0)12 else hour%12
 "%d:%02d %s".format(Locale.US,h,m,if(hour<12)"ص" else "م")
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun AppointmentDetailsScreen(navController:NavController,id:String){
 val item=SharedAppointmentStore.get(id)?:return
 var cancel by remember { mutableStateOf(false) }
 var edit by remember { mutableStateOf(false) }
 var link by remember { mutableStateOf(false) }
 SharedAppointmentPage(navController,"تفاصيل الموعد"){
 SharedAppointmentCard{
 Text("بيانات الموعد",color=JathrPrimary)
 SharedAppointmentInfo("رقم الحجز",item.id)
 SharedAppointmentInfo(if(SharedAppointmentStore.role=="العميل")"الأخصائي" else "العميل",item.person)
 SharedAppointmentInfo("الحالة",if(item.cancelled)"ملغي" else if(item.completed)"مكتمل" else "مؤكد")
 }
 SharedAppointmentCard{
 Text("تاريخ ووقت الجلسة",color=JathrPrimary)
 SharedAppointmentInfo("التاريخ",sharedAppointmentDate(item.date))
 SharedAppointmentInfo("الوقت",item.time)
 SharedAppointmentInfo("نوع الجلسة","عن بُعد")
 }
 if(!item.cancelled&&!item.completed){
 SharedAppointmentButton("فتح رابط الجلسة"){link=true}
 Row(horizontalArrangement=Arrangement.spacedBy(10.dp)){
 OutlinedButton(onClick={edit=true},modifier=Modifier.weight(1f)){Text("تعديل الموعد")}
 OutlinedButton(onClick={cancel=true},modifier=Modifier.weight(1f)){Text("إلغاء الموعد",color=sharedDanger)}
 }
 }
 }
 if(link)AlertDialog(onDismissRequest={link=false},title={Text("رابط الجلسة")},text={Text("رابط الجلسة الحقيقي لم يُضف بعد.")},confirmButton={TextButton(onClick={link=false}){Text("حسنًا")}})
 if(cancel)AlertDialog(onDismissRequest={cancel=false},title={Text("تأكيد إلغاء الموعد")},text={Text("سينتقل الموعد إلى المواعيد السابقة بحالة ملغي. هل تريد المتابعة؟")},confirmButton={TextButton(onClick={
 SharedAppointmentStore.update(item.copy(cancelled=true));SharedAppointmentStore.tab="السابقة";cancel=false
 navController.popBackStack()
 }){Text("تأكيد الإلغاء",color=sharedDanger)}},dismissButton={TextButton(onClick={cancel=false}){Text("رجوع")}})
 if(edit)SharedEditAppointmentDialog(item,onDismiss={edit=false},onSave={date,time->SharedAppointmentStore.update(item.copy(date=date,time=time));edit=false})
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable private fun SharedEditAppointmentDialog(item:SharedAppointment,onDismiss:()->Unit,onSave:(LocalDate,String)->Unit){
 var date by remember(item.id){mutableStateOf(item.date)}
 var time by remember(item.id){mutableStateOf(item.time)}
 var calendar by remember { mutableStateOf(false) }
 var timeList by remember { mutableStateOf(false) }
 AlertDialog(onDismissRequest=onDismiss,title={Text("تعديل الموعد")},text={
 Column(verticalArrangement=Arrangement.spacedBy(10.dp)){
 Text("الأيام المتاحة: الأحد إلى الخميس")
 OutlinedButton(onClick={calendar=true},modifier=Modifier.fillMaxWidth()){Text("التاريخ: ${sharedAppointmentDate(date)}")}
 OutlinedButton(onClick={timeList=true},modifier=Modifier.fillMaxWidth()){Text("الوقت: $time")}
 Text("الأوقات من 8 صباحًا إلى 10 مساءً كل 20 دقيقة")
 }
 },confirmButton={TextButton(onClick={onSave(date,time)},enabled=date.dayOfWeek!=DayOfWeek.FRIDAY&&date.dayOfWeek!=DayOfWeek.SATURDAY){Text("حفظ التعديل")}},dismissButton={TextButton(onClick=onDismiss){Text("إلغاء")}})
 if(calendar){
 val state=rememberDatePickerState(initialSelectedDateMillis=date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli(),selectableDates=object:SelectableDates{
 override fun isSelectableDate(utcTimeMillis:Long):Boolean{
 val day=Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneOffset.UTC).dayOfWeek
 return day!=DayOfWeek.FRIDAY&&day!=DayOfWeek.SATURDAY
 }
 })
 DatePickerDialog(onDismissRequest={calendar=false},confirmButton={TextButton(onClick={state.selectedDateMillis?.let{date=Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).toLocalDate()};calendar=false}){Text("اختيار")}},dismissButton={TextButton(onClick={calendar=false}){Text("رجوع")}}){DatePicker(state=state)}
 }
 if(timeList)AlertDialog(onDismissRequest={timeList=false},title={Text("اختيار الوقت")},text={
 Column(Modifier.heightIn(max=340.dp).verticalScroll(rememberScrollState())){
 times.forEach{option->TextButton(onClick={time=option;timeList=false},modifier=Modifier.fillMaxWidth()){Text(option)}}
 }
 },confirmButton={TextButton(onClick={timeList=false}){Text("إغلاق")}})
}
