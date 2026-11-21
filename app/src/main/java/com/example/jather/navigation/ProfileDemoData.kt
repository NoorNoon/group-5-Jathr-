package com.example.jather.navigation

import com.example.jather.data.DemoData
import com.example.jather.ui.screens.AccountUiState
import com.example.jather.ui.screens.EditProfileUiState

// Demo data shown after Login (Sign Up starts empty).
// TODO: logic - I will write it (replace with real data from Firestore)

fun demoAccountState(isSpecialist: Boolean): AccountUiState =
    if (isSpecialist) {
        AccountUiState(
            isSpecialist = true,
            fullName = DemoData.SPECIALIST_NAME,
            email = "sara@example.com",
            phone = "0559876543",
            specialization = "تغذية علاجية",
            qualification = "بكالوريوس علوم التغذية",
            certificates = "شهادة هيئة التخصصات الصحية",
            yearsOfExperience = "5 سنوات",
            workplace = "مستشفى الملك فيصل",
            newNotificationsCount = 2
        )
    } else {
        AccountUiState(
            isSpecialist = false,
            fullName = DemoData.currentClientName,
            email = "noura@example.com",
            phone = "0512345678",
            age = "٢٨ سنة",
            heightWeight = "١٦٢ سم / ٧٠٫٢ كجم",
            goal = DemoData.client(DemoData.CURRENT_CLIENT_ID)?.goal.orEmpty(),
            chronicConditions = "ضغط الدم",
            allergies = "الفول السوداني",
            medicalFilesCount = 4,
            newNotificationsCount = 2
        )
    }

fun demoEditProfileState(isSpecialist: Boolean): EditProfileUiState =
    if (isSpecialist) {
        EditProfileUiState(
            isSpecialist = true,
            fullName = DemoData.SPECIALIST_NAME,
            phone = "0559876543",
            email = "sara@example.com",
            gender = "أنثى",
            specialization = "تغذية علاجية",
            qualification = "بكالوريوس علوم التغذية",
            certificates = "شهادة هيئة التخصصات الصحية",
            yearsOfExperience = "5",
            workplace = "مستشفى الملك فيصل"
        )
    } else {
        EditProfileUiState(
            isSpecialist = false,
            fullName = DemoData.currentClientName,
            phone = "0512345678",
            email = "noura@example.com",
            gender = "أنثى",
            birthDate = "1997/04/12",
            heightCm = "162",
            weightKg = "70.2",
            goal = DemoData.client(DemoData.CURRENT_CLIENT_ID)?.goal.orEmpty(),
            chronicConditions = "ضغط الدم",
            allergies = "الفول السوداني"
        )
    }
