package com.example.myapplication.data.model

import com.example.myapplication.core.utils.DateUtils

data class StorageLocationModel(
    val id: String,
    val name: String,
    val totalItems: Int,
    val expiringItems: Int,
    val iconResName: String = "location"
)

enum class ProductCategory(val displayName: String, val group: String) {
    RICE("Rice", "Food"),
    WHEAT_FLOUR("Wheat Flour", "Food"),
    SUGAR("Sugar", "Food"),
    SALT("Salt", "Food"),
    PULSES("Pulses", "Food"),
    COOKING_OIL("Cooking Oil", "Food"),
    MILK("Milk", "Food"),
    VEGETABLES("Vegetables", "Food"),
    FRUITS("Fruits", "Food"),
    SNACKS("Snacks", "Food"),
    SPICES("Spices", "Food"),
    OTHER_FOOD("Other Food", "Food"),

    DETERGENTS("Detergents", "Household"),
    SOAP("Soap", "Household"),
    SHAMPOO("Shampoo", "Household"),
    TOOTHPASTE("Toothpaste", "Household"),
    CLEANING_SUPPLIES("Cleaning Supplies", "Household"),
    BATTERIES("Batteries", "Household"),
    BULBS("Bulbs", "Household"),
    STATIONERY("Stationery", "Household"),
    KITCHEN_SUPPLIES("Kitchen Supplies", "Household"),
    TOOLS("Tools", "Household"),
    ELECTRONICS("Electronics", "Household"),
    APPLIANCES("Appliances", "Household"),
    OTHER_HOUSEHOLD("Other Household", "Household")
}

data class InventoryItemModel(
    val id: String,
    val name: String,
    val brand: String,
    val category: ProductCategory,
    val quantity: Double,
    val unit: String,
    val minStock: Double,
    val bufferStock: Double = 0.0,
    val location: String,
    val purchasePrice: Double = 0.0,
    val purchaseDate: String = DateUtils.today(),
    val expiryDate: String = DateUtils.addMonths(6),
    val openedDate: String? = null,
    val useWithinDays: Int? = null,
    val notes: String = "",
    val barcode: String = ""
) {
    val isLowStock: Boolean get() = quantity <= minStock
    val isExpiringSoon: Boolean get() = DateUtils.isExpiringSoon(expiryDate, 7)
    val isExpired: Boolean get() = DateUtils.isExpired(expiryDate)
}

data class ShoppingItemModel(
    val id: String,
    val name: String,
    val category: ProductCategory,
    val quantity: Double,
    val unit: String,
    val isPurchased: Boolean = false,
    val estimatedPrice: Double = 0.0,
    val notes: String = ""
)

enum class ReminderType { EXPIRY, REFILL, MAINTENANCE, TASK }
enum class ReminderPriority { LOW, MEDIUM, HIGH }

data class ReminderItemModel(
    val id: String,
    val title: String,
    val description: String,
    val type: ReminderType,
    val dueDate: String,
    val priority: ReminderPriority = ReminderPriority.MEDIUM,
    val isCompleted: Boolean = false,
    val relatedItemName: String? = null
)

object SampleDataProvider {
    val sampleLocations = listOf(
        StorageLocationModel("loc_1", "Pantry", 24, 2),
        StorageLocationModel("loc_2", "Kitchen", 18, 1),
        StorageLocationModel("loc_3", "Fridge", 14, 3),
        StorageLocationModel("loc_4", "Freezer", 8, 0),
        StorageLocationModel("loc_5", "Bathroom", 12, 0),
        StorageLocationModel("loc_6", "Store Room", 9, 1),
        StorageLocationModel("loc_7", "Custom Location", 4, 0)
    )

    val sampleItems = listOf(
        InventoryItemModel(
            id = "1",
            name = "Basmati Rice",
            brand = "Royal",
            category = ProductCategory.RICE,
            quantity = 2.0,
            unit = "kg",
            minStock = 5.0,
            bufferStock = 2.0,
            location = "Pantry",
            purchasePrice = 14.99,
            expiryDate = DateUtils.addMonths(8),
            notes = "Shelf 2, Box A"
        ),
        InventoryItemModel(
            id = "2",
            name = "Whole Milk",
            brand = "Dairy Fresh",
            category = ProductCategory.MILK,
            quantity = 1.0,
            unit = "litre",
            minStock = 2.0,
            bufferStock = 1.0,
            location = "Fridge",
            purchasePrice = 3.49,
            expiryDate = DateUtils.addDays(2),
            openedDate = DateUtils.addDays(-1),
            useWithinDays = 3,
            notes = "Top shelf"
        ),
        InventoryItemModel(
            id = "3",
            name = "AA Alkaline Batteries",
            brand = "Duracell",
            category = ProductCategory.BATTERIES,
            quantity = 8.0,
            unit = "pieces",
            minStock = 4.0,
            bufferStock = 2.0,
            location = "Store Room",
            purchasePrice = 11.99,
            expiryDate = DateUtils.addYears(5),
            notes = "Drawer 3"
        ),
        InventoryItemModel(
            id = "4",
            name = "Olive Oil Extra Virgin",
            brand = "Borges",
            category = ProductCategory.COOKING_OIL,
            quantity = 0.5,
            unit = "litre",
            minStock = 1.0,
            bufferStock = 0.5,
            location = "Kitchen",
            purchasePrice = 18.50,
            expiryDate = DateUtils.addDays(-5),
            notes = "Spice rack"
        ),
        InventoryItemModel(
            id = "5",
            name = "Laundry Detergent",
            brand = "Tide",
            category = ProductCategory.DETERGENTS,
            quantity = 3.5,
            unit = "kg",
            minStock = 2.0,
            bufferStock = 1.0,
            location = "Bathroom",
            purchasePrice = 22.00,
            expiryDate = DateUtils.addYears(2),
            notes = "Under sink"
        )
    )

    val sampleShoppingItems = listOf(
        ShoppingItemModel("s1", "Basmati Rice 5kg", ProductCategory.RICE, 1.0, "packets", false, 14.99, "Low stock trigger"),
        ShoppingItemModel("s2", "Whole Milk 2L", ProductCategory.MILK, 2.0, "bottles", false, 6.98, "Expiring soon"),
        ShoppingItemModel("s3", "Dishwashing Liquid", ProductCategory.CLEANING_SUPPLIES, 1.0, "bottle", true, 4.50, "Purchased yesterday"),
        ShoppingItemModel("s4", "Fresh Tomatoes", ProductCategory.VEGETABLES, 1.5, "kg", false, 5.00, "For salad")
    )

    val sampleReminders = listOf(
        ReminderItemModel("r1", "Milk expiring tomorrow", "Use or replace Dairy Fresh milk", ReminderType.EXPIRY, DateUtils.addDays(1), ReminderPriority.HIGH, false, "Whole Milk"),
        ReminderItemModel("r2", "Refill Cooking Oil", "Olive oil stock is below minimum 1.0 L", ReminderType.REFILL, DateUtils.today(), ReminderPriority.HIGH, false, "Olive Oil Extra Virgin"),
        ReminderItemModel("r3", "Clean Water Filter", "Quarterly replacement of main filter unit", ReminderType.MAINTENANCE, DateUtils.addDays(4), ReminderPriority.MEDIUM, false),
        ReminderItemModel("r4", "Pay Electricity Bill", "Monthly utility bill due", ReminderType.TASK, DateUtils.addDays(6), ReminderPriority.MEDIUM, false)
    )
}
