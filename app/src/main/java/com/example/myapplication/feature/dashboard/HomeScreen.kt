package com.example.myapplication.feature.dashboard

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.core.ui.components.QuickActionButton
import com.example.myapplication.core.ui.components.SectionHeader
import com.example.myapplication.core.ui.components.StatusBadge
import com.example.myapplication.core.ui.components.StorageLocationCard
import com.example.myapplication.core.ui.components.SummaryCard
import com.example.myapplication.data.model.InventoryItemModel
import com.example.myapplication.data.model.ReminderItemModel
import com.example.myapplication.data.model.SampleDataProvider
import com.example.myapplication.ui.theme.LightBlue
import com.example.myapplication.ui.theme.LightGreen
import com.example.myapplication.ui.theme.LightRed
import com.example.myapplication.ui.theme.LightYellow
import com.example.myapplication.ui.theme.PrimaryGreen
import com.example.myapplication.ui.theme.StatusDanger
import com.example.myapplication.ui.theme.StatusWarning

@Composable
fun HomeScreen(
    onNavigateToInventory: () -> Unit = {},
    onNavigateToShopping: () -> Unit = {},
    onNavigateToReminders: () -> Unit = {},
    onAddItemClick: () -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    val items = SampleDataProvider.sampleItems
    val locations = SampleDataProvider.sampleLocations
    val shoppingItems = SampleDataProvider.sampleShoppingItems
    val reminders = SampleDataProvider.sampleReminders

    val totalItemsCount = items.size
    val lowStockCount = items.count { it.isLowStock }
    val expiringCount = items.count { it.isExpiringSoon || it.isExpired }
    val pendingShoppingCount = shoppingItems.count { !it.isPurchased }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header Area
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(PrimaryGreen, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "HS",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Good Morning 👋",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "HomeStock",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                BadgedBox(
                    badge = {
                        if (reminders.isNotEmpty()) {
                            Badge { Text("${reminders.size}") }
                        }
                    }
                ) {
                    IconButton(onClick = onNavigateToReminders) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
            Text(
                text = "Your household at a glance",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
        }

        // Summary Cards 2x2 Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SummaryCard(
                        title = "Total Items",
                        count = "$totalItemsCount",
                        icon = Icons.Outlined.Inventory2,
                        containerColor = LightGreen,
                        contentColor = PrimaryGreen,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToInventory
                    )
                    SummaryCard(
                        title = "Low Stock",
                        count = "$lowStockCount",
                        icon = Icons.Default.Warning,
                        containerColor = LightYellow,
                        contentColor = StatusWarning,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToInventory
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SummaryCard(
                        title = "Expiring Soon",
                        count = "$expiringCount",
                        icon = Icons.Default.AddAlert,
                        containerColor = LightRed,
                        contentColor = StatusDanger,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToInventory
                    )
                    SummaryCard(
                        title = "Shopping Items",
                        count = "$pendingShoppingCount",
                        icon = Icons.Default.Checklist,
                        containerColor = LightBlue,
                        contentColor = Color(0xFF0288D1),
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToShopping
                    )
                }
            }
        }

        // Quick Actions
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Quick Actions",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuickActionButton(
                            title = "Add Item",
                            icon = Icons.Default.Add,
                            containerColor = LightGreen,
                            contentColor = PrimaryGreen,
                            onClick = onAddItemClick
                        )
                        QuickActionButton(
                            title = "Search",
                            icon = Icons.Default.Search,
                            containerColor = LightBlue,
                            contentColor = Color(0xFF0288D1),
                            onClick = onSearchClick
                        )
                        QuickActionButton(
                            title = "Shopping",
                            icon = Icons.Default.LocalGroceryStore,
                            containerColor = LightYellow,
                            contentColor = StatusWarning,
                            onClick = onNavigateToShopping
                        )
                        QuickActionButton(
                            title = "Reminder",
                            icon = Icons.Default.Notifications,
                            containerColor = LightRed,
                            contentColor = StatusDanger,
                            onClick = onNavigateToReminders
                        )
                    }
                }
            }
        }

        // Storage Locations Section
        item {
            SectionHeader(
                title = "Storage Locations",
                actionText = "View All",
                onActionClick = onNavigateToInventory
            )
        }

        items(locations.take(4)) { location ->
            StorageLocationCard(
                name = location.name,
                totalItems = location.totalItems,
                expiringItems = location.expiringItems,
                icon = Icons.Default.Kitchen,
                onClick = onNavigateToInventory
            )
        }

        // Recently Added & Low Stock Section
        item {
            SectionHeader(
                title = "Low Stock Alerts",
                actionText = "View All",
                onActionClick = onNavigateToInventory
            )
        }

        items(items.filter { it.isLowStock }) { item ->
            HomeItemRow(item = item, onClick = onNavigateToInventory)
        }

        // Upcoming Reminders & Tasks
        item {
            SectionHeader(
                title = "Upcoming Tasks & Reminders",
                actionText = "View All",
                onActionClick = onNavigateToReminders
            )
        }

        items(reminders.take(3)) { reminder ->
            HomeReminderRow(reminder = reminder, onClick = onNavigateToReminders)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun HomeItemRow(
    item: InventoryItemModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${item.location} • ${item.quantity} ${item.unit} (Min: ${item.minStock} ${item.unit})",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (item.isLowStock) {
                StatusBadge(
                    text = "Low Stock",
                    backgroundColor = LightYellow,
                    textColor = StatusWarning
                )
            }
        }
    }
}

@Composable
fun HomeReminderRow(
    reminder: ReminderItemModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = reminder.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Due: ${reminder.dueDate} • ${reminder.type.name}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            StatusBadge(
                text = reminder.priority.name,
                backgroundColor = if (reminder.priority == com.example.myapplication.data.model.ReminderPriority.HIGH) LightRed else LightBlue,
                textColor = if (reminder.priority == com.example.myapplication.data.model.ReminderPriority.HIGH) StatusDanger else Color(0xFF0288D1)
            )
        }
    }
}
