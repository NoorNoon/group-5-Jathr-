package com.example.jathrproject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jathrproject.ui.theme.DarkPurple
import com.example.jathrproject.ui.theme.LightLavender
import com.example.jathrproject.ui.theme.PrimaryPurple
import com.example.jathrproject.ui.theme.SecondaryText

@Composable
fun JathrHeader(
    title: String,
    subtitle: String,
    backgroundColor: Color = LightLavender,
    showBackArrow: Boolean = false,
    onBackClick: () -> Unit = {},
    showNotification: Boolean = false,
    onNotificationClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        // Centered Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "جذر",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DarkPurple,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = SecondaryText,
                textAlign = TextAlign.Center
            )
        }

        // Back Arrow (Right side in RTL layout)
        if (showBackArrow) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "رجوع",
                    tint = DarkPurple
                )
            }
        }

        // Notification Icon (Left side in RTL layout)
        if (showNotification) {
            IconButton(
                onClick = onNotificationClick,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "الإشعارات",
                    tint = DarkPurple
                )
            }
        }
    }
}
