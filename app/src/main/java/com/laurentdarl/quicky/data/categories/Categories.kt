package com.laurentdarl.quicky.data.categories

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Categories(
    val breakfast: String = "breakfast",
    val pastries: String = "pastries",
    val meats: String = "meats",
    val fries: String = "fries",
    val grills: String = "grills",
    val drinks: String = "drinks",
    val desserts: String = "desserts",
    val soups: String = "soups",
    val cakes: String = "cakes",
    val events: String = "events"
): Parcelable