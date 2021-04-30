package com.example.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "images")
@Parcelize
data class ImageModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val urlPhoto: String
) : Parcelable

typealias ListImages = List<ImageModel>?
