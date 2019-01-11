package com.example.martin.meteorlist.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "meteorite")
@Parcelize
data class Meteorite(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("mass")
    val mass: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("reclat")
    val reclat: Double,
    @SerializedName("reclong")
    val reclong: Double,
    @SerializedName("year")
    val year: String
) : Parcelable