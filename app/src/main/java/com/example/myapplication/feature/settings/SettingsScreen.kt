package com.example.myapplication.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.LightGreen
import com.example.myapplication.ui.theme.PrimaryGreen
import com.example.myapplication.ui.theme.StatusDanger

@Composable
fun SettingsScreen() {
    var expiryNotifEnabled by remember { mutableStateOf(true) }
    var lowStockNotifEnabled by remember { mutableStateOf(true) }
    var maintenanceNotifEnabled by remember { mutableStateOf(true) }
    var appLockEnabled by remember { mutableStateOf(false) }

    var showBackupDialog by remember { mutableStateOf(false) }
    var showSyncDialog by remember { mutableStateOf(false) }
    var showClearDataDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Settings",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Manage your HomeStock household & application preferences",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Section 1: My Household
        item {
            SettingsSectionHeader(title = "My Household")
            SettingsCard {
                SettingsRow(
                    icon = Icons.Default.Home,
                    title = "Household Name",
                    subtitle = "My Sweet Home (ID: HS-8932)",
                    onClick = {}
                )
                SettingsRow(
                    icon = Icons.Default.People,
                    title = "Household Members",
                    subtitle = "3 members (Owner: You)",
                    onClick = {}
                )
            }
        }

        // Section 2: Backup and Synchronization
        item {
            SettingsSectionHeader(title = "Backup & Synchronization")
            SettingsCard {
                SettingsRow(
                    icon = Icons.Default.CloudSync,
                    title = "Google Drive Backup",
                    subtitle = "Last backup: Today, 08:30 AM",
                    onClick = { showBackupDialog = true }
                )
                SettingsRow(
                    icon = Icons.Default.CloudSync,
                    title = "Multi-Device Sync",
                    subtitle = "Sync status: Up to date (2 devices)",
                    onClick = { showSyncDialog = true }
                )
            }
        }

        // Section 3: Notifications
        item {
            SettingsSectionHeader(title = "Notifications")
            SettingsCard {
                SettingsSwitchRow(
                    icon = Icons.Default.Notifications,
                    title = "Expiry Alerts",
                    subtitle = "Get notified before items expire",
                    checked = expiryNotifEnabled,
                    onCheckedChange = { expiryNotifEnabled = it }
                )
                SettingsSwitchRow(
                    icon = Icons.Default.Notifications,
                    title = "Low Stock Reminders",
                    subtitle = "Alert when quantity drops below minimum",
                    checked = lowStockNotifEnabled,
                    onCheckedChange = { lowStockNotifEnabled = it }
                )
                SettingsSwitchRow(
                    icon = Icons.Default.Notifications,
                    title = "Maintenance Tasks",
                    subtitle = "Reminders for home appliances & tasks",
                    checked = maintenanceNotifEnabled,
                    onCheckedChange = { maintenanceNotifEnabled = it }
                )
            }
        }

        // Section 4: Security & Privacy
        item {
            SettingsSectionHeader(title = "Security & Privacy")
            SettingsCard {
                SettingsSwitchRow(
                    icon = Icons.Default.Lock,
                    title = "App Lock",
                    subtitle = "Require biometric / PIN authentication",
                    checked = appLockEnabled,
                    onCheckedChange = { appLockEnabled = it }
                )
                SettingsRow(
                    icon = Icons.Default.Security,
                    title = "Backup Encryption",
                    subtitle = "AES-256-GCM encrypted local archive",
                    onClick = {}
                )
                SettingsRow(
                    icon = Icons.Default.Info,
                    title = "Clear Local Data",
                    subtitle = "Reset local inventory database",
                    onClick = { showClearDataDialog = true },
                    textColor = StatusDanger
                )
            }
        }

        // Section 5: About HomeStock
        item {
            SettingsSectionHeader(title = "About HomeStock")
            SettingsCard {
                SettingsRow(
                    icon = Icons.Default.Info,
                    title = "Version",
                    subtitle = "HomeStock v1.0.0 (Production Build)",
                    onClick = {}
                )
                SettingsRow(
                    icon = Icons.Default.Palette,
                    title = "Appearance Theme",
                    subtitle = "Follow System Default",
                    onClick = {}
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    if (showBackupDialog) {
        AlertDialog(
            onDismissRequest = { showBackupDialog = false },
            title = { Text("Google Drive Backup", fontWeight = FontWeight.Bold) },
            text = { Text("Encrypt and backup all inventory data, stock history, and shopping lists to your Google Drive storage.") },
            confirmButton = {
                Button(
                    onClick = { showBackupDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text("Backup Now")
                }
            },
            dismissButton = {
                TextButton(onClick = { showBackupDialog = false }) { Text("Cancel") }
            }
        )
    }

    if (showSyncDialog) {
        AlertDialog(
            onDismissRequest = { showSyncDialog = false },
            title = { Text("Multi-Device Sync", fontWeight = FontWeight.Bold) },
            text = { Text("Synchronize pending operations with household devices using encrypted operation log on Google Drive.") },
            confirmButton = {
                Button(
                    onClick = { showSyncDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text("Sync Now")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSyncDialog = false }) { Text("Close") }
            }
        )
    }

    if (showClearDataDialog) {
        AlertDialog(
            onDismissRequest = { showClearDataDialog = false },
            title = { Text("Clear Local Data?", fontWeight = FontWeight.Bold, color = StatusDanger) },
            text = { Text("This action will reset your local database and cannot be undone unless you have a recent backup.") },
            confirmButton = {
                Button(
                    onClick = { showClearDataDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = StatusDanger)
                ) {
                    Text("Clear Everything")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDataDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = PrimaryGreen,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun SettingsCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            content()
        }
    }
}

@Composable
fun SettingsRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    textColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(LightGreen, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = textColor
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SettingsSwitchRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(LightGreen, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = PrimaryGreen)
        )
    }
}
