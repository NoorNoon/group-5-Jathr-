package com.example.jather.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// TODO: logic - I will write it (read the real role from Firebase after login)
object SessionRole {
    var isSpecialist by mutableStateOf(false)
}
