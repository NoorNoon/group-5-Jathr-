package com.example.jather.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jather.R
import com.example.jather.ui.theme.JathrPrimary
import com.example.jather.ui.theme.JatherTheme

@Composable
fun RoleSelector(
    isSpecialist: Boolean,
    onRoleSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RoleButton(
            text = stringResource(R.string.login_role_client),
            selected = !isSpecialist,
            onClick = { onRoleSelected(false) },
            modifier = Modifier.weight(1f)
        )
        RoleButton(
            text = stringResource(R.string.login_role_specialist),
            selected = isSpecialist,
            onClick = { onRoleSelected(true) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun RoleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) JathrPrimary else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (selected) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
private fun RoleSelectorPreview() {
    JatherTheme {
        RoleSelector(isSpecialist = false, onRoleSelected = {})
    }
}
