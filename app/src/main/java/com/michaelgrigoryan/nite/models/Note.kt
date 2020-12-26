package com.michaelgrigoryan.nite.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize @Entity
data class Note(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "heading") var heading: String?,
        @ColumnInfo(name = "note") val note: String?,
        @ColumnInfo(name = "time") val time: String
): Parcelable

