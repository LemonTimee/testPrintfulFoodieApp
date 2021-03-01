package com.printful.foodie.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Food(

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,

    @ColumnInfo(name = "calorie")
    @SerializedName("calorie")
    val calorie: String?,

    @ColumnInfo(name = "carbohydrate")
    @SerializedName("carbohydrate")
    val carbohydrate: String?,

    @ColumnInfo(name = "protein")
    @SerializedName("protein")
    val protein: String?,

    @ColumnInfo(name = "fat")
    @SerializedName("fat")
    val fat: String?,

    @ColumnInfo(name = "image")
    @SerializedName("image")
    val image: String?
) {

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}