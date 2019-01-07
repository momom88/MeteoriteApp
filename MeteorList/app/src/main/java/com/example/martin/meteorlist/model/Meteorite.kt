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
    @Expose
    val id: Int,
    @SerializedName("mass")
    @Expose
    val mass: Double,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("reclat")
    @Expose
    val reclat: Double,
    @SerializedName("reclong")
    @Expose
    val reclong: Double,
    @SerializedName("year")
    @Expose
    val year: String
) : Parcelable