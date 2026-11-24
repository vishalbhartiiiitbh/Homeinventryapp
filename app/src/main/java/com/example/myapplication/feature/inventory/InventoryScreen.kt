package com.example.myapplication.feature.inventory

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.core.ui.components.CustomSearchBar
import com.example.myapplication.core.ui.components.StatusBadge
import com.example.myapplication.data.model.InventoryItemModel
import com.example.myapplication.data.model.ProductCategory
import com.example.myapplication.data.model.SampleDataProvider
import com.example.myapplication.ui.theme.LightBlue
import com.example.myapplication.ui.theme.LightGreen
import com.example.myapplication.ui.theme.LightRed
import com.example.myapplication.ui.theme.LightYellow
import com.example.myapplication.ui.theme.PrimaryGreen
import com.example.myapplication.ui.theme.StatusDanger
import com.example.myapplication.ui.theme.StatusWarning

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen() {
    var itemsList by remember { mutableStateOf(SampleDataProvider.sampleItems) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf("All") }
    var selectedCategoryFilter by remember { mutableStateOf("All") }
    var selectedStatusFilter by remember { mutableStateOf("All") }

    var showAddItemDialog by remember { mutableStateOf(false) }
    var selectedItemForDetail by remember { mutableStateOf<InventoryItemModel?>(null) }
    var itemToConsume by remember { mutableStateOf<InventoryItemModel?>(null) }

    val locationsList = listOf("All") + SampleDataProvider.sampleLocations.map { it.name }
    val statusFilters = listOf("All", "Low Stock", "Expiring Soon", "Expired")

    // Filter Logic
    val filteredItems = itemsList.filter { item ->
        val matchesSearch = item.name.contains(searchQuery, ignoreCase = true) ||
                item.brand.contains(searchQuery, ignoreCase = true) ||
                item.location.contains(searchQuery, ignoreCase = true) ||
                item.category.displayName.contains(searchQuery, ignoreCase = true)

        val matchesLocation = selectedLocation == "All" || item.location == selectedLocation
        val matchesCategory = selectedCategoryFilter == "All" || item.category.group == selectedCategoryFilter || item.category.displayName == selectedCategoryFilter

        val matchesStatus = when (selectedStatusFilter) {
            "Low Stock" -> item.isLowStock
            "Expiring Soon" -> item.isExpiringSoon
            "Expired" -> item.isExpired
            else -> true
        }

        matchesSearch && matchesLocation && matchesCategory && matchesStatus
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddItemDialog = true },
                containerColor = PrimaryGreen,
                contentColor = androidx.compose.ui.graphics.Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Inventory Item")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Header Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Inventory",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${filteredItems.size} items listed",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Search bar
            CustomSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                placeholder = "Search items, brands, locations..."
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Filter Chips Horizontal Row: Locations & Status
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Text(
                        text = "Location:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                items(locationsList) { loc ->
                    FilterChip(
                        selected = selectedLocation == loc,
                        onClick = { selectedLocation = loc },
                        label = { Text(loc, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryGreen,
                            selectedLabelColor = androidx.compose.ui.graphics.Color.White
                        )
                    )
                }
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Text(
                        text = "Status:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                items(statusFilters) { status ->
                    FilterChip(
                        selected = selectedStatusFilter == status,
                        onClick = { selectedStatusFilter = status },
                        label = { Text(status, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = when(status) {
                                "Low Stock" -> StatusWarning
                                "Expiring Soon", "Expired" -> StatusDanger
                                else -> PrimaryGreen
                            },
                            selectedLabelColor = androidx.compose.ui.graphics.Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Inventory Item Cards List
            if (filteredItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Outlined.Inventory2,
                            contentDescription = "Empty",
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No inventory items found",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredItems) { item ->
                        InventoryItemCard(
                            item = item,
                            onClick = { selectedItemForDetail = item },
                            onConsumeClick = { itemToConsume = item }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }

    // Add Item Modal Dialog
    if (showAddItemDialog) {
        AddItemModal(
            onDismiss = { showAddItemDialog = false },
            onSave = { newItem ->
                itemsList = listOf(newItem) + itemsList
                showAddItemDialog = false
            }
        )
    }

    // Item Details Modal Dialog
    selectedItemForDetail?.let { item ->
        ItemDetailModal(
            item = item,
            onDismiss = { selectedItemForDetail = null },
            onDelete = {
                itemsList = itemsList.filter { it.id != item.id }
                selectedItemForDetail = null
            },
            onConsume = {
                itemToConsume = item
                selectedItemForDetail = null
            }
        )
    }

    // Consume Stock Dialog
    itemToConsume?.let { item ->
        ConsumeStockModal(
            item = item,
            onDismiss = { itemToConsume = null },
            onConfirm = { consumedAmount ->
                itemsList = itemsList.map {
                    if (it.id == item.id) {
                        it.copy(quantity = (it.quantity - consumedAmount).coerceAtLeast(0.0))
                    } else it
                }
                itemToConsume = null
            }
        )
    }
}

@Composable
fun InventoryItemCard(
    item: InventoryItemModel,
    onClick: () -> Unit,
    onConsumeClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${item.brand} • ${item.category.displayName}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    if (item.isExpired) {
                        StatusBadge("Expired", LightRed, StatusDanger)
                    } else if (item.isExpiringSoon) {
                        StatusBadge("Expiring Soon", LightRed, StatusDanger)
                    }
                    if (item.isLowStock) {
                        StatusBadge("Low Stock", LightYellow, StatusWarning)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Quantity: ${item.quantity} ${item.unit}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryGreen
                    )
                    Text(
                        text = "Location: ${item.location} | Expiry: ${item.expiryDate}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                OutlinedButton(
                    onClick = onConsumeClick,
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryGreen)
                ) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = "Consume", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Use", fontSize = 12.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemModal(
    onDismiss: () -> Unit,
    onSave: (InventoryItemModel) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(ProductCategory.RICE) }
    var quantity by remember { mutableStateOf("1.0") }
    var unit by remember { mutableStateOf("kg") }
    var minStock by remember { mutableStateOf("2.0") }
    var location by remember { mutableStateOf("Pantry") }
    var price by remember { mutableStateOf("0.0") }
    var notes by remember { mutableStateOf("") }

    val units = listOf("kg", "g", "litre", "ml", "pieces", "packets", "boxes", "bottles", "cans", "rolls")
    val locations = listOf("Pantry", "Kitchen", "Fridge", "Freezer", "Bathroom", "Store Room")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add New Inventory Item", fontWeight = FontWeight.Bold) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Item Name *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = brand,
                    onValueChange = { brand = it },
                    label = { Text("Brand") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        label = { Text("Quantity") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = unit,
                        onValueChange = { unit = it },
                        label = { Text("Unit") },
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = minStock,
                        onValueChange = { minStock = it },
                        label = { Text("Min Stock") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Location") },
                        modifier = Modifier.weight(1f)
                    )
                }
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Purchase Price ($)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes / Shelf details") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val newItem = InventoryItemModel(
                            id = System.currentTimeMillis().toString(),
                            name = name,
                            brand = brand.ifBlank { "Generic" },
                            category = category,
                            quantity = quantity.toDoubleOrNull() ?: 1.0,
                            unit = unit,
                            minStock = minStock.toDoubleOrNull() ?: 1.0,
                            location = location.ifBlank { "Pantry" },
                            purchasePrice = price.toDoubleOrNull() ?: 0.0,
                            notes = notes
                        )
                        onSave(newItem)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text("Save Item")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun ItemDetailModal(
    item: InventoryItemModel,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onConsume: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = item.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = StatusDanger)
                }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Brand: ${item.brand}", fontWeight = FontWeight.Medium)
                Text("Category: ${item.category.displayName}")
                Text("Storage Location: ${item.location}")
                Text("Current Quantity: ${item.quantity} ${item.unit}", color = PrimaryGreen, fontWeight = FontWeight.Bold)
                Text("Minimum Stock Threshold: ${item.minStock} ${item.unit}")
                Text("Expiry Date: ${item.expiryDate}")
                if (item.notes.isNotBlank()) {
                    Text("Notes: ${item.notes}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConsume,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text("Use / Consume Stock")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Close") }
        }
    )
}

@Composable
fun ConsumeStockModal(
    item: InventoryItemModel,
    onDismiss: () -> Unit,
    onConfirm: (Double) -> Unit
) {
    var consumeAmount by remember { mutableStateOf("1.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Consume Stock: ${item.name}") },
        text = {
            Column {
                Text("Current Available: ${item.quantity} ${item.unit}")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = consumeAmount,
                    onValueChange = { consumeAmount = it },
                    label = { Text("Quantity to Consume") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amount = consumeAmount.toDoubleOrNull() ?: 1.0
                    onConfirm(amount)
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text("Confirm Consumption")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
