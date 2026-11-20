package com.example.jather.ui.components

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jather.ui.theme.DarkPurple
import com.example.jather.ui.theme.LightLavender
import com.example.jather.ui.theme.PrimaryPurple
import com.example.jather.ui.theme.SecondaryText

@Composable
fun JathrHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = LightLavender,

    // Leading Icon (e.g., Back Button)
    showBackArrow: Boolean = false,
    backIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    backIconContentDescription: String = "رجوع",
    onBackClick: () -> Unit = {},

    // Trailing Icon (e.g., Notification or Custom Action)
    showNotification: Boolean = false,
    notificationIcon: ImageVector = Icons.Default.Notifications,
    notificationContentDescription: String = "الإشعارات",
    onNotificationClick: () -> Unit = {},

    // Optional custom composable slot for advanced actions/badges
    trailingContent: (@Composable () -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        // Centered Titles
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

        // Leading Action (Back Arrow)
        if (showBackArrow) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = backIcon,
                    contentDescription = backIconContentDescription,
                    tint = DarkPurple
                )
            }
        }

        // Trailing Action (Notification or Custom Slot)
        Box(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterEnd)
        ) {
            if (trailingContent != null) {
                trailingContent()
            } else if (showNotification) {
                IconButton(
                    onClick = onNotificationClick,
                    modifier = Modifier.matchParentSize()
                ) {
                    Icon(
                        imageVector = notificationIcon,
                        contentDescription = notificationContentDescription,
                        tint = DarkPurple
                    )
                }
            }
        }
    }
}
